package sample;

import java.math.BigDecimal;

public class CurrencyInvlType extends CurrencyType{
    BigDecimal from,to;
    String[] val=new String[2];

    @Override
    public String getValue() {
        if(Double.parseDouble(val[0])>Double.parseDouble(val[1])) return val[1]+":"+val[0];
        else return val[0]+":"+val[1];
    }
    @Override
    public String toString() {
        return "MONEY INVL";
    }
    @Override
    public void setValue(String value) {
        val[0]="";
        val[1]="";
        String[] nums=value.split(":");
        from=new BigDecimal(nums[0]);
        to=new BigDecimal(nums[1]);

        for(int i=0;i<nums.length;i++) {
            String[] valarr = new String[2];
            if (nums[i].contains(".")) {
                valarr = nums[i].split("\\.");
            } else {
                valarr[0] = nums[i];
                valarr[1] = "empty";
            }
            for (int j = valarr[0].length() - 1; j >= 0; j--) {
                if (j % 3 == 2 && j != valarr[0].length() - 1) strval += ",";
                val[i] += valarr[0].charAt(valarr[0].length() - j - 1);
            }
            if (!valarr[1].equals("empty")) val[i] += "." + valarr[1];

        }
    }
}
