plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }

    register("newDay", Copy::class) {
        val number = project.property("dayNumber").toString().padStart(2, '0')
        val day = "Day$number"
        check(!file("src/$day.kt").exists()) { "Files for $day already exists" }

        from("src/template")
        into("src")
        filter { it.replace("Template", day) }
        rename { it.replace("Template", day) }

        project.logger.lifecycle("Generated files for $day")
    }
}
