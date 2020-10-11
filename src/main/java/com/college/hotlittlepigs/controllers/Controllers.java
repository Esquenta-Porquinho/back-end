package com.college.hotlittlepigs.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.log_controllers.LogControllers;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "controllers")
@Entity(name = "controllers")
public class Controllers implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 1, nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="box_id", nullable = false)
    private Box box;
    
    @JsonIgnore
    @OneToMany(mappedBy = "controller")
    private List<LogControllers> logs = new ArrayList<LogControllers>();
}
