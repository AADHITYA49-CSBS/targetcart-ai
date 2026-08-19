#!/bin/bash
# 00-init.sh - Deterministic database initialization for TargetCart-AI.
# Source-of-truth SQL lives in /db-ref/schema/ and /db-ref/seed/.
#
# IMPORTANT: The mysql:8.0 entrypoint executes .sh files as subprocesses
# (not sourced) when they have execute permission, which bind-mounted files
# always do on Docker Desktop. Therefore docker_process_sql is NOT available.
# We call the mysql client directly. By the time init scripts run, the
# entrypoint has already called FLUSH PRIVILEGES, so root authentication is
# required via MYSQL_ROOT_PASSWORD.

set -euo pipefail

echo "==> Initializing schema..."

mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/schema/001-create-users.sql
mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/schema/002-create-products.sql
mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/schema/003-create-carts.sql
mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/schema/004-create-cart-items.sql
mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/schema/005-create-campaigns.sql
mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/schema/006-create-execution-logs.sql

echo "==> Schema complete. Initializing seed data..."

mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/seed/001-users.sql
mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/seed/002-products.sql
mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/seed/003-carts.sql
mysql -u root "-p${MYSQL_ROOT_PASSWORD}" --protocol=socket "${MYSQL_DATABASE}" < /db-ref/seed/004-cart-items.sql

echo "==> Seed data complete."
