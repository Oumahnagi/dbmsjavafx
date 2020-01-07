package sample;

public class ComplexIntNumber implements AType {
int x,y;
    @Override
    public void setValue(String value) {
        String[] numbers=value.split(",");
        this.x=Integer.parseInt(numbers[0]);
        this.y=Integer.parseInt(numbers[1]);
    }

    @Override
    public String getValue() {
        return "{"+Integer.toString(x)+","+Integer.toString(y)+"}";
    }
    @Override
    public String toString() {
        return "COMPLEX INTEGER";
    }
    public int real() {
        return x;
    }

    public int imaginary() {
        return y;
    }
    public  ComplexNumber add(ComplexNumber a, ComplexNumber b) {

        ComplexNumber c = new ComplexNumber();
        c.x=a.x+b.x;
        c.y=a.y+b.y;
        return c;
    }
    public  ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
        ComplexNumber c = new ComplexNumber();
        c.x=a.x * b.x - a.y * b.y;
        c.y=a.x * b.y + a.y * b.x;
        return c;
    }


}
