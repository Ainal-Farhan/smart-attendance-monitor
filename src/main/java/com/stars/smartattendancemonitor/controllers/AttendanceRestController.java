package com.stars.smartattendancemonitor.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    // Shared variables for accessing the database
    private static List<Attendance> attendanceListLast30Days = new ArrayList<Attendance>();
    private static int totalNormalLast30Days = 0;
    private static int totalHighLast30Days = 0;

    private static Object attendanceListLock = new Object();
    private static Object totalNormalLock = new Object();
    private static Object totalHighLock = new Object();

    @GetMapping("/api/attendance/last30days")
    public List<Attendance> getAttendanceFromLast30Days(@RequestParam(name = "arduinoName") String arduinoName) {

        java.util.Date now = new java.util.Date();
        java.util.Date last30DaysDate = new java.util.Date(now.getTime() - 30 * 24 * 3600 * 1000l);
        java.util.Date nextOneDay = new java.util.Date(now.getTime() + 1 * 24 * 3600 * 1000l);

        java.sql.Date sqllast30DaysDate = new java.sql.Date(last30DaysDate.getTime());
        java.sql.Date sqlNextOneDayDate = new java.sql.Date(nextOneDay.getTime());

        System.out.println(arduinoName + ": waiting totalNormalLock");
        synchronized (totalNormalLock) {
            System.out.println(arduinoName + ": holding totalNormalLock");

            totalNormalLast30Days = 0;

            System.out.println(arduinoName + ": waiting totalHighLock");
            synchronized (totalHighLock) {
                System.out.println(arduinoName + ": holding totalHighLock & totalNormalLock");

                totalHighLast30Days = 0;

                System.out.println(arduinoName + ": waiting attendanceListLock");
                synchronized (attendanceListLock) {
                    System.out.println(arduinoName + ": holding attendanceListLock & totalHighLock & totalNormalLock");

                    attendanceListLast30Days = attendanceService.getAttendanceFromSelectedDate(sqllast30DaysDate,
                            sqlNextOneDayDate);

                    for (int i = 0; i < attendanceListLast30Days.size(); i++) {
                        final String status = attendanceListLast30Days.get(i).getStatus();

                        if (status.equalsIgnoreCase("normal"))
                            ++totalNormalLast30Days;
                        else if (status.equalsIgnoreCase("high"))
                            ++totalHighLast30Days;
                    }

                    System.out.println("Total High Status: " + totalHighLast30Days);
                    System.out.println("Total Normal Status: " + totalNormalLast30Days);
                }
            }
            return attendanceListLast30Days;
        }
    }

    @GetMapping("/api/save/data")
    public Attendance saveData(@RequestParam(name = "temp") double temp, @RequestParam(name = "status") String status,
            @RequestParam(name = "arduinoName") String arduinoName) {
        java.util.Date now = new java.util.Date();

        Attendance attendance = new Attendance();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        attendance.setTemperature(temp);
        attendance.setStatus(status);
        attendance.setTime(java.sql.Time.valueOf(dateFormat.format(now)));
        attendance.setDate(new java.sql.Date(now.getTime()));

        System.out.println(arduinoName + ": waiting totalNormalLock");
        synchronized (totalNormalLock) {
            System.out.println(arduinoName + ": holding totalNormalLock");

            System.out.println(arduinoName + ": waiting totalHighLock");
            synchronized (totalHighLock) {
                System.out.println(arduinoName + ": holding totalNormalLock & totalHighLock");

                if (status.equalsIgnoreCase("normal"))
                    ++totalNormalLast30Days;
                else if (status.equalsIgnoreCase("high"))
                    ++totalHighLast30Days;

                System.out.println("Total High Status: " + totalHighLast30Days);
                System.out.println("Total Normal Status: " + totalNormalLast30Days);

                System.out.println(arduinoName + ": waiting attendanceListLock");
                synchronized (attendanceListLock) {
                    System.out.println(arduinoName + ": holding totalNormalLock & totalHighLock & attendanceListLock");

                    attendanceListLast30Days.add(attendanceService.save(attendance));
                    return attendance;
                }
            }
        }
    }

    @GetMapping("/api/attendance/selectedDate")
    public List<Attendance> getAttendanceFromSelectedDate(@RequestParam("d") String d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(d);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            java.util.Date now = new java.util.Date();
            java.sql.Date currSqlDate = new java.sql.Date(now.getTime());

            return attendanceService.getAttendanceFromSelectedDate(sqlDate, currSqlDate);
        } catch (ParseException e) {
            System.out.println(e);
            return new ArrayList<Attendance>();
        }
    }

    @GetMapping("delete/deleteAllByDate")
    public String deleteAllByDate(@RequestParam(name = "date") String dateText) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(dateText);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            long deletedData = attendanceService.deleteAllByDate(sqlDate);
            return deletedData + " data deleted with date: " + dateText;
        } catch (ParseException e) {
            System.out.println(e);
            return "Failed to delete any data with date: " + dateText;
        }
    }

    @GetMapping("/add/dummy-data")
    public List<Attendance> addDummyData() {
        List<Attendance> attendances = new ArrayList<>();
        int day = 1;

        while (day < 31) {
            Attendance att = new Attendance();
            int rand = new Random().nextInt(3) + 1;

            java.util.Date date = new java.util.Date();
            java.util.Date last30DaysDate = new java.util.Date(date.getTime() - day * 24 * 3600 * 1000l);
            java.sql.Date sqlDate = new java.sql.Date(last30DaysDate.getTime());

            att.setDate(sqlDate);
            att.setTime(java.sql.Time.valueOf("00:00:00"));

            switch (rand) {
                case 1:
                    att.setStatus("low");
                    att.setTemperature(20.3);
                    break;
                case 2:
                    att.setStatus("normal");
                    att.setTemperature(35.3);
                    break;
                case 3:
                    att.setStatus("high");
                    att.setTemperature(40.3);
                    break;
            }

            System.out.println(rand);
            System.out.println(att);
            attendances.add(att);

            day++;
        }

        attendanceService.saveAll(attendances);

        return attendances;
    }
}
