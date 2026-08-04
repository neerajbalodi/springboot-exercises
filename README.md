# Spring Boot System Design Labs

Scenario-based labs for engineers with ~2–3 years of experience. Each package under
`com.labs.systemdesign` is a self-contained exercise: **starter code with `// TODO`
markers plus a failing test**. The job is to make the reds go green.

## How students work

```bash
mvn test
```

Everything runs offline on an in-memory H2 database — no Docker, no AWS, no external
services. First run downloads dependencies from Maven Central.

Run one exercise at a time:

```bash
mvn test -Dtest=PaymentServiceTest
mvn test -Dtest=KeysetPaginationTest
```

**Prerequisites:** JDK 17+ and Maven 3.9+. (Java 21 works too — the project targets 17.)

## Suggested flow

1. Read the class-level Javadoc in the exercise — it states the scenario and the TODOs.
2. Run the exercise's test, watch it fail, read the assertion message.
3. Implement until green.
4. Discuss the "why" using the prompts below — the green test is necessary but the
   design reasoning is the point.

## The exercises

| # | Package | Scenario | Core idea being tested |
|---|---------|----------|------------------------|
| 01 | `exercise01idempotency` | Duplicate `POST /payments` on client retry | Idempotency key as a DB primary key; race-safe insert |
| 02 | `exercise02async` | Slow shipping call exhausts the thread pool | Async + timeout + fallback |
| 03 | `exercise03caching` | Read-heavy catalog | `@Cacheable` + cache correctness on write |
| 04 | `exercise04bulkimport` | Import 500k rows | Chunked inserts + `flush()`/`clear()` |
| 05 | `exercise05events` | Signup must notify email + analytics | Event fan-out, decoupled consumers |
| 06 | `exercise06transfer` | Concurrent money transfer | Overdraft guard + locking |
| 07 | `exercise07pagination` | 100k orders per customer | Keyset (cursor) pagination |
| 08 | `exercise08saga` | Multi-step checkout, partial failure | Compensating actions (saga) |
| 09 | `exercise09ratelimit` | One client hammers the API | Token-bucket rate limiting |
| 10 | `exercise10nplus1` | Order list fires hundreds of queries | N+1 fix via `join fetch` |
| 11 | `exercise11retry` | Downstream returns intermittent 5xx | Retry with backoff + recover |

## Discussion prompts (per exercise)

- **01** Why is a DB unique constraint safer than an in-memory `if exists` check? What
  should happen if the *first* charge is still in flight when the retry arrives?
- **02** Where does the request thread actually block? What does moving the call to a
  queue buy you over `CompletableFuture`? What timeout is safe vs. the client's timeout?
- **03** `@CacheEvict` vs `@CachePut` on the write — trade-offs? What breaks with two app
  instances and an in-JVM cache? When do you reach for Redis + a TTL?
- **04** Why can `GenerationType.IDENTITY` silently disable JDBC batching? Where do you
  put the transaction boundary for a very large file? How do you stream the file itself?
- **05** Why `@TransactionalEventListener(AFTER_COMMIT)` in production — what goes wrong if
  you email before commit? When do in-process events stop being enough (→ Kafka/outbox)?
- **06** Why doesn't `@Transactional` alone stop a lost update? Optimistic (`@Version`) vs
  pessimistic (`SELECT ... FOR UPDATE`) — when each? How do you avoid deadlocks?
- **07** Why does `OFFSET 100000` get slower with depth? What breaks keyset paging if the
  sort column isn't unique? How do you page backwards?
- **08** What if a compensation itself fails? Why is an outbox + async saga more robust
  than a `try/catch`? What does "at-least-once" force you to make idempotent?
- **09** Why does the in-memory bucket under-count behind a load balancer? Token bucket vs
  fixed window vs sliding window? Where does the 429 + `Retry-After` header belong?
- **10** How did you detect the N+1? Why does `join fetch` on a collection break `Pageable`
  (in-memory pagination warning)? When is a DTO projection better than fetching entities?
- **11** Why is retrying a non-idempotent POST dangerous? What is a retry storm, and how do
  backoff + jitter + a circuit breaker prevent it? Retry 5xx but never 4xx — why?

## Notes

- Solutions live in `INSTRUCTOR_SOLUTIONS.md` (keep that out of the students' copy, or
  hand it out after the session).
- The build could not be compiled in the environment it was generated in, so run
  `mvn test` once yourself before class to confirm all 11 tests are red as expected.
# springboot-exercises
