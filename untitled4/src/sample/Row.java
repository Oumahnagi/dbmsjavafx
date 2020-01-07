package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;

import java.util.ArrayList;

public class Row {
    //ArrayList<AType>Cells=new ArrayList<>();
    ObservableList<AType> Cells = FXCollections.observableArrayList();
    public Row(ObservableList<Column> columns){
        for(int i=0;i < columns.size();i++){
            if(columns.get(i).getType().equals("STRING")){
                Cells.add(new StringType());
            }else if(columns.get(i).getType().equals("INTEGER")){
                Cells.add(new IntegerType());
            }
            else if(columns.get(i).getType().equals("CHAR")){
                Cells.add(new CharType());
            }
            else if(columns.get(i).getType().equals("REAL")){
                Cells.add(new DoubleType());
            }
            else if(columns.get(i).getType().equals("MONEY")){
                Cells.add(new CurrencyType());
            }
            else if(columns.get(i).getType().contains("COMPLEX REAL")){
                Cells.add(new ComplexNumber());
            }
            else if(columns.get(i).getType().contains("COMPLEX INTEGER")){
                Cells.add(new ComplexIntNumber());
            }
            else if(columns.get(i).getType().contains("MONEY INVL")){
                Cells.add(new CurrencyInvlType());
            }
        }
    }

    public void getRow(){

    }
    public AType getCell(int index){
        return Cells.get(index);
    }
    public void editCell(){

    }

}
