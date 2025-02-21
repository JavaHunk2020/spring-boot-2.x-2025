package cp.spring.security;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatExample {
    public static void main(String[] args) throws Exception {
        String inputDate = "99/27/2025";
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy");

        Date date = inputFormat.parse(inputDate);
        String formattedDate = outputFormat.format(date);

        System.out.println(formattedDate); // Output: 02/27/2025
    }
}
