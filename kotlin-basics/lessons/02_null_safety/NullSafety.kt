package lessons.nullsafety

// Lesson 2. Replace each TODO() with your solution. Reference: python_reference.py

/** null -> "Anonymous", otherwise the name itself (an empty string stays empty). Use ?: */
fun displayName(name: String?): String = name ?: "Anonymous"

/** Length of the name, or null if there is no name. Use ?. */
fun nameLength(name: String?): Int? = name?.length

/** First letter in upper case, or null (for null and for ""). Hint: firstOrNull(), uppercaseChar() */
fun initial(name: String?): Char? = name?.firstOrNull()?.uppercaseChar()

/** Domain after '@', or "unknown" (for null and for a string without '@'). Hint: substringAfter, ?: */
fun emailDomain(email: String?): String =
    email?.takeIf { "@" in it }?.substringAfter("@") ?: "unknown"

/** Return the phone; for null throw IllegalArgumentException("phone is required"). Hint: ?: throw ... or requireNotNull */
fun requirePhone(phone: String?): String =
    phone ?: throw IllegalArgumentException("phone is required")

/** If phone != null, add the line "SMS to <phone>" to log. Use ?.let { } */
fun sendReminder(phone: String?, log: MutableList<String>) {
    phone?.let { log.add("SMS to $it") }
}
