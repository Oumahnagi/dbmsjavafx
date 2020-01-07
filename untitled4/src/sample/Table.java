package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Table {
   //ArrayList<Row> rows = new ArrayList<>();
    ObservableList<Row> rows = FXCollections.observableArrayList();
    ObservableList<Column> columns;// = FXCollections.observableArrayList();
    String name;

    @Override
    public String toString() {
        return name ;
    }

    public Table(String name, ObservableList<Column> columns){
        this.name=name;
        this.columns=columns;
    }
    public String getName() {
        return name;
    }

    public void addRow(){
        rows.add(new Row(columns));

    }
    public void editRow(){

    }
    public void deleteRow(){

    }
    public void searchByPattern(){

    }




}
