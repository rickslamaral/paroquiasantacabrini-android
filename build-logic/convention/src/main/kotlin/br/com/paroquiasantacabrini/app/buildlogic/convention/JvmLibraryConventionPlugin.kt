package br.com.paroquiasantacabrini.app.buildlogic.convention

import br.com.paroquiasantacabrini.app.buildlogic.configureDetekt
import br.com.paroquiasantacabrini.app.buildlogic.configureJvmTesting
import br.com.paroquiasantacabrini.app.buildlogic.configureKotlinJvm
import br.com.paroquiasantacabrini.app.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType

class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(plugin = "org.jetbrains.kotlin.jvm")
                apply(plugin = "app.android.lint")
                apply(plugin = "app.spotless")
                apply(plugin = "io.gitlab.arturbosch.detekt")
            }
            configureKotlinJvm()
            configureJvmTesting()
            configureDetekt()
            tasks {
                withType<Test> {
                    useJUnitPlatform()
                }
            }
            dependencies {
                "testImplementation"(libs.findLibrary("junit5-api").get())
                "testImplementation"(libs.findLibrary("junit5-params").get())
                "testImplementation"(libs.findLibrary("assertj-core").get())
                "testRuntimeOnly"(libs.findLibrary("junit5-jupiterEngine").get())
                "testRuntimeOnly"(libs.findLibrary("junit5-vintageEngine").get())
                "testImplementation"(libs.findLibrary("kotlin.test").get())
            }
        }
    }
}
