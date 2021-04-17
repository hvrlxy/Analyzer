plugins {
    java
    application
}

dependencies {
    "testImplementation"("junit:junit:4.13") 
}

repositories {
    mavenCentral()
}

tasks {
    named<Test>("test") {
        testLogging.showExceptions = true
    }
}

application {
    mainClassName = "Analyzer"
}
