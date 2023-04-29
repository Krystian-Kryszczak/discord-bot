plugins {
    id("java")
}

group = "krystian.kryszczak"
version = "0.5-beta"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:VERSION")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
