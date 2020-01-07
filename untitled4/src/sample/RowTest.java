package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class RowTest {
    ObservableList<AType> Cells = FXCollections.observableArrayList();
    String m = "STRING";
    @Test
    public void testGetRow() {


        if(m.equals("STRING"))    Cells.add(new StringType());
        assertEquals(Cells.size(),1);
    }
}