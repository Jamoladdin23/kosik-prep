# MISTAKES.md

Format: **mistake → why → how it differs from the Python habit.**

## Lesson 2. Null safety

1. **`"Anonymus"` instead of `"Anonymous"`** → typo in a string literal → the compiler checks types,
   not string contents. Same in Python, but there the habit of "the compiler will catch it" does not exist.
   How to avoid: copy the text from the task and run the tests right away.
2. **`"Phone is required"` instead of `"phone is required"`** → wrong case in the exception message →
   `e.message` is compared as a plain string, so case matters. Same as `raise ValueError("...")` in Python;
   tests on error text are just written less often there.

## Lesson 3. data class, enum, when

1. **Forgot `data` before `class Booking` but called `.copy()`** → without `data` the compiler does not
   generate `copy`, `equals`, `toString` → in Python `@dataclass` is also required, otherwise
   `dataclasses.replace` fails; the difference is that Kotlin reports it as a compile error, not at runtime.

## Lesson 4. Collections

1. **Pasted the `groupByStatus` body into `countByStatus` and left `groupByStatus` as `TODO()`** → the return type
   `Map<Status, List<Booking>>` no longer matched what the test expects (`Map<Status, Int>`) → the compiler reported
   `Type inference failed` in the test file, not in the solution, which is confusing. In Python a wrong return
   type would only show up as a failing assertion; in Kotlin it is a compile error, and one compile error blocks
   the tests of every lesson. `groupBy` gives `Map<K, List<T>>`; to count, add `.mapValues { it.value.size }`.
   How to avoid: after typing each function, run the tests, and read the return type in the signature.

## Lesson 6. Coroutines

1. **Missing closing `}` of `coroutineScope { ... }` in `fetchAll`** → the next function was parsed as part of the
   block, so the compiler reported `Expecting '}'` at the end of the file and a misleading
   `Argument type mismatch: Unit` → in Python a missing bracket is also a syntax error, but indentation makes
   it visible; Kotlin ignores indentation and only braces count. How to avoid: type the closing brace right after
   the opening one, or let the IDE auto-close it, and read the *first* compiler error, not the last.

## Stage 2. BookingDto

1. **Pasted the solution below the original TODO template instead of replacing it** → the file ended up with
   `package`/`import` twice → `Syntax error: imports are only allowed in the beginning of file` and
   `Conflicting import: ... is ambiguous`. In Python a duplicated `import` at the top of a module is silently
   harmless (it just re-imports); in Kotlin an import statement is only legal before any declaration, so a
   second one further down the file is a hard syntax error. How to avoid: select-all and replace the whole
   file content, don't append.

## Stage 2. BookingController (repeat of the same mistake)

1. **Pasted the solution below the old file content again, and this time even the explanation text
   ("На что обратить внимание...") ended up inside the .kt file** → duplicate `package`/`import`, and two
   `class BookingController` declarations → `Conflicting import: ... is ambiguous` and
   `Redeclaration: class BookingController`. Same root cause as the BookingDto mistake: replacing a file means
   selecting everything and overwriting it, not adding to the end. How to avoid: before pasting, Cmd+A then
   paste (replaces selection), and only paste code blocks — never prose explanations — into a .kt file.

## Stage 3, Task 1. Vue setup (teacher's mistake, not the student's)

1. **I deleted `TheWelcome.vue` while cleaning up demo files, but `views/HomeView.vue` (from
   `npm create vue`) still imported it** → Vite failed with `Failed to resolve import`. Fixed by rewriting
   `HomeView.vue` to render `BookingList` instead. The student's own code (`BookingList.vue`) was correct
   and needed no changes.

## Stage 3, Task 3. BookingList.vue

1. **Pasted the new `<script>`/`<template>` content after the existing file instead of replacing it** →
   the file ended up with two `<template>` blocks → Vite/vue-compiler-sfc error
   `Single file component can contain only one <template> element`. Same root cause as the earlier Kotlin
   mistakes (BookingDto, BookingController): a .vue file, like a .kt file, must be replaced wholesale
   (Cmd+A then paste), not appended to — a Single File Component is parsed as one `<script>` + one
   `<template>` + optional `<style>`, never more than one of each.

## Stage 3, Task 4. BookingForm.vue

1. **Left a leftover `const nextId = ref(100)` after switching to `store.nextId`** → dead code, harmless
   but unused. TypeScript does not flag unused local `const`s by default (only unused imports/parameters in
   strict configs), so nothing failed — but it's a smell: a variable declared and never read. In Python this
   is the same as leaving an unused local variable after refactoring; linters (`ruff`, `flake8`) would flag it,
   just as `eslint`'s `no-unused-vars` would here if it were enabled.

## Stage 4. bookings.ts (teacher's mistake, not the student's)

1. **I posted the full solution in chat but wrote only the TODO skeleton to the actual file on disk** →
   the file compiled fine (a store with just `bookings`/`query`/`filteredBookings` is valid TypeScript,
   just incomplete) and `vue-tsc`/`vite build` had nothing to complain about — the missing functions were
   never referenced from inside `bookings.ts` itself, only from other files. The error only showed up at
   runtime in the browser console: `store.loadBookings is not a function`. Lesson: a clean typecheck of one
   file doesn't prove another file's calls into it are satisfied — always grep or re-read the actual file
   content after writing it, not just the message where the answer was shown.
