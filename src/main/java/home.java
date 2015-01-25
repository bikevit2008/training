import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Vitaly on 25.01.15.
 */
public class home {
    public static ByteBuf getContent(String webSocketLocation) {
        Properties props = new Properties();
        props.setProperty("websocket-location", webSocketLocation);
        String html = "";
        try {
            html = Templator.get("landing.html", props);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Unpooled.copiedBuffer(html, CharsetUtil.UTF_8);
    }

    private home() {
        // Unused
    }
}
