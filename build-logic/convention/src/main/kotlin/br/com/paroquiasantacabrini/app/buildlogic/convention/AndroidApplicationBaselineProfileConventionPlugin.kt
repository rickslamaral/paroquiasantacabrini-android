package br.com.paroquiasantacabrini.app.buildlogic.convention

import br.com.paroquiasantacabrini.app.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationBaselineProfileConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "androidx.baselineprofile")

            dependencies {
                "implementation"(libs.findLibrary("profileinstaller").get())
            }
        }
    }
}
