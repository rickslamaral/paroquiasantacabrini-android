import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    alias(libs.plugins.detekt)
    alias(libs.plugins.spotless)
}

group = "br.com.paroquiasantacabrini.app.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21
    }
}

spotless {
    kotlin {
        target("src/**/*.kt")
        ktlint(libs.versions.ktlintCli.get()).editorConfigOverride(
            mapOf("android" to "true"),
        )
        endWithNewline()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint(libs.versions.ktlintCli.get()).editorConfigOverride(
            mapOf("android" to "true"),
        )
        endWithNewline()
    }
}

detekt {
    buildUponDefaultConfig = true
    baseline = rootProject.file("../config/detekt/detekt-baseline.xml")
    config.setFrom(
        rootProject.file("../config/detekt/detekt.yml"),
        rootProject.file("../config/detekt/detekt-compose.yml"),
    )
    source.setFrom(files("src"))
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = libs.versions.jvmTarget.get()
    reports {
        listOf(xml, html, txt, md, sarif).forEach {
            it.required.set(true)
        }
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.detekt.api)
    compileOnly(libs.detekt.gradle)
    compileOnly(libs.spotless.gradlePlugin)
    compileOnly(libs.compose.guardPlugin)
    compileOnly(libs.dependencyGuard.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
    compileOnly(libs.android.junit5.gradlePlugin)
    compileOnly(libs.screenshot.gradlePlugin)
    compileOnly(libs.baselineprofile.gradlePlugin)

    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.detekt.rules)

    testImplementation(libs.detekt.api)
    testImplementation(libs.detekt.test)
    testImplementation(libs.junit5.api)
    testRuntimeOnly(libs.junit5.jupiterEngine)
    testRuntimeOnly(libs.junit5.platformLauncher)
}

tasks.test {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "app.android.application.compose"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "app.android.application"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "app.android.library.compose"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "app.android.library"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "app.android.feature"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidFeatureConventionPlugin"
        }
        register("hilt") {
            id = "app.hilt"
            implementationClass = "br.com.paroquiasantacabrini.app.buildlogic.convention.HiltConventionPlugin"
        }
        register("spotless") {
            id = "app.spotless"
            implementationClass = "br.com.paroquiasantacabrini.app.buildlogic.convention.SpotlessConventionPlugin"
        }
        register("androidLint") {
            id = "app.android.lint"
            implementationClass = "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidLintConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "app.android.application.jacoco"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidApplicationJacocoConventionPlugin"
        }
        register("androidApplicationBaselineProfile") {
            id = "app.android.application.baselineprofile"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidApplicationBaselineProfileConventionPlugin"
        }
        register("androidBenchmark") {
            id = "app.android.benchmark"
            implementationClass = "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidBenchmarkConventionPlugin"
        }
        register("androidComposeScreenshot") {
            id = "app.android.compose.screenshot"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidComposeScreenshotConventionPlugin"
        }
        register("androidJUnit5") {
            id = "app.android.junit5"
            implementationClass = "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidJUnit5ConventionPlugin"
        }
        register("androidTest") {
            id = "app.android.test"
            implementationClass = "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidTestConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "app.android.library.jacoco"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidLibraryJacocoConventionPlugin"
        }
        register("jvmLibrary") {
            id = "app.jvm.library"
            implementationClass = "br.com.paroquiasantacabrini.app.buildlogic.convention.JvmLibraryConventionPlugin"
        }
        register("kotlinExplicitVisibility") {
            id = "app.kotlin.explicit-visibility"
            implementationClass =
                "br.com.paroquiasantacabrini.app.buildlogic.convention.ExplicitVisibilityConventionPlugin"
        }
        register("room") {
            id = "app.android.room"
            implementationClass = "br.com.paroquiasantacabrini.app.buildlogic.convention.AndroidRoomConventionPlugin"
        }
    }
}
