# Lesson 3. data class, copy, enum class, `when` as an expression

**What it is.**
- `data class Booking(val client: String, val hour: Int)` — a container class: `equals`, `hashCode`,
  `toString` and `copy` are generated.
- `booking.copy(hour = 11)` — a new copy with one field changed (the original is untouched).
- `enum class Status { PENDING, CONFIRMED, CANCELLED }` — a fixed set of values.
- `when` — like Python 3.10 `match`, but it is an **expression**: `val x = when (s) { A -> 1; B -> 2 }`.
  For an enum the compiler requires every case to be covered.

**Python equivalent.** `@dataclass(frozen=True)` + `dataclasses.replace(b, hour=11)`; `enum.Enum`; `match/case`.

**Main trap.** A `data class` with `val` is immutable only on the surface: if a field is a `MutableList`,
its contents can still change. And `when` on an enum without `else` is the norm (the compiler checks
completeness), while `else` **hides** the error when a new status is added later.

**Task.** Replace the `TODO()`s in `DataEnumWhen.kt`. Reference: `python_reference.py`.
Run: `./gradlew cleanTest test --tests '*DataEnumWhenTest*' --rerun-tasks`
