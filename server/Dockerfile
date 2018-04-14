FROM openjdk:8
COPY /build/install/ .
WORKDIR .
RUN ./server/bin/server
CMD ["java", "Main"]
