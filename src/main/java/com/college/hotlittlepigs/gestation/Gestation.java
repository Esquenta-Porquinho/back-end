package com.college.hotlittlepigs.gestation;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.college.hotlittlepigs.box.Box;
import com.college.hotlittlepigs.matrix.Matrix;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="gestation")
@Table(name="gestation")
public class Gestation implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private Integer livePigs;

    @Column(length = 10)
    private Integer deadPigs;

    @Column(length = 45, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expectedParturition;

    @Column(length = 45)
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveParturition;

    @Column(length=10, nullable = false)
    private Integer numberParturition;

    @Column(length = 45)
    @Temporal(TemporalType.TIMESTAMP)
    private Date weaning;

    @Column(length = 1, nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="box_id", nullable = false)
    private Box box;

    @ManyToOne
    @JoinColumn(name="matrix_id", nullable = false)
    private Matrix matrix;
}
