package org.example.goalpet.domain

import jakarta.persistence.Entity

@Entity
class Visitor(
    var username: String,
    var email: String
) : BaseEntity<Long>()