package lessons.collections

// Lesson 4. Reference: python_reference.py

enum class Status { PENDING, CONFIRMED, CANCELLED }

data class Booking(val id: Int, val client: String, val hour: Int, val status: Status, val price: Int)

/** Client names of confirmed bookings only (with duplicates, in the original order). Hint: filter + map */
fun confirmedClients(bookings: List<Booking>): List<String> =
    bookings.filter { it.status == Status.CONFIRMED }.map { it.client }

/** Sum of price over confirmed bookings. Hint: filter + sumOf { } */
fun revenue(bookings: List<Booking>): Int =
    bookings.filter { it.status == Status.CONFIRMED }.sumOf { it.price }

/** Bookings grouped by status. Hint: groupBy { } */
fun groupByStatus(bookings: List<Booking>): Map<Status, List<Booking>> =
    bookings.groupBy { it.status }

/** How many bookings there are in each status. Hint: groupBy { }, then mapValues { } (list length is .size) */
fun countByStatus(bookings: List<Booking>): Map<Status, Int> =
    bookings.groupBy { it.status }.mapValues { it.value.size }

/** Bookings in ascending hour order (equal hours keep their order). Hint: sortedBy { } */
fun sortedByHour(bookings: List<Booking>): List<Booking> =
    bookings.sortedBy { it.hour }

/** Map of id -> booking. Hint: associateBy { } */
fun byId(bookings: List<Booking>): Map<Int, Booking> =
    bookings.associateBy { it.id }

/** The hour with the most bookings; null for an empty list. Hint: groupBy, maxByOrNull, ?. */
fun busiestHour(bookings: List<Booking>): Int? =
    bookings.groupBy { it.hour }.maxByOrNull { it.value.size }?.key

/** Unique client names in alphabetical order. Hint: map, distinct, sorted */
fun uniqueClientsSorted(bookings: List<Booking>): List<String> =
    bookings.map { it.client }.distinct().sorted()
