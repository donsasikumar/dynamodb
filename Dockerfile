FROM 725071346040.dkr.ecr.eu-west-1.amazonaws.com/scanned/release/shp/base-images/openjdk:1.8.0.latest-20210812

ARG APP

USER root
WORKDIR /home/app
RUN chown -R app:app /home/app

USER app
COPY --chown=app:app . /home/app/
RUN mv ./target/${APP} /home/app/app.jar

RUN echo  "#!/bin/bash" > /home/app/docker-entrypoint.sh
RUN echo "java \$JAVA_OPTS -jar -Dspring.profiles.active=local-issuers,mock-security-redis /home/app/app.jar" >> /home/app/docker-entrypoint.sh
ENTRYPOINT ["/bin/bash","/home/app/docker-entrypoint.sh"]
