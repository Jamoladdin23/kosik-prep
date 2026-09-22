# Lesson 4. Collections: filter / map / groupBy / sortedBy / associateBy

**What it is.** Instead of list comprehensions, Kotlin chains functions on a collection:
`list.filter { condition }.map { transform }`. Inside `{ }` the current element is called `it`.
- `filter { }` keeps matching elements; `map { }` transforms each one
- `groupBy { }` → `Map<key, List<element>>`; `associateBy { }` → `Map<key, element>`
- `sortedBy { }` → a new sorted list (the original is unchanged)
- `sumOf { }`, `distinct()`, `maxByOrNull { }`, `mapValues { }`

**Python equivalent.** `[b.client for b in bs if b.ok]` → `bs.filter { it.ok }.map { it.client }`;
`itertools.groupby` (which requires sorted input!) → `groupBy`; `{b.id: b for b in bs}` → `associateBy { it.id }`;
`sorted(bs, key=lambda b: b.hour)` → `sortedBy { it.hour }`.

**Main trap.** `maxByOrNull` and `firstOrNull` return `null` for an empty list (Python's `max([])` raises),
so the result is `Int?` and the `?.` / `?:` operators from lesson 2 are needed again.
Also: `filter` + `map` create intermediate lists; for big data use `asSequence()` (like a generator).

**Task.** Replace the `TODO()`s in `Collections.kt`. Reference: `python_reference.py`.
Run: `./gradlew cleanTest test --tests '*CollectionsTest*' --rerun-tasks`
