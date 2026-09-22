package lessons.sealed

// Lesson 5. Reference: python_reference.py

data class Booking(val id: Int, val client: String, val hour: Int, val price: Int)

/** The sealed interface is given. Note: three variants, each a data class. */
sealed interface BookingResult {
    data class Success(val booking: Booking) : BookingResult
    data class Conflict(val existing: Booking) : BookingResult
    data class Invalid(val reason: String) : BookingResult
}

// ---------- Part 1: extension functions ----------

/** "olena shevchenko".initials() == "OS". Skip empty words. Hint: split(" "), filter, map, joinToString("") */
fun String.initials(): String =
    split(" ").filter { it.isNotBlank() }.map { it.first().uppercaseChar() }.joinToString("")

/** Phone: "+" followed by 9 to 15 digits. Hint: matches(Regex("""\+\d{9,15}""")) */
fun String.isValidPhone(): Boolean = matches(Regex("""\+\d{9,15}"""))

/** 9.toTimeLabel() == "09:00". Hint: "%02d:00".format(this) */
fun Int.toTimeLabel(): String = "%02d:00".format(this)

/** Sum of price over all bookings in the list. An extension on List<Booking>. Hint: sumOf { } */
fun List<Booking>.totalPrice(): Int = sumOf { it.price }

/** true if the result is a Success. An extension on the sealed type. Hint: this is BookingResult.Success */
fun BookingResult.isSuccess(): Boolean = this is BookingResult.Success

// ---------- Part 2: sealed + when ----------

/**
 * Success  -> "Booked: <client> at <HH:00>"   (use toTimeLabel)
 * Conflict -> "Slot taken by <client of the existing booking>"
 * Invalid  -> "Invalid: <reason>"
 * `when` as an expression WITHOUT else — the compiler checks exhaustiveness.
 */
fun describe(r: BookingResult): String = when (r) {
    is BookingResult.Success -> "Booked: ${r.booking.client} at ${r.booking.hour.toTimeLabel()}"
    is BookingResult.Conflict -> "Slot taken by ${r.existing.client}"
    is BookingResult.Invalid -> "Invalid: ${r.reason}"
}

/**
 * Try to book a slot. Check in this order:
 * 1) client is empty/blank      -> Invalid("client is blank")
 * 2) hour outside 8..19         -> Invalid("hour <hour> is outside 8..19")
 * 3) a booking at that hour exists -> Conflict(that booking)
 * 4) otherwise Success(Booking(id, client, hour, price)), where id = (max id in existing, or 0) + 1
 * Hint: isBlank(), !in 8..19, firstOrNull { }, maxOfOrNull { } ?: 0
 */
fun tryBook(existing: List<Booking>, client: String, hour: Int, price: Int): BookingResult {
    if (client.isBlank()) return BookingResult.Invalid("client is blank")
    if (hour !in 8..19) return BookingResult.Invalid("hour $hour is outside 8..19")
    val taken = existing.firstOrNull { it.hour == hour }
    if (taken != null) return BookingResult.Conflict(taken)
    val nextId = (existing.maxOfOrNull { it.id } ?: 0) + 1
    return BookingResult.Success(Booking(nextId, client, hour, price))
}
