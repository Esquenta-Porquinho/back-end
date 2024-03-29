package com.college.hotlittlepigs.log_controllers;

import com.college.hotlittlepigs.controller.Controller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class LogController implements Serializable {

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
  @JoinColumn(name = "controller_id", nullable = false)
  private Controller controller;
}
