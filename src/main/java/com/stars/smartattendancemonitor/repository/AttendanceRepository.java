package com.stars.smartattendancemonitor.repository;

import com.stars.smartattendancemonitor.models.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
}