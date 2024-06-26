package org.example.goalpet.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Hotel(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var location: String
) : BaseEntity<Long>()