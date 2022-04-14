#!/bin/bash

java --add-opens java.base/java.lang=ALL-UNNAMED \
  -cp /usr/lib/gap/gap.jar:/usr/lib/gap/lib/* io.surati.gap.maccount.module.web.server.Main \
  --port=8080 --driver=org.postgresql.Driver \
  --db-url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME} \
  --db-user=${DB_USER} --db-password=${DB_PASSWORD} \
  --threads=${NB_THREADS}
