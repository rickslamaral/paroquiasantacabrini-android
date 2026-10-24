package br.com.paroquiasantacabrini.app.buildlogic.convention

import br.com.paroquiasantacabrini.app.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("app.android.library.compose")
                apply("app.hilt")
            }
            dependencies {
                add("implementation", project(":core:designsystem"))
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-lifecycle-runtime-testing").get(),
                )
            }
        }
    }
}
