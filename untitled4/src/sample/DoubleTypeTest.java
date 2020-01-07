package sample;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DoubleTypeTest {
Double xd = 10.;
String x;
    @Test
    public void testSetValue() {

        x= "10";
        assertEquals(xd,testGetValue(x));
    }

    @Test
    public Double testGetValue(String x) {
        return Double.parseDouble(x);
    }


}