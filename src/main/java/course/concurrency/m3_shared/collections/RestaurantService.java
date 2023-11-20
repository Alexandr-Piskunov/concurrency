package course.concurrency.m3_shared.collections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class RestaurantService {

    private Map<String, Restaurant> restaurantMap = new HashMap<>() {{
        put("A", new Restaurant("A"));
        put("B", new Restaurant("B"));
        put("C", new Restaurant("C"));
    }};

    private Map<String, LongAdder> stat = new HashMap<>() {{
        put("A", new LongAdder());
        put("B", new LongAdder());
        put("C", new LongAdder());
    }};

    public Restaurant getByName(String restaurantName) {
        addToStat(restaurantName);
        return restaurantMap.get(restaurantName);
    }

    public void addToStat(String restaurantName) {
        stat.computeIfPresent(restaurantName, (key, value) -> {
            value.increment();
            return value;
        });
    }

    public Set<String> printStat() {
        HashSet<String> strings = new HashSet<>();
        strings.add("A - " + stat.get("A"));
        strings.add("B - " + stat.get("B"));
        strings.add("C - " + stat.get("C"));
        return strings;
    }
}
