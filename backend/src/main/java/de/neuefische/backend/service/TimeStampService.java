package de.neuefische.backend.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TimeStampService {

    public String getCurrentTime(){
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return localDateFormat.format(new Date());
    }
}
