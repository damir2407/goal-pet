package org.example.goalpet.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Room(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    var hotel: Hotel,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var roomType: RoomType,

    @Column(nullable = false)
    var price: Double,

    @Column(nullable = false, columnDefinition = "bool default true")
    var availibility: Boolean
) : BaseEntity<Long>()