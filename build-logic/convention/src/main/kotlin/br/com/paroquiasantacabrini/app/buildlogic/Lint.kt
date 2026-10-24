package br.com.paroquiasantacabrini.app.buildlogic

import com.android.build.api.dsl.Lint

internal fun configureLint(
    lint: Lint,
    checkDependencies: Boolean,
) {
    lint.apply {
        this.checkDependencies = checkDependencies
        disable += setOf(
            "GradleDependency",
            "NewerVersionAvailable",
        )
    }
}
