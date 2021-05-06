package com.stars.smartattendancemonitor.repository;

import java.util.List;

import com.stars.smartattendancemonitor.models.Attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    @Modifying
    @Query("SELECT a FROM Attendance a WHERE a.date >= :date AND a.date < :currDate ORDER BY a.date ASC")
    public List<Attendance> getByDate(@Param("date") java.sql.Date date, @Param("currDate") java.sql.Date currDate);

    @Modifying
    @Query("delete from Attendance a where a.date=:date")
    public int deleteByDate(@Param("date") java.sql.Date date);
}