package dev.mrlich.dogeshop.util.test;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class TestUtils {

    public long randomLong() {
        return new Random().nextLong();
    }

}
