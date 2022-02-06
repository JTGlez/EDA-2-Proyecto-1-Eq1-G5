
/*
* Estructura de Datos y Algoritmos II - Proyecto #1: "Ordenamiento externo"
* Grupo: 05
* Equipo: 1
* Menú principal del programa.
 */
package edaiiproyecto1v2;

import java.io.IOException;
import java.util.Scanner;

/**
 * Integrantes del equipo:
 *
 * @autor: Marco Antonio López Lara
 * @autor: Olivera Martínez Jenyffer Brigitte
 * @autor: Téllez González Jorge Luis
 */
class EDAIIProyecto1 {

    static Scanner lectorOpcion;
    static Scanner lectura;

    /**
     * Menú principal del Proyecto #1: "Ordenamiento externo". Permite
     * seleccionar entre 3 métodos de ordenamiento externo: Polifase, Mezcla
     * Natural y Radix Externo.
     *
     * @param args argumentos de la línea de comando.
     */
    public static void main(String[] args) throws IOException {

        System.out.println("Estructura de Datos y Algoritmos II - Proyecto #1: Ordenamiento externo.\nCreado por: Equipo 01.\n\n");

        int opcionAlgoritmo = 0;
        while (opcionAlgoritmo != 4) {

            System.out.println("¿Qué algoritmo de ordenamiento desea utilizar para ordenar un archivo?"
                    + "\n1)Simulación de Método por Polifase\n2)Simulación de Método por Mezcla Natural\n3)Simulación de Radix\n4)Salir del programa\n");

            lectorOpcion = new Scanner(System.in);
            lectura = new Scanner(System.in);
            System.out.print("Opción: ");
            opcionAlgoritmo = lectorOpcion.nextInt();

            switch (opcionAlgoritmo) {

                case 1: {

                    System.out.println("\nUsted ha seleccionado la simulación del método por Polifase: \n");
                    Polifase opcionPolifase = new Polifase();
                    PolifaseStrings opcionPolifaseN = new PolifaseStrings();

                    System.out.print("\nIngrese el nombre del archivo: ");
                    String nombreDelArchivo = lectura.nextLine();

                    if (Utilidades.verificadorArchivo(nombreDelArchivo) == true) {

                        System.out.println("\n¿Qué tipo de criterio de ordenamiento desea utilizar?\n1)Ascendente\n2)Descendente\n");
                        System.out.print("Opción: ");
                        int criterioDeOrdenamiento = lectura.nextInt();

                        if (criterioDeOrdenamiento == 1 || criterioDeOrdenamiento == 2) {

                            System.out.println("\n¿Qué campo de las claves desea ordenar?\n1)Nombre\n2)Apellido\n3)Número de cuenta\n");
                            System.out.print("Opción: ");

                            int campoDeOrdenamiento = lectura.nextInt();

                            switch (campoDeOrdenamiento) {

                                case 1:

                                    Utilidades.resetArchivoAuxPolyphase("F0");
                                    opcionPolifaseN.ordenamientoPolifaseString(nombreDelArchivo, 4, criterioDeOrdenamiento, 1);

                                    break;

                                case 2:

                                    Utilidades.resetArchivoAuxPolyphase("F0");
                                    opcionPolifaseN.ordenamientoPolifaseString(nombreDelArchivo, 4, criterioDeOrdenamiento, 2);

                                    break;

                                case 3:

                                    Utilidades.resetArchivoAuxPolyphase("F0");
                                    opcionPolifase.ordenamientoPolifase(nombreDelArchivo, 4, criterioDeOrdenamiento);

                                    break;

                                default:

                                    System.out.println("Usted introdujo un campo inválido. Por favor, intente de nuevo.\n");

                                    break;

                            }

                        } else {

                            System.out.println("Usted introdujo un criterio inválido. Por favor, intente de nuevo.\n");
                        }

                    }

                    break;

                }

                case 2: {

                    System.out.println("\nUsted ha seleccionado la simulación del método por Mezcla Natural: \n");
                    NaturalMix opcionMezcla = new NaturalMix();
                    NaturalMixStrings opcionMezclaS = new NaturalMixStrings();
                    
                    System.out.print("\nIngrese el nombre del archivo: ");
                    String nombreDelArchivo = lectura.nextLine();

                    if (Utilidades.verificadorArchivo(nombreDelArchivo) == true) {

                        System.out.println("\n¿Qué tipo de criterio de ordenamiento desea utilizar?\n1)Ascendente\n2)Descendente\n");
                        System.out.print("Opción: ");
                        int criterioDeOrdenamiento = lectura.nextInt();

                        if (criterioDeOrdenamiento == 1 || criterioDeOrdenamiento == 2) {

                            System.out.println("\n¿Qué campo de las claves desea ordenar?\n1)Nombre\n2)Apellido\n3)Número de cuenta\n");
                            System.out.print("Opción: ");

                            int campoDeOrdenamiento = lectura.nextInt();

                            switch (campoDeOrdenamiento) {

                                case 1:

                                    Utilidades.resetArchivoAuxPolyphase("F0");
                                    opcionMezclaS.mezclaNatural(nombreDelArchivo, criterioDeOrdenamiento, campoDeOrdenamiento);

                                    break;

                                case 2:

                                    Utilidades.resetArchivoAuxPolyphase("F0");
                                    opcionMezclaS.mezclaNatural(nombreDelArchivo, criterioDeOrdenamiento, campoDeOrdenamiento);

                                    break;

                                case 3:

                                    Utilidades.resetArchivoAuxPolyphase("F0");
                                    opcionMezcla.mezclaNatural(nombreDelArchivo, criterioDeOrdenamiento);

                                    break;

                                default:

                                    System.out.println("Usted introdujo un campo inválido. Por favor, intente de nuevo.\n");

                                    break;

                            }

                        } else {

                            System.out.println("Usted introdujo un criterio inválido. Por favor, intente de nuevo.\n");
                        }

                    }
                    

                    break;

                }

                case 3: {

                    System.out.println("\nUsted ha seleccionado la simulación de Radix.");
                    System.out.print("\nIngrese el nombre del archivo: ");
                    String nombreDelArchivo = lectura.nextLine();

                    if (Utilidades.verificadorArchivo(nombreDelArchivo) == true) {

                        System.out.println("\n¿Qué tipo de criterio de ordenamiento desea utilizar?\n1)Ascendente\n2)Descendente\n");
                        System.out.print("Opcion: ");
                        int criterioDeOrdenamiento = lectura.nextInt();

                        if (criterioDeOrdenamiento == 1 || criterioDeOrdenamiento == 2) {

                            RadixExterno opcionRadix = new RadixExterno();
                            opcionRadix.ordenamientoRadix(nombreDelArchivo, criterioDeOrdenamiento);

                            System.out.println("¡Felicidades! Los datos han sido ordenados.\n\nSaliendo de la opción...\n");
                            break;

                        } else {

                            System.out.println("Usted introdujo un criterio inválido. Por favor, intente de nuevo.\n");
                        }

                    }

                    break;

                }

                case 4: {

                    System.out.println("\n¡Adiós!\nSaliendo del programa...\n\n");
                    break;

                }

                default: {

                    System.out.println("\nPor favor seleccione una opción correcta.\n");
                    break;
                }
            }

        }

    }

}
