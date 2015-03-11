
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

    public static ByteBuf getContent(String webSocketLocation, String videoId) {
        Properties props = new Properties();
        String script;
        String player;
        try{
            Integer.parseInt(videoId);
            script = "vimeo.html";
            player = "<iframe id=\"player1\" src=\"//player.vimeo.com/video/${videoId}?api=1&player_id=player1\" frameborder=\"0\" webkitallowfullscreen=\"\" mozallowfullscreen=\"\" allowfullscreen=\"\"></iframe>\n";

        } catch(NumberFormatException e){
            script = "youtube.html";
            player = "<div id=\"player\"></div>\n\n" +
                    "<div class=\"elements\">\n" +
                    "\t  \t\t\t\t<span class=\"playToggle\"><a href=\"javascript://\" class=\"play\"> \n" +
                    "\t  \t\t\t\t\t<svg version=\"1.1\" id=\"Play_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"14px\" height=\"100%\" viewBox=\"0 0 373.008 373.008\" xml:space=\"preserve\"> <g> <g id=\"Layer_8_16_\"> <path d=\"M61.792,2.588C64.771,0.864,68.105,0,71.444,0c3.33,0,6.663,0.864,9.655,2.588l230.116,167.2 c5.963,3.445,9.656,9.823,9.656,16.719c0,6.895-3.683,13.272-9.656,16.713L81.099,370.427c-5.972,3.441-13.334,3.441-19.302,0 c-5.973-3.453-9.66-9.833-9.66-16.724V19.305C52.137,12.413,55.818,6.036,61.792,2.588z\"></path></g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g></svg> \n" +
                    "\t  \t\t\t\t</a>\n" +
                    "<a href=\"javascript://\" class=\"pause\">" +
                    "\t  \t\t\t\t\t<svg version=\"1.1\" id=\"Pause_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"14px\" height=\"100%\" viewBox=\"0 0 357 357\" xml:space=\"preserve\"><g><g id=\"pause\"><path d=\"M25.5,357h102V0h-102V357z M229.5,0v357h102V0H229.5z\"></path></g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g></svg>\n" +
                    "</a></span>" +
                    "\t  \t\t\t\t<span class=\"time\"><p id=\"currentTime\">00:00</p>/<p id=\"duration\">00:00</p></span>\n" +
                    "\t                <span class=\"seek\">\n" +
                    "\t                \t<div class=\"progressBar\">\n" +
                    "\t                \t\t<div class=\"bufferBar\"></div>\n" +
                    "<div class=\"timeBar\"></div>" +
                    "   \t\t\t\t\t\t\t<div class=\"timeBar\"></div>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t                </span>\n" +
                    "\t                <span class=\"volume\">\n" +
                    "\t                \t<svg version=\"1.1\" id=\"Volume_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"15px\" height=\"100%\" viewBox=\"0 0 93.038 93.038\" xml:space=\"preserve\"><g><path d=\"M46.547,75.521c0,1.639-0.947,3.128-2.429,3.823c-0.573,0.271-1.187,0.402-1.797,0.402c-0.966,0-1.923-0.332-2.696-0.973 l-23.098-19.14H4.225C1.892,59.635,0,57.742,0,55.409V38.576c0-2.334,1.892-4.226,4.225-4.226h12.303l23.098-19.14 c1.262-1.046,3.012-1.269,4.493-0.569c1.481, .695,2.429,2.185,2.429,3.823L46.547,75.521L46.547,75.521z M62.784,68.919 c-0.103,0.007-0.202,0.011-0.304,0.011c-1.116,0-2.192-0.441-2.987-1.237l-0.565-0.567c-1.482-1.479-1.656-3.822-0.408-5.504 c3.164-4.266,4.834-9.323,4.834-14.628c0-5.706-1.896-11.058-5.484-15.478c-1.366-1.68-1.24-4.12,0.291-5.65l0.564-0.565 c0.844-0.844,1.975-1.304,3.199-1.231c1.192,0.06,2.305,0.621,3.061,1.545c4.977,6.09,7.606,13.484,7.606,21.38\tc0,7.354-2.325,14.354-6.725,20.24C65.131,68.216,64.007,68.832,62.784,68.919z M80.252,81.976 c-0.764,0.903-1.869,1.445-3.052,1.495c-0.058,0.002-0.117,0.004-0.177,0.004c-1.119,0-2.193-0.442-2.988-1.237l-0.555-0.555 c-1.551-1.55-1.656-4.029-0.246-5.707c6.814-8.104,10.568-18.396,10.568-28.982c0-11.011-4.019-21.611-11.314-29.847 c-1.479-1.672-1.404-4.203,0.17-5.783l0.554-0.555c0.822-0.826,1.89-1.281,3.115-1.242c1.163,0.033,2.263,0.547,3.036,1.417 c8.818,9.928,13.675,22.718,13.675,36.01C93.04,59.783,88.499,72.207,80.252,81.976z\"></path></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g></svg>\n" +
                    "\t                \t<div class=\"progressBar\">\n" +
                    "\n" +
                    "   \t\t\t\t\t\t\t<div class=\"timeBar\"></div>\n" +
                    "\t\t\t\t\t\t</div>\n" +
                    "\t                </span>\n" +
                    "\t                <span class=\"hd\"><a href=\"javascript://\" name=\"hd\">HD</a></span>\n" +
                    "\t                <span class=\"full\"><a href=\"javascript://\" class=\"fullscreen\"> \n" +
                    "\t  \t\t\t\t\t<svg version=\"1.1\" id=\"Full_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"14px\" height=\"100%\" viewBox=\"0 0 28.361 28.361\" xml:space=\"preserve\"><g><g id=\"c115_arrows\"><path d=\"M28.36,19.595c0-0.868-0.665-1.57-1.491-1.57c-0.819,0.002-1.492,0.702-1.492,1.57v3.25l-6.018-6.021 c-0.582-0.583-1.524-0.583-2.106,0c-0.582,0.582-0.582,1.527,0,2.109l5.989,5.987h-3.235c-0.881,0.002-1.591,0.669-1.591,1.491 c0,0.824,0.71,1.49,1.591,1.49h6.761c0.881,0,1.59-0.665,1.593-1.49c-0.003-0.022-0.006-0.039-0.009-0.061 c0.003-0.028,0.009-0.058,0.009-0.087v-6.668H28.36z\"></path><path d=\"M9,16.824l-6.015,6.021v-3.25c0-0.868-0.672-1.568-1.493-1.57c-0.824,0-1.49,0.702-1.49,1.57L0,26.264 c0,0.029,0.008,0.059,0.01,0.087c-0.002,0.021-0.006,0.038-0.008,0.061c0.002,0.825,0.712,1.49,1.592,1.49h6.762 c0.879,0,1.59-0.666,1.59-1.49c0-0.822-0.711-1.489-1.59-1.491H5.121l5.989-5.987c0.58-0.582,0.58-1.527,0-2.109 C10.527,16.241,9.584,16.241,9,16.824z\"></path><path d=\"M19.359,11.535l6.018-6.017v3.25c0,0.865,0.673,1.565,1.492,1.568c0.826,0,1.491-0.703,1.491-1.568V2.097 c0-0.029-0.006-0.059-0.009-0.085c0.003-0.021,0.006-0.041,0.009-0.062c-0.003-0.826-0.712-1.491-1.592-1.491h-6.761 c-0.881,0-1.591,0.665-1.591,1.491c0,0.821,0.71,1.49,1.591,1.492h3.235l-5.989,5.987c-0.582,0.581-0.582,1.524,0,2.105 C17.835,12.12,18.777,12.12,19.359,11.535z\"></path><path d=\"M5.121,3.442h3.234c0.879-0.002,1.59-0.671,1.59-1.492c0-0.826-0.711-1.491-1.59-1.491H1.594 c-0.88,0-1.59,0.665-1.592,1.491C0.004,1.971,0.008,1.991,0.01,2.012C0.008,2.038,0,2.067,0,2.097l0.002,6.672 c0,0.865,0.666,1.568,1.49,1.568c0.821-0.003,1.493-0.703,1.493-1.568v-3.25L9,11.535c0.584,0.585,1.527,0.585,2.11,0 c0.58-0.581,0.58-1.524,0-2.105L5.121,3.442z\"></path></g><g id=\"Capa_1_253_\"></g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g><g></g></svg>\n" +
                    "\t  \t\t\t\t</a>" +
                    "<a href=\"javascript://\" class=\"fullscreenExit\">" +
                    "<svg width=\"28.361\" height=\"28.361\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "\n" +
                    " <g>\n" +
                    "  <title>background</title>\n" +
                    "  <rect fill=\"none\" id=\"canvas_background\" height=\"402\" width=\"582\" y=\"-1\" x=\"-1\"/>\n" +
                    " </g>\n" +
                    " <g>\n" +
                    "  <title>Layer 1</title>\n" +
                    "  <g stroke=\"null\" id=\"svg_22\">\n" +
                    "   <path stroke=\"null\" id=\"svg_2\" d=\"m16.33786,23.45839c0,0.5779 0.41797,1.04528 0.93714,1.04528c0.51477,-0.00133 0.93776,-0.46738 0.93776,-1.04528l0,-2.1638l3.78249,4.00869c0.3658,0.38815 0.95788,0.38815 1.32368,0c0.3658,-0.38749 0.3658,-1.01665 0,-1.40414l-3.76426,-3.98605l2.03329,0c0.55373,-0.00133 0.99999,-0.44541 0.99999,-0.99269c0,-0.54861 -0.44625,-0.99202 -0.99999,-0.99202l-4.24948,0c-0.55374,0 -0.99936,0.44275 -1.00125,0.99202c0.00189,0.01465 0.00377,0.02596 0.00566,0.04061c-0.00189,0.01864 -0.00566,0.03862 -0.00566,0.05792l0,4.43946l0.00063,0z\"/>\n" +
                    "   <path stroke=\"null\" id=\"svg_3\" d=\"m7.27681,25.20773l3.7806,-4.00869l0,2.1638c0,0.5779 0.42236,1.04395 0.93839,1.04528c0.51791,0 0.93651,-0.46738 0.93651,-1.04528l0.00126,-4.44012c0,-0.01931 -0.00503,-0.03928 -0.00628,-0.05792c0.00126,-0.01398 0.00377,-0.0253 0.00503,-0.04061c-0.00126,-0.54927 -0.44751,-0.99202 -1.00062,-0.99202l-4.25011,0c-0.55248,0 -0.99936,0.44341 -0.99936,0.99202c0,0.54727 0.44688,0.99135 0.99936,0.99269l2.03329,0l-3.76426,3.98605c-0.36455,0.38749 -0.36455,1.01665 0,1.40414c0.36643,0.38882 0.95913,0.38882 1.3262,0.00067z\"/>\n" +
                    "   <path stroke=\"null\" id=\"svg_4\" d=\"m22.08517,7.51966l-3.78248,4.00603l0,-2.1638c0,-0.57591 -0.423,-1.04195 -0.93776,-1.04395c-0.51917,0 -0.93714,0.46804 -0.93714,1.04395l0,4.44145c0,0.01931 0.00377,0.03928 0.00566,0.0566c-0.00189,0.01398 -0.00377,0.02729 -0.00566,0.04127c0.00189,0.54994 0.44751,0.99268 1.00062,0.99268l4.24948,0c0.55373,0 0.99999,-0.44275 0.99999,-0.99268c0,-0.54661 -0.44625,-0.99202 -0.99999,-0.99335l-2.03329,0l3.76426,-3.98605c0.3658,-0.38683 0.3658,-1.01466 0,-1.40148c-0.3658,-0.39015 -0.95788,-0.39015 -1.32368,-0.00067z\"/>\n" +
                    "   <path stroke=\"null\" stroke-width=\"0\" id=\"svg_5\" d=\"m9.80527,12.81156l-2.03266,0c-0.55247,0.00133 -0.99936,0.44674 -0.99936,0.99335c0,0.54994 0.44689,0.99269 0.99936,0.99269l4.24949,0c0.5531,0 0.99936,-0.44275 1.00061,-0.99269c-0.00125,-0.01399 -0.00377,-0.0273 -0.00502,-0.04128c0.00125,-0.01731 0.00628,-0.03662 0.00628,-0.05659l-0.00126,-4.44213c0,-0.5759 -0.41859,-1.04395 -0.93651,-1.04395c-0.51602,0.002 -0.93839,0.46805 -0.93839,1.04395l0,2.16381l-3.7806,-4.00536c-0.36706,-0.38948 -0.95976,-0.38948 -1.3262,0c-0.36455,0.38682 -0.36455,1.01466 0,1.40148l3.76426,3.98672z\"/>\n" +
                    "   <g stroke=\"null\" id=\"Capa_1_253_\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_6\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_7\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_8\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_9\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_10\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_11\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_12\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_13\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_14\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_15\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_16\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_17\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_18\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_19\"/>\n" +
                    "   <g stroke=\"null\" id=\"svg_20\"/>\n" +
                    "  </g>\n" +
                    " </g>\n" +
                    "</svg></a>" +
                    "</span>\n" +
                    "\t            </div>";
        }
        props.setProperty("websocket-location", webSocketLocation);
        props.setProperty("videoId", videoId);
        try{
            script = Templator.get(script, props);
        }catch (IOException e){
            e.printStackTrace();
        }
        props.setProperty("script", script);
        props.setProperty("player", player);
        String html = "";
        try {
            html = Templator.get("templates/video.html", props);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Unpooled.copiedBuffer(html, CharsetUtil.UTF_8);
    }

    private WebSocketServerIndexPage() {
        // Unused
    }
}

