package com.college.hotlittlepigs.box;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name="box")
@Table(name="box")
public class Box implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 10, nullable = false, unique = true, updatable = false)
    private Integer number;

    @Column(length = 60, nullable = false)
    private String controller;

    @Column(length = 45, nullable = false)
    private String area;

    @Column(length = 1, nullable = false)
    private Boolean status;


}
