package avatar.events.protocol;

import avatar.Avatar;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

public class ServerToClient extends PacketAdapter {

    public ServerToClient() {
        super(Avatar.INSTANCE, ListenerPriority.NORMAL, PacketType.Play.Server.SPAWN_ENTITY_LIVING);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if(event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING){
            PacketContainer packet = event.getPacket();
        }
    }
}
