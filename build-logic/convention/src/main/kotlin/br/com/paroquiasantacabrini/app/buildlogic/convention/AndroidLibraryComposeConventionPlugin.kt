package br.com.paroquiasantacabrini.app.buildlogic.convention

import br.com.paroquiasantacabrini.app.buildlogic.configureAndroidCompose
import br.com.paroquiasantacabrini.app.buildlogic.configureComposeGuard
import com.android.build.api.dsl.LibraryExtension
import com.joetr.compose.guard.ComposeCompilerCheckExtension
import com.joetr.compose.guard.ComposeCompilerExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "app.android.library")
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            apply(plugin = "com.joetr.compose.guard")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)
            configureComposeGuard(
                extensions.getByType<ComposeCompilerExtension>(),
                extensions.getByType<ComposeCompilerCheckExtension>(),
            )
        }
    }
}
