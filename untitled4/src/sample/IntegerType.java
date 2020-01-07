package sample;

public class IntegerType implements AType {
Integer val;
    @Override
    public void setValue(String value) {

        val=Integer.parseInt(value);
    }

    @Override
    public String getValue() {
        return (val.toString());
    }
    @Override
    public String toString() {
        return "INTEGER";
    }
}
