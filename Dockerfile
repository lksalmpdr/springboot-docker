# Imagem do Java como build
FROM openjdk:20 as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw

# Download das dependencias
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Imagem de producaoo do Java
FROM openjdk:20 as production
ARG DEPENDENCY=/app/target/dependency

# Copiar as dependencias para o build artifact
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build /app/target/*.jar app.jar

# Rodar a aplicação Spring boot
#ENTRYPOINT ["java", "-cp", "app:app/lib/*","com.loja.LojaApplication", "--server.port=3000"]

ENTRYPOINT ["java", "-jar", "app.jar"]