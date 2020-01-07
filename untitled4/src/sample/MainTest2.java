package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class MainTest2 {
    @Test
            public void diftest(){

        Table diftable = mock(Table.class);
        ObservableList<Column> m = mock(ObservableList.class);
        int i=0;
        ObservableList<Column>testlist = FXCollections.observableArrayList();

        ObservableList<Row>testrowlist = FXCollections.observableArrayList();
        Column testcol = new Column("test","STRING");
        testlist.add(testcol);
        Row test = new Row(testlist);
        diftable.rows = testrowlist;
        testrowlist.add(test);
        when(m.get(i)).thenReturn(testcol);
        testlist.remove(m.get(i));
         assertEquals(testlist.size(),0);
        //data.tableList.add(diftable);
    }
}