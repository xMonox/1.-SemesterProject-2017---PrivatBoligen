package logic.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


//Used to format Myqsldate to LocalDate and vice versa
public class DateService {
    //Bertram
    //Takes a Mysqldate as a string, formats the date, creates a LocalDate object from Mysqldate, returns it formatted as a String.
    public String formatMysqlDateToLocalDateString(String mysqlDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(mysqlDate);
        return formatter.format(localDate).toString();
    }
    public String formatLocalDateStringToMysqlDate(String localDateString) {
        LocalDate localDate = LocalDate.parse(localDateString);
        return localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();
    }
    //Bertram
    //Takes a date as String, formats the String, creates a LocalDate object from the formatted String, returns the localdate object.
    public LocalDate formatStringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
    //Bertram
    public String formatLocalDateToString(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatter.format(localDate);
    }
}
