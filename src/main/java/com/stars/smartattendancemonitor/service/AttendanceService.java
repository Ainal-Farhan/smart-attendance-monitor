package com.stars.smartattendancemonitor.service;

import java.util.List;

import com.stars.smartattendancemonitor.models.Attendance;

public interface AttendanceService {
    public void save(Object attendance);
    public List<Attendance> getAttendanceFromSelectedDate(java.sql.Date date, java.sql.Date currDate);
    public void saveAll(List<Attendance> attendances);
}
