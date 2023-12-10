package tsp;

import java.util.ArrayList;
import java.util.List;

public class HashMap<K, V> {
    private List<CustomEntry<K, V>> entries = new ArrayList<>();

    public void put(K key, V value) {
        for (CustomEntry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        entries.add(new CustomEntry<>(key, value));
    }

    public V get(K key) {
        for (CustomEntry<K, V> entry : entries) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    private static class CustomEntry<K, V> {
        private final K key;
        private V value;

        public CustomEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}