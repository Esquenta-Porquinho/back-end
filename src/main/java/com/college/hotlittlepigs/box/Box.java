package com.college.hotlittlepigs.box;

import com.college.hotlittlepigs.controllers.Controllers;
import com.college.hotlittlepigs.gestation.Gestation;
import com.college.hotlittlepigs.parameters.Parameters;
import com.college.hotlittlepigs.sensors.Sensors;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Box implements Serializable {

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

  @JsonIgnore
  @OneToMany(mappedBy = "box")
  private List<Gestation> gestations = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "box")
  private List<Parameters> parameters = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "box")
  private List<Sensors> sensors = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "box")
  private List<Controllers> controllers = new ArrayList<>();
}
