package com.stars.smartattendancemonitor.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBL_ATTENDANCE")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonIgnore
    private Long id;

    @Column(name = "temperature", nullable = false)
    private double temperature;

    @Column(name = "status", nullable = false)
    private  String status;
    
    @JsonFormat(pattern="HH:mm:ss")
    @Column(name = "time", nullable = false)
    private java.sql.Time time;
    
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "date", nullable = false)
    private java.sql.Date date;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.sql.Time getTime() {
        return this.time;
    }

    public void setTime(java.sql.Time time) {
        this.time = time;
    }

    public java.sql.Date getDate() {
        return this.date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", status='" + getStatus() + "'" +
            ", time='" + getTime() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }

}
