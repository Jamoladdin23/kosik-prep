package com.kosikprep.backend

import jakarta.persistence.*
import java.time.LocalDateTime

enum class Status { PENDING, CONFIRMED, CANCELLED }

// Python: class Booking(models.Model): id = AutoField(...) (created implicitly)
// @Entity tells Hibernate: "this is a DB table", like inheriting from models.Model.
@Entity
class Booking(
    // Python: client = models.CharField(max_length=255)
    // nullable = false is the same as blank/null=False by default in Django.
    @Column(nullable = false)
    var client: String,

    // Python: starts_at = models.DateTimeField()
    var startsAt: LocalDateTime,

    // Python: status = models.CharField(choices=Status.choices, default=Status.PENDING)
    // @Enumerated(EnumType.STRING) stores it as text "PENDING", not as a number 0/1/2.
    // Without this annotation Hibernate stores the enum's ordinal position by default,
    // and inserting a new value in the middle of the enum later would corrupt existing rows.
    @Enumerated(EnumType.STRING)
    var status: Status = Status.PENDING
) {
    // Python: id = models.AutoField(primary_key=True) — Django creates this field itself.
    // In JPA it must be declared explicitly.
    // @GeneratedValue(strategy = GenerationType.IDENTITY) — DB auto-increment, like AutoField.
    // var, not val: Hibernate sets id AFTER the row is saved; before that it is 0 (the default).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
