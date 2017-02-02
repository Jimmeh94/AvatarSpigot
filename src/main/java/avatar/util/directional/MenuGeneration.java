package avatar.util.directional;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuGeneration {

    public static List<Location> getRelativeLocations(int needed, Player player){
        //n = -z, s = z, w = -x, e = x
        //in degrees, south = 0, w = 90, n = 179 (-79), e = 270 (-90)
        //7x7
        String direction = PlayerDirection.getCardinalDirection(player);
        Location start = player.getLocation().clone();
        Location use;
        List<Location> list = new ArrayList<>();
        Directions d = Directions.W;

        if(direction.contains("n")){ //start with left position first
            start.subtract(3,0,0);
            d = Directions.W;
        } else if(direction.contains("e")){
            start.subtract(0,0,3);
            d = Directions.N;
        } else if(direction.contains("s")){
            start.add(3,0,0);
            d = Directions.E;
        } else if(direction.contains("w")){
            start.add(0,0,3);
            d = Directions.S;
        }
        start.setYaw(d.getYaw());
        list.add(start);
        use = start.clone();

        for(int i = 1; i < needed; i++){
            if(i > 7)
                break;
            use.add(d.getX(), d.getY(), d.getZ());
            d = d.getNext();
            use.setYaw(d.getYaw());
            list.add(use);
            use = use.clone();
        }
        return list;
    }

    private enum Directions{
        //the x y and z are used to transition to next step, do those before getting next
        N(0, 2, 0, 1), //facing south, yaw, x, y, z for next step
        NE(45, 1, 0, 2),
        E(90, -1, 0, 2),
        SE(135, -2, 0, 1),
        S(-180, -2, 0, -1),
        SW(-135, -1, 0, -2),
        W(-90, 1, 0, -2),
        NW(-45, 2, 0, -1);

        private float yaw;
        private int x,y,z;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        Directions(float yaw, int x, int y, int z){
            this.yaw = yaw;
            this.x = x;
            this.y = y;
            this.z = z;

        }

        public float getYaw() {return yaw;}

        public Directions getNext(){
            List<Directions> temp = Arrays.asList(Directions.values());
            int id = temp.indexOf(this);
            if(id == temp.size() - 1){
                return N;
            } else {
                return temp.get(id + 1);
            }
        }

    }



}
