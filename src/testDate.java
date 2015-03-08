import java.util.*;
import java.text.*;

public class DateDemo {
   public static void main(String args[]) {
    String startDateString = "06/27/2007";
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
    Date startDate;
    try {
        startDate = df.parse(startDateString);
        String newDateString = df.format(startDate);
        System.out.println(newDateString);
    } catch (ParseException e) {
        e.printStackTrace();
    }
   }
}
