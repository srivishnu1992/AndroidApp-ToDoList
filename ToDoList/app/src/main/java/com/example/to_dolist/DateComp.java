package com.example.to_dolist;

import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateComp implements Comparator<Task>{
    @Override
    public int compare(Task o1, Task o2) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
            Date d1 = sd.parse(o1.getDate());
            Date d2 = sd.parse(o2.getDate());

            return sd.format(d1).compareTo(sd.format(d2));
        }catch (Exception e) {
            return 0;
        }
    }
}
