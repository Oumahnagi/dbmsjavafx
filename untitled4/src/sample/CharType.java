package sample;

public class CharType implements AType {
Character val;

    @Override
    public void setValue(String value) {
        val=value.charAt(0);

    }

    @Override
    public String getValue() {
        return val.toString();
    }
    @Override
    public String toString() {
        return "CHAR";
    }
}
