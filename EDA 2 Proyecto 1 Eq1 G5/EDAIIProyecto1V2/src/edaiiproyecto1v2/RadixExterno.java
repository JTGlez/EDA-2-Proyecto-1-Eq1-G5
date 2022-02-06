/*
* Estructura de Datos y Algoritmos II - Proyecto #1: "Ordenamiento externo"
* Grupo: 05
* Equipo: 1
* Implementación de Radix-Sort para el ordenamiento externo.
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
public class RadixExterno {

    static String rutaArchivo = "./Archivos/";
    static String rutaAuxiliar = "./Archivos/RadixExterno/";

    FileWriter escritorDeClaves;
    BufferedWriter bufferClave;

    StringTokenizer identificador;
    Scanner lectura;

    static ArrayList<ArrayList<String>> lecturaDF0;

    /**
     * Método principal del ordenamiento externo Radix. Su proceso es: 1)Elimina
     * archivos secundarios procedentes de anteriores ejecuciones. 2)Construye
     * los archivos secundarios para cada dígito significativo. 3)Borra el
     * archivo original con el fin de evitar errores de escritura. 4)Ordena y
     * vacía los archivos auxiliares de acuerdo al criterio elegido. 5)Imprime
     * el archivo modificado por cada iteración.
     *
     * @param nombreDelArchivo Nombre completo del archivo original.
     * @param tipoDeOrdenamiento Indica el criterio de ordenmaiento:
     * Ascendente(1) o Descendente(2).
     */
    public void ordenamientoRadix(String nombreDelArchivo, int tipoDeOrdenamiento) {

        int contadorIteraciones = 0;
        lecturaDF0 = Utilidades.scanSuperKeys(nombreDelArchivo);

        ArrayList<ArrayList<String>> archivoF0 = lecturaDF0;

        contadorIteraciones++;
        System.out.println("-----Iteración actual del ordenamiento: " + contadorIteraciones);
        recolectorDeBasura();
        System.out.println("\nSe ha actualizado la posición significativa a: " + 1);
        System.out.println("");
        queueBuilder(archivoF0, 1);
        Utilidades.eliminarArchivo(nombreDelArchivo + "Sorted");
        criterioSeleccionado(nombreDelArchivo, tipoDeOrdenamiento);

        impresionArchivo(nombreDelArchivo + "Sorted");

        for (int i = 10; i <= 100000; i *= 10) {

            contadorIteraciones++;
            System.out.println("-----Iteración actual del ordenamiento: " + contadorIteraciones);
            recolectorDeBasura();
            System.out.println("\nSe ha actualizado la posición significativa a: " + i);
            System.out.println("");
            queueBuilder1K(nombreDelArchivo + "Sorted", i);
            Utilidades.eliminarArchivo(nombreDelArchivo + "Sorted");
            criterioSeleccionado(nombreDelArchivo, tipoDeOrdenamiento);

            impresionArchivo(nombreDelArchivo + "Sorted");

        }

    }

    /**
     * Realiza un proceso de lectura de claves a partir del archivo auxiliar
     * recibido y, conforme se reciben, se escriben sobre el archivo final para
     * completar el proceso de ordenamiento.
     *
     * @param nombreDelArchivo Nombre completo del archivo original.
     * @param tipoDeOrdenamiento Indica el criterio de ordenamiento:
     * Ascendente(1) o Descendente(2).
     */
    public void criterioSeleccionado(String nombreDelArchivo, int tipoDeOrdenamiento) {

        if (tipoDeOrdenamiento == 1) {

            sortRadix("archivoN0", nombreDelArchivo);
            sortRadix("archivoN1", nombreDelArchivo);
            sortRadix("archivoN2", nombreDelArchivo);
            sortRadix("archivoN3", nombreDelArchivo);
            sortRadix("archivoN4", nombreDelArchivo);
            sortRadix("archivoN5", nombreDelArchivo);
            sortRadix("archivoN6", nombreDelArchivo);
            sortRadix("archivoN7", nombreDelArchivo);
            sortRadix("archivoN8", nombreDelArchivo);
            sortRadix("archivoN9", nombreDelArchivo);
        } else {
            sortRadix("archivoN9", nombreDelArchivo);
            sortRadix("archivoN8", nombreDelArchivo);
            sortRadix("archivoN7", nombreDelArchivo);
            sortRadix("archivoN6", nombreDelArchivo);
            sortRadix("archivoN5", nombreDelArchivo);
            sortRadix("archivoN4", nombreDelArchivo);
            sortRadix("archivoN3", nombreDelArchivo);
            sortRadix("archivoN2", nombreDelArchivo);
            sortRadix("archivoN1", nombreDelArchivo);
            sortRadix("archivoN0", nombreDelArchivo);

        }

    }

    /**
     * Realiza una lectura del archivo original, posteriormente, clasifica y
     * almacena cada clave en su archivo correspondiente.
     *
     * @param nombreDelArchivo Hallar la ubicación de la ruta del archivo
     * contenedor de claves.
     * @param digitoSignificativo Dígito significativo utilizado durante el
     * proceso de ordenamiento.
     */
    public void queueBuilder1K(String nombreDelArchivo, double digitoSignificativo) {

        try {

            File archivoLectura = new File(this.rutaArchivo + nombreDelArchivo + ".txt");
            lectura = new Scanner(archivoLectura);

            while (lectura.hasNextLine()) {

                String saltoLinea = lectura.nextLine();
                identificador = new StringTokenizer(saltoLinea, ",");

                while (identificador.hasMoreTokens()) {

                    int elementoClave = (int) Double.parseDouble(identificador.nextToken());
                    System.out.println("Número de cuenta: " + elementoClave);

                    int posicionOrdenamiento = (int) ((elementoClave % (digitoSignificativo * 10)) / digitoSignificativo);
                    System.out.println("Posición de ordenamiento significativa: " + posicionOrdenamiento);

                    switch (posicionOrdenamiento) {

                        case 0: {

                            appendKeyToFile("archivoN0", elementoClave);

                            break;
                        }

                        case 1: {

                            appendKeyToFile("archivoN1", elementoClave);

                            break;
                        }

                        case 2: {

                            appendKeyToFile("archivoN2", elementoClave);

                            break;
                        }

                        case 3: {

                            appendKeyToFile("archivoN3", elementoClave);

                            break;
                        }

                        case 4: {

                            appendKeyToFile("archivoN4", elementoClave);

                            break;
                        }

                        case 5: {

                            appendKeyToFile("archivoN5", elementoClave);

                            break;
                        }

                        case 6: {

                            appendKeyToFile("archivoN6", elementoClave);

                            break;
                        }

                        case 7: {

                            appendKeyToFile("archivoN7", elementoClave);

                            break;
                        }

                        case 8: {

                            appendKeyToFile("archivoN8", elementoClave);

                            break;
                        }

                        case 9: {

                            appendKeyToFile("archivoN9", elementoClave);

                            break;
                        }

                    }

                }

            }
            lectura.close();

        } catch (FileNotFoundException | NumberFormatException e) {

            System.out.println("\nSe detectó un error en la lectura/escritura"
                    + " de los archivos. Verifique que el archivo con las "
                    + "claves originales se encuentre con el formato correcto.\n");

        }

    }

    /**
     * Realiza una lectura del archivo original, posteriormente, clasifica y
     * almacena cada clave en su archivo correspondiente. Método utilizado
     * específicamente para ejecutarse una única vez al inicio del programa.
     *
     *
     * @param archivoF0 Arreglo contenedor de superclaves.
     * @param digitoSignificativo Dígito significativo utilizado durante el
     * proceso de ordenamiento.
     */
    public void queueBuilder(ArrayList<ArrayList<String>> archivoF0, double digitoSignificativo) {

        for (int i = 0; i < archivoF0.size(); i++) {

            int elementoClave = (int) Double.parseDouble(archivoF0.get(i).get(2));
            System.out.println("Número de cuenta: " + elementoClave);

            int posicionOrdenamiento = (int) ((elementoClave % (digitoSignificativo * 10)) / digitoSignificativo);
            System.out.println("Posición de ordenamiento significativa: " + posicionOrdenamiento);

            switch (posicionOrdenamiento) {

                case 0: {

                    appendKeyToFile("archivoN0", elementoClave);

                    break;
                }

                case 1: {

                    appendKeyToFile("archivoN1", elementoClave);

                    break;
                }

                case 2: {

                    appendKeyToFile("archivoN2", elementoClave);

                    break;
                }

                case 3: {

                    appendKeyToFile("archivoN3", elementoClave);

                    break;
                }

                case 4: {

                    appendKeyToFile("archivoN4", elementoClave);

                    break;
                }

                case 5: {

                    appendKeyToFile("archivoN5", elementoClave);

                    break;
                }

                case 6: {

                    appendKeyToFile("archivoN6", elementoClave);

                    break;
                }

                case 7: {

                    appendKeyToFile("archivoN7", elementoClave);

                    break;
                }

                case 8: {

                    appendKeyToFile("archivoN8", elementoClave);

                    break;
                }

                case 9: {

                    appendKeyToFile("archivoN9", elementoClave);

                    break;
                }

            }

        }

    }

    /**
     * Añade en un archivo auxiliar la clave introducida como parámetro.
     * Verificación del archivo auxiliar necesaria: se crea uno en caso de no
     * existir en la carpeta RadixExterno.
     *
     * @param nombreAuxiliar Hallar la ubicación de la ruta del archivo
     * auxiliar.
     * @param elementoClave Clave a almacenar en un archivo auxiliar.
     */
    public void appendKeyToFile(String nombreAuxiliar, int elementoClave) {

        try {
            File archivoAuxiliar = new File(RadixExterno.rutaAuxiliar + nombreAuxiliar + ".txt");

            if (!archivoAuxiliar.exists()) {
                archivoAuxiliar.createNewFile();

            }
            escritorDeClaves = new FileWriter(archivoAuxiliar, true);
            bufferClave = new BufferedWriter(escritorDeClaves);
            bufferClave.append(elementoClave + ",");
            bufferClave.close();
            escritorDeClaves.close();

        } catch (IOException e) {
            System.out.println("\nSe detectó un error en la lectura/escritura"
                    + " de los archivos. Verifique que el archivo con las "
                    + "claves originales se encuentre con el formato correcto.\n");
        }

    }

    /**
     * Realiza un proceso de lectura de claves a partir del archivo auxiliar
     * recibido y, conforme se reciben, se escriben sobre el archivo original
     * para completar el proceso de ordenamiento.
     *
     * @param nombreAuxiliar Nombre completo del archivo auxiliar que almacena
     * las claves de acuerdo a su dígito significativo.
     * @param nombreDelArchivo Nombre completo del archivo original.
     */
    public void sortRadix(String nombreAuxiliar, String nombreDelArchivo) {

        File archivoTarget = new File(RadixExterno.rutaAuxiliar + nombreAuxiliar + ".txt");

        if (archivoTarget.exists()) {
            try {

                File archivoMod = new File(RadixExterno.rutaArchivo + nombreDelArchivo + "Sorted.txt");

                if (!archivoMod.exists()) {
                    archivoMod.createNewFile();
                }

                escritorDeClaves = new FileWriter(archivoMod, true);
                bufferClave = new BufferedWriter(escritorDeClaves);

                lectura = new Scanner(archivoTarget);

                while (lectura.hasNextLine()) {

                    String saltoLinea = lectura.nextLine();
                    identificador = new StringTokenizer(saltoLinea, ",");

                    while (identificador.hasMoreTokens()) {

                        String elementoClave = identificador.nextToken();
                        bufferClave.append(elementoClave + ",");
                    }
                }

                bufferClave.close();
                escritorDeClaves.close();
                lectura.close();

            } catch (IOException e) {

                System.out.println("\nSe detectó un error en la lectura/escritura"
                        + " de los archivos. Verifique que el archivo con las "
                        + "claves originales se encuentre con el formato correcto.\n");
            }
        }

    }

    /**
     * Elimina archivos auxiliares procedentes de ejecuciones anteriores.
     */
    public void recolectorDeBasura() {

        String ubicacionAuxiliares = RadixExterno.rutaAuxiliar;
        File archivosAEliminar = new File(ubicacionAuxiliares);
        File[] archivosAuxiliares = archivosAEliminar.listFiles();

        if (archivosAuxiliares.length != 0) {

            for (File archivosAuxiliare : archivosAuxiliares) {
                File archivoActual = new File(archivosAuxiliare.toString());
                /*Eliminando esta línea se pueden observar los archivos auxiliares... aunque de una forma muy fea
                  que afecta al resultado final. */
                archivoActual.delete();
            }

        }

    }

    /**
     * Realiza un proceso de lectura sobre el archivo, e imprime las claves
     * válidas que encuentre.
     *
     * @param nombreDelArchivo Nombre completo del archivo a imprimir.
     */
    public void impresionArchivo(String nombreDelArchivo) {

        try {
            System.out.println("\nIniciando el proceso de vaciado sobre el archivo original...\n");
            File archivoAImprimir = new File(RadixExterno.rutaArchivo + nombreDelArchivo + ".txt");
            lectura = new Scanner(archivoAImprimir);

            while (lectura.hasNextLine()) {

                String saltoLinea = lectura.nextLine();
                identificador = new StringTokenizer(saltoLinea, ",");

                while (identificador.hasMoreTokens()) {

                    System.out.println(identificador.nextToken() + ",");
                }
            }
            lectura.close();
            System.out.println("\nOperación realizada con éxito.\n");
        } catch (FileNotFoundException e) {

            System.out.println("\nSe detectó un error de integridad"
                    + " en los archivos auxiliares. Verifique"
                    + " que otro programa no interfiera sobre la"
                    + " ejecución. \n");

        }
    }

}
