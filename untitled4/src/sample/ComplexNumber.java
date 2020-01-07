package sample;

public class ComplexNumber implements AType {
    public double x, y;

    @Override
    public void setValue(String value) {
        String[] numbers=value.split(",");
        this.x=Double.parseDouble(numbers[0]);
        this.y=Double.parseDouble(numbers[1]);
    }

    @Override
    public String getValue() {
        return "{"+Double.toString(x)+","+Double.toString(y)+"}";
    }
    @Override
    public String toString() {
        return "COMPLEX REAL";
    }

        public double real() {
            return x;
        }

        public double imaginary() {
            return y;
        }

        public double magnitude() {
            return Math.sqrt(x * x + y * y);
        }

     /*   public String toString() {
            return "{" + x + "," + y + "}";
        }*/
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

