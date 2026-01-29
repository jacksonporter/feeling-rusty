plugins {
    kotlin("jvm") version "2.0.21"
}

group = "dev.jacksonporter"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.java.dev.jna:jna:5.14.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

kotlin {
    jvmToolchain(17)
}

tasks.jar {
    manifest {
        attributes("Implementation-Title" to "feeling-rusty", "Implementation-Version" to version)
    }
}
