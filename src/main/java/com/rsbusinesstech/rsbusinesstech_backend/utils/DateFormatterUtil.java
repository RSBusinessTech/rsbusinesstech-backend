package com.rsbusinesstech.rsbusinesstech_backend.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DateFormatterUtil
{
    //This method converts "yyyy-MM-dd" to "MMM dd, yyyy";
    public String getFormattedDate(String date){
      if(StringUtils.hasText(date)){
          LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          return localDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
      }
      return date;
    }

    //This method give true if the date passed as parameter is past or today's date.
    public boolean isBeforeOrToday(String date){
        if(StringUtils.hasText(date)){
            LocalDate givenDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if(!givenDate.isAfter(LocalDate.now())){    // true if before or today.
                return true;
            }
        }
        return false;
    }

    //This method give true if the date passed as parameter belongs to current month & year.
    public boolean isInCurrentMonthYear(String date){
        if(StringUtils.hasText(date)){
            LocalDate givenDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate now = LocalDate.now();

            return givenDate.getMonth() == now.getMonth()
                    && givenDate.getYear() == now.getYear();
        }
        return false;
    }
}
