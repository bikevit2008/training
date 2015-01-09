/**
 * Created by Vitaly on 07.01.15.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

public class dateTimeModel {
    public static void main(String[] args){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyMMddHHmmssSSS");
        String date = ft.format(dNow);
        System.out.println("URL: " + replace(date));
        System.out.println("CurrentTime yyMMddHHmmssSSS: " + date);

    }
    public static StringBuilder replace(String date){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i<15; i++){
            result.append(replaceSymbol(date.substring(i, i + 1)));
        }
        return result;
    }
    public static String replaceSymbol(String dateSymbol){
        String symbols = "abcdefghij";
        int index = Integer.parseInt(dateSymbol);
        String replace = symbols.substring(index, index + 1);
        return replace;
    }
}
