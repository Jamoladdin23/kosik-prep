# Lesson 2. Null safety: `?`, `?.`, `?:`, `!!`, `let`

**What it is.** `String` cannot be `null`. If a value may be absent, the type is `String?`.
The compiler will not let you call a method on `String?` until `null` is handled.
- `x?.length` — safe call: if `x == null` the result is `null`
- `x ?: "default"` — elvis: the value, or the default if the left side is `null`
- `x!!` — "I am sure it is not null"; if wrong, `NullPointerException`
- `x?.let { ... }` — run the block only if `x != null`

**Python equivalent.** `Optional[str]` + `if x is None`, `x or "default"`, `x.strip() if x else None`.
Difference: in Python this is a hint for mypy, in Kotlin it is a compile error.

**Main trap.** `!!` is a return to Python: the compiler stays silent and it crashes at runtime.
Second: `x ?: default` triggers only on `null`, whereas Python `x or default` also triggers on `""` and `0`.

**Task.** Replace the `TODO()`s in `NullSafety.kt`. Reference: `python_reference.py`.
Run: `./gradlew cleanTest test --tests '*NullSafetyTest*' --rerun-tasks`
