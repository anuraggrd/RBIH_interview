# Loan Offer Service

Spring Boot service that evaluates a loan application and returns a single approve/reject decision with a priced offer (requested tenure only).

## Requirements

- JDK 17+
- Maven 3.9+

## Run

```bash
mvn spring-boot:run
```

## Example

```bash
curl -X POST http://localhost:8080/applications \
  -H "Content-Type: application/json" \
  -d '{
    "applicant": {
      "name": "Anita Sharma",
      "age": 30,
      "monthlyIncome": 75000,
      "employmentType": "SALARIED",
      "creditScore": 720
    },
    "loan": {
      "amount": 500000,
      "tenureMonth": 36,
      "purpose": "PERSONAL"
    }
  }'
```

## Tests

```bash
mvn test
```

See `DEVELOPMENT_NOTES.md` for design notes and assumptions.
