/*
* Estructura de Datos y Algoritmos II - Proyecto #1: "Ordenamiento externo"
* Grupo: 05
* Equipo: 1
* Implementación del método por Mezcla Natural para el ordenamiento externo.
* Versión adaptada a la lectura de claves no numéricas.
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
public class NaturalMixStrings extends NaturalMix {

    //Arreglo auxiliar en la recuperación de claves no numéricas.
    ArrayList<String> arrayF0S;

    //Arreglos auxiliares con las claves presentes en cada bloque. 
    ArrayList<String> arrayAuxiliarF0S;
    ArrayList<String> arrayAuxiliarF1S;
    ArrayList<String> arrayAuxiliarF2S;

    /**
     * Ejecuta el procedimiento inicial del ordenamiento por Mezcla Natural al
     * archivo introducido como parámetro.
     *
     * @param nombreDelArchivo Nombre completo del archivo que se desea ordenar
     * sin extensión.
     * @param criterioDeOrdenamiento Criterio de ordenamiento seleccionado:
     * 1)Ascendente 2)Descendente
     * @param campoDeOrdenamiento Campo que se desea ordenar de las claves:
     * 1)Nombre 2)Apellido.
     * @throws java.io.IOException Error crítico de ejecución; asociado a la
     * lectura incorrecta sobre los ficheros.
     *
     */
    public void mezclaNatural(String nombreDelArchivo, int criterioDeOrdenamiento, int campoDeOrdenamiento) throws IOException {

        Utilidades.resetArchivoAuxMix("F0");
        Utilidades.resetArchivoAuxMix("F1");
        Utilidades.resetArchivoAuxMix("F2");

        //Se obtiene el arreglo dinámico con las claves del archivo introducido.
        lecturaDF0 = Utilidades.scanSuperKeys(nombreDelArchivo);
        System.out.println(lecturaDF0);

        //Arreglo dinámico utilizado para seleccionar y almacenar las claves no numéricas (Nombre y apellido).
        arrayF0S = new ArrayList<>();

        for (int i = 0; i < lecturaDF0.size(); i++) {

            arrayF0S.add(i, (lecturaDF0.get(i).get(campoDeOrdenamiento - 1)));

        }

        System.out.println("\nLectura de claves obtenidas de acuerdo al campo seleccionado: ");
        System.out.println(arrayF0S);

        System.out.println("\nConstruyendo un nuevo archivo con las claves a ordenar...\n");

        for (int i = 0; i < arrayF0S.size(); i++) {

            appendKeyToFile("F0", arrayF0S.get(i));
        }

        System.out.println("Las claves han sido vaciadas a un archivo inicial F0. Iniciando el procesamiento de sub-claves..\n");

        //El contenido del arreglo generado es añadido como el primer bloque a procesar del archivo F0.
        arrayBlocksF0.add(arrayF0S);

        switch (criterioDeOrdenamiento) {

            case 1:

                //Mientras se detecte la presencia de una partición válida sobre F0, se realizará el proceso de Mezcla.
                while ((blockBuilderS((ArrayList<String>) arrayBlocksF0.get(lineaDeOperacion), criterioDeOrdenamiento)) == true) {
                    mixSort(archivoF0, criterioDeOrdenamiento);

                }
                System.out.println("\nNo se han detectado más particiones sobre F0.");
                System.out.println("¡Felicidades! Los datos han sido ordenados.\n\nSaliendo de la opción...\n");

                break;

            case 2:

                while ((blockBuilderS((ArrayList<String>) arrayBlocksF0.get(lineaDeOperacion), criterioDeOrdenamiento)) == true) {
                    mixSort(archivoF0, criterioDeOrdenamiento);

                }
                System.out.println("\nNo se han detectado más particiones sobre F0.");
                System.out.println("¡Felicidades! Los datos han sido ordenados.\n\nSaliendo de la opción...\n");

                break;

        }

    }

    /**
     * Genera las particiones sobre el archivo F0, previamente convertido a un
     * arreglo, y las almacena sobre los arreglos asociados a los bloques de
     * claves presentes en los archivos F1 y F2.
     *
     * @param arrayF0 Arreglo contenedor de las claves presentes en F0.
     * @param criterioDeOrdenamiento Criterio de ordenamiento seleccionado:
     * 1)Ascendente 2)Descendente.
     * @return
     */
    public boolean blockBuilderS(ArrayList<String> arrayF0, int criterioDeOrdenamiento) {

        //La primera partición generada será enviada siempre a F1.
        int bloqueActual = 1;
        //La variable booleana indica si se ha generado una partición válida sobre F0.
        boolean particionCreada = false;
        //Variables de control utilizadas en la comparación de claves.
        String claveActual, claveAnterior;

        //Se obtiene la primera clave presente en el arreglo de claves en F0.
        claveActual = arrayF0.get(0);

        //Inicialización de la lista de claves en F1.
        arrayAuxiliarF1S = new ArrayList<>();
        arrayAuxiliarF1S.add(claveActual);

        //Condición inicial para el primer elemento.
        claveAnterior = claveActual;

        switch (criterioDeOrdenamiento) {

            //Particiones ascendentes de máximo tamaño.
            case 1:

                /*Se obtienen las claves almacenadas en el arreglo y se comparan
                  con la clave anterior a su posición. Mientras la condición del
                  if sea válida, se almacenarán las claves sobre los arreglos
                  auxiliares. */
                for (int i = 1; i < arrayF0.size(); i++) {
                    claveActual = arrayF0.get(i);

                    if (claveActual.compareTo(claveAnterior) >= 0) {
                        claveAnterior = claveActual;

                        if (bloqueActual == 1) {
                            //Se escribe sobre F1.
                            arrayAuxiliarF1S.add(claveActual);

                        } else {
                            //Se escribe sobre F2.
                            arrayAuxiliarF2S.add(claveActual);
                        }

                        /*Cuando la condición del if se rompe, indica que se ha
                          detectado una clave que no cumple la secuencia
                          ascendente. Por tanto, se carga el primer bloque de
                          claves y se procede a generar el siguiente. */
                    } else {

                        claveAnterior = claveActual;
                        particionCreada = true;

                        /*Se añade la clave que no cumple el criterio ascendente
                          a F2 y el bloque anterior se carga sobre F1.*/
                        if (bloqueActual == 1) {

                            arrayAuxiliarF2S = new ArrayList<>();
                            arrayAuxiliarF2S.add(claveActual);
                            addBlockS(arrayAuxiliarF1S, bloqueActual);
                            //Se procede a generar una partición para F2.
                            bloqueActual = 2;

                            /*Se añade la clave que no cumple el criterio ascendente
                          a F1 y el bloque anterior se carga sobre F2.*/
                        } else {

                            arrayAuxiliarF1S = new ArrayList<>();
                            arrayAuxiliarF1S.add(claveActual);
                            addBlockS(arrayAuxiliarF2S, bloqueActual);
                            //Se procede a generar una partición para F1.
                            bloqueActual = 1;

                        }

                    }

                }

                /*Cualquier bloque restante es añadido a
                  a F1 y a F2, respectivamente. */
                if (bloqueActual == 1) {
                    addBlockS(arrayAuxiliarF1S, bloqueActual);

                } else {
                    addBlockS(arrayAuxiliarF2S, bloqueActual);

                }

                System.out.println("Particiones máximas generadas para F1: " + arrayBlocksF1);
                System.out.println("Particiones máximas generadas para F2: " + arrayBlocksF2);

                return particionCreada;

            //Particiones descendentes de máximo tamaño.
            case 2:

                for (int i = 1; i < arrayF0.size(); i++) {
                    claveActual = arrayF0.get(i);

                    if (claveActual.compareTo(claveAnterior) <= 0) {
                        claveAnterior = claveActual;

                        if (bloqueActual == 1) {

                            arrayAuxiliarF1S.add(claveActual);

                        } else {

                            arrayAuxiliarF2S.add(claveActual);
                        }

                    } else {

                        particionCreada = true;
                        claveAnterior = claveActual;

                        if (bloqueActual == 1) {

                            arrayAuxiliarF2S = new ArrayList<>();
                            arrayAuxiliarF2S.add(claveActual);
                            addBlockS(arrayAuxiliarF1S, bloqueActual);
                            bloqueActual = 2;

                        } else {

                            arrayAuxiliarF1S = new ArrayList<>();
                            arrayAuxiliarF1S.add(claveActual);
                            addBlockS(arrayAuxiliarF2S, bloqueActual);
                            bloqueActual = 1;

                        }

                    }

                }

                if (bloqueActual == 1) {
                    addBlockS(arrayAuxiliarF1S, bloqueActual);

                } else {
                    addBlockS(arrayAuxiliarF2S, bloqueActual);
                }

                System.out.println("Particiones máximas generadas para F1: " + arrayBlocksF1);
                System.out.println("Particiones máximas generadas para F2: " + arrayBlocksF2);

                return particionCreada;

        }

        return particionCreada;

    }

    /**
     * Realiza el proceso de escritura de los bloques generados por el método
     * blockBuilder sobre los archivos F1 Y F2. Posteriormente, mezcla los
     * bloques de ambos archivos con el método blockMerge y escribe el resultado
     * sobre el archivo F0.
     *
     * @param archivoF0 Archivo generado a partir del campo de ordenamiento
     * seleccionado.
     * @param criterioDeOrdenamiento Criterio de ordenamiento seleccionado:
     * 1)Ascendente 2)Descendente.
     */
    @Override
    public void mixSort(File archivoF0, int criterioDeOrdenamiento) {

        System.out.println("\nCargando los bloques leídos en F1...\n");

        /*Por medio de un ciclo for-each se recupera cada bloque almacenado
          en el arreglo de bloques correspondiente a F1 y se escriben sobre 
          el archivo. */
        arrayBlocksF1.forEach(
                (i) -> {
                    System.out.println("Bloque cargado exitosamente en F1: " + i);
                    appendBlockToFile(this.archivoF1, i, true);

                }
        );
        //Se escriben 2 saltos de línea en F1 para separar las iteraciones.
        saltoLinea(this.archivoF1, true);
        System.out.println("\nCargando los bloques leídos en F2...\n");

        /*Por medio de un ciclo for-each se recupera cada bloque almacenado
          en el arreglo de bloques correspondiente a F2 y se escriben sobre 
          el archivo. */
        arrayBlocksF2.forEach(
                (i) -> {
                    System.out.println("Bloque cargado exitosamente en F2: " + i);
                    appendBlockToFile(archivoF2, i, true);

                }
        );

        //Se escriben 2 saltos de línea en F0 y F2 para separar sus iteraciones.
        saltoLinea(this.archivoF2, true);
        saltoLinea(this.archivoF0, true);

        /*Se realiza un ciclo iterativo de mezcla de cada uno de los bloques 
          de claves presentes en los arreglos asociados a los bloques de claves
          presentes en los archivos F1 y F2. Este proceso es realizado hasta
          que se detecte que uno de los arreglos se encuentra vacío. El
          resultado se escribe sobre el archivo F0. */
        while (!arrayBlocksF1.isEmpty() && !arrayBlocksF2.isEmpty()) {

            appendBlockToFileS(archivoF0, blockMergeS(criterioDeOrdenamiento), true);

        }

        /*Cualquier bloque restante es añadido al archivo F0, de existir alguno.
          Una vez añadido, se elimina del arreglo correspondiente. */
        while (!arrayBlocksF1.isEmpty()) {

            appendBlockToFileS(archivoF0, this.arrayAuxiliarF1S, true);
            arrayBlocksF1.remove(0);
        }
        while (!arrayBlocksF2.isEmpty()) {
            appendBlockToFileS(archivoF0, this.arrayAuxiliarF2S, true);
            arrayBlocksF2.remove(0);
        }

        /*Se aumenta la línea de operación; tomando en cuenta los saltos de línea
        que separan las iteraciones del programa.*/
        this.lineaDeOperacion = lineaDeOperacion + 2;
        this.contadorIteraciones++;

        System.out.println("\nSe han añadido los bloques mezclados a F0 exitosamente.");
        System.out.println("Iteración realizada: " + (this.contadorIteraciones));
        //Limpieza del arreglo con los bloques en F0 con el de iniciar una nueva inspección a F0.
        arrayBlocksF0.clear();
        System.out.println("");
        fileToArray(archivoF0);

    }

    /**
     * Realiza el proceso de mezcla de los bloques de claves presentes en los
     * archivos F1 y F2 por medio de sus arreglos contenedores de bloques.
     *
     * @param criterioDeOrdenamiento Criterio de ordenamiento seleccionado:
     * 1)Ascendente 2)Descendente.
     * @return Arreglo de claves mezclado proveniente de F1 y F2.
     */
    public ArrayList<String> blockMergeS(int criterioDeOrdenamiento) {

        //Arreglos auxiliares utilizados durante la ejecución del método.
        ArrayList<String> mixKeysF1 = new ArrayList<>();
        ArrayList<String> mixKeysF2 = new ArrayList<>();

        //Variable usada para denotar cada clave procesada.
        String elementoActual;

        System.out.println("");
        System.out.println("Estado actual de F1: " + arrayBlocksF1);
        System.out.println("Estado actual de F2: " + arrayBlocksF2);
        System.out.println("");

        //Variables booleanas que indican si F1 o F2 tienen claves no vaciadas en su interior.
        boolean flagF1 = true, flagF2 = true;

        //Por medio de un ciclo for anidado, se extrae cada bloque de claves en F1 y sus respectivas subclaves.
        for (ArrayList blockKeysF1 : arrayBlocksF1) {

            if (flagF1 == true) {

                for (int i = 0; i < blockKeysF1.size(); i++) {

                    elementoActual = (String) blockKeysF1.get(i);
                    mixKeysF1.add(elementoActual);
                    flagF1 = false;
                }
            }
        }
        arrayBlocksF1.remove(0);

        //Por medio de un ciclo for anidado, se extrae cada bloque de claves en F1 y sus respectivas subclaves.
        for (ArrayList blockKeysF2 : arrayBlocksF2) {

            if (flagF2 == true) {

                for (int i = 0; i < blockKeysF2.size(); i++) {

                    elementoActual = (String) blockKeysF2.get(i);
                    mixKeysF2.add(elementoActual);
                    flagF2 = false;
                }
            }
        }
        arrayBlocksF2.remove(0);

        //Se procede a mezclar los arreglos auxiliares con las claves recuperadas.
        ArrayList<String> mixedArray = new ArrayList<>();

        for (String key : mixKeysF1) {
            mixedArray.add(key);
        }

        for (String key : mixKeysF2) {
            mixedArray.add(key);
        }

        //Se ordenan las claves presentes en el arreglo mezclado.
        if (criterioDeOrdenamiento == 1) {
            mixedArray = Utilidades.insertionSortingStrings(mixedArray, criterioDeOrdenamiento);

        } else {
            mixedArray = Utilidades.insertionSortingStrings(mixedArray, criterioDeOrdenamiento);

        }

        System.out.println("Bloque mezclado de claves generado: ");
        System.out.println(mixedArray);
        return mixedArray;

    }

    /**
     * Añade el bloque de claves recuperado por blockBuilder al arreglo de
     * bloques asociado a F1 o F2, respectivamente.
     *
     * @param arrayAuxiliar Bloque de claves recuperado por blockBuilder.
     * @param bloqueActual 1)Indica una operación de escritura sobre el arreglo
     * de bloques asociado al archivo F1. 2)Indica una operación de escritura
     * sobre el arreglo de bloques asociado el archivo F2.
     */
    public void addBlockS(ArrayList<String> arrayAuxiliar, int bloqueActual) {

        if (bloqueActual == 1) {
            this.arrayBlocksF1.add(arrayAuxiliar);

        } else {
            this.arrayBlocksF2.add(arrayAuxiliar);
        }

    }

    /**
     * Escribe un bloque de claves sobre el archivo seleccionado.
     *
     * @param archivoTarget Archivo sobre el que se desea operar.
     * @param bloqueClave Bloque de claves a escribir sobre el archivo.
     * @param opDeseada true indica una escritura al final del archivo. false
     * indica una escritura el inicio del archivo.
     */
    public void appendBlockToFileS(File archivoTarget, ArrayList<String> bloqueClave, boolean opDeseada) {

        try {

            escritor = new FileWriter(archivoTarget, opDeseada);
            bufferEscritor = new BufferedWriter(escritor, 1);

            for (int i = 0; i < bloqueClave.size(); i++) {

                bufferEscritor.append(bloqueClave.get(i) + ",");

            }
            bufferEscritor.close();
            escritor.close();

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
    public void appendKeyToFile(String nombreArchivoTarget, String elementoClave) {

        try {
            File archivoFile = new File(NaturalMix.rutaAuxiliar + nombreArchivoTarget + ".txt");

            if (!archivoFile.exists()) {
                archivoFile.createNewFile();

            }
            escritorDeClaves = new FileWriter(archivoFile, true);
            bufferEscritor = new BufferedWriter(escritorDeClaves);
            bufferEscritor.append(elementoClave + ",");
            bufferEscritor.close();
            escritorDeClaves.close();

        } catch (IOException e) {
            System.out.println("\nError inesperado de lectura/escritura. "
                    + "Verifique la integridad del archivo.\n");
        }

    }

    /**
     * Recupera las claves presentes en un archivo y las almacena en un arreglo.
     * Este método está asociado a la lectura y recuperación de claves sobre F0
     * y no se prevee su uso sobre otro archivo.
     *
     * @param archivoF0 Archivo sobre el que se desea operar (F0 por defecto en
     * el programa).
     *
     */
    @Override
    public void fileToArray(File archivoF0) {

        String lineaDeLectura;
        String claveLeida;

        try {
            lectorArchivo = new FileReader(archivoF0);
            bufferLector = new BufferedReader(lectorArchivo);

            while ((lineaDeLectura = bufferLector.readLine()) != null) {

                arrayAuxiliarF0S = new ArrayList<>();
                identificador = new StringTokenizer(lineaDeLectura, ",");

                while (identificador.hasMoreTokens()) {
                    claveLeida = identificador.nextToken();

                    arrayAuxiliarF0S.add(claveLeida);
                }
                this.arrayBlocksF0.add(arrayAuxiliarF0S);
            }

            bufferLector.close();
            lectorArchivo.close();
            System.out.println("Claves procesadas en F0: ");
            System.out.println(arrayAuxiliarF0S);

        } catch (IOException e) {

            System.out.println("\nError inesperado de lectura/escritura. "
                    + "Verifique la integridad del archivo.\n");

        }

    }

}
