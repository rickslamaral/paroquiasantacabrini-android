package br.com.paroquiasantacabrini.app.buildlogic.convention

import br.com.paroquiasantacabrini.app.buildlogic.configureDetekt
import br.com.paroquiasantacabrini.app.buildlogic.configureGradleManagedDevices
import br.com.paroquiasantacabrini.app.buildlogic.configureKotlinAndroid
import br.com.paroquiasantacabrini.app.buildlogic.libs
import com.android.build.api.dsl.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.test")
                apply("app.android.lint")
                apply("app.spotless")
                apply("io.gitlab.arturbosch.detekt")
            }

            configureDetekt()

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                configureGradleManagedDevices(this)
                defaultConfig.targetSdk =
                    libs.findVersion("targetSdk").get().requiredVersion.toInt()
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
        }
    }
}
