package com.hangaries.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter {

    public static Date getDate(String date, String dateFormat) throws ParseException {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        return sdf.parse(date);
    }
}
