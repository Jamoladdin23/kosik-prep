# Lesson 6. Coroutines: suspend, launch, async, awaitAll

**What it is.** A coroutine is a lightweight "task" that can suspend without blocking a thread.
- `suspend fun` — a function that may suspend (like `async def`); it can only be called from another
  `suspend` function or from a coroutine. There is no `await` keyword: calling a `suspend` function already
  waits for its result.
- `coroutineScope { }` — a scope that returns only when ALL coroutines started inside have finished;
  an error in one cancels the others (structured concurrency).
- `launch { }` — start and do not wait for a result (fire-and-forget inside the scope); `async { }` — start and get
  a `Deferred`, read the result with `.await()`; `list.awaitAll()` waits for all (like `asyncio.gather`).
- `delay(ms)` — a non-blocking pause (`asyncio.sleep`). `withTimeoutOrNull(ms) { }` — `asyncio.wait_for`,
  but returns `null` instead of raising.

**Python equivalent.** `async def` / `await` / `asyncio.gather(*tasks)` / `asyncio.sleep` / `asyncio.wait_for`.
Difference: Kotlin has no `await` to write, and concurrency only starts with `async { }`: a loop calling
`fetchPrice(id)` runs the calls one after another, just like `await` inside a loop in Python.

**Main trap.** Inside `retry { }` never swallow `CancellationException`: it is the coroutine cancellation
signal, and catching it like an ordinary error breaks cancellation. Also: `Thread.sleep` blocks the thread,
`delay` does not.

**The tests use virtual time** (`runTest`): `delay(1000)` does not really wait, but `currentTime` shows how much
"time" passed. The test uses it to check that the parallel version is faster than the sequential one.

**Task.** Replace the `TODO()`s in `Coroutines.kt`. Reference: `python_reference.py`.
Run: `./gradlew cleanTest test --tests '*CoroutinesTest*' --rerun-tasks`
