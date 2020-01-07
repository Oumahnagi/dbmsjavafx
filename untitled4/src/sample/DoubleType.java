package sample;

public class DoubleType implements AType {
    Double val;
    @Override
    public void setValue(String value) {
        val=Double.parseDouble(value);
    }

    @Override
    public String getValue() {
        return val.toString();
    }
    @Override
    public String toString() {
        return "REAL";
    }
}
