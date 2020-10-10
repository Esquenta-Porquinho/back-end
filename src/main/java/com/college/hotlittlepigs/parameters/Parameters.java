package com.college.hotlittlepigs.parameters;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.college.hotlittlepigs.box.Box;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="parameters")
@Table(name="parameters")
public class Parameters implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private Double ambientMax;
    
    @Column(length = 10, nullable = false)
    private Double ambientMin;
    
    @Column(length = 10, nullable = false)
    private Double floorMax;
    
    @Column(length = 10, nullable = false)
    private Double floorMin;
    
    @Column(length = 10, nullable = false)
    private Double boilerMax;
    
    @Column(length = 10, nullable = false)
    private Double boilerMin;
    
    @Column(length = 10, nullable = false)
    private Double weeks;
    
    @Column(length = 1, nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="box_id", nullable = false)
    private Box box;
}
