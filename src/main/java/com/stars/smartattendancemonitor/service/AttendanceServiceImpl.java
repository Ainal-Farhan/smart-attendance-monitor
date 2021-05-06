package com.stars.smartattendancemonitor.service;

import com.stars.smartattendancemonitor.repository.AttendanceRepository;

import java.util.List;

import com.stars.smartattendancemonitor.models.Attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttendanceServiceImpl implements AttendanceService{
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public Attendance save(Object attendance) {
        return attendanceRepository.save((Attendance) attendance);
    }

    @Override
    public List<Attendance> getAttendanceFromSelectedDate(java.sql.Date date, java.sql.Date currDate) {
        return attendanceRepository.getByDate(date, currDate);
    }

    @Override
    public void saveAll(List<Attendance> attendances) {
        attendanceRepository.saveAll(attendances);        
    }

    @Transactional
    @Override
    public long deleteAllByDate(java.sql.Date date) {
        return attendanceRepository.deleteByDate(date);
    }
}
