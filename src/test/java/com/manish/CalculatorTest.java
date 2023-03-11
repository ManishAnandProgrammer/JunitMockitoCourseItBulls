package com.manish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void whenProvidingTwoAndThreeItShouldReturnFive() {
        Calculator calculator = new Calculator();
        int actualResult = calculator.add(2, 3);
        int expectedResult = 5;

        Assertions.assertEquals(expectedResult, actualResult);
    }
}