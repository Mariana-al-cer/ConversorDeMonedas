import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CambioDeDivisas {
    private String API;
    private String monedaAConvertir;
    private String monedaConvertida;
    private double cantidadAConvertir;
    private double resultado;

    private static final List<String> todosLosCambios = new ArrayList<>();

    public CambioDeDivisas() {
        // Constructor vacío
    }

    public Divisa cambioDeDivisas(String monedaAConvertir, String monedaConvertida, double cantidadAConvertir) {
        this.monedaAConvertir = monedaAConvertir;
        this.monedaConvertida = monedaConvertida;
        this.cantidadAConvertir = cantidadAConvertir;

        // Crear una cadena de timestamp con la fecha y hora actuales
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);
        API = "dc9c6bfd3e9c2658a8fac25c";
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + API +
                "/pair/" + monedaAConvertir + "/" + monedaConvertida + "/" + cantidadAConvertir);

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(direccion)
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            Divisa divisa = new Gson().fromJson(response.body(), Divisa.class);
            this.resultado = divisa.conversion_result();

            todosLosCambios.add(this.toString() + " realizada con fecha: " + timestamp);
            return divisa;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException("No se encontró la divisa");
        }
    }

    public static List<String> getTodosLosCambios() {
        return todosLosCambios;
    }

    @Override
    public String toString() {
        return "La conversión de " + cantidadAConvertir
                + " [ De " + monedaAConvertir
                + "] a [" + monedaConvertida
                + "] es igual a: " + resultado + " [" + monedaConvertida + "]\n";
    }
}