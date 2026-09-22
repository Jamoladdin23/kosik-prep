package lessons.nullsafety

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class NullSafetyTest {
    @Test fun displayNameNull() = assertEquals("Anonymous", displayName(null))
    @Test fun displayNameValue() = assertEquals("Olena", displayName("Olena"))
    @Test fun displayNameEmptyStaysEmpty() = assertEquals("", displayName(""))

    @Test fun nameLengthNull() = assertNull(nameLength(null))
    @Test fun nameLengthValue() = assertEquals(5, nameLength("Olena"))

    @Test fun initialNull() = assertNull(initial(null))
    @Test fun initialEmpty() = assertNull(initial(""))
    @Test fun initialValue() = assertEquals('O', initial("olena"))

    @Test fun emailDomainOk() = assertEquals("gmail.com", emailDomain("a@gmail.com"))
    @Test fun emailDomainNull() = assertEquals("unknown", emailDomain(null))
    @Test fun emailDomainNoAt() = assertEquals("unknown", emailDomain("nonsense"))

    @Test fun requirePhoneOk() = assertEquals("+420123", requirePhone("+420123"))
    @Test fun requirePhoneNull() {
        val e = assertFailsWith<IllegalArgumentException> { requirePhone(null) }
        assertEquals("phone is required", e.message)
    }

    @Test fun sendReminderLogs() {
        val log = mutableListOf<String>()
        sendReminder("+420123", log)
        assertEquals(listOf("SMS to +420123"), log)
    }
    @Test fun sendReminderSkipsNull() {
        val log = mutableListOf<String>()
        sendReminder(null, log)
        assertEquals(emptyList(), log)
    }
}
