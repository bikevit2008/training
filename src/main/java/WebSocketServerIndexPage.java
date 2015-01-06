
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

    /**
     * Generates the demo HTML page which is served at http://localhost:8080/
     */
    public final class WebSocketServerIndexPage {

        private static final String NEWLINE = "\r\n";

        public static ByteBuf getContent(String webSocketLocation) {
            return Unpooled.copiedBuffer(
                    "<html><head><title>Web Socket Test</title>" +
                            "<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\" />" + NEWLINE +
                            "</head>" + NEWLINE +
                            "<body>" + NEWLINE +
                            "<script type=\"text/javascript\">" + NEWLINE +
                            "var socket;" + NEWLINE +
                            "if (!window.WebSocket) {" + NEWLINE +
                            "  window.WebSocket = window.MozWebSocket;" + NEWLINE +
                            '}' + NEWLINE +
                            "if (window.WebSocket) {" + NEWLINE +
                            "  socket = new WebSocket(\"" + webSocketLocation + "\");" + NEWLINE +
                            "  socket.onmessage = function(event) {" + NEWLINE +
                            "    var ta = document.getElementById('responseText');" + NEWLINE +
                            "    ta.value = event.data;" + NEWLINE +
                            "                              if(event.data == \"play\"){\n" +
                            "                                    player.playVideo();\n" +
                            "                              }\n" +
                            "                              else if(event.data == \"pause\"){\n" +
                            "                                    player.pauseVideo();\n" +
                            "                              }" +
                            "else if(event.data.indexOf(\"currentTime\") > -1) {" +
                            "if(document.getElementById('play').currentTime == event.data.substring(12)){}" +
                            "else {document.getElementById('play').currentTime = event.data.substring(12);}" +
                            "}" + NEWLINE +
                            "  };" + NEWLINE +
                            "  socket.onopen = function(event) {" + NEWLINE +
                            "    var ta = document.getElementById('responseText');" + NEWLINE +
                            "    ta.value = \"Web Socket opened!\";" + NEWLINE +
                            "  };" + NEWLINE +
                            "  socket.onclose = function(event) {" + NEWLINE +
                            "    var ta = document.getElementById('responseText');" + NEWLINE +
                            "    ta.value = ta.value + \"Web Socket closed\"; " + NEWLINE +
                            "  };" + NEWLINE +
                            "} else {" + NEWLINE +
                            "  alert(\"Your browser does not support Web Socket.\");" + NEWLINE +
                            '}' + NEWLINE +
                            NEWLINE +
                            "function send(message) {" + NEWLINE +
                            "  if (!window.WebSocket) { return; }" + NEWLINE +
                            "  if (socket.readyState == WebSocket.OPEN) {" + NEWLINE +
                            "    socket.send(message);" + NEWLINE +
                            "  } else {" + NEWLINE +
                            "    alert(\"The socket is not open.\");" + NEWLINE +
                            "  }" + NEWLINE +
                            '}' + NEWLINE +
                            "                            function onPlay(){\n" +
                            "                                send(\"play\");\n" +
                            "                            }\n" +
                            "                            function onPause(){\n" +
                            "                                send(\"pause\");\n" +
                            "                            }" + NEWLINE +
                            "function onSeeked(){" + NEWLINE +
                            "send(\"currentTime=\" + document.getElementById('play').currentTime);" + NEWLINE +
                            "}" +
                            "</script>" + NEWLINE +
                            "<form onsubmit=\"return false;\">" + NEWLINE +
                            "<input type=\"text\" name=\"message\" value=\"Hello, World!\"/>" +
                            "<input type=\"button\" value=\"Send Web Socket Data\"" + NEWLINE +
                            "       onclick=\"send(this.form.message.value)\" />" + NEWLINE +
                            "<h3>Output</h3>" + NEWLINE +
                            "<textarea id=\"responseText\" style=\"width:500px;height:300px;\"></textarea>" + NEWLINE +
                            "</form>" + NEWLINE +
                            "            <div id=\"player\"></div>\n" +
                            "            \n" +
                            "            <script>\n" +
                            "      // 2. This code loads the IFrame Player API code asynchronously.\n" +
                            "      var tag = document.createElement('script');\n" +
                            "\n" +
                            "      tag.src = \"https://www.youtube.com/iframe_api\";\n" +
                            "      var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
                            "      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
                            "\n" +
                            "      // 3. This function creates an iframe (and YouTube player)\n" +
                            "      //    after the API code downloads.\n" +
                            "      var player;\n" +
                            "      function onYouTubeIframeAPIReady() {\n" +
                            "        player = new YT.Player('player', {\n" +
                            "          height: '390',\n" +
                            "          width: '640',\n" +
                            "          videoId: 'LBr7kECsjcQ',\n" +
                            "          events: {\n" +
                            "            'onReady': onPlayerReady,\n" +
                            "            'onStateChange': onPlayerStateChange\n" +
                            "          }\n" +
                            "        });\n" +
                            "      }\n" +
                            "\n" +
                            "      // 4. The API will call this function when the video player is ready.\n" +
                            "      function onPlayerReady(event) {\n" +
                            "      }\n" +
                            "\n" +
                            "      // 5. The API calls this function when the player's state changes.\n" +
                            "      //    The function indicates that when playing a video (state=1),\n" +
                            "      //    the player should play for six seconds and then stop.\n" +
                            "      function onPlayerStateChange(event) {\n" +
                            "\n" +
                            "        switch (player.getPlayerState()) {\n" +
                            "    case -1:\n" +
                            "        break;\n" +
                            "    case 0:\n" +
                            "        break;\n" +
                            "    case 1:\n" +
                            "        onPlay();\n" +
                            "        break;\n" +
                            "    case 2:\n" +
                            "        onPause();\n" +
                            "        break;\n" +
                            "    case 3:\n" +
                            "        break;\n" +
                            "    case 5:\n" +
                            "        break;        \n" +
                            "}\n" +
                            "      }\n" +
                            "    </script>" + NEWLINE +
                            "</body>" + NEWLINE +
                            "</html>" + NEWLINE, CharsetUtil.US_ASCII);
        }

        private WebSocketServerIndexPage() {
            // Unused
        }
    }

