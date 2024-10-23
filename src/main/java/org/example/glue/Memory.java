package org.example.glue;

import java.util.Map;
import java.util.TreeMap;

public class Memory {
    Map<String, String> memory;

    public Memory() {
        memory = new TreeMap<>();
    }

    public void set(String key, String value) {
        memory.put(key, value);
    }

    public String get(String key) {
        return memory.get(key);
    }

    @Override
    public String toString() {
        return "Memory" +
                memory;
    }
}
