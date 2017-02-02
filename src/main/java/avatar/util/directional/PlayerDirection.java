package avatar.util.directional;


import avatar.util.text.AltCodes;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerDirection {

    /*
     * Gets string representation of player's direction
     */
    public static String getCardinalDirection(Entity entity) {

        double rotation = entity.getLocation().getYaw() % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return "n";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "ne";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "e";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "se";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "s";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "sw";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "w";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "nw";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "n";
        } else {
            return null;
        }
    }

    /*
     * Gets the string representation of the opposite direction of current direction
     */
    public static String reflect(String d){
        d = d.toLowerCase();
        switch(d){
            case "n": return "s";
            case "e": return "w";
            case "s": return "n";
            case "w": return "e";
            case "ne": return "sw";
            case "nw": return "se";
            case "se": return "nw";
            case "sw": return "ne";
        }
        return null;
    }

    /*
     * Returns arrow pointing towards the desired location. Used for quest-target-location tracker
     */
    public static String getDesiredDirection(Player player, Location target) {
        //n = -z, s = z, w = -x, e = x
        Location at = player.getLocation();
        String current = getCardinalDirection(player);
        if(current.length() > 1)
            current = current.substring(0, 1);
        String give = "";
        String result = null;
        double deltaZ = Math.max(Math.abs(target.getZ()), Math.abs(at.getZ())) - Math.min(Math.abs(target.getZ()), Math.abs(at.getZ()));
        double deltaX = Math.max(Math.abs(target.getX()), Math.abs(at.getX())) - Math.min(Math.abs(target.getX()), Math.abs(at.getX()));

        if(deltaZ >= deltaX){
            if(target.getZ() < at.getZ()){ //need to go to z more
                give = "s";
            } else if(target.getZ() > at.getZ()){ //need to go to z more
                give = "n";
            }
        } else {
            if(target.getX() < at.getX()){ //need to go to z more
                give = "e";
            } else if(target.getX() > at.getX()){
                give = "w";
            }
        }

        int rotate = 0;
        while(!current.equalsIgnoreCase(give)){
            rotate++;
            if(current.equalsIgnoreCase("n"))
                current = "e";
            else if(current.equalsIgnoreCase("e"))
                current = "s";
            else if(current.equalsIgnoreCase("s"))
                current = "w";
            else if(current.equalsIgnoreCase("w"))
                current = "n";
        }

        switch (rotate){
            case 0: result = AltCodes.ARROW_UP.getSign();
                break;
            case 1: result = AltCodes.ARROW_RIGHT.getSign();
                break;
            case 2: result = AltCodes.ARROW_DOWN.getSign();
                break;
            case 3: result = AltCodes.ARROW_LEFT.getSign();
        }

        String text;
        if(player.getLocation().getBlockY() < target.getBlockY()){
           text = ChatColor.GREEN + result;
        } else if(player.getLocation().getBlockY() > target.getBlockY()){
            text = ChatColor.RED +  result;
        } else {
            text = ChatColor.GRAY + result;
        }
        return text;
    }
}
