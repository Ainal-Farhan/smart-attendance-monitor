package com.stars.smartattendancemonitor.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.stars.smartattendancemonitor.models.Attendance;
import com.stars.smartattendancemonitor.service.AttendanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AttendanceRestController {
    @Autowired
    private AttendanceService attendanceService;
    
    @GetMapping("/api/attendance/selectedDate")
    public List<Attendance> getAttendanceFromSelectedDate(@RequestParam("d") String d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(d);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            java.util.Date now = new java.util.Date();
            java.sql.Date currSqlDate = new java.sql.Date(now.getTime());

            return attendanceService.getAttendanceFromSelectedDate(sqlDate, currSqlDate);
        }
        catch(ParseException e) {
            System.out.println(e);
            return new ArrayList<Attendance>();
        }
    }

    @GetMapping("/api/attendance/last30days")
    public List<Attendance> getAttendanceFromLast30Days() {
        java.util.Date date = new java.util.Date();
        java.util.Date last30DaysDate = new java.util.Date(date.getTime() - 30 * 24 * 3600 * 1000l);
        java.sql.Date sqlDate = new java.sql.Date(last30DaysDate.getTime());

        java.util.Date now = new java.util.Date();
        java.sql.Date currSqlDate = new java.sql.Date(now.getTime());
        
        return attendanceService.getAttendanceFromSelectedDate(sqlDate, currSqlDate);
    }

    @GetMapping("/api/save/data")
    public Attendance saveData(
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
            return attendance;
        }
        catch (ParseException e) {
            System.out.println(e);
            return new Attendance();
        }

    }
}
