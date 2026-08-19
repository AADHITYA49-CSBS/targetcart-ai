# TargetCart-AI Database Foundation

## Purpose

MySQL 8.x schema for the TargetCart-AI abandoned-cart recovery and personalized e-commerce marketing engine.

## Table Responsibilities

| Table | Purpose |
|---|---|
| `users` | Customers on the e-commerce platform |
| `products` | Product catalog |
| `carts` | Shopping carts, tracking lifecycle from active through abandoned/recovered/expired |
| `cart_items` | Products inside carts, preserving price at time of addition |
| `campaigns` | Recovery marketing campaigns generated for abandoned carts |
| `execution_logs` | AI and campaign execution metrics with token/duration tracking |

## Relationship Overview

```
users
  1 ──── N carts

carts
  1 ──── N cart_items

products
  1 ──── N cart_items

users
  1 ──── N campaigns

carts
  1 ──── N campaigns

campaigns
  1 ──── N execution_logs
```

## Schema Execution Order

Scripts must run in numeric order due to foreign-key dependencies:

```
db/schema/001-create-users.sql
db/schema/002-create-products.sql
db/schema/003-create-carts.sql          (depends on users)
db/schema/004-create-cart-items.sql     (depends on carts, products)
db/schema/005-create-campaigns.sql      (depends on carts, users)
db/schema/006-create-execution-logs.sql (depends on campaigns)
```

## Seed Execution Order

```
db/seed/001-users.sql
db/seed/002-products.sql
db/seed/003-carts.sql       (depends on users)
db/seed/004-cart-items.sql  (depends on carts, products)
```

## Local MySQL / Docker Usage

Start a temporary MySQL instance:

```bash
cd db
docker compose up -d
```

Connect and run schema:

```bash
docker compose exec mysql mysql -uroot -proot targetcart_ai -e "source /docker-entrypoint-initdb.d/001-create-users.sql"
# ... repeat for each file in order
```

Or load all at once:

```bash
docker compose exec mysql mysql -uroot -proot targetcart_ai \
  -e "source /docker-entrypoint-initdb.d/schema/001-create-users.sql" \
  -e "source /docker-entrypoint-initdb.d/schema/002-create-products.sql" \
  -e "source /docker-entrypoint-initdb.d/schema/003-create-carts.sql" \
  -e "source /docker-entrypoint-initdb.d/schema/004-create-cart-items.sql" \
  -e "source /docker-entrypoint-initdb.d/schema/005-create-campaigns.sql" \
  -e "source /docker-entrypoint-initdb.d/schema/006-create-execution-logs.sql" \
  -e "source /docker-entrypoint-initdb.d/seed/001-users.sql" \
  -e "source /docker-entrypoint-initdb.d/seed/002-products.sql" \
  -e "source /docker-entrypoint-initdb.d/seed/003-carts.sql" \
  -e "source /docker-entrypoint-initdb.d/seed/004-cart-items.sql"
```

Stop and remove:

```bash
docker compose down -v
```

## Design Decisions

- **InnoDB** for transaction support and foreign-key enforcement.
- **utf8mb4** for full Unicode support including emojis.
- **BIGINT UNSIGNED** primary keys for scalability.
- **DECIMAL(12,2)** for cart/campaign monetary values; **DECIMAL(10,2)** for product prices.
- **ENUM** for cart status, campaign type, campaign status, and execution status.
- **CHECK constraints** for data integrity (e.g., positive quantity).
- **Explicit foreign keys** with CASCADE delete for child records, RESTRICT for products.
- **Schema files are the source of truth** — Hibernate `ddl-auto` is set to `validate`.
- **No seed data for campaigns or execution_logs** — these will be created by the Spring Boot/Spring AI workflows.
