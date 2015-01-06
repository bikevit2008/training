import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by ws on 07.01.15.
 */
public class Templator {
    public static String get(String name, Properties props) throws IOException {
        URL res = Templator.class.getResource("html/" + name);
        Object data = res.getContent();
        String html = new String((byte[]) data);
        for (Object k : props.keySet()) {
            String marker = "${" + k + "}";
            html = html.replace(marker, props.get(k).toString());
        }
        return html;
    }
}
