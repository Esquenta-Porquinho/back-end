package com.college.hotlittlepigs.user;

import com.college.hotlittlepigs.log.Log;
import com.college.hotlittlepigs.user.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 75, nullable = false)
  private String name;

  @Column(length = 75, nullable = false, unique = true)
  private String email;

  @JsonProperty(access = Access.WRITE_ONLY)
  @Column(length = 100, nullable = false)
  private String password;

  @Column(length = 20, nullable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @JsonIgnore
  @OneToMany(mappedBy = "owner")
  private List<Log> logs = new ArrayList<>();
}
