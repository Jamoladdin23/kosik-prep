package lessons.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutinesTest {
    @Test fun sequentialTotalAndTime() = runTest {
        assertEquals(600, totalSequential(listOf(1, 2, 3)))
        assertEquals(3000, currentTime)              // 3 requests one after another = 3 virtual seconds
    }

    @Test fun fetchAllParallelKeepsOrder() = runTest {
        assertEquals(listOf(100, 200, 300), fetchAll(listOf(1, 2, 3)))
        assertEquals(1000, currentTime)              // in parallel = 1 virtual second
    }

    @Test fun totalParallelIsFast() = runTest {
        assertEquals(600, totalParallel(listOf(1, 2, 3)))
        assertEquals(1000, currentTime)
    }

    @Test fun runAllWaitsForEveryone() = runTest {
        val log = mutableListOf<String>()
        runAll(listOf("a", "b", "c"), log)
        assertEquals(listOf("a", "b", "c"), log.sorted())
        assertEquals(100, currentTime)
    }

    @Test fun timeoutReturnsNull() = runTest { assertNull(fetchWithTimeout(1, 500)) }

    @Test fun noTimeoutReturnsValue() = runTest { assertEquals(100, fetchWithTimeout(1, 1500)) }

    @Test fun retrySucceedsAfterFailures() = runTest {
        var calls = 0
        val result = retry(3) {
            calls++
            if (calls < 3) error("fail $calls") else "ok"
        }
        assertEquals("ok", result)
        assertEquals(3, calls)
    }

    @Test fun retryThrowsLastError() = runTest {
        var calls = 0
        val e = assertFailsWith<IllegalStateException> {
            retry(2) { calls++; throw IllegalStateException("boom $calls") }
        }
        assertEquals("boom 2", e.message)
        assertEquals(2, calls)
    }

    @Test fun retryRejectsNonPositiveTimes() {
        assertFailsWith<IllegalArgumentException> { runTest { retry(0) { 1 } } }
    }
}
