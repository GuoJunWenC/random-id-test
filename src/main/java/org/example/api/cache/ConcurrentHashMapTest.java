package org.example.api.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    private final Map<String, Object> cache;

    public ConcurrentHashMapTest() {
        cache = new ConcurrentHashMap<>();
    }
    public Object get(String key) {
        return cache.get(key);
    }
    public void put(String key, Object value) {
        cache.put(key, value);
    }
    public void clear() {
        cache.clear();
    }

}
