Windows PowerShell run instructions

1) Build (uses system Maven):

mvn -DskipTests package

2) Run with the local (H2) profile so no external MySQL server is required:

java -jar target\demo-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=local

Or using JVM property:

java -Dspring.profiles.active=local -jar target\demo-0.0.1-SNAPSHOT.jar

Notes:
- Ensure JDK 17 (or compatible) is installed and JAVA_HOME is set.
- If you prefer to use MySQL, ensure MySQL is running and the credentials in `src/main/resources/application.yml` are correct.
