package site.dpbr.dsjs.global.openAPI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {
    // API KEY
    private static final String API_KEY = "";
    private static final String MAPLESTORY_NEXON_OPEN_API_URL = "https://open.api.nexon.com/maplestory/v1";

    // HTTP 요청
    private HttpURLConnection execute(String path) throws IOException {
        URL url = new URL(MAPLESTORY_NEXON_OPEN_API_URL + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("x-nxopen-api-key", API_KEY);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        connection.setDoOutput(true);

        return connection;
    }
}
