package at.fhtw.sampleapp.service;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.StringReader;

public class WeatherService extends AbstractService {

    // Sample hardcoded weather data
    private Map<String, String> weatherDataMap = new HashMap<>();

    public WeatherService() {
        // Initializing with some sample data
        weatherDataMap.put("1", "{\"location\":\"New York\",\"temperature\":\"22°C\",\"humidity\":\"65%\"}");
        weatherDataMap.put("2", "{\"location\":\"London\",\"temperature\":\"18°C\",\"humidity\":\"70%\"}");
        weatherDataMap.put("3", "{\"location\":\"Tokyo\",\"temperature\":\"25°C\",\"humidity\":\"60%\"}");
    }

    // GET /weather/:id
    public Response getWeather(String id) {
        if (weatherDataMap.containsKey(id)) {
            return new Response(
                    HttpStatus.OK,
                    ContentType.JSON,
                    weatherDataMap.get(id)
            );
        } else {
            return new Response(
                    HttpStatus.NOT_FOUND,
                    ContentType.JSON,
                    "{\"message\":\"Location not found\"}"
            );
        }
    }

    // GET /weather
    public Response getWeather() {
        return getWeatherPerRepository();  // Redirect to getWeatherPerRepository for simplicity
    }

    // POST /weather
    public Response addWeather(Request request) {
        // Extracting POST data from the request.
        // Assuming that the POST data is stored in the Request object's body.
        String postData = request.getBody();

        return new Response(
                HttpStatus.CREATED,
                ContentType.JSON,
                postData
        );
    }

    // GET /weather
    public Response getWeatherPerRepository() {
        // Convert all data to a JSON array
        String allWeatherData = weatherDataMap.values().stream()
                .collect(Collectors.joining(",", "[", "]"));
        return new Response(
                HttpStatus.OK,
                ContentType.JSON,
                allWeatherData
        );
    }
}
