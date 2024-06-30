FROM eclipse-temurin:17.0.9_9-jre-ubi9-minimal

ENV JAVA_TOOL_OPTIONS='-agentlib:jdwp=transport=dt_socket,address=*:7001,server=y,suspend=n'

ARG JAR_FILE

RUN mkdir -p /apps
COPY ./target/${JAR_FILE} /apps/app.jar
COPY ./entrypoint.sh /apps/entrypoint.sh
RUN chmod +x /apps/entrypoint.sh

EXPOSE 8080
CMD ["/apps/entrypoint.sh"]