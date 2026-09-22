package com.kosikprep.backend

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

// Python: a read-only ModelSerializer with fields = ["id", "client", "starts_at", "status"].
// This is what the API returns to the client — separate from the entity on purpose,
// so JPA internals (lazy fields, the id before save) never leak into the API response.
data class BookingResponse(
    val id: Long,
    val client: String,
    val startsAt: LocalDateTime,
    val status: Status
)

// Python: a write serializer with client = CharField() and starts_at = DateTimeField(),
// where @NotBlank/@Future are validators declared as annotations instead of code.
// `@field:` tells Kotlin to put the annotation on the class FIELD, not on the constructor
// parameter or the generated getter — Bean Validation reads annotations from fields.
data class CreateBookingRequest(
    @field:NotBlank
    val client: String,

    @field:Future
    val startsAt: LocalDateTime
)

// Python: BookingSerializer(booking).data — turning a model instance into serialized data.
// An extension function (lesson 5) on Booking: booking.toResponse().
fun Booking.toResponse(): BookingResponse = BookingResponse(id, client, startsAt, status)
