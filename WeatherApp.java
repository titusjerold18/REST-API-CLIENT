import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import com.example.demo.DemoApplication;
import static org.springframework.boot.web.server.servlet.Session.SessionTrackingMode.URL;

public class WeatherApp {

    public static void main(String[] args) {
        String city = "virudhunagar"; // You can change this
        String apiKey = "d6a9aa26f5d5d898f4cc132af1114df1"; // Replace with your actual OpenWeatherMap API key

        try {
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    city + "&appid=" + apiKey + "&units=metric";

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response
            JSONObject json = new JSONObject(response.toString());

            String cityName = json.getString("name");
            JSONObject main = json.getJSONObject("main");
            double temp = main.getDouble("temp");
            int humidity = main.getInt("humidity");

            String weatherDescription = json
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");

            System.out.println("=== Weather Report ===");
            System.out.println("City: " + cityName);
            System.out.println("Temperature: " + temp + "°C");
            System.out.println("Weather: " + weatherDescription);
            System.out.println("Humidity: " + humidity + "%");

        } catch (Exception e) {
            System.out.println("Error fetching weather data.");
            e.printStackTrace();
        }
    }
}
