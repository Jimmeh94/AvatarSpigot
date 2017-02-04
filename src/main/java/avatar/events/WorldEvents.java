package avatar.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldEvents implements Listener {

    @EventHandler
    public void onIgnite(BlockIgniteEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onDecay(LeavesDecayEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onChange(WeatherChangeEvent event){
        if(event.toWeatherState()){ //trying to rain
            event.setCancelled(true);
        }
    }

}
