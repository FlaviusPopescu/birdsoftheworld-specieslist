plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("com.fleeksoft.ksoup:ksoup-lite:0.1.8")
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}


println("${project.sourceSets.forEach { println(it) }}")