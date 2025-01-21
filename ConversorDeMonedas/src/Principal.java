import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner consulta = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("-----------Bienvenido/a al conversor de monedas -----------");

            System.out.println("---------Opciones disponibles en el programa -----------");
            System.out.println("--------------------------------------------------------------------");
            String[] monedas = {"ARS", "BOB", "BRL", "CLP", "COP", "EUR", "GBP", "JPY", "MXN", "USD", "UYU"};

            System.out.println("0.- Salir del programa");
            for (int i = 0; i < monedas.length; i++) {
                System.out.printf("%d.- %s\n", i + 1, monedas[i]);
            }
            System.out.print("Seleccione el número correspondiente a la moneda que desea convertir o " +
                    "0 en caso de que desee salir del programa: ");
            int monedaOrigen = consulta.nextInt();

            if (monedaOrigen == 0) {
                System.out.println("Salió de la aplicación correctamente...");
                break;
            } else if ( monedaOrigen > monedas.length) {
                    System.out.println("Opcion no valida, por favor ingrese un numero que este dentro de las opciones disponibles.");
            }

            // Selección de moneda de destino
            System.out.println("-------Seleccione la divisa a la que desea convertir -----------");
            for (int i = 0; i < monedas.length; i++) {
                System.out.printf("%d.- %s\n", i + 1, monedas[i]);
            }
            System.out.print("Seleccione la moneda de destino: ");
            int monedaDestino = consulta.nextInt();

            // Leer la cantidad a convertir
            System.out.print("Ingrese la cantidad a convertir: ");
            double cantidadAConvertir = consulta.nextDouble();

            CambioDeDivisas cambioDeDivisas = new CambioDeDivisas();

            try {
                // Realizar la conversión de divisas
                Divisa resultado = cambioDeDivisas.cambioDeDivisas(monedas[monedaOrigen - 1], monedas[monedaDestino - 1], cantidadAConvertir);
                System.out.println(resultado);

                // Registrar el cambio
                RegistroDeCambios.registrarCambioDeDivisas(monedas[monedaOrigen - 1], monedas[monedaDestino - 1],
                        cantidadAConvertir, resultado.conversion_result());


                System.out.println("¿Desea realizar otra conversión? (s/n)");
                String respuesta = consulta.next().toLowerCase();
                continuar = respuesta.equals("s");

            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor ingrese un número válido.");
            } catch (IOException e) {
                System.out.println("No fue posible generar el archivo, por favor intente nuevamente");
            }
        }
        System.out.println("Si desea consultar su Historial antes de salir presione la letra (h)");
        System.out.println(" Si desea unicamente salir del programa presione la letra (s)");

        String respuesta = consulta.next().toLowerCase();
        if (respuesta.equals("h")) {
            System.out.println("Usted realizo los siguientes cambios: ");
            System.out.println(CambioDeDivisas.getTodosLosCambios());
        }
        System.out.println("Ha salido del programa, Gracias por su preferencia!!!");
        consulta.close(); // Cierra el scanner al final9
    }
}