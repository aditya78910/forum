FROM gradle:8.7.0-jdk21 AS builder
VOLUME /home/gradle/.gradle
VOLUME /tmp
USER root
ADD . /home/gradle/project
WORKDIR /home/gradle/project
RUN chown gradle:gradle -R /home/gradle
USER gradle
RUN gradle bootJar
#Start from a java:8
RUN ls -l /home/gradle/project/build/libs
RUN ls -l /home/gradle/project/build/libs
RUN mv /home/gradle/project/build/libs/*.jar /home/gradle/project/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/home/gradle/project/app.jar"]
