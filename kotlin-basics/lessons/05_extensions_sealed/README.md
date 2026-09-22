# Lesson 5. Extension functions, sealed interface + `when`

**What it is.**
- **Extension function** — a function "added" to an existing type without inheritance:
  `fun String.initials(): String = ...`, called as `"Olena Shevchenko".initials()`. Inside, `this` is the object.
- **`sealed interface`** — a closed set of variants: the compiler knows every subtype.
  A `when` over such a type without `else` is checked for exhaustiveness.
- **Smart cast** — inside the branch `is Success ->` the object already has type `Success`, so `r.booking`
  works without a cast.

**Python equivalent.** An extension is a plain function `initials(s)` (Python cannot add a method to `str`,
and monkey-patching is dangerous). Sealed is `Union[Success, Conflict, Invalid]` of `@dataclass`es + `match`,
but Python does not check `match` for exhaustiveness and Kotlin does.

**Main trap.** An extension has **no** access to `private` members and does **not** override a class method:
if the type already has a method with that name, the class method wins. And an `else` in a `when` over a
sealed type turns exhaustiveness checking off — do not add one.

**Task.** Replace the `TODO()`s in `ExtensionsSealed.kt`. Reference: `python_reference.py`.
Run: `./gradlew cleanTest test --tests '*ExtensionsSealedTest*' --rerun-tasks`
