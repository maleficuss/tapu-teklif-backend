FROM openjdk:11

COPY ./target/todo.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch todo.jar'

ENTRYPOINT ["java","-jar","todo.jar"]