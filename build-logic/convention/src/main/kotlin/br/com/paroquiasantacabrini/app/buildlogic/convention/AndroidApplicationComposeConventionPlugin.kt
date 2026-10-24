package br.com.paroquiasantacabrini.app.buildlogic.convention

import br.com.paroquiasantacabrini.app.buildlogic.configureAndroidCompose
import br.com.paroquiasantacabrini.app.buildlogic.configureComposeGuard
import com.android.build.api.dsl.ApplicationExtension
import com.joetr.compose.guard.ComposeCompilerCheckExtension
import com.joetr.compose.guard.ComposeCompilerExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "app.android.application")
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            apply(plugin = "com.joetr.compose.guard")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
            configureComposeGuard(
                extensions.getByType<ComposeCompilerExtension>(),
                extensions.getByType<ComposeCompilerCheckExtension>(),
            )
        }
    }
}
