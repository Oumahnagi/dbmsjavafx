package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DB {
    String name;
    //ArrayList <Table> tables = new ArrayList<Table>();

    public DB(String name) {
        this.name = name;
    }

    ObservableList<Table> tableList = FXCollections.observableArrayList();
    public void load(){

    }

    public String getname(){
return name;
    }

    public void createTable(String name,ObservableList<Column>columns){
    Table table=new Table(name,columns);
    tableList.add(table);
    }
    public void deleteTable(){

    }
    public ObservableList<Table> getTables(){
        return tableList;
    }
    public void tableDif(){

    }

    @Override
    public String toString() {
        return this.name;
    }

}
