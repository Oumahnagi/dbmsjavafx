package sample;

public class StringType implements AType {
    String val;
    public void setValue(String value){
        val=value;

    }
    public String getValue(){
    return val;
    }

    @Override
    public String toString() {
        return "STRING";
    }
}
