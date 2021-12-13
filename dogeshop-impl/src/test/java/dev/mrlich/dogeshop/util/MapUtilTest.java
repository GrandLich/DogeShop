package dev.mrlich.dogeshop.util;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MapUtilTest {

    @Test
    void mergeMapsTest() {
        Map<String, String> firstMap = generateStringMap();
        Map<String, String> secondMap = generateStringMap();

        Map<String, String> actual = MapUtil.mergeMaps(firstMap, secondMap);

        assertNotNull(actual);
        assertEquals(actual.size(), firstMap.size() + secondMap.size());
        assertTrue(actual.keySet().containsAll(firstMap.keySet()));
        assertTrue(actual.keySet().containsAll(secondMap.keySet()));
    }

    private Map<String, String> generateStringMap() {
        return Map.of(
                UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), UUID.randomUUID().toString()
        );
    }

}
