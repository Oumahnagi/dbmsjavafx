package sample;

public class Column {
    String name;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
