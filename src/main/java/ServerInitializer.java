import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipe = ch.pipeline();

        pipe.addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(65536), // 1048576

                new ChunkedWriteHandler(),
                // new HttpContentCompressor(),
                new ServerHandler()
        );
    }
}