package org.example.goalpet.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.OffsetDateTime

@Entity
class Booking(
    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    var visitor: Visitor,

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    var room: Room,

    @Column(nullable = false)
    var checkInDate: OffsetDateTime,

    var checkOutDate: OffsetDateTime? = null
) : BaseEntity<Long>()