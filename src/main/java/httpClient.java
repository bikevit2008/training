/**
 * Created by user-325 on 22.01.2015.
 */
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class httpClient {
    public static void main(String[] args) throws Exception {
        String content = null;
        URLConnection connection = null;
        try {
            connection =  new URL("https://www.youtube.com/embed/LBr7kECsjcQ?enablejsapi=1&origin=http%3A%2F%2Fonplayka.tk").openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        System.out.println(content);
    }
}
