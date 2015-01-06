import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by ws on 07.01.15.
 */
public class Templator {
    public static String get(String name, Properties props) throws IOException {
        byte[] data = Files.readAllBytes(Paths.get("./html/index.html"));
        String html = new String(data);
        for (Object k : props.keySet()) {
            String marker = "${" + k + "}";
            html = html.replace(marker, props.get(k).toString());
        }
        return html;
    }
}
