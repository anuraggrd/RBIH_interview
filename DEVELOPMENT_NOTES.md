# Development Notes

## Overall approach

I built this as a straightforward Spring Boot REST service with a clear request flow:

1. Validate the payload (Bean Validation → HTTP 400)
2. Run hard eligibility checks
3. If eligible, classify risk and price the offer for the requested tenure
4. Re-check EMI against the 50% income cap for the final rate
5. Persist the decision (approved or rejected) for audit

I kept pricing and eligibility in small, focused classes so the rules stay easy to read and unit-test without spinning up the full Spring context for every case.

## Key design decisions

- **H2 + JPA for audit storage** — the brief asks to store decisions. In-memory H2 is enough for this exercise and keeps the project self-contained. The entity stores both the input snapshot and the outcome (status, offer fields / rejection reasons).
- **Collect all eligibility failures** — instead of failing fast on the first rule, I accumulate reasons. That matches the sample rejected response which shows multiple reason codes.
- **Base rate for eligibility EMI, final rate for the offer** — eligibility uses the 12% base rate (as specified). Offer EMI is recalculated with the risk-adjusted rate, then checked against the 50% cap.
- **BigDecimal throughout money/rate math** — scale 2, `HALF_UP`, with a slightly higher working precision inside the EMI power calculation to avoid premature rounding.
- **Sparse JSON on optional fields** — `offer` / `rejectionReasons` are omitted when null. `riskBand` is always present (including `null` on rejects) to stay aligned with the sample contract.
- **Rejection reason for the 50% offer cap** — the brief only named a couple of codes. I added `EMI_EXCEEDS_50_PERCENT` and `CREDIT_SCORE_BELOW_MINIMUM` so every reject path has an explicit, stable code.

## Trade-offs

- No auth / idempotency keys — out of scope for the exercise.
- Rejection reasons on the entity are stored as a comma-separated string rather than a join table. Simple and fine for audit of a small number of codes; I'd normalise this in a real system.
- Tenure-in-years for the age rule is `tenureMonths / 12` with fractional years (e.g. 18 months → 1.5). The brief didn't specify integer vs fractional; fractional felt closer to "tenure in years".
- I didn't expose a GET-by-id endpoint. Persistence is there for audit; the create call returns everything the client needs.

## Assumptions

- Age + tenure uses `>` against 65, so exactly 65 is still allowed.
- Loan size premium applies only when amount is **greater than** 10,00,000 (not equal).
- Credit scores in 300–599 fail validation… wait, no — validation allows 300–900, and eligibility rejects `< 600`. So 300–599 are syntactically valid requests that come back as REJECTED, not 400.
- Invalid enum values (e.g. unknown employment type) are treated as malformed input → 400.
- A single offer always uses the requested tenure; no alternate tenure search.

## What I'd improve with more time

- Integration tests with `@SpringBootTest` + MockMvc for the full HTTP contract
- Flyway migrations and a proper relational schema for rejection reasons
- Configurable rate cards / caps via config or a rules table instead of hardcoded constants
- Request correlation id + structured logging around decisions
- Actuator health/metrics and a thin Dockerfile
- GET `/applications/{id}` for audit lookup
