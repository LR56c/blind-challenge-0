import io.gitlab.arturbosch.detekt.Detekt

plugins {
	kotlin("jvm") version "1.9.22"
	id("io.gitlab.arturbosch.detekt") version ("1.23.3")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	implementation("io.arrow-kt:arrow-core:1.2.0")
	implementation("io.arrow-kt:arrow-fx-coroutines:1.2.0")
	detektPlugins("com.github.hbmartin:hbmartin-detekt-rules:0.1.4")
	val ktor_version = "2.3.8"
	implementation("io.ktor:ktor-client-core:$ktor_version")
	implementation("io.ktor:ktor-client-cio:$ktor_version")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
	implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
	implementation("io.ktor:ktor-client-logging:$ktor_version")
	implementation("ch.qos.logback:logback-classic:1.2.3")
	implementation("com.aallam.ulid:ulid-kotlin:1.3.0")
}

detekt {
	toolVersion = "1.23.3"
	config.setFrom(file("config/detekt/detekt.yml"))
	buildUponDefaultConfig = true
}

// Kotlin DSL
tasks.withType<Detekt>().configureEach {
	reports {
		xml.required.set(true)
		html.required.set(true)
		txt.required.set(true)
		sarif.required.set(true)
		md.required.set(true)
	}
}

tasks.test {
	useJUnitPlatform()
}
kotlin {
	jvmToolchain(17)
}
