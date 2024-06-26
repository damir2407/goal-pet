package org.example.goalpet.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Visitor(

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false, unique = true)
    var email: String
) : BaseEntity<Long>()