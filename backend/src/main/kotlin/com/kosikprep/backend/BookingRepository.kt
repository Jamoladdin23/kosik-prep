package com.kosikprep.backend

import org.springframework.data.jpa.repository.JpaRepository

// Python: BookingRepository ~ Booking.objects (Django's default manager) —
// you get save/find/delete for free by inheriting, without writing any implementation.
// JpaRepository<Booking, Long>: first type is the entity, second is the type of its id.
interface BookingRepository : JpaRepository<Booking, Long> {

    // Python: Booking.objects.filter(status=status)
    // Spring Data parses the method NAME ("findBy" + "Status", the field on Booking)
    // and generates the SQL itself — no method body needed.
    fun findByStatus(status: Status): List<Booking>
}
