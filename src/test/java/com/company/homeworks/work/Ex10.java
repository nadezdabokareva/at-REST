package com.company.homeworks.work;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class Ex10 {

    @Test
    public void ex10(){
        int count = new Random().nextInt(20);
        String varString = RandomStringUtils.randomAlphabetic(count);

        Assertions.assertTrue(varString.length() > 15, "длина меньше 15");
    }
}
