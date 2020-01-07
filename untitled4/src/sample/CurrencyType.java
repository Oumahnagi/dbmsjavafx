package sample;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyType implements AType {
    String strval="";
    //Double realval;
    BigDecimal realval;
    @Override
    public void setValue(String value) {
        //String substring="";
        //realval=Double.parseDouble(value);
        realval=new BigDecimal(value);
        String[]valarr = new String[2];
        if (value.contains(".")){
            valarr=value.split("\\.");
        }else {
            valarr[0]=value;
            valarr[1]="empty";
        }
        for(int i=valarr[0].length()-1;i>=0;i--){
            if(i%3==2&&i!=valarr[0].length()-1)strval+=",";
            strval+=valarr[0].charAt(valarr[0].length()-i-1);
        }
        if (!valarr[1].equals("empty"))strval+="."+valarr[1];

    }

    @Override
    public String getValue() {
        return strval;
    }
    @Override
    public String toString() {
        return "MONEY";
    }
    public BigDecimal getRealval(){
        return realval;
    }
}
