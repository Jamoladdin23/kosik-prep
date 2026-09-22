package lessons.coroutines

import kotlinx.coroutines.*

// Lesson 6. Reference: python_reference.py

/** Simulates a network request: 1 second of waiting. Given — note the `suspend` keyword and `delay`. */
suspend fun fetchPrice(id: Int): Int {
    delay(1000)
    return id * 100
}

/** Sum of prices, requests ONE AFTER ANOTHER (takes ids.size seconds). Hint: a plain loop/sumOf, call fetchPrice directly. */
suspend fun totalSequential(ids: List<Int>): Int {
    var total = 0
    for (id in ids) total += fetchPrice(id)
    return total
}

/** Prices of all ids IN PARALLEL, in the original order (takes 1 second). Hint: coroutineScope { ids.map { async { ... } }.awaitAll() } */
suspend fun fetchAll(ids: List<Int>): List<Int> = coroutineScope {
    ids.map { async { fetchPrice(it) } }.awaitAll()}

/** Sum of prices, requests in parallel. Hint: use fetchAll. */
suspend fun totalParallel(ids: List<Int>): Int = fetchAll(ids).sum()

/** For each name launch a coroutine: delay(100), then log.add(name). Return when ALL have finished. Hint: coroutineScope + launch */
suspend fun runAll(names: List<String>, log: MutableList<String>) {
    coroutineScope {
        for (name in names) {
            launch {
                delay(100)
                log.add(name)
            }
        }
    }
}

/** fetchPrice(id), but if more than timeoutMs passes, return null. Hint: withTimeoutOrNull(timeoutMs) { } */
suspend fun fetchWithTimeout(id: Int, timeoutMs: Long): Int? =
    withTimeoutOrNull(timeoutMs) { fetchPrice(id) }

/**
 * Call block up to [times] times; return the first successful result.
 * If every attempt fails, throw the LAST error. times <= 0 -> IllegalArgumentException.
 * Do NOT swallow CancellationException — rethrow it (a separate catch before catch (e: Exception)).
 * Hint: repeat(times) { try { return block() } catch ... }, var lastError: Exception?
 */
suspend fun <T> retry(times: Int, block: suspend () -> T): T {
    require(times > 0) { "times must be > 0" }
    var lastError: Exception? = null
    repeat(times) {
        try {
            return block()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            lastError = e
        }
    }
    throw lastError!!
}

