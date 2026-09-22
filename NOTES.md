# NOTES.md — Python → Kotlin / Vue cheat sheet

## Lesson 1. Basics

| Python | Kotlin | Notes |
|---|---|---|
| `x = 5` | `val x = 5` / `var x = 5` | `val` cannot be reassigned, `var` can. Prefer `val` |
| Type hints, not enforced | Static types, checked by the compiler | `val x: Int = "a"` does not compile |
| A variable can change type | A variable's type is fixed | `var x = 1; x = "a"` is an error |
| `f"Hi, {name}, {a + b}"` | `"Hi, $name, ${a + b}"` | Expressions go in `${}` |
| `f"{h:02d}"` | `"%02d".format(h)` | `$` templates have no format spec, use `.format` |
| `def f(a, b=0):` | `fun f(a: Int, b: Int = 0): Int` | Parameter and return types are required |
| `f(a=1, b=2)` | `f(a = 1, b = 2)` | Named arguments work the same way |
| `def f(): return x` | `fun f() = x` | Expression-bodied function, no `return` |
| `7 / 2 == 3.5`, `7 // 2 == 3` | `7 / 2 == 3`, `7 / 2.0 == 3.5` | `Int / Int` is `Int`. Trap: `12 / 100 == 0` |
| `1` and `1.0` are compatible | `val x: Double = 1` is an error, use `1.0` | No implicit conversion; `.toDouble()`, `.toInt()` |
| `round(x, 2)` | `Math.round(x * 100) / 100.0` | `Math.round` returns `Long` |
| `for i in range(1, n + 1)` | `for (i in 1..n)` | `..` includes the end; `1 until n` does not |
| Indentation defines blocks | Curly braces `{}`, no `;` | |
| `list = [1]; list.append(2)` | `val list = mutableListOf(1); list.add(2)` | `val` protects the reference, not the contents |

**Traps:** `Int / Int` is an integer; `val` ≠ immutable object; `Double` cannot be replaced by `Int`.

## Lesson 2. Null safety (my notes)

**Main idea:** in Python any variable can be `None`, and the error only appears at runtime.
In Kotlin `String` cannot be `null`; if it can, I write `String?` and the compiler forces me to handle it.

**The `?` sign has three places and three meanings:**

| What I write | Meaning | Python |
|---|---|---|
| `String?` | the value may be `null` | `Optional[str]` |
| `name?.length` | safe call: if `null`, the whole result is `null` | `len(name) if name is not None else None` |
| `name ?: "Anonymous"` | elvis: if the left side is `null`, use the right side | `name if name is not None else "Anonymous"` |

**What I ran into**
- `?.` makes the result nullable: `name?.length` is `Int?`, not `Int`, so `nameLength` returns `Int?`.
- `?:` reacts **only to `null`**. `"" ?: "Anonymous"` returns `""`. Python `name or "Anonymous"` would also
  replace an empty string — these are different things.
- Chains: `name?.firstOrNull()?.uppercaseChar()` — each `?.` skips the next step if the left side is `null`.
- `takeIf { "@" in it }` returns the value itself if the condition holds, otherwise `null`. Handy with `?.` and `?:`.
- `throw` is an expression in Kotlin: `phone ?: throw IllegalArgumentException("...")`.
  After this line the compiler knows `phone` is not `null`.
- `phone?.let { ... }` — "run the block only if not `null`". Inside, `it` is `String`, not `String?`.
  It replaces `if phone is not None:`.
- `!!` means "I am sure it is not `null`"; if wrong, `NullPointerException`. It brings the Python behaviour
  back, so avoid it (not needed in this lesson).
- Smart cast: after `if (email == null) return ...` the compiler treats `email` as a non-null `String`.
  There is nothing like it in Python.

**My mistakes:** typo `"Anonymus"`, and `"Phone"` instead of `"phone"` — the compiler checks types, not
the contents of strings, so text must be compared with the task letter by letter.

**One line:** `Optional[str]` + `if x is None` → `String?` + `?.` and `?:` — the compiler will not let me forget the check.

## Lesson 3. data class, enum, when (my notes)

| Python | Kotlin | Notes |
|---|---|---|
| `@dataclass(frozen=True) class B:` | `data class B(val a: String, val h: Int)` | `data` generates `equals`, `hashCode`, `toString`, `copy`; `val` ≈ `frozen` |
| `dataclasses.replace(b, h=11)` | `b.copy(h = 11)` | a new object, the original is unchanged |
| `class S(Enum): A = "a"` | `enum class S { A, B, C }` | values are objects already: `S.A` |
| `match s: case S.A: ...` | `when (s) { S.A -> ... }` | but `when` is an **expression** and returns a value |
| `if / elif / else` | `when { x < 1 -> ...; else -> ... }` | `when` without an argument is a chain of conditions |
| `s in (A, B)` | `S.A, S.B -> true` | several values separated by a comma |

**What to notice and not forget**
- `data` is what generates `copy`, `equals` and `toString`. Without it, `toString()` prints `Booking@1a2b3c`
  and `==` compares references, not fields.
- `when` without `else` on an enum is **good**: if a new status is added, the code stops compiling until
  it is handled. An `else` would silently hide the mistake. For `Int`/`String` an `else` is required.
- `when` is an expression, so its result can be returned or assigned directly (`val label = when (...) {...}`).
- `copy` takes named arguments (`copy(hour = 11)`); everything not mentioned stays as it was.
- Trap: `val` protects the field, not its contents — a `MutableList` inside a `data class` can still be changed.

**What I ran into:** I wrote `.copy()` but forgot `data` in front of `class Booking`, and the compiler said
`Unresolved reference 'copy'` — which also stopped the tests of all other lessons from compiling,
because all lessons are compiled together.

**One line:** `@dataclass(frozen=True)` + `replace()` → `data class` + `copy()`; `match` → `when`, which returns a value itself.

## Lesson 4. Collections (my notes)

| Python | Kotlin | Notes |
|---|---|---|
| `[b.client for b in bs if b.ok]` | `bs.filter { it.ok }.map { it.client }` | `it` is the current element; chains read left to right |
| `sum(b.price for b in bs)` | `bs.sumOf { it.price }` | an empty list gives `0` |
| `sorted(bs, key=lambda b: b.hour)` | `bs.sortedBy { it.hour }` | returns a **new** list, stable like Python |
| `{b.id: b for b in bs}` | `bs.associateBy { it.id }` | `Map<key, element>` |
| `defaultdict(list)` + loop | `bs.groupBy { it.status }` | `Map<key, List<element>>`; no pre-sorting (unlike `itertools.groupby`) |
| `Counter(b.status for b in bs)` | `bs.groupBy { it.status }.mapValues { it.value.size }` | `mapValues` changes values, keeps keys |
| `sorted(set(names))` | `names.distinct().sorted()` | `distinct()` keeps the order |
| `max(...)` on an empty list raises | `maxByOrNull { }` returns `null` | result is nullable, so `?.` / `?:` again |

**What to notice and not forget**
- `groupBy` returns lists; to get counts add `.mapValues { it.value.size }`. Read the return type in the signature.
- `filter`, `map`, `sortedBy` never change the original list; they return a new one.
- `maxByOrNull` / `firstOrNull` return `null` on an empty list — handle it (lesson 2).
- For very large data, `asSequence()` avoids intermediate lists (like a generator in Python).

**What I ran into:** I pasted the `groupBy` answer into `countByStatus` and left `groupByStatus` as `TODO()`.
The return type `Map<Status, List<Booking>>` did not match the test (`Map<Status, Int>`), and the compiler error
appeared in the *test* file (`Type inference failed`), which was confusing. One compile error blocks all lessons' tests.

**One line:** list comprehension → `filter`/`map` chain; `defaultdict`/`Counter` → `groupBy` (+ `mapValues`); `dict` comprehension → `associateBy`.

## Lesson 5. Extension functions, sealed interface + when (my notes)

| Python | Kotlin | Notes |
|---|---|---|
| plain function `initials(s)` | `fun String.initials(): String` → `s.initials()` | an extension is a plain function with a nicer call; `this` is the receiver |
| `"".join(w[0].upper() for w in s.split() if w)` | `split(" ").filter { it.isNotBlank() }.map { it.first().uppercaseChar() }.joinToString("")` | `split(" ")` yields empty words on double spaces |
| `r"\+\d{9,15}"` + `re.fullmatch` | `matches(Regex("""\+\d{9,15}"""))` | `"""..."""` is a raw string; `matches` needs the whole string to fit |
| `isinstance(r, Success)` | `r is BookingResult.Success` | `is` checks the type |
| `hour not in range(8, 20)` | `hour !in 8..19` | in Kotlin `..` includes the end |
| `Union[A, B, C]` of dataclasses | `sealed interface R { data class A(...) : R ... }` | the compiler knows every variant |
| `match r: case Success(...)` | `when (r) { is Success -> ... }` | **no `else`**: the compiler checks all variants are covered; Python's `match` does not |
| `max(ids, default=0)` | `maxOfOrNull { it.id } ?: 0` | nullable result + elvis |

**What to notice and not forget**
- Inside `is Success ->` the variable is smart-cast to `Success` (`r.booking` works with no cast).
- Do not add `else` to a `when` over a sealed type: it turns off the completeness check, which is the whole point.
- An extension cannot see `private` members and never overrides a member of the class with the same name.
- Early `return` for validation is fine, and after `if (x != null) return ...` the compiler treats `x` as non-null.
- This lesson combined the earlier ones: `?:`, `firstOrNull`, `sumOf`, smart cast, `data class`.

**What I ran into:** nothing failed — all 19 tests passed on the first run.

**One line:** free helper function → extension (`s.initials()`); `Union` + `match` → `sealed interface` + `when` without `else`, where the compiler checks completeness.

## Lesson 6. Coroutines (my notes)

| Python (asyncio) | Kotlin (coroutines) | Notes |
|---|---|---|
| `async def f():` | `suspend fun f()` | can only be called from another `suspend` fun or a coroutine |
| `await f()` | `f()` | no `await` keyword: calling a `suspend` function already waits |
| `await` in a loop (sequential) | plain loop calling `f()` | sequential: N requests = N × the delay |
| `await asyncio.gather(*tasks)` | `coroutineScope { ids.map { async { f(it) } }.awaitAll() }` | parallel; results keep the input order |
| `asyncio.create_task(...)` (fire and forget) | `launch { ... }` inside `coroutineScope` | `launch` returns a `Job`, `async` returns a `Deferred` with a result |
| `asyncio.sleep(1)` | `delay(1000)` | non-blocking; `Thread.sleep` blocks the thread |
| `asyncio.wait_for(...)` + `except TimeoutError` | `withTimeoutOrNull(ms) { ... }` | returns `null` on timeout, so the type is `T?` |
| `except asyncio.CancelledError: raise` | `catch (e: CancellationException) { throw e }` | never swallow cancellation |
| `TypeVar` | `fun <T> retry(...)` | generic function |

**What to notice and not forget**
- Concurrency starts only with `async { }`. A plain loop calling a `suspend` function is sequential — the same
  mistake as `await` inside a loop in Python.
- `coroutineScope` waits for all children and cancels the rest if one fails (structured concurrency).
  `asyncio.gather` does not do that by default.
- `launch` = "start and do not wait", `async` = "start and I need the result".
- In a `retry`, catch `CancellationException` first and rethrow it, then catch `Exception`.
- `return` inside `repeat { }` returns from the whole function, because `repeat` is `inline`.
- `require(cond) { message }` throws `IllegalArgumentException`; `lastError!!` is safe here only because
  `times > 0` guarantees at least one failed attempt (the compiler cannot know that).
- Tests use `runTest` with virtual time: `delay` is skipped, and `currentTime` proves parallel (1 s) vs sequential (3 s).

**What I ran into:** a missing closing `}` after `coroutineScope { ... }` in `fetchAll`. Kotlin ignores indentation, so the
next function was parsed as part of the block and the compiler reported `Expecting '}'` at the end of the file plus a
misleading `Argument type mismatch: Unit`. Read the *first* error, and close braces immediately.

**One line:** `async def` / `await` / `gather` → `suspend` / plain call / `async { }.awaitAll()` inside `coroutineScope`, which also gives structured concurrency.

## Stage 2. Django → Spring Boot cheat sheet

| Django/DRF | Spring Boot (Kotlin) | Notes |
|---|---|---|
| `models.Model` subclass | `@Entity class Booking` | maps to a DB table |
| `models.CharField(max_length=...)` | `@Column(length = ...) val client: String` | |
| `models.ForeignKey` | `@ManyToOne` + `@JoinColumn` | |
| `class Meta: ...` / migrations | Hibernate (`ddl-auto=update`) generates the schema from entities | fine for dev, not for production |
| `Model.objects` (manager) | `interface BookingRepository : JpaRepository<Booking, Long>` | Spring Data generates the implementation |
| `Booking.objects.filter(status="x")` | `fun findByStatus(status: Status): List<Booking>` on the repository | Spring Data derives the query from the method name |
| DRF `Serializer` | a `data class` DTO + manual mapping (or a mapper function) | no automatic reflection-based serialization by default |
| DRF `APIView` / `ViewSet` | `@RestController` + `@RequestMapping` | |
| `@api_view(["GET"])` | `@GetMapping` | |
| `request.data` validated by serializer | `@Valid @RequestBody dto: CreateBookingRequest` | validation via `jakarta.validation` annotations |
| `settings.py` DATABASES | `application.properties` (`spring.datasource.*`) | H2 in-memory DB used here, like SQLite for local dev |
| `python manage.py test` | `./gradlew test` (JUnit 5 + MockMvc) | |
| `pytest.mark.django_db` + APIClient | `@SpringBootTest` + `MockMvc` | |

**Why an entity is usually not a `data class`:** JPA/Hibernate needs a no-arg constructor and lazy-loaded
proxy objects, and it mutates entities internally (sets the id after insert, replaces collections for lazy
loading). A `data class`'s generated `equals`/`hashCode` use ALL fields, including the id and lazy relations —
comparing or hashing an entity (e.g. putting it in a `HashSet`) can trigger unwanted DB queries or break once
the id changes after save. Plain classes with `var` fields and an identity-based (or id-based) `equals` are safer.

**Why the `kotlin-spring` and `kotlin-jpa` plugins are needed:** Kotlin classes are `final` and constructor
parameters are not mutable by default, but Spring proxies (`@Transactional`, `@Async`) and Hibernate proxies
need to subclass, and JPA needs a no-arg constructor and non-final entity classes. `kotlin-spring` auto-opens
classes annotated with `@Component`, `@Service`, `@Configuration`, etc. `kotlin-jpa` auto-opens `@Entity` classes
and generates a no-arg constructor for them.

**Why `@Transactional` does not work on private methods:** it works through a dynamic proxy (subclassing or
a JDK proxy) — Spring wraps the bean and adds transaction start/commit around the call. A proxy can only
intercept calls that go through it from outside; a call to a `private` method (or a call from another method
of the same class) bypasses the proxy entirely, so no transaction is opened. Same root cause as why the class
must not be `final`.

**N+1 vs `select_related`:** in Django, `Booking.objects.all()` then accessing `b.client.name` per row issues
one extra query per row unless you called `.select_related("client")`. JPA has the identical problem: fetching
`bookingRepository.findAll()` then reading `b.client.name` triggers a separate `SELECT` per booking for a
lazy `@ManyToOne`, unless you use a JOIN FETCH query (`@Query("... JOIN FETCH b.client")`) or `@EntityGraph`,
which are the JPA equivalents of `select_related`.

## Stage 2, Task 2. BookingRepository — why it matters

Extending `JpaRepository<Booking, Long>` gives CRUD (`save`, `findAll`, `findById`, `deleteById`, ...) with
zero code, the way Django gives `Booking.objects` for free. `findByStatus(status: Status)` needed no body:
Spring Data parses the method name ("findBy" + the field "Status") and builds the SQL itself — a derived
query method, the equivalent of `Booking.objects.filter(status=status)`.

## Stage 2, Task 3. BookingDto — why it matters

Separating `BookingResponse`/`CreateBookingRequest` from the `Booking` entity is what a DRF serializer does:
API shape stays stable even if the entity later gets lazy relations or internal fields.
`@field:NotBlank`/`@field:Future` are Bean Validation annotations, the equivalent of DRF field validators—
Spring checks them automatically when the controller uses `@Valid`. `toResponse()` is an extension function
(lesson 5) that maps entity → DTO, like calling a serializer on a model instance.

## Stage 2, Task 4. BookingController — why it matters

`@RestController` = `@Controller` + `@ResponseBody`: Spring serializes the return value to JSON itself,
like DRF's `Response(serializer.data)`. Constructor injection (`private val repository: BookingRepository`
in the class header) is how Spring wires dependencies — the class simply can't compile without one, unlike
`@Autowired` on a field. `@Valid` on `@RequestBody` runs the Bean Validation annotations from the DTO
automatically and returns 400 before the method body runs if they fail — the same effect as
`serializer.is_valid(raise_exception=True)`. `@PathVariable` reads `{id}` from the URL, like `pk` in
Django's `urls.py`. `findById(id).orElseThrow()` is the JPA/Java equivalent of `get_object_or_404`.

## Stage 2, curl check — result

`GET /api/bookings` → `[]`, `POST` → `201` with the created booking (`status: PENDING`), a second `GET` shows
the list with it, `PATCH .../1/cancel` returns the same booking with `status: CANCELLED`, confirmed by a final
`GET`. The whole read/write/update loop through H2 worked first try.

## Stage 2, Task 5. BookingControllerTest — why it matters

`@SpringBootTest` boots the whole Spring context (all beans, the repository, H2), unlike a lightweight unit
test — the equivalent of `@pytest.mark.django_db` with a real `APIClient`. `@AutoConfigureMockMvc` gives
`MockMvc`, which sends requests without opening a real port, like Django's test `Client`. `lateinit var` is a
promise that Spring fills the field before first use, avoiding a nullable type with `!!`/`?.` everywhere.
`jsonPath("$[0].client")` reads a field from the JSON response body directly, without deserializing into a class.

## Stage 3, Task 1. Bookings list with search — my notes

| React | Vue (Composition API) | Notes |
|---|---|---|
| `useState(x)` / `setX` | `ref(value)`, read/write as `x.value` in `<script>` | in `<template>` Vue unwraps it automatically, no `.value` |
| `useMemo(() => ..., [deps])` | `computed(() => ...)` | no dependency array — Vue tracks what was read inside |
| `{list.map(i => <li key={i.id}>{i}</li>)}` | `<li v-for="i in list" :key="i.id">{{ i }}</li>` | `:key` is short for `v-bind:key`, same purpose as React's `key` |
| `<input value={x} onChange={e => setX(e.target.value)} />` | `<input v-model="x" />` | one directive instead of value + onChange |

**What to notice and not forget**
- `ref()` is a reactive box: `.value` in script, no `.value` in template.
- `computed()` recalculates automatically when a `ref` it reads changes.
- `<script setup lang="ts">` — everything declared at the top level is automatically exposed to the template,
  no `return { ... }` needed (unlike the Options API or plain `setup()`).

**What I ran into:** none — the teacher's project skeleton had a broken import in `HomeView.vue`
(unrelated to this task), fixed before grading. My own code was correct on the first submission.

**One line:** `useState` + `useMemo` → `ref` + `computed`, Vue tracks dependencies automatically instead of a dependency array.

## Stage 3, Task 2. BookingItem component — my notes

| React | Vue (Composition API) | Notes |
|---|---|---|
| `function Item({ booking }: Props)` | `defineProps<{ booking: Booking }>()` | a compiler macro, no import; props are read-only |
| `onCancel(booking.id)` callback prop | `defineEmits<{ cancel: [id: number] }>()` + `emit('cancel', id)` | the child declares and fires a typed custom event |
| `{cond && <button onClick={fn}>}` | `<button v-if="cond" @click="fn">` | `v-if` removes the element from the DOM entirely when false |
| `setBookings(prev => prev.map(...))` (new array) | find the object in a `ref` array and mutate its field directly | `ref()` tracks nested mutations too, no need to copy |

**What to notice and not forget**
- `defineProps`/`defineEmits` need no `import` — they are compiler macros, available automatically inside `<script setup>`.
- Props are one-way and read-only in the child, exactly like React props; to change something in the parent,
  emit an event instead of mutating the prop.
- Mutating an object inside a `ref` array (`booking.status = 'CANCELLED'`) is reactive by itself — unlike React,
  there is no need to build a new array with `.map()`.

**What I ran into:** none — both files compiled and typechecked on the first submission.

**One line:** callback props (`onX`) → typed custom events (`defineEmits` + `emit`); `setState` copy-on-write → direct mutation of a reactive object.

## Stage 3, Task 3. Create-booking form — my notes

| React | Vue | Notes |
|---|---|---|
| `useState('')` per field | `ref('')` per field | each bound with its own `v-model` |
| `onSubmit={e => { e.preventDefault(); ... }}` | `<form @submit.prevent="handleSubmit">` | `.prevent` is an event modifier, replaces `e.preventDefault()` in the handler |
| `onCreate(newBooking)` callback prop | `defineEmits<{ create: [booking: Booking] }>()` + `emit('create', booking)` | same pattern as `BookingItem`'s `cancel` event |
| `setBookings(prev => [...prev, newBooking])` | `bookings.value.push(booking)` | a `ref` array is reactive on `.push()` too, no spread copy needed |

**What to notice and not forget**
- Event modifiers (`.prevent`, `.stop`, `.once`, ...) move small pieces of handler logic into the template.
- A Single File Component (`.vue`) allows exactly one `<script>`, one `<template>`, and an optional `<style>` —
  pasting new content after the existing blocks instead of replacing the file produces a duplicate block.

**What I ran into:** pasted the updated `BookingList.vue` content after the old file instead of replacing it,
producing two `<template>` blocks and a Vite compile error. Same mistake pattern as earlier Kotlin lessons —
always Cmd+A then paste to replace a whole file.

**One line:** per-field `useState` + manual `preventDefault` → per-field `ref` + `@submit.prevent`; callback props → typed `emit`.

## Stage 3, Task 4. Pinia store — my notes

| React | Vue (Pinia setup store) | Notes |
|---|---|---|
| Context + Provider holding state | `defineStore('name', () => { ... return {...} })` | a "setup store" reads exactly like `<script setup>`: `ref` = state, `computed` = getters, functions = actions |
| `useContext(BookingsContext)` | `useBookingsStore()` | called inside any component that needs the shared state |
| passing state down via props/callbacks | components import the store directly and read/call it | no prop drilling |
| `store.dispatch(action)` (Redux) | `store.cancelBooking(id)` — call the action directly | Pinia actions are plain functions, no action-type strings |

**What to notice and not forget**
- Everything returned from the store setup function becomes its public API — same idea as a module's exports.
- `v-model="store.query"` and `@cancel="store.cancelBooking"` work directly on store properties/methods —
  no need to destructure the store first (destructuring a Pinia store loses reactivity unless done with
  Pinia's `storeToRefs`, which wasn't needed here since we always access via `store.`).
- Moving `bookings`/`query`/`nextId` out of `BookingList.vue` and into the store removed all the "lifting state
  up" plumbing (`handleCancel`, `handleCreate`, event listeners in the parent) — the store is available to any
  component without threading props through the tree.

**What I ran into:** left a stray `const nextId = ref(100)` in `BookingForm.vue` after switching to
`store.nextId` — dead code that compiled fine but was never read. Same class of mistake as an unused
local variable in Python after a refactor.

**One line:** Context/Redux-style global state → a Pinia "setup store", written exactly like `<script setup>`, imported directly wherever it's needed.

## Stage 4. Connecting frontend and backend — result

Vite proxy (`/api` → `http://localhost:8080`) added to `vite.config.ts`; the store's `loadBookings`,
`cancelBooking`, `createBooking` now use `fetch` against `/api/bookings` instead of local hardcoded data.
Verified end to end with curl through the proxy port (5173): GET returned the same data as hitting the
backend directly on 8080, and a POST through the proxy created a real row in the H2 database. No CORS
issue in dev, since the browser only ever talks to :5173.

## Stage 4. End-to-end check — confirmed by the student

List loads on page open (`onMounted` → `loadBookings`), search still filters the live data, the create form
adds a real row via `POST`, and Cancel updates status via `PATCH` — all persisted in the backend's H2 database,
not in a local JS variable. Refreshing the page keeps the data, confirming the API is now the source of truth.
