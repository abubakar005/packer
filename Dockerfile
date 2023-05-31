FROM openjdk:11
MAINTAINER Abubakar

# Refer to Maven build
ARG JAR_FILE=target/packer-1.0-SNAPSHOT.jar

# set working directory
WORKDIR /opt/app

# copy jar file to the deirectory /opt/app/
COPY ${JAR_FILE} packer.jar

# Run project container
ENTRYPOINT ["java","-jar","packer.jar"]