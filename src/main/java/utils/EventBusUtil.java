package utils;

import com.google.common.eventbus.EventBus;

public class EventBusUtil {


    private EventBusUtil() {
    }

    private  final static EventBus bus = new EventBus();

    public static EventBus getDefault(){
        return bus;
    }
}
