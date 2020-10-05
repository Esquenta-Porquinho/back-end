package com.college.hotlittlepigs.log;

import com.college.hotlittlepigs.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name="log")
@Table(name="log")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String description;

    @Column(length = 75, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @ManyToOne
    @JoinColumn(name="owner_id", nullable = false)
    private User owner_id;
}
