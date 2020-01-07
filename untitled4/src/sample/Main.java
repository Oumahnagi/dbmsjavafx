package sample;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    //String[] AllTypes={"INTEGER","CHAR","REAL","STRING","MONEY","MONEY INVL","COMPLEX INTEGER","COMPLEX REAL"};
    ObservableList<String>AllTypes=FXCollections.observableArrayList("INTEGER","CHAR","REAL","STRING","MONEY","MONEY INVL","COMPLEX INTEGER","COMPLEX REAL");
    FlowPane root;
    Button createDB;
    Button editDB;
    Button saveDB;
    Button loadDB;
    ObservableList<DB> databaseList;
    ListView<DB> viewDB;
    Button tableDif = new Button("dif");
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Database Management System");
        root = new FlowPane();
        createDB = new Button("Create DB");
        saveDB = new Button("Save");
        TextField load = new TextField("load...");

        int dbtxtiter=1;
        //
        // PrintWriter writer = new PrintWriter("db"+".txt", "UTF_8");
        ArrayList<PrintWriter>printWriters=new ArrayList<>();
        saveDB.setOnAction(e->{try {
            saveDB(viewDB.getSelectionModel().getSelectedItem(), printWriters);
        }catch (IOException x){ }

            //printWriters.add(new PrintWriter("db"+".txt", "UTF_8"));
        });
        load.setOnAction(ee->{try {
            loadData(load.getText(), databaseList);
            viewDB.refresh();
        }catch (Exception x){

        }
        });
        editDB = new Button("Edit DB");
        root.getChildren().addAll(createDB, editDB,saveDB,load);
        databaseList = FXCollections.observableArrayList();
        viewDB = new ListView<DB>(databaseList);
        viewDB.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        editDB.setOnAction(editevent -> this.editDBAction());
        root.getChildren().add(viewDB);
        createDB.setOnAction(createDBevent -> createDBAction(primaryStage));
        readFromCloud();
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //https://drive.google.com/open?id=1A6ezM6U6gYoj0q6bL9nTVUT6jle38wBh
    }
    private void saveDB(DB data,ArrayList<PrintWriter>printWriters)throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(data.name + ".txt"));

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
    private  void loadData(String name,ObservableList<DB> databaseList) throws FileNotFoundException,IOException{
        BufferedReader reader =  new BufferedReader(new FileReader(name+".txt"));
        DB loadin = new DB(name);
        databaseList.add(loadin);
        int index = databaseList.indexOf(loadin);
        String line;
        while (!(line=reader.readLine()).equals(".end.")){
            if(line.contains(":")){
                ObservableList<Column>cols=FXCollections.observableArrayList();
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
    }
    private void readFromCloud() throws IOException {
        URL oracle = new URL("https://drive.google.com/uc?export=download&id=1A6ezM6U6gYoj0q6bL9nTVUT6jle38wBh");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        BufferedWriter writer = new BufferedWriter(new FileWriter("cloud" + ".txt"));

        while (!(inputLine = in.readLine()).equals( ".end.")) {
            if(inputLine==null)writer.newLine();
            writer.write(inputLine);
            writer.newLine();
        }
        writer.write(".end.");
        writer.close();
        in.close();
    }
    private void editDBAction() {

        Button createTable;
        Button editTable;
        Button deleteTable;
        DB selected = viewDB.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            return;
        }
        ListView<Table> viewTables = new ListView<>(selected.getTables());
        Button tableDif = new Button("dif");
        tableDif.setOnAction(ee->{
            Table first = viewTables.getSelectionModel().getSelectedItem();
            FlowPane difPane = new FlowPane();
            Scene difScene = new Scene(difPane,500,300);
            Label difLabel = new Label("Choose another one");
            Stage difStage = new Stage();
            difStage.setTitle("Choose another one");
            Button ok = new Button("ok");
            ok.setOnAction(eee->{
                tableDif(selected,first,viewTables.getSelectionModel().getSelectedItem());
                difStage.close();
            });
            difPane.getChildren().addAll(viewTables,ok);
            difStage.setScene(difScene);
            difStage.show();

        });
        createTable = new Button("Add Table");
        editTable = new Button("Edit Table");
        deleteTable = new Button("Delete Table");
        viewTables.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        editTable.setOnAction(editTableEvent->editTableAction(viewTables.getSelectionModel().getSelectedItem()));
        FlowPane editingPane = new FlowPane();
        deleteTable.setOnAction(e->{
            selected.tableList.remove(viewTables.getSelectionModel().getSelectedItem());
        });
        Scene editingScene = new Scene(editingPane, 500,300);
        Label editingDBName = new Label("Editing " + selected);
        Stage editingStage = new Stage();

        createTable.setOnAction(createTableEvent->{
            createTableAction(selected);
            viewTables.refresh();
        });
        editingStage.setTitle("Editing DB");
        editingPane.getChildren().addAll(editingDBName,viewTables,createTable,editTable,deleteTable,tableDif);
        editingStage.setScene(editingScene);
        editingStage.show();

    }

    private void tableDif(DB data,Table first,Table second){
        Table diftable = new Table(first.name+"-"+second.name,first.columns);
        diftable.rows=first.rows;
        //for(int i=0;i<first.rows.size();i++){
        //  diftable.rows.add(first.rows.get(i));
        // }
        for(int i=0;i<first.rows.size();i++){
            for(int j=0;j<second.rows.size();j++){
                if(compare(first.rows.get(i),second.rows.get(j)))diftable.rows.remove(i);
            }
        }
        //data.tableList.add(diftable);
    }//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    private boolean compare(Row first,Row second){
        //boolean check = false;
        for(int i=0;i<first.Cells.size();i++){
            if(!first.Cells.get(i).getValue().equals(second.Cells.get(i).getValue()))return false;

        }
        return true;
    }

    private void editTableAction(Table selectedTable){
        // FlowPane editPane = new FlowPane();
        int theGreatIteratorOfRows=0;

        GridPane editPane = new GridPane();
        ScrollPane scrollPane = new ScrollPane(editPane);
        Stage editStage = new Stage();
        editStage.setTitle("Editing "+selectedTable.name);
        Scene editScene = new Scene(scrollPane,500, 300);
        TableView<Row> tableEdit = new TableView<>(selectedTable.rows);

        tableEdit.setEditable(true);

        for (int i = 0; i < selectedTable.columns.size(); i++){
            Label columnName = new Label(selectedTable.columns.get(i).name);
            columnName.setMinHeight(30);
            columnName.setMinWidth(60);
            GridPane.setConstraints(columnName, i, theGreatIteratorOfRows);
            editPane.getChildren().add(columnName);
        }
        ObservableList<ObservableList<TextField>> grid = FXCollections.observableArrayList(FXCollections.observableArrayList());
        TextField searchField = new TextField("search...");
        searchField.setOnAction(e->{
            for (int i=0;i<selectedTable.rows.size();i++){
                for (int j=0;j<selectedTable.columns.size();j++){
                    grid.get(i).get(j).setStyle("-fx-text-inner-color: black;");
                }
            }
            for (int i=0;i<selectedTable.rows.size();i++){
                for (int j=0;j<selectedTable.columns.size();j++){
                    if(grid.get(i).get(j).getText().equals(searchField.getText())){
                        /*grid.get(i).add(0,grid.get(i).get(j));
                        grid.get(i).remove(i+1,j+1);*/
                        grid.get(i).get(j).setStyle("-fx-text-inner-color: green;");
                    }
                }
            }
        });
        ObservableList<Button>buttons = FXCollections.observableArrayList();
        for(int i=0;i<selectedTable.rows.size();i++){
            int iter=i;
            Button deleteButton = new Button("-");
            buttons.add(deleteButton);
            editPane.getChildren().add(deleteButton);
            GridPane.setConstraints(deleteButton,selectedTable.columns.size(),i+1);
            deleteButton.setOnAction(ee->{
                selectedTable.rows.remove(iter);
                tableEdit.refresh();
                editPane.getChildren().remove(iter);
                editStage.close();
                editStage.setScene(editScene);
                editStage.show();
            });
        }
        //deleteButton.setOnAction(e->{

        //});
        for (int i=0; i<selectedTable.rows.size();i++){
            ObservableList<TextField> gridrow = FXCollections.observableArrayList();
            grid.add(gridrow);
            for (int j=0;j<selectedTable.columns.size();j++){
                TextField text = new TextField(selectedTable.rows.get(i).getCell(j).getValue());
                if(text.getText().equals("")){
                    text.setText(".");
                }
                gridrow.add(text);
                AType cell = selectedTable.rows.get(i).getCell(j);
                text.setOnAction(e->{
                    for (int a=0;a<selectedTable.rows.size();a++){
                        for (int b=0;b<selectedTable.columns.size();b++){
                            grid.get(a).get(b).setStyle("-fx-text-inner-color: black;");
                        }
                    }
                    try {
                        cell.setValue(text.getText());
                    }catch (Exception x){
                        text.setStyle("-fx-text-inner-color: red;");
                        return;
                    }
                    //if();
                });
                GridPane.setConstraints(text,j,i+1);
                if(!gridrow.get(gridrow.size()-1).getText().equals(""))editPane.getChildren().add(text);
            }
        }
        Great_Recurator(selectedTable,grid,editPane);
        GridPane.setConstraints(searchField,selectedTable.columns.size()+1,0);
        editPane.getChildren().addAll(searchField);



        //editPane.getChildren().add(saveButton);
        editStage.setScene(editScene);
        editStage.show();



    }

    private void Great_Recurator(Table selectedTable,ObservableList<ObservableList<TextField>> grid,GridPane editPane){
        ObservableList<TextField> gridrowNew = FXCollections.observableArrayList();
        String[] values = new String[selectedTable.columns.size()];
        for(int i=0;i<values.length;i++){
            values[i]="";
        }
        for (int i=0; i<values.length; i++){
            TextField newText = new TextField();
            gridrowNew.add(newText);
            int iter=i;
            newText.setOnAction(e->{
                values[iter]=newText.getText();
                for(int j=0; j<values.length; j++){
                    if(values[j].equals("")) {
                        return;
                    }
                }
                int inet=0;
                selectedTable.addRow();
                try {

                    for(int j=0; j<selectedTable.columns.size(); j++){
                        inet=j;
                        selectedTable.rows.get(selectedTable.rows.size() - 1).getCell(j).setValue(values[j]);

                    }
                }catch (Exception x) {
                    gridrowNew.get(inet).setStyle("-fx-text-inner-color: red;");
                    selectedTable.rows.remove(selectedTable.rows.size()-1);
                    return;
                }

                Great_Recurator(selectedTable,grid,editPane);

            });
            GridPane.setConstraints(newText,i,selectedTable.rows.size()+1);
            editPane.getChildren().add(newText);
        }
        grid.add(gridrowNew);
    }

    private  void deleteRowEvent(Table table,ObservableList<ObservableList<TextField>>grid){


    }

    private void createDBAction(Stage primaryStage) {

        Button dbNameok= new Button("ok");

        Label secondLabel = new Label("Enter name");

        FlowPane secondaryLayout = new FlowPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        TextField dbName = new TextField();

        secondaryLayout.getChildren().add(dbName);
        Stage newWindow = new Stage();
        secondaryLayout.getChildren().add(dbNameok);
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);
        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX());
        newWindow.setY(primaryStage.getY());

        newWindow.show();
        dbNameok.setOnAction(event1 ->
        {
            databaseList.add(new DB(dbName.getText()));
            newWindow.close();
        });
    }

    private void createTableAction(DB selectedDB){
        FlowPane addTablePane = new FlowPane();
        Scene addTableScene = new Scene(addTablePane, 500,300);
        Label addTableTableName = new Label("Enter table name");
        Button addTableok=new Button("ok");
        TextField addTablename = new TextField();
        //Button addColumnButon = new Button("Add Column");
        //addColumnButon.setOnAction(addColumnEvent -> addColumnAction(selectedDB));
        Stage addTableStage = new Stage();
        addTableStage.setTitle("Adding table");
        addTableStage.setScene(addTableScene);
        Button addColumn=new Button("+");
        TextField addColumnname = new TextField();
        ComboBox<String>addColumntype = new ComboBox(AllTypes);
        ObservableList<Column> columnList = FXCollections.observableArrayList();
        TableView<Column> table = new TableView<Column>(columnList);
        TableColumn<Column,String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<Column, String>("name"));
        table.getColumns().add(name);
        TableColumn<Column,String> type = new TableColumn<>("Type");
        type.setCellValueFactory(new PropertyValueFactory<Column, String>("type"));
        table.getColumns().add(type);
        addColumn.setOnAction(addColumnEvent->addColumnAction(columnList,addColumnname.getText(),addColumntype.getSelectionModel().getSelectedItem()));
        addTableok.setOnAction(saveTableEvent->{selectedDB.createTable(addTablename.getText(),columnList);
            addTableStage.close();
        });
        addTablePane.getChildren().addAll(addTableTableName,addTablename,addTableok,addColumnname,addColumntype,addColumn,table);
        addTableStage.show();
    }

    private void addColumnAction(ObservableList<Column> columnList,String name,String type) {
        columnList.add(new Column(name, type));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

