# PROGRESS.md

**Interview:** Košík.cz, Wed 23 Sep, 16:00. Software Engineer, full-stack (Kotlin/Java + Vue/TypeScript).

## Rules (for a new session)
I am the teacher. The student is a Python/Django/DRF developer who knows a little React.
I explain in Russian, always by comparison with Python/Django/React. Domain: salon client
bookings (Booking, Client, Status), like the student's SaaS reservla.
**I do not write the student's code.** I provide the skeleton, a Python reference, tasks and tests.

Student commands: "проверь [урок N]" (run tests + code review + log mistakes in MISTAKES.md),
"подсказка" (a hint, no code), "объясни X", "сдаюсь" (show the solution; the student may also ask
for solutions directly, then give them with comments and Python comparisons),
"вопросы" (5 interview questions, one at a time, with feedback), "дальше" (next task).
At the end of each stage, run "вопросы" automatically.

### Language rule (IMPORTANT)
The company will read this repository and does not read Russian. Therefore:
1. **New tasks** (lesson README, hints, TODO comments) are given to the student **in Russian**.
2. **After a task is completed**, translate everything about it into **English**: the lesson
   `README.md`, the comments/hints in the `.kt`/`.py` files (keep the student's code unchanged),
   and the entries in `NOTES.md`, `MISTAKES.md`, `PROGRESS.md`.
3. `NOTES.md`, `MISTAKES.md` and `PROGRESS.md` are always kept in English. Test string values
   (e.g. Czech labels) stay as they are.
4. After a task is accepted ("проверь" passes), also add a short "why this matters" note to NOTES.md — not just the code translation, but the explanation of what the piece gives you.
5. Before the final `git push`, check that nothing Russian is left:
   `grep -rlP "[А-Яа-яЁё]" . --exclude-dir=.git --exclude-dir=build --exclude-dir=node_modules`

## Stages
1. `kotlin-basics/` — 6 lessons (1 basics, 2 null safety, 3 data class/enum/when,
   4 collections, 5 extensions + sealed, 6 coroutines)
2. `backend/` — Spring Boot: Booking entity → Repository → DTO → Controller → 1 MockMvc test;
   explain: why an entity is not a data class, kotlin-spring/kotlin-jpa plugins,
   @Transactional on private methods, N+1 vs select_related
3. `frontend/` — Vue 3 (TS, Router, Pinia): list + search, booking component, form, store
4. Connect: proxy /api → :8080, the student writes the fetch calls, README
   (the section "how it differed from Django/React" is written by the student)

## Where we are
- [x] Environment: JDK 21, Node 24, npm 11, Gradle (brew, only to generate the wrapper)
- [x] `kotlin-basics/` skeleton (Gradle Kotlin DSL, JUnit 5, `./gradlew test`)
- [x] Lesson 1 — passed (8/8). NOTES.md was written by the teacher at the student's request
- [x] Lesson 2 — passed (15/15) after fixing two string typos
- [x] Lesson 3 — passed (9/9) after adding the missing `data` keyword
- [x] Lesson 4 — passed (11/11) after fixing `groupByStatus`/`countByStatus`
- [x] Lesson 5 — passed (19/19) on the first run
- [x] Lesson 6 — passed (9/9) after adding a missing closing brace
- [x] **Stage 1 (kotlin-basics) complete: 71 tests green.** Stage-1 review questions: Q1 answered (2/10, incomplete), Q2–Q5 pending. Student reads `podskaz/Kotlin_povtorenie.md` (git-ignored) Tue morning and answers Q1–Q5 in chat
- [x] **Stage 2 (backend/ Spring Boot) complete:** Booking entity, BookingRepository, DTOs, BookingController
  (GET/POST/PATCH, verified with curl), BookingControllerTest (@SpringBootTest + MockMvc) — all green
- [x] Stage 3, Task 1 (bookings list + search) — passed on first submission
- [x] Stage 3, Task 2 (BookingItem: defineProps/defineEmits) — passed on first submission
- [x] Stage 3, Task 3 (create-booking form) — passed after fixing a duplicate <template> block
- [x] Stage 3, Task 4 (Pinia store) — passed; removed one leftover dead-code line
- [x] **Stage 3 (frontend, hardcoded data) complete**
- [x] **Stage 4: connected** — Vite proxy `/api` -> :8080, store uses fetch, verified end-to-end via curl through the proxy
- [x] **Stage 4 confirmed working end-to-end by the student in the browser**
- [x] README.md written (the "How it differed" section left as a TODO heading for the student)
- [x] README.md "How it differed" section drafted by the teacher at the student's request (student should read & personalize before the interview)
- [ ] **Next: git init/push to GitHub** (student confirms before push, per rule)
- [ ] Stage 4: connect frontend + backend, push to GitHub

## Useful commands
```bash
cd ~/kosik-prep/kotlin-basics
./gradlew cleanTest test --tests '*CollectionsTest*' --rerun-tasks   # one lesson
./gradlew cleanTest test --rerun-tasks                               # everything
```
IntelliJ shows no ▶ button until the Gradle project is imported, so run tests from the terminal.
