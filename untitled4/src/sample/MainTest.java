package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

import java.io.*;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class MainTest {

    @Test
            public void saveDBtest()throws IOException{
        ObservableList list = mock(ObservableList.class);
        DB base = mock(DB.class);
        when(base.getname()).thenReturn("mocktestsample");
        DB db = loadData(base.getname(),list);
        when(list.get(0)).thenReturn(db);

        ArrayList<PrintWriter>printWriters=new ArrayList<>();
        DB loaded = loadData("mocktestsaved",list);
        saveDB(db,printWriters);

        DB expected= loadData("mocktestsaved",list);
        //expected.name = "mocktestsaved";
        Table first = loaded.tableList.get(0);
        Table second = expected.tableList.get(0);
        boolean b=true;
        for(int i=0;i<first.rows.size();i++){

                if(!compare(first.rows.get(i),second.rows.get(i)))b=false;

        }
        assertTrue(b);




    }
    private boolean compare(Row first,Row second){
        //boolean check = false;
        for(int i=0;i<first.Cells.size();i++){
            if(!first.Cells.get(i).getValue().equals(second.Cells.get(i).getValue()))return false;

        }
        return true;
    }
    private void saveDB(DB data,ArrayList<PrintWriter>printWriters)throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("mocktestsaved" + ".txt"));

        for (int i=0;i<data.tableList.size();i++){
            writer.write(data.tableList.get(i).name+":");
            for (int j=0;j<data.tableList.get(i).columns.size();j++){
                writer.write(data.tableList.get(i).columns.get(j).name+
                        " "+data.tableList.get(i).columns.get(j).type+";");
            }
            writer.newLine();
            for (int k=0;k<data.tableList.get(i).rows.size();k++) {
                for (int j=0;j<data.tableList.get(i).columns.size();j++){
                    writer.write(data.tableList.get(i).rows.get(k).Cells.get(j).getValue() + " ");
                }
                writer.newLine();
            }
            writer.newLine();
        }
        writer.write(".end.");
        writer.close();

    }
    private  DB loadData(String name, ObservableList<DB> databaseList) throws FileNotFoundException,IOException{
        BufferedReader reader =  new BufferedReader(new FileReader(name+".txt"));
        DB loadin = new DB(name);
        databaseList.add(loadin);
        int index = databaseList.indexOf(loadin);
        String line;
        while (!(line=reader.readLine()).equals(".end.")){
            if(line.contains(":")){
                ObservableList<Column>cols= FXCollections.observableArrayList();
                String []strcols=line.split(":")[1].split(";");
                for(int i=0;i<strcols.length;i++){
                    Column column = new Column(strcols[i].split(" ")[0],strcols[i].split(" ")[1]);
                    cols.add(column);
                }
                Table table = new Table(line.split(":")[0],cols);
                while (!(line=reader.readLine()).contains(":")&!line.equals("")){
                    Row row = new Row(cols);
                    for (int i=0;i<table.columns.size();i++){
                        row.Cells.get(i).setValue(line.split(" ")[i]);
                    }
                    table.rows.add(row);
                }
                loadin.tableList.add(table);
            }


        }
        return loadin;
    }


}