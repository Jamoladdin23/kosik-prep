package lessons.basics

import kotlin.test.Test
import kotlin.test.assertEquals

class BasicsTest {
    @Test fun greetDefault() = assertEquals("Ahoj, Jamal!", greet("Jamal"))

    @Test fun greetNamedArgument() = assertEquals("Hello, Olena!", greet(name = "Olena", greeting = "Hello"))

    @Test fun fullPriceDefaultVat() = assertEquals(121.0, fullPrice(100.0))

    @Test fun fullPriceCustomVat() = assertEquals(112.0, fullPrice(100.0, vatPercent = 12))

    @Test fun bookingLabelPadsDigits() = assertEquals("Olena @ 09:05", bookingLabel("Olena", 9, 5))

    @Test fun bookingLabelDefaultMinute() = assertEquals("Olena @ 14:00", bookingLabel("Olena", 14))

    @Test fun countUpSums() = assertEquals(15, countUp(5))

    @Test fun countUpZero() = assertEquals(0, countUp(0))
}
