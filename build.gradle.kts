plugins {
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.lib) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.jvm) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt)
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        source = objects.fileCollection().from(
            io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_SRC_DIR_JAVA,
            io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_TEST_SRC_DIR_JAVA,
            io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_SRC_DIR_KOTLIN,
            io.gitlab.arturbosch.detekt.extensions.DetektExtension.DEFAULT_TEST_SRC_DIR_KOTLIN,
        )
        buildUponDefaultConfig = false
        config = files("../config/detekt.yml")
    }
    //disable detekt on regular build
    tasks.getByPath("detekt").onlyIf { gradle.startParameter.taskNames.any { it.contains("detekt") } }
}

dependencies {
    detekt(libs.detekt.cli)
    detektPlugins(libs.detekt.formatting)
}