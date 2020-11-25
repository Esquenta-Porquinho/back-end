package com.college.hotlittlepigs.gestation;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.matrix.Matrix;
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
public class Gestation implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10)
  private Integer livePigs;

  @Column(length = 10)
  private Integer deadPigs;

  @Column(length = 45, nullable = false)
  @Temporal(TemporalType.DATE)
  private Date expectedParturition;

  @Column(length = 45)
  @Temporal(TemporalType.DATE)
  private Date effectiveParturition;

  @Column(length = 10, nullable = false)
  private Integer numberParturition;

  @Column(length = 45)
  @Temporal(TemporalType.DATE)
  private Date weaning;

  @Column(length = 1, nullable = false)
  private Boolean status;

  @ManyToOne
  @JoinColumn(name = "box_id", nullable = false)
  private Box box;

  @ManyToOne
  @JoinColumn(name = "matrix_id", nullable = false)
  private Matrix matrix;
}
