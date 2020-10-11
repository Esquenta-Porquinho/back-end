package com.college.hotlittlepigs.log_controllers;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.college.hotlittlepigs.controllers.Controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="log_controllers")
@Entity(name= "log_controllers")
public class LogControllers implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    
    @Column(length = 1, nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="controller_id", nullable = false)
    private Controllers controller;
}
