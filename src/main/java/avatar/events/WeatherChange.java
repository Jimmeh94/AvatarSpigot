package avatar.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener {

    @EventHandler
    public void onChange(WeatherChangeEvent event){
        if(event.toWeatherState()){ //trying to rain
            event.setCancelled(true);
        }
    }

}
