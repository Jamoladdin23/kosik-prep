package lessons.dataenumwhen

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotSame
import kotlin.test.assertTrue

class DataEnumWhenTest {
    private val base = Booking("Olena", 10)

    @Test fun statusOrder() =
        assertEquals(listOf("PENDING", "CONFIRMED", "CANCELLED"), Status.values().map { it.name })

    @Test fun defaultStatusIsPending() = assertEquals(Status.PENDING, base.status)

    @Test fun dataClassEquality() = assertEquals(Booking("Olena", 10), base)

    @Test fun dataClassToString() = assertEquals("Booking(client=Olena, hour=10, status=PENDING)", base.toString())

    @Test fun confirmCopies() {
        val c = confirm(base)
        assertEquals(Status.CONFIRMED, c.status)
        assertEquals(Status.PENDING, base.status)
        assertNotSame(base, c)
    }

    @Test fun rescheduleKeepsOtherFields() = assertEquals(Booking("Olena", 15), reschedule(base, 15))

    @Test fun labels() {
        assertEquals("Čeká na potvrzení", statusLabel(Status.PENDING))
        assertEquals("Potvrzeno", statusLabel(Status.CONFIRMED))
        assertEquals("Zrušeno", statusLabel(Status.CANCELLED))
    }

    @Test fun cancelRules() {
        assertTrue(canCancel(Status.PENDING))
        assertTrue(canCancel(Status.CONFIRMED))
        assertFalse(canCancel(Status.CANCELLED))
    }

    @Test fun hourCategories() {
        assertEquals("morning", hourCategory(9))
        assertEquals("afternoon", hourCategory(12))
        assertEquals("afternoon", hourCategory(17))
        assertEquals("evening", hourCategory(18))
    }
}
