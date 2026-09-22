# Lesson 1. val/var, types, string templates, functions

**What it is.** `val` is an immutable reference (like a constant), `var` is mutable.
Types are inferred but static. Strings: `"Hello, $name, ${a + b}"`.
Functions: `fun name(a: Int, b: Int = 0): Int`; arguments can be named at the call site.

**Python equivalent.** `f"{name}"` → `"$name"`; `def f(a, b=0)` → `fun f(a: Int, b: Int = 0)`;
`f(a=1, b=2)` → `f(a = 1, b = 2)`. Parameter types are mandatory, not comments.

**Main trap.** `val` does not make the object immutable: with `val list = mutableListOf(1)` the reference
cannot be reassigned, but `list.add(2)` works. Also, there is no implicit numeric conversion:
`val x: Double = 1` is a compile error (write `1.0`), whereas in Python `1` and `1.0` get along.

**Task.** Open `Basics.kt` and replace each `TODO()`. Reference: `python_reference.py`.
Run: `./gradlew cleanTest test --tests '*BasicsTest*' --rerun-tasks`
