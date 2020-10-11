package com.college.hotlittlepigs.sensors;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

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
import com.college.hotlittlepigs.meansurement.Measurement;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="sensors")
@Entity(name="sensors")
public class Sensors implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String dataType;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 1, nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="box_id", nullable = false)
    private Box box;

    @JsonIgnore
    @OneToMany(mappedBy = "sensor")
    private List<Measurement> sensors = new ArrayList<Measurement>();

}