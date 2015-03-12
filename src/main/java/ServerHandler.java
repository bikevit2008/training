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
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Handles handshakes and messages
 */
    public class ServerHandler extends SimpleChannelInboundHandler<Object>{
    public String RoomUri;
    DB db = DBMaker
            .newFileDB(new File("DB"))
            .closeOnJvmShutdown()
            .make();

    //open an collection, TreeMap has better performance then HashMap
    ConcurrentNavigableMap<String,String> videos = db.getTreeMap("videos");
    static Map<String, ChannelGroup> rooms = new HashMap<String, ChannelGroup>();

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        if(rooms.containsKey(RoomUri)){
            rooms.get(RoomUri).remove(ctx.channel());
            rooms.get(RoomUri).writeAndFlush(new TextWebSocketFrame(JSON.encode("CountUsersOnline", String.valueOf(rooms.get(RoomUri).size()))));
            if(rooms.get(RoomUri).size() == 0){
                rooms.get(RoomUri).clear(); // Надо доделать
                rooms.remove(RoomUri);
            }
        }
    }

    private static final String WEBSOCKET_PATH = "/websocket";

    private WebSocketServerHandshaker handshaker;
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
    }
    ChannelGroup room;
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        String format = req.getUri().toString();
        String substringAfter = format.substring(format.lastIndexOf("m") + 2);
        RoomUri = substringAfter.replace("/", "");

        if(videos.containsKey(RoomUri)){
            if(rooms.containsKey(RoomUri)){

            }
            else{
                room = new DefaultChannelGroup(RoomUri ,GlobalEventExecutor.INSTANCE);
                rooms.put(RoomUri, room);

            }
        }
        else if(req.getUri().toString().replace("/", "").equals("websocket")){

            room = new DefaultChannelGroup("websocket" ,GlobalEventExecutor.INSTANCE);

            rooms.put(RoomUri, room);

            rooms.get(RoomUri).add(ctx.channel());
        }
        else{
            System.out.println(videos.containsKey(RoomUri));
        }
        // Handle a bad request.
        if (!req.getDecoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.getMethod() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }
        if(videos.containsKey(RoomUri)){
        // Send the demo page and favicon.ico
        if (req.getUri().contains("/room/") && !req.getUri().contains("websocket")) {
            ByteBuf content = WebSocketServerIndexPage.getContent(getWebSocketLocation(req), videos.get(RoomUri));
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

            res.headers().set("Content-Type", "text/html; charset=UTF-8");
            HttpHeaders.setContentLength(res, content.readableBytes());
            sendHttpResponse(ctx, req, res);
            return;
            }
        }
         if (req.getUri().equals("/")) {
            ByteBuf content = home.getContent(getWebSocketLocation(req));
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

            res.headers().set("Content-Type", "text/html; charset=UTF-8");
            HttpHeaders.setContentLength(res, content.readableBytes());
            sendHttpResponse(ctx, req, res);
            return;
        }
        else if (req.getUri().equals("/404")) {
            ByteBuf content = notFound.getContent();
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

            res.headers().set("Content-Type", "text/html; charset=UTF-8");
            HttpHeaders.setContentLength(res, content.readableBytes());
            sendHttpResponse(ctx, req, res);
            return;
        }
         else if (req.getUri().contains("/resources")) {

             byte[] data = new byte[0];
             try {
                 data = Files.readAllBytes(Paths.get("./html/resources" + req.getUri().substring(10)));

                 ByteBuf content = Unpooled.copiedBuffer(data);
                 FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
                 sendHttpResponse(ctx, req, res);
                 return;
             } catch (IOException e) {
                 e.printStackTrace();


                 FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FOUND);
                 res.headers().set("Location", "/404");
                 res.headers().set("Content-Type", "text/html; charset=UTF-8");
                 sendHttpResponse(ctx, req, res);
             }
             return;
         }
        else if(req.getUri().contains("websocket")){
        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                getWebSocketLocation(req), null, true);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
        }
        else{

             FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FOUND);
             res.headers().set("Location", "/404");
             res.headers().set("Content-Type", "text/html; charset=UTF-8");
             sendHttpResponse(ctx, req, res);
         }
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass()
                    .getName()));
        }

        // Send the uppercase string back.
        String request = ((TextWebSocketFrame) frame).text();
        System.err.printf("%s received %s%n", ctx.channel(), request);
        System.out.println(rooms.get(RoomUri));
        System.out.println(rooms);
        if(rooms.containsKey(RoomUri)){
            if(!rooms.get(RoomUri).contains(ctx.channel())){
            rooms.get(RoomUri).add(ctx.channel());
            rooms.get(RoomUri).writeAndFlush(new TextWebSocketFrame(JSON.encode("CountUsersOnline", String.valueOf(rooms.get(RoomUri).size()))));

        }
        }
        if(rooms.get("websocket") != null){
        if(rooms.get("websocket").contains(ctx.channel()))
        {
            String url = dateTimeModel.GenerateUrl();
            videos.put(url, JSON.parseValue(request));
            db.commit();
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.encode("RoomLink", url)));
            System.out.println(url);
        }
        }
        if(rooms.containsKey(RoomUri)){
        for (Channel ch : rooms.get(RoomUri))
            if (!ch.equals(ctx.channel()))
            {
                if(JSON.parseMethod(request).equals("Play")){
                    ch.writeAndFlush(new TextWebSocketFrame(JSON.encode("Play", "")));
                }
                else if(JSON.parseMethod(request).equals("Pause")){
                    ch.writeAndFlush(new TextWebSocketFrame(JSON.encode("Pause", "")));
                } else if(JSON.parseMethod(request).equals("CurrentTime")){
                    ch.writeAndFlush(new TextWebSocketFrame(JSON.encode("CurrentTime", JSON.parseValue(request))));
                }
                else if (JSON.parseMethod(request).equals("Buffering")){
                    ch.writeAndFlush(new TextWebSocketFrame(JSON.encode("Buffering", "")));
                }
            }
        }



    }



    private static void sendHttpResponse(
            ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(res, res.content().readableBytes());
        }

        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private String getWebSocketLocation(FullHttpRequest req) {
        String location = WEBSOCKET_PATH + req.getUri();
            return location;
    }
}