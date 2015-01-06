
/*
* Copyright 2012 The Netty Project
*
* The Netty Project licenses this file to you under the Apache License,
* version 2.0 (the "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at:
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * Generates the demo HTML page which is served at http://localhost:8080/
 */
public final class WebSocketServerIndexPage {

    private static final String NEWLINE = "\r\n";

    public static ByteBuf getContent(String webSocketLocation) {
        Properties props = new Properties();
        props.setProperty("websocket-location", webSocketLocation);
        String html = "";
        try {
            html = Templator.get("index.html", props);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Unpooled.copiedBuffer(html, CharsetUtil.US_ASCII);
    }

    private WebSocketServerIndexPage() {
        // Unused
    }
}

