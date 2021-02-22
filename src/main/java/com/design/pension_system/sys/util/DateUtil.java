package com.design.pension_system.sys.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public  static  String getToday(){
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String format = sdf.format(date);
        return format;
    }
}
