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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date date = sdf.parse(d);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            return attendanceService.getAttendanceFromSelectedDate(sqlDate);
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

        return attendanceService.getAttendanceFromSelectedDate(sqlDate);
    }
}
