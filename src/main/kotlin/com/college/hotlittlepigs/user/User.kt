package com.college.hotlittlepigs.user

import com.college.hotlittlepigs.log.Log
import com.college.hotlittlepigs.user.enums.Role
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
class User() {
    // TODO Add a ObjectMapper library so we dont need to do that and use generated Mappers
    constructor(name: String, email: String, password: String) : this() {
        this.name = name
        this.email = email
        this.password = password
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(length = 75, nullable = false)
    lateinit var name: String

    @Column(length = 75, nullable = false, unique = true)
    lateinit var email: String

    @Column(length = 100, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    lateinit var password: String

    @Column(length = 20, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    lateinit var role: Role

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    val logs: Set<Log> = setOf()
}
