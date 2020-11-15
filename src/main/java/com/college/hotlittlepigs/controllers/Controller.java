package com.college.hotlittlepigs.controllers;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.log_controllers.LogController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Controller implements Serializable {
  // TODO Acho que o nome poderia ser no singular, que tal?
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 45, nullable = false)
  private String name;

  @Column(length = 1, nullable = false)
  private Boolean status;

  @ManyToOne
  @JoinColumn(name = "box_id", nullable = false)
  private Box box;

  @JsonIgnore
  @OneToMany(mappedBy = "controller")
  private List<LogController> logs = new ArrayList<>();
}
