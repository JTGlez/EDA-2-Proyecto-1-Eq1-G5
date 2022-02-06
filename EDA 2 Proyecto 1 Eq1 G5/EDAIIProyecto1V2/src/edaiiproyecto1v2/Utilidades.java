/*
* Estructura de Datos y Algoritmos II - Proyecto #1: "Ordenamiento externo"
* Grupo: 05
* Equipo: 1
* Herramientas auxiliares para la ejecución del programa.
 */
package edaiiproyecto1v2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Integrantes del equipo:
 *
 * @autor: Marco Antonio López Lara
 * @autor: Olivera Martínez Jenyffer Brigitte
 * @autor: Téllez González Jorge Luis
 */
public class Utilidades {

    //Referencias a objetos utilizados por scanSuperKeys.
    static Scanner lectura;
    static String saltoClave;
    static StringTokenizer identificador;
    static ArrayList<ArrayList<String>> lecturaDF0;
    static ArrayList<String> subClaves;

    //Ruta estática de acceso a archivos.
    static String rutaArchivo = "./Archivos/";

    /**
     * Elimina un archivo presente en la carpeta "Archivos" del programa.
     *
     * @param nombreArchivo Nombre del archivo que se desea eliminar sin
     * extensión.
     */
    public static void eliminarArchivo(String nombreArchivo) {
        File archivoAEliminar = new File(Utilidades.rutaArchivo + nombreArchivo + ".txt");
        if (archivoAEliminar.exists()) {
            archivoAEliminar.delete();
        }
    }

    /**
     * Elimina un archivo auxiliar generado por el método de ordenamiento
     * Polifase en ejecuciones anteriores y lo reconstruye en un nuevo archivo;
     * cerrando cualquier flujo de escritura presente si existiese.
     *
     * @param nombreArchivo Nombre del archivo que se desea eliminar sin
     * extensión.
     * @throws java.io.IOException
     */
    public static void resetArchivoAuxPolyphase(String nombreArchivo) throws IOException {
        File archivoAEliminar = new File(Polifase.rutaAuxiliar + nombreArchivo + ".txt");
        FileWriter archivoAEliminar2 = new FileWriter(Polifase.rutaAuxiliar + nombreArchivo + ".txt");

        if (archivoAEliminar.exists()) {
            archivoAEliminar.delete();
            archivoAEliminar.createNewFile();
            //Cierra el flujo de datos de ejecuciones anteriores.
            BufferedWriter sobreescritura = new BufferedWriter(archivoAEliminar2, 1);
            sobreescritura.close();
        }
    }

    public static void resetArchivoAuxMix(String nombreArchivo) throws IOException {
        File archivoAEliminar = new File(NaturalMix.rutaAuxiliar + nombreArchivo + ".txt");
        FileWriter archivoAEliminar2 = new FileWriter(NaturalMix.rutaAuxiliar + nombreArchivo + ".txt");

        if (archivoAEliminar.exists()) {
            archivoAEliminar.delete();
            archivoAEliminar.createNewFile();
            //Cierra el flujo de datos de ejecuciones anteriores.
            BufferedWriter sobreescritura = new BufferedWriter(archivoAEliminar2, 1);
            sobreescritura.close();
        }
    }

    /**
     * Verifica que el archivo introducido como parámetro exista en la carpeta
     * "Archivos" del programa.
     *
     * @param nombreDelArchivo Nombre del archivo a verificar sin extensión.
     * @return false si el archivo no se encuentra en la carpeta y true en caso
     * contrario.
     */
    public static boolean verificadorArchivo(String nombreDelArchivo) {

        File archivoAVerificar = new File(Utilidades.rutaArchivo + nombreDelArchivo + ".txt");

        if (!archivoAVerificar.exists()) {

            System.out.println("\nError: no existe un archivo con el nombre introducido en la carpeta Archivos.\n");
            return false;

        } else {
            return true;
        }

    }

    /**
     * Método de ordenamiento interno utilizado en la ejecución de Polifase y
     * Mezcla Natural. Nota: Recuperado y modificado del código proporcionado
     * para la Práctica 1 del laboratorio de EDA II.
     *
     * @param arregloAOrdenar Lista de enteros a ordenar.
     * @param criterioDeOrdenamiento 1)Ascendente. 2)Descendente.
     * @return Lista de enteros ordenada.
     */
    public static ArrayList<Integer> insertionSorting(ArrayList<Integer> arregloAOrdenar, int criterioDeOrdenamiento) {

        int i, j;
        int n = arregloAOrdenar.size();

        if (criterioDeOrdenamiento == 1) {
            for (i = 1; i < n; i++) {

                Integer claveActual = arregloAOrdenar.get(i);
                j = i - 1;

                while (j > -1 && (arregloAOrdenar.get(j) > claveActual)) {

                    arregloAOrdenar.set(j + 1, arregloAOrdenar.get(j));
                    j--;
                }
                arregloAOrdenar.set(j + 1, claveActual);
            }

            return arregloAOrdenar;

        } else {

            for (i = 1; i < n; i++) {

                Integer claveActual = arregloAOrdenar.get(i);
                j = i - 1;

                while (j > -1 && (arregloAOrdenar.get(j) < claveActual)) {

                    arregloAOrdenar.set(j + 1, arregloAOrdenar.get(j));
                    j--;
                }
                arregloAOrdenar.set(j + 1, claveActual);
            }
            return arregloAOrdenar;

        }

    }

    /**
     * Método de ordenamiento interno utilizado en la ejecución de Polifase y
     * Mezcla Natural, adaptado al ordenamiento de claves no numéricas. Nota:
     * Recuperado y modificado del código proporcionado para la Práctica 1 del
     * laboratorio de EDA II.
     *
     * @param arregloAOrdenar Lista de enteros a ordenar.
     * @param criterioDeOrdenamiento 1)Ascendente. 2)Descendente.
     * @return Lista de cadenas ordenada.
     */
    public static ArrayList<String> insertionSortingStrings(ArrayList<String> arregloAOrdenar, int criterioDeOrdenamiento) {

        int i, j;
        int n = arregloAOrdenar.size();

        if (criterioDeOrdenamiento == 1) {

            for (i = 1; i < n; i++) {

                String claveActual = arregloAOrdenar.get(i);
                j = i - 1;

                while (j > -1 && (arregloAOrdenar.get(j).compareTo(claveActual)) > 0) {

                    arregloAOrdenar.set(j + 1, arregloAOrdenar.get(j));
                    j--;
                }
                arregloAOrdenar.set(j + 1, claveActual);
            }

            return arregloAOrdenar;

        } else {

            for (i = 1; i < n; i++) {

                String claveActual = arregloAOrdenar.get(i);
                j = i - 1;

                while (j > -1 && (arregloAOrdenar.get(j).compareTo(claveActual)) < 0) {

                    arregloAOrdenar.set(j + 1, arregloAOrdenar.get(j));
                    j--;
                }
                arregloAOrdenar.set(j + 1, claveActual);
            }
            return arregloAOrdenar;

        }

    }

    /**
     * Recupera cada una de las superclaves almacenadas en el archivo
     * introducido como parámetro, generando una lista anidada que contiene cada
     * superclave y sus subclaves(Nombre, Apellido y Número de Cuenta).
     *
     * @param nombreDelArchivo Archivo con las superclaves a recuperar.
     * @return Lista anidada con cada una de las superclaves y sus respectivas
     * subclaves.
     */
    public static ArrayList<ArrayList<String>> scanSuperKeys(String nombreDelArchivo) {

        lecturaDF0 = new ArrayList<>();

        try {

            File archivoLectura = new File(Utilidades.rutaArchivo + nombreDelArchivo + ".txt");
            lectura = new Scanner(archivoLectura);

            while (lectura.hasNextLine()) {

                saltoClave = lectura.nextLine();
                identificador = new StringTokenizer(saltoClave, "");

                while (identificador.hasMoreTokens()) {

                    String superClave = identificador.nextToken();
                    //System.out.println("\nEsta es una superclave: \n");
                    //System.out.println(superClave);

                    //System.out.println("\nEsta es la superclave separada en un arreglo con sus subclaves.");
                    subClaves = new ArrayList<>();

                    StringTokenizer separador = new StringTokenizer(superClave, ",");

                    for (int i = 0; i < 3; i++) {

                        subClaves.add(separador.nextToken());

                    }
                    //System.out.println(subClaves);
                    lecturaDF0.add(subClaves);

                }
                //System.out.println("\nEl arreglo inicial F0 es el siguiente: \n");
                //System.out.println(lecturaDF0);

            }

        } catch (FileNotFoundException e) {

            System.out.println("\nSe detectó un error de integridad sobre"
                    + " el archivo original. Verifique que otro programa"
                    + " no se encuentre interfiriendo sobre la ejecución.\n");

        }

        return lecturaDF0;
    }

}
