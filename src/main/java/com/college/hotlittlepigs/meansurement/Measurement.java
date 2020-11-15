package com.college.hotlittlepigs.meansurement;

import com.college.hotlittlepigs.sensors.Sensors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Measurement implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10, nullable = false, updatable = false)
  private Double measure;

  @ManyToOne
  @JoinColumn(name = "sensor_id", nullable = false, updatable = false)
  private Sensors sensor;
}
