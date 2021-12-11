package dev.mrlich.dogeshop.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class MapUtil {

    @SafeVarargs
    public <K, V> Map<K, V> mergeMaps(Map<K, V>... maps) {
        Map<K, V> resultMap = new HashMap<>();
        for (Map<K, V> map : maps) {
            resultMap.putAll(map);
        }
        return resultMap;
    }

}
