package com.college.hotlittlepigs.meansurement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.college.hotlittlepigs.sensors.Sensors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "meansurement")
@Entity(name= "meansurement")
public class Measurement implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false, updatable = false)
    private Double measure;

    @ManyToOne
    @JoinColumn(name="sensor_id", nullable = false, updatable = false)
    private Sensors sensor;
}
