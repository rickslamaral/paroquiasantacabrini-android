package br.com.paroquiasantacabrini.app.buildlogic.convention

import br.com.paroquiasantacabrini.app.buildlogic.configureSpotlessForAndroid
import br.com.paroquiasantacabrini.app.buildlogic.configureSpotlessForJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

class SpotlessConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("com.android.application") {
                configureSpotlessForAndroid()
            }
            pluginManager.withPlugin("com.android.library") {
                configureSpotlessForAndroid()
            }
            pluginManager.withPlugin("com.android.test") {
                configureSpotlessForAndroid()
            }
            pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
                configureSpotlessForJvm()
            }
        }
    }
}
