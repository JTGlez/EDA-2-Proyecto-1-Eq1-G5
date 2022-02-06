/*
* Estructura de Datos y Algoritmos II - Proyecto #1: "Ordenamiento externo"
* Grupo: 05
* Equipo: 1
* Implementación del método por Polifase para el ordenamiento externo.
 */
package edaiiproyecto1v2;

import java.io.*;
import java.util.*;

/**
 * Integrantes del equipo:
 *
 * @autor: Marco Antonio López Lara
 * @autor: Olivera Martínez Jenyffer Brigitte
 * @autor: Téllez González Jorge Luis
 */
public class Polifase {

    //Rutas de acceso a las carpetas.
    static String rutaArchivo = "./Archivos/";
    static String rutaAuxiliar = "./Archivos/Polifase/";

    //Buffers de lectura para los archivos.
    BufferedReader lectorF23;
    BufferedReader lectorF01;

    //Buffers de escritura para los archivos.
    BufferedWriter escritorF01;
    BufferedWriter escritorF23;
    BufferedWriter bufferClave;

    //FileWriter para la inserción de caracteres en F0.
    FileWriter escritorDeClaves;

    //Arreglos auxiliares en la recuperación de claves.
    ArrayList<ArrayList<String>> lecturaDF0;
    ArrayList<Integer> arrayF0;

    //Identificador para identificar y separar claves.
    StringTokenizer identificador;

    /**
     * Ejecuta el procedimiento inicial del ordenamiento Polifase al archivo
     * introducido como parámetro.
     *
     * @param nombreDelArchivo Nombre completo del archivo que se desea ordenar
     * sin extensión.
     * @param tamañoDelBloque Cantidad de claves introducidas en cada bloque de
     * lectura: definido en 4 para propósitos de estabilidad en la
     * implmentación.
     * @param criterioDeOrdenamiento Criterio de ordenamiento seleccionado:
     * 1)Ascendente 2)Descendente
     * @throws java.io.IOException Error crítico de ejecución; asociado a la
     * lectura incorrecta sobre los ficheros.
     *
     */
    public void ordenamientoPolifase(String nombreDelArchivo, int tamañoDelBloque, int criterioDeOrdenamiento) throws IOException {

        Utilidades.resetArchivoAuxPolyphase("F0");
        Utilidades.resetArchivoAuxPolyphase("F1");
        Utilidades.resetArchivoAuxPolyphase("F2");
        Utilidades.resetArchivoAuxPolyphase("F3");

        System.out.println("\nIniciando la lectura de los bloques de archivos.\n");

        //Se obtiene el arreglo dinámico con las claves del archivo introducido.
        lecturaDF0 = Utilidades.scanSuperKeys(nombreDelArchivo);
        System.out.println(lecturaDF0);

        //Arreglo dinámico utilizado para seleccionar y almacenar las claves numéricas (Números de cuenta).
        arrayF0 = new ArrayList<>();

        for (int i = 0; i < lecturaDF0.size(); i++) {

            arrayF0.add(i, Integer.parseInt((lecturaDF0.get(i).get(2))));

        }

        System.out.println("\nLectura de claves obtenidas de acuerdo al campo seleccionado: ");
        System.out.println(arrayF0);

        System.out.println("\nConstruyendo un nuevo archivo con las claves a ordenar...\n");

        for (int i = 0; i < arrayF0.size(); i++) {

            appendKeyToFile("F0", arrayF0.get(i));

        }
        System.out.println("Las claves han sido vaciadas a un archivo inicial F0. Iniciando Polifase...\n");

        System.out.println("\nIniciando la lectura de los bloques de archivos.\n");

        String cadenaDeClaves;
        int numElementosProcesados = 0;
        int bloqueProcesado = 0;
        ArrayList<Integer> arrayAuxiliarF0 = new ArrayList<>();
        /*Nota: arrayF0 es equivalente. Sin embargo, no se encontró una forma factible de iterar sobre el mismo, por lo que se 
                prefirió utilizar un nuevo auxiliar y dejar al arrayF0 únicamente como auxiliar en la construcción de F0. */

        this.hubReader("F0", 0);
        this.hubWriter("F1", 1);
        this.hubWriter("F2", 2);

        try {

            while ((cadenaDeClaves = this.lectorF01.readLine()) != null) {

                StringTokenizer identificadorDeClaves = new StringTokenizer(cadenaDeClaves, ",");

                while (identificadorDeClaves.hasMoreTokens()) {

                    //Recuperación de las claves almacenadas en el archivo F0.
                    arrayAuxiliarF0.add(Integer.parseInt(identificadorDeClaves.nextToken()));
                    numElementosProcesados++;

                    if (numElementosProcesados == tamañoDelBloque) {
                        bloqueProcesado++;

                        System.out.println("\nSe ha cargado un bloque.");
                        System.out.println("Estado actual :" + arrayAuxiliarF0);
                        numElementosProcesados = 0;

                        System.out.println("Bloques procesados: " + bloqueProcesado);

                        //Bloques par.
                        if (bloqueProcesado % 2 == 0) {

                            String cadenaDeInsercion = "";

                            //Se almacenan las claves de cada bloque en una cadena.
                            for (Integer clave : arrayAuxiliarF0) {
                                cadenaDeInsercion = cadenaDeInsercion + clave + ",";

                            }

                            //Impresión del bloque convertido a cadena y ordenado. Posteriormente, se escribe en F2.
                            System.out.println("Bloque almacenado en F2: " + this.internalSortingArray(cadenaDeInsercion, criterioDeOrdenamiento));

                            this.escritorF23.write(this.internalSortingArray(cadenaDeInsercion, criterioDeOrdenamiento));
                            this.escritorF23.write("  ");

                            //Bloques impar.
                        } else {

                            String cadenaDeInsercion = "";

                            for (Integer clave : arrayAuxiliarF0) {
                                cadenaDeInsercion = cadenaDeInsercion + clave + ",";

                            }

                            System.out.println("Bloque almacenado en F1: " + this.internalSortingArray(cadenaDeInsercion, criterioDeOrdenamiento));

                            this.escritorF01.write(this.internalSortingArray(cadenaDeInsercion, criterioDeOrdenamiento));
                            this.escritorF01.write("  ");

                        }
                        arrayAuxiliarF0.clear();

                    }

                }

            }

            /*Verificación adicional sobre el arreglo auxiliar
                            en caso de que exista un bloque que no complete
                            los 4 elementos establecidos. */
            if (!arrayAuxiliarF0.isEmpty()) {

                System.out.println("\nSe ha cargado un bloque.");
                System.out.println("Estado actual :" + arrayAuxiliarF0);
                System.out.println("Bloques procesados: " + (bloqueProcesado + 1));

                //Si la lectura de bloques terminó en F2 con bloqueProcesado par, enviará el bloque restante a F1.
                if (bloqueProcesado % 2 == 0) {

                    String cadenaDeInsercion = "";
                    for (Integer clave : arrayAuxiliarF0) {

                        cadenaDeInsercion = cadenaDeInsercion + clave + ",";

                    }
                    System.out.println("Bloque almacenado en F1: " + this.internalSortingArray(cadenaDeInsercion, criterioDeOrdenamiento));

                    this.escritorF01.write(this.internalSortingArray(cadenaDeInsercion, criterioDeOrdenamiento));
                    this.escritorF01.write("  ");
                    
                //Si la lectura de bloques terminó en F1 con bloqueProcesado impar, enviará el bloque restante a F2.
                } else {

                    String cadenaDeInsercion = "";
                    for (Integer clave : arrayAuxiliarF0) {

                        cadenaDeInsercion = cadenaDeInsercion + clave + ",";

                    }
                    System.out.println("Bloque almacenado en F2: " + this.internalSortingArray(cadenaDeInsercion, criterioDeOrdenamiento));

                    this.escritorF23.write(this.internalSortingArray(cadenaDeInsercion, criterioDeOrdenamiento));
                    this.escritorF23.write("  ");

                }

            }

            //Se cierran todos los lectores y escritores utilizados.
            this.escritorF23.write("\n\n");
            this.escritorF01.write("\n\n");
            this.escritorF01.close();
            this.escritorF23.close();
            this.lectorF01.close();
            this.hubWriter("F0", 0);

        } catch (IOException | NumberFormatException e) {
            System.out.println("\nSe detectó un error en la lectura/escritura"
                    + " de los archivos. Verifique que el archivo con las "
                    + "claves originales se encuentre con el formato correcto.\n");
        }

        System.out.println("\nSe han creado los bloques iniciales existosamente. \n");
        System.out.println("Iniciando el proceso de intercalación... \n");
        this.mergingPolyphase(criterioDeOrdenamiento);

    }

    /**
     * Ejecuta el procedimiento de mezcla de los bloques de claves sobre
     * los archivos auxiliares.
     *
     * @param criterioDeOrdenamiento Criterio de ordenamiento seleccionado:
     * 1)Ascendente 2)Descendente.
     *
     */
    public void mergingPolyphase(int criterioDeOrdenamiento) {

        //Inicializa la lectura sobre los archivos F1 y F2.
        this.hubReader("F1", 0);
        this.hubReader("F2", 1);

        //Inicializa el proceso de escritura sobre los archivos F0 y F3.
        this.hubWriter("F0", 1);
        this.hubWriter("F3", 2);

        //Cadenas auxiliares utilizadas para recuperar los bloques en cada archivo.
        String cadenaAMezclar1, cadenaAMezclar2;

        int bloquesProcesados = -1;
        int lineaDeLecturaActual = 0;

        try {

            do {

                bloquesProcesados++;
                cadenaAMezclar1 = this.lecturaLinea(lineaDeLecturaActual, 0);
                cadenaAMezclar2 = this.lecturaLinea(lineaDeLecturaActual, 1);

                System.out.println("Bloque 1: " + cadenaAMezclar1);
                System.out.println("Bloque 2: " + cadenaAMezclar2);

                /*Arreglos auxiliares utilizados para almacenar y fusionar 
                los bloques de claves.*/
                String bloqueDeClavesF1[] = cadenaAMezclar1.split("  ");
                String bloqueDeClavesF2[] = cadenaAMezclar2.split("  ");

                /*Instrucción crítica en la ejecución: La naturaleza de Polifase
                implica la posibilidad de que el número de bloques en F1 sea mayor
                en una unidad a los bloques en F2; lo cual resultaría en una
                disparidad en el tamaño de ambos arreglos y, por tanto, 
                en un error fatal en la ejecución del ciclo for. Con la siguiente
                condicional, se asegura que tal situación nunca ocurra añadiendo
                un elemento adicional al arreglo de tal forma que el número de
                índices de ambos arreglos sea siempre el mismo.
                 */
                if (bloqueDeClavesF1.length > bloqueDeClavesF2.length) {

                    System.out.println("\nAlerta: Se ha detectado que uno de los archivos contiene más bloques que el otro.");
                    cadenaAMezclar2 = cadenaAMezclar2 + "!";
                    System.out.println("Se ha añadido un bloque adicional:\nBloque 2: " + cadenaAMezclar2);
                    bloqueDeClavesF2 = cadenaAMezclar2.split("  ");
                }

                for (int i = 0; i < bloqueDeClavesF1.length || i < bloqueDeClavesF2.length; i++) {

                    if (bloquesProcesados % 2 == 0) { //Cuenta las veces que se realiza el intercalado de bloques.

                        //Escritura sobre F0.
                        if (i % 2 == 0) {

                            this.escritorF01.write(this.internalSortingArray((bloqueDeClavesF1[i] + bloqueDeClavesF2[i]), criterioDeOrdenamiento));
                            System.out.println("\nBloque fusionado y añadido a F0: " + this.internalSortingArray((bloqueDeClavesF1[i] + bloqueDeClavesF2[i]), criterioDeOrdenamiento));
                            this.escritorF01.write("  ");

                            //Escritura sobre F3.
                        } else {

                            this.escritorF23.write(this.internalSortingArray((bloqueDeClavesF1[i] + bloqueDeClavesF2[i]), criterioDeOrdenamiento));
                            System.out.println("Bloque fusionado y añadido a F3: " + this.internalSortingArray((bloqueDeClavesF1[i] + bloqueDeClavesF2[i]), criterioDeOrdenamiento));
                            this.escritorF23.write("  ");
                        }

                    } else {

                        //Escritura sobre F1.
                        if (i % 2 == 0) {

                            this.escritorF01.write(this.internalSortingArray((bloqueDeClavesF1[i] + bloqueDeClavesF2[i]), criterioDeOrdenamiento));
                            System.out.println("\nBloque fusionado y añadido a F1: " + this.internalSortingArray((bloqueDeClavesF1[i] + bloqueDeClavesF2[i]), criterioDeOrdenamiento));
                            this.escritorF01.write("  ");

                            //Escritura sobre F2.
                        } else {

                            this.escritorF23.write(this.internalSortingArray((bloqueDeClavesF1[i] + bloqueDeClavesF2[i]), criterioDeOrdenamiento));
                            System.out.println("Bloque fusionado y añadido a F2: " + this.internalSortingArray((bloqueDeClavesF1[i] + bloqueDeClavesF2[i]), criterioDeOrdenamiento));
                            this.escritorF23.write("  ");
                        }

                    }

                }
                //Tras cada escritura de bloques se añaden 2 saltos de línea para mostrar cada iteración de Polifase.
                this.escritorF01.write("\n\n");
                this.escritorF23.write("\n\n");
                this.escritorF01.close();
                this.escritorF23.close();

                //Impresiones en pantalla del estado actual de la ejecución.
                if (bloquesProcesados % 2 == 0) {

                    //Detecta si una de las cadenas de bloques recuperadas ya no contienen más claves por fusionar y detiene la ejecución.
                    if (cadenaAMezclar1.equals("") || cadenaAMezclar2.equals("")) {
                        System.out.println("¡Felicidades! Los datos han sido ordenados.\n\nSaliendo de la opción...");
                        this.lectorF01.close();
                        this.lectorF23.close();
                        this.escritorF01.close();
                        this.escritorF23.close();
                        break;

                    }
                    System.out.println("\n\nLos archivos F0 y F3 contienen los siguientes bloques: ");
                    //Inicializa escritura para F1 y F2.
                    this.hubWriter("F1", 1);
                    this.hubWriter("F2", 2);

                    //Inicializa lectura para F0 y F3.
                    this.hubReader("F0", 0);
                    this.hubReader("F3", 1);

                } else {
                    System.out.println("\n\nLos archivos F1 y F2 contienen los siguientes bloques: ");
                    //Inicializa escritura para F0 y F3.
                    this.hubWriter("F0", 1);
                    this.hubWriter("F3", 2);

                    //Inicializa lectura para F1 y F2.
                    this.hubReader("F1", 0);
                    this.hubReader("F2", 1);
                    lineaDeLecturaActual += 2;

                }

            } while (true);

        } catch (IOException e) {

            System.out.println("\nSe detectó un error en la lectura/escritura"
                    + " de los archivos. Verifique que el archivo con las "
                    + "claves originales se encuentre con el formato correcto.\n");

        }

    }

    /**
     * Lee y recupera el contenido presente en la línea establecida como
     * parámetro de los archivos auxiliares de Polifase. Retorna el contenido
     * leído como una cadena.
     *
     *
     * @param numLineaDeLectura Línea del archivo auxiliar que se desea
     * recuperar.
     * @param opDeseada 1)Recupera líneas de F0 y F1. 2)Recupera líneas de F2 y
     * F3.
     * @return Lectura realizada convertida en una cadena manipulable.
     */
    public String lecturaLinea(int numLineaDeLectura, int opDeseada) {

        try {

            switch (opDeseada) {

                case 0:

                    for (int i = 0; i < numLineaDeLectura; i++) {
                        this.lectorF01.readLine();
                    }
                    String cadenaLeidaF01 = this.lectorF01.readLine();
                    return cadenaLeidaF01;

                case 1:

                    for (int i = 0; i < numLineaDeLectura; i++) {
                        this.lectorF23.readLine();
                    }
                    String cadenaLeidaF23 = this.lectorF23.readLine();
                    return cadenaLeidaF23;
            }

        } catch (IOException e) {

            System.out.println("\nSe detectó un error en la lectura/escritura"
                    + " de los archivos. Verifique que el archivo con las "
                    + "claves originales se encuentre con el formato correcto.\n");

        }

        System.out.println("Parámetro incorrecto.\n");
        return "";
    }

    /**
     * Método especializado en la creación de lectores de archivos (FileReader)
     * para los archivos generados por Polifase.
     *
     * @param nombreDelArchivo Nombre completo del archivo que se desea leer sin
     * extensión.
     * @param opDeseada Indica la operación a realizar por el hub. 0) Inicializa
     * un buffer de lectura para los archivos F0 y F1. 1)Inicializa un buffer de
     * lectura para los archivos F2 y F3.
     *
     */
    public void hubReader(String nombreDelArchivo, int opDeseada) {

        File archivoTarget = new File(Polifase.rutaAuxiliar + nombreDelArchivo + ".txt");
        FileReader lectorArchivo;

        try {

            lectorArchivo = new FileReader(archivoTarget);

            if (opDeseada == 0) {

                //Buffer de lectura dedicado a F0 y F1.
                this.lectorF01 = new BufferedReader(lectorArchivo);

            } else if (opDeseada == 1) {
                //Buffer de lectura dedicado a F2 y F3.
                this.lectorF23 = new BufferedReader(lectorArchivo);

            }

        } catch (FileNotFoundException e) {

            System.out.println("\nSe detectó un error de integridad"
                    + " en los archivos auxiliares. Verifique"
                    + " que otro programa no interfiera sobre la"
                    + " ejecución. \n");
        }

    }

    /**
     * Método especializado en la inicialización de objetos FileWriter y en la
     * creación de Buffers de Escritura (BufferedWriter) para los archivos F0,
     * F1, F2 y F3.
     *
     * @param nombreDelArchivo Nombre completo del archivo sobre el que se desea
     * escribir.
     * @param opDeseada Indica la operación a realizar por el hub: 0) Genera un
     * Buffer de escritura que escribirá información al inicio del archivo F2 o
     * F3. 1) Genera un Buffer de escritura que escribirá información al final
     * de los archivo F0 o F1. 2) Genera un Buffer de escritura que escribirá
     * información al final de los archivo F2 o F3.
     *
     */
    public void hubWriter(String nombreDelArchivo, int opDeseada) {

        File archivoTarget = new File(Polifase.rutaAuxiliar + nombreDelArchivo + ".txt");
        FileWriter escritorArchivo;

        try {

            switch (opDeseada) {

                case 0:

                    escritorArchivo = new FileWriter(archivoTarget, false);
                    this.escritorF23 = new BufferedWriter(escritorArchivo);

                    break;

                case 1:

                    escritorArchivo = new FileWriter(archivoTarget, true);
                    this.escritorF01 = new BufferedWriter(escritorArchivo);

                    break;

                case 2:

                    escritorArchivo = new FileWriter(archivoTarget, true);
                    this.escritorF23 = new BufferedWriter(escritorArchivo);

                    break;

            }

        } catch (IOException e) {

            System.out.println("\nSe detectó un error en la lectura/escritura"
                    + " de los archivos. Verifique que el archivo con las "
                    + "claves originales se encuentre con el formato correcto.\n");

        }

    }

    /**
     * Añade la clave introducida como parámetro en un archivo. En caso de que
     * el archivo "nombreArchivoTarget" no se encuentre en la carpeta
     * "Archivos", genera el archivo automáticamente.
     *
     * @param nombreArchivoTarget Nombre completo del archivo sobre el que sea
     * desea introducir una clave.
     * @param elementoClave Clave a introducir en el archivo.
     *
     */
    public void appendKeyToFile(String nombreArchivoTarget, int elementoClave) {

        try {
            File archivoFile = new File(Polifase.rutaAuxiliar + nombreArchivoTarget + ".txt");

            if (!archivoFile.exists()) {
                archivoFile.createNewFile();

            }
            escritorDeClaves = new FileWriter(archivoFile, true);
            bufferClave = new BufferedWriter(escritorDeClaves);
            bufferClave.append(elementoClave + ",");
            bufferClave.close();
            escritorDeClaves.close();

        } catch (IOException e) {
            System.out.println("\nError inesperado de lectura/escritura. "
                    + "Verifique la integridad del archivo.\n");
        }

    }

    /**
     * Recibe una cadena, la separa y almacena en un arreglo de cadenas que,
     * posteriormente, se transforma en un arreglo dinámico. Finalmente, se
     * ordena el arreglo con el método insertionSorting de Utilidades y el
     * resultado se vuelve a convertir en una cadena lista para insertarse sobre
     * un archivo.
     *
     * @param cadenaDeInsercion Cadena a ordenar.
     * @param tipoDeOrdenamiento Bajo qué criterio desea ordenarse el archivo:
     * 1) Ascendente 2) Descendente.
     * @return cadenaDeInsercion
     *
     */
    public String internalSortingArray(String cadenaDeInsercion, int tipoDeOrdenamiento) {

        cadenaDeInsercion = cadenaDeInsercion.replaceAll("!", " ");
        String[] arrayAux = cadenaDeInsercion.split(",");
        ArrayList<Integer> dynamicAux = new ArrayList<>();

        for (String arrayAux1 : arrayAux) {
            if (!arrayAux1.equals(" ")) {
                dynamicAux.add(Integer.parseInt(arrayAux1));
            }
        }

        dynamicAux = Utilidades.insertionSorting(dynamicAux, tipoDeOrdenamiento);
        cadenaDeInsercion = "";

        for (Integer clave : dynamicAux) {
            cadenaDeInsercion = cadenaDeInsercion + clave + ",";
        }

        return cadenaDeInsercion;
    }

}
