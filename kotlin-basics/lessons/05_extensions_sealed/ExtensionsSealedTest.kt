package lessons.sealed

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ExtensionsSealedTest {
    private val b1 = Booking(1, "Olena", 10, 500)
    private val b2 = Booking(5, "Petr", 14, 300)

    @Test fun initialsBasic() = assertEquals("OS", "olena shevchenko".initials())
    @Test fun initialsSkipsExtraSpaces() = assertEquals("J", "  Jana  ".initials())
    @Test fun initialsEmpty() = assertEquals("", "".initials())

    @Test fun validPhone() = assertTrue("+420123456789".isValidPhone())
    @Test fun invalidPhoneNoPlus() = assertFalse("420123456789".isValidPhone())
    @Test fun invalidPhoneTooShort() = assertFalse("+42012".isValidPhone())
    @Test fun invalidPhoneLetters() = assertFalse("+42012345abc".isValidPhone())

    @Test fun timeLabelPads() = assertEquals("09:00", 9.toTimeLabel())
    @Test fun timeLabelTwoDigits() = assertEquals("14:00", 14.toTimeLabel())

    @Test fun totalPrice() = assertEquals(800, listOf(b1, b2).totalPrice())
    @Test fun totalPriceEmpty() = assertEquals(0, emptyList<Booking>().totalPrice())

    @Test fun isSuccessTrue() = assertTrue(BookingResult.Success(b1).isSuccess())
    @Test fun isSuccessFalse() {
        assertFalse(BookingResult.Conflict(b1).isSuccess())
        assertFalse(BookingResult.Invalid("x").isSuccess())
    }

    @Test fun describeAll() {
        assertEquals("Booked: Olena at 10:00", describe(BookingResult.Success(b1)))
        assertEquals("Slot taken by Petr", describe(BookingResult.Conflict(b2)))
        assertEquals("Invalid: oops", describe(BookingResult.Invalid("oops")))
    }

    @Test fun tryBookBlankClient() =
        assertEquals(BookingResult.Invalid("client is blank"), tryBook(listOf(b1), "   ", 12, 100))

    @Test fun tryBookHourOutOfRange() {
        assertEquals(BookingResult.Invalid("hour 7 is outside 8..19"), tryBook(emptyList(), "Jana", 7, 100))
        assertEquals(BookingResult.Invalid("hour 20 is outside 8..19"), tryBook(emptyList(), "Jana", 20, 100))
    }

    @Test fun tryBookConflict() =
        assertEquals(BookingResult.Conflict(b1), tryBook(listOf(b1, b2), "Jana", 10, 100))

    @Test fun tryBookSuccessNextId() =
        assertEquals(BookingResult.Success(Booking(6, "Jana", 12, 100)), tryBook(listOf(b1, b2), "Jana", 12, 100))

    @Test fun tryBookFirstIdIsOne() =
        assertEquals(BookingResult.Success(Booking(1, "Jana", 9, 50)), tryBook(emptyList(), "Jana", 9, 50))
}
