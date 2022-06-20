package com.college.hotlittlepigs.matrix;

import com.college.hotlittlepigs.gestation.Gestation;
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
public class Matrix implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10, nullable = false, unique = true)
  private Integer number;

  @Column(length = 1, nullable = false)
  private Boolean status;

  @JsonIgnore
  @OneToMany(mappedBy = "matrix")
  private List<Gestation> gestations = new ArrayList<>();
}
