package com.stars.smartattendancemonitor.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.stars.smartattendancemonitor.models.Attendance;
import com.stars.smartattendancemonitor.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/save/data")
    public String saveData(
        @RequestParam(name = "temp")    double temp,
        @RequestParam(name = "status")  String status,
        @RequestParam(name = "t")    String t,    // format: hh:mm:ss,    eg: 10:20:31 
        @RequestParam(name = "d")    String d     // format: yyyy-mm-dd,  eg: 2011-12-31
    ) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(d);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            java.sql.Time sqlTime = java.sql.Time.valueOf(t);

            Attendance attendance = new Attendance();

            attendance.setTemperature(temp);
            attendance.setStatus(status);
            attendance.setTime(sqlTime);
            attendance.setDate(sqlDate);
            
            attendanceService.save(attendance);
        }
        catch (ParseException e) {
            System.out.println(e);
        }

        return "redirect:/";
    }
}
