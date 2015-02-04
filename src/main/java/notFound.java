import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Vitaly on 04.02.15.
 */
public class notFound {

    private static final String NEWLINE = "\r\n";

    public static ByteBuf getContent() {
        Properties props = new Properties();
        String html = "";
        try {
            html = Templator.get("404.html", props);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Unpooled.copiedBuffer(html, CharsetUtil.UTF_8);
    }

    private notFound() {
        // Unused
    }
}
