package model.csvtransform.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static Date parseDate (String dateString) throws ParseException {
        return DATE_FORMAT.parse(dateString);
    }
}
