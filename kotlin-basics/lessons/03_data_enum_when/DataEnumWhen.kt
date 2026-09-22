package lessons.dataenumwhen

// Lesson 3. Reference: python_reference.py

/** Booking statuses. Given for free — look at what an enum class looks like. */
enum class Status { PENDING, CONFIRMED, CANCELLED }

/**
 * TASK 1: turn this into a data class (one word before `class`). The fields are already val.
 * As a plain class, the equals/toString/copy tests stay red.
 */
data class Booking(val client: String, val hour: Int, val status: Status = Status.PENDING)

/** Return a copy of the booking with status CONFIRMED. Use copy(). */
fun confirm(b: Booking): Booking = b.copy(status = Status.CONFIRMED)

/** Return a copy of the booking with a new hour. Use copy(). */
fun reschedule(b: Booking, newHour: Int): Booking = b.copy(hour = newHour)

/**
 * PENDING -> "Čeká na potvrzení", CONFIRMED -> "Potvrzeno", CANCELLED -> "Zrušeno".
 * Use `when` as an expression WITHOUT else — the compiler checks that every status is covered.
 */
fun statusLabel(s: Status): String = when (s) {
    Status.PENDING -> "Čeká na potvrzení"
    Status.CONFIRMED -> "Potvrzeno"
    Status.CANCELLED -> "Zrušeno"
}

/** PENDING and CONFIRMED can be cancelled. Hint: `when` with several values separated by a comma. */
fun canCancel(s: Status): Boolean = when (s) {
    Status.PENDING, Status.CONFIRMED -> true
    Status.CANCELLED -> false
}

/** hour < 12 -> "morning", hour < 18 -> "afternoon", otherwise "evening". Hint: `when` without an argument. */
fun hourCategory(hour: Int): String = when {
    hour < 12 -> "morning"
    hour < 18 -> "afternoon"
    else -> "evening"
}
