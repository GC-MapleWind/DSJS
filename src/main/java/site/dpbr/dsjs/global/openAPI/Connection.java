package site.dpbr.dsjs.global.openAPI;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.global.openAPI.exception.FailToConnectNexonOpenAPIException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class Connection {

    @Value("${api-key}")
    private String API_KEY;

    private static final String API_URL = "https://open.api.nexon.com/maplestory/v1";

    public String execute(String path) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(API_URL + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-nxopen-api-key", API_KEY);

            BufferedReader in;
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
            } else {
                JsonObject jsonObject = JsonParser.parseString(
                        new BufferedReader(new InputStreamReader(connection.getErrorStream())).readLine()).getAsJsonObject();

                JsonObject errorObject = jsonObject.getAsJsonObject("error");
                String errorName = errorObject.get("name").getAsString();
                String errorMessage = errorObject.get("message").getAsString();

                log.info(url.toString());
                log.error("Error name: {}, Error message: {}", errorName, errorMessage);
                throw new FailToConnectNexonOpenAPIException();
            }

        } catch (IOException e) {
            throw new FailToConnectNexonOpenAPIException();
        }

        return response.toString();
    }
}
