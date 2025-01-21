import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroDeCambios {
    public static void registrarCambioDeDivisas(String monedaOrigen, String monedaDestino, double cantidad, double resultado) throws IOException {
        // Registro de fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String registro = String.format("Conversión de %f %s a %s es igual a %f", cantidad, monedaOrigen, monedaDestino, resultado);

        try (FileWriter archivoRegistroCambios = new FileWriter("Cambios_Realizado_el_dia_" + timestamp + ".json")) {
            archivoRegistroCambios.write(gson.toJson(registro));
            System.out.println("Historial de cambios guardado!");
        } catch (Exception e) {
            System.out.println("No fue posible almacenar el archivo: " + e.getMessage());
        }
    }
}