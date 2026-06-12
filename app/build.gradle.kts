plugins {
    java
    application
}

group = "com.matchflix"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {

    // Ler .env
    implementation("io.github.cdimascio:java-dotenv:5.2.2")

    // JSON (users.json)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0")

    // Conexão RestTemplate
    implementation("org.springframework:spring-web:6.2.7")
}

application {
    mainClass.set("Application")
}

tasks.test {
    useJUnitPlatform()
}