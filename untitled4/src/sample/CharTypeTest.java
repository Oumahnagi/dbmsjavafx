package sample;

import org.testng.annotations.Test;
import org.mockito.Mockito.*;

import java.util.Iterator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class CharTypeTest {

    @Test
    public void testSetValue() {
        String val = "test";
        Character v = 'q';
        v=val.charAt(0);
        assertNotEquals(val.charAt(0),'q');


    }
}