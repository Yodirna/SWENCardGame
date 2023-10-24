package at.fhtw.sampleapp.controller.weather;

import at.fhtw.sampleapp.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testWeatherServiceGetCompleteList() throws Exception {
        URL url = new URL("http://localhost:10001/weather");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            //Weather[] weatherList = new ObjectMapper().readValue(bufferedReader.readLine(), Weather[].class);
            //assertEquals(3, weatherList.length);
            List<User> userList = new ObjectMapper().readValue(bufferedReader.readLine(), new TypeReference<List<User>>(){});
            assertEquals(3, userList.size());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        bufferedReader.close();
    }

    @Test
    void testWeatherServiceGetByIdCheckString() throws Exception {
        URL url = new URL("http://localhost:10001/weather/1");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        assertEquals("{\"id\":1,\"region\":\"Europe\",\"city\":\"Vienna\",\"temperature\":9.0}", bufferedReader.readLine());

        bufferedReader.close();
    }

    @Test
    void testWeatherServiceGetById() throws Exception {
        URL url = new URL("http://localhost:10001/weather/1");
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            User user = new ObjectMapper().readValue(bufferedReader.readLine(), User.class);
            assertEquals(1, user.getId());
            assertEquals("Ridoy", user.getUsername());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        bufferedReader.close();
    }

}