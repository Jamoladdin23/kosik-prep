package lessons.collections

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CollectionsTest {
    private val b1 = Booking(1, "Olena", 10, Status.CONFIRMED, 500)
    private val b2 = Booking(2, "Petr", 10, Status.PENDING, 300)
    private val b3 = Booking(3, "Olena", 14, Status.CONFIRMED, 700)
    private val b4 = Booking(4, "Jana", 9, Status.CANCELLED, 400)
    private val b5 = Booking(5, "Petr", 10, Status.CONFIRMED, 200)
    private val all = listOf(b1, b2, b3, b4, b5)

    @Test fun confirmedClientsKeepsOrderAndDuplicates() =
        assertEquals(listOf("Olena", "Olena", "Petr"), confirmedClients(all))

    @Test fun revenueOnlyConfirmed() = assertEquals(1400, revenue(all))

    @Test fun revenueEmpty() = assertEquals(0, revenue(emptyList()))

    @Test fun groupByStatusGroups() {
        val g = groupByStatus(all)
        assertEquals(listOf(b1, b3, b5), g[Status.CONFIRMED])
        assertEquals(listOf(b2), g[Status.PENDING])
        assertEquals(listOf(b4), g[Status.CANCELLED])
    }

    @Test fun countByStatusCounts() =
        assertEquals(mapOf(Status.CONFIRMED to 3, Status.PENDING to 1, Status.CANCELLED to 1), countByStatus(all))

    @Test fun sortedByHourIsStable() = assertEquals(listOf(b4, b1, b2, b5, b3), sortedByHour(all))

    @Test fun sortedByHourDoesNotChangeOriginal() {
        sortedByHour(all)
        assertEquals(listOf(b1, b2, b3, b4, b5), all)
    }

    @Test fun byIdLooksUp() {
        val m = byId(all)
        assertEquals(b3, m[3])
        assertEquals(5, m.size)
    }

    @Test fun busiestHourFound() = assertEquals(10, busiestHour(all))

    @Test fun busiestHourEmptyIsNull() = assertNull(busiestHour(emptyList()))

    @Test fun uniqueClientsSortedAlphabetically() =
        assertEquals(listOf("Jana", "Olena", "Petr"), uniqueClientsSorted(all))
}
