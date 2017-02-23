package avatar.events.protocol;

import avatar.Avatar;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public class ServerToClient extends PacketAdapter {

    public ServerToClient() {
        super(Avatar.INSTANCE, ListenerPriority.NORMAL, PacketType.Play.Server.SPAWN_ENTITY_LIVING);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        /*if(event.getPacketType() == PacketType.Play.Server.SPAWN_ENTITY_LIVING){
            PacketContainer packet = event.getPacket();
            UUID entityID = packet.getUUIDs().read(0);
            //System.out.println("id " + entityID);
            Optional<Entity> entity = EntityHiding.findEntity(entityID);
            if(entity.isPresent()){
                System.out.println("PRESENT");
                Optional<User> optional = Avatar.INSTANCE.getUserManager().findUser(entity.get());

                if(optional.isPresent()){
                    if(!optional.get().canSee(event.getPlayer())){
                        event.setCancelled(true);
                    }
                }
            } else System.out.println("NOT PRESENT");
        }*/
    }
}
