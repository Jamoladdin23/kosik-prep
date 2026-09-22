package lessons.basics


/** "Ahoj, Jamal!" — greeting defaults to "Ahoj". Use a string template. */
fun greet(name: String, greeting: String = "Ahoj"): String = "$greeting, $name!"

/**
 * Price including VAT, rounded to 2 decimals. vatPercent defaults to 21.
 * Hint: Double, not Int; rounding is Math.round(x * 100) / 100.0
 */
fun fullPrice(price: Double, vatPercent: Int = 21): Double =
    Math.round(price * (1 + vatPercent / 100.0) * 100) / 100.0

/** "Olena @ 09:05" — hours and minutes are always two digits, minute defaults to 0. Hint: "%02d".format(x) */
fun bookingLabel(client: String, hour: Int, minute: Int = 0): String =
    "$client @ ${"%02d".format(hour)}:${"%02d".format(minute)}"

/** Sum of 1..limit. Use a var and a for (1..limit) loop inside, not a formula. */
fun countUp(limit: Int): Int {
    var total = 0
    for (i in 1..limit) {
        total += i
    }
    return total
}
