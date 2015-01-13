import io.netty.channel.Channel;

import java.util.ArrayList;

/**
 * Created by Vitaly on 13.01.15.
 */
public class Komnata {
    ArrayList<Channel> channels;

    public void addChannel(Channel ch){
        channels.add(ch);
    }
}
