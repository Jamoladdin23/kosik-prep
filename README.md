# Košík interview prep

A small full-stack project built while preparing for a technical interview at Košík.cz:
a Kotlin/Spring Boot API and a Vue 3/TypeScript frontend for managing salon client bookings.

## What's here

- **`kotlin-basics/`** — 6 self-contained Kotlin lessons (syntax, null safety, data classes/enums/`when`,
  collections, extension functions/sealed interfaces, coroutines), each with a Python reference solution,
  a task file, and a test suite. 71 tests, all green.
- **`backend/`** — a Spring Boot (Kotlin) REST API for managing bookings: JPA entity, repository, DTOs with
  Bean Validation, a REST controller (`GET`/`POST`/`PATCH`), and a `@SpringBootTest` + MockMvc test.
- **`frontend/`** — a Vue 3 app (Composition API, `<script setup>`, TypeScript, Vue Router, Pinia): a bookings
  list with search, a reusable booking item component, a create-booking form, and a Pinia store that now talks
  to the real backend over `fetch`.

## How to run it

Two terminals, both from the project root:

```bash
# terminal 1 — backend (Spring Boot, port 8080)
cd backend
./gradlew bootRun

# terminal 2 — frontend (Vite dev server, port 5173)
cd frontend
npm install   # first time only
npm run dev
```

Open `http://localhost:5173`. The frontend's dev server proxies `/api/*` requests to the backend
(configured in `frontend/vite.config.ts`), so the browser only ever talks to port 5173 — no CORS setup needed
in development. Data is stored in an in-memory H2 database, so it resets whenever the backend restarts.

To run the Kotlin lesson tests:

```bash
cd kotlin-basics
./gradlew test
```

To run the backend tests:

```bash
cd backend
./gradlew test
```

## API

| Method | Path                        | Description                          |
|--------|------------------------------|---------------------------------------|
| GET    | `/api/bookings`              | list all bookings                     |
| POST   | `/api/bookings`               | create a booking (`client`, `startsAt`) |
| PATCH  | `/api/bookings/{id}/cancel`  | cancel a booking                      |

## How it differed from Django/React

Coming from Python/Django/DRF and a bit of React, the biggest shift was Kotlin's static typing and
null safety: instead of finding out about a missing value at runtime (`AttributeError: 'NoneType'`),
the compiler forces you to handle `String?` before you can use it, which catches a whole class of bugs
before the code even runs. `data class` plus `copy()` replaced `@dataclass(frozen=True)` and
`dataclasses.replace()` almost one to one, and Kotlin's `when` on a `sealed interface` goes further than
Python's `match` by checking at compile time that every case is handled.

On the Spring Boot side, DTOs took over the role DRF serializers play — keeping the API's shape separate
from the JPA entity — and Spring Data's derived query methods (`findByStatus`) felt like a more explicit,
compiled version of Django's `.filter()`. Understanding *why* an entity isn't a `data class`, and why
`@Transactional` only works through a proxy (so it silently does nothing on a `private` method), mattered
more than in Django, where the ORM hides more of that machinery.

On the frontend, Vue's Composition API (`ref`, `computed`, `<script setup>`) maps closely to React hooks
(`useState`, `useMemo`), but `v-model` and `v-for`/`:key` remove a lot of the boilerplate React needs for
two-way binding and lists. Pinia's "setup store" reads just like a component, which made moving state out
of `BookingList.vue` and into a shared store feel much lighter than wiring up Context or Redux.
