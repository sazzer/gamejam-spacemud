FROM openjdk:8u151-jre-alpine

ARG WAR_FILE
RUN echo WAR_FILE=${WAR_FILE}

ENV PG_URL=
ENV PORT=8080

COPY ./${WAR_FILE} /opt/spacemud/backend.war
WORKDIR /opt/spacemud

CMD java -Dspring.datasource.url="$PG_URL" -Dserver.port="$PORT" -jar backend.war


