package com.stars.smartattendancemonitor.service;

import com.stars.smartattendancemonitor.repository.AttendanceRepository;

import java.util.List;

import com.stars.smartattendancemonitor.models.Attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl implements AttendanceService{
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public void save(Object attendance) {
        attendanceRepository.save((Attendance) attendance);
    }

    @Override
    public List<Attendance> getAttendanceFromSelectedDate(java.sql.Date date, java.sql.Date currDate) {
        return attendanceRepository.getByDate(date, currDate);
    }
}
