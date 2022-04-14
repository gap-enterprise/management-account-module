FROM openjdk:17-oracle

LABEL description="GAP - Gestion Automatisée des Paiements."
LABEL maintainer="team@surati.io"

RUN groupadd -r -g 2020 gap && \
   adduser -M -r -g gap -u 2021 -s /sbin/nologin gap && \
   mkdir -p /etc/gap /usr/lib/gap /var/gap && \
   chown gap:gap -R /etc/gap /usr/lib/gap /var/gap

USER 2021:2020

COPY ./target/management-account-module-1.0-SNAPSHOT.jar /usr/lib/gap/gap.jar
COPY ./target/dependency /usr/lib/gap/lib
COPY ./run.sh /etc/gap

ENV DB_NAME="db_gap" \
    DB_PORT=5432 \
    DB_USER="gap" \
    DB_PASSWORD="admin" \
    DB_HOST="pg_gap" \
    NB_THREADS="5"

EXPOSE 8080

VOLUME /etc/gap /var/gap
WORKDIR /var/gap

ENTRYPOINT ["sh", "/etc/gap/run.sh"]