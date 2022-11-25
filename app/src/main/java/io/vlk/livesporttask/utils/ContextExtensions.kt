package io.vlk.livesporttask.utils

import android.content.Context
import io.vlk.livesporttask.utils.model.LocaleConfiguration
import java.io.FileNotFoundException
import java.util.Properties

fun Context.readLocaleConfig(): LocaleConfiguration {
    val properties = runCatching {
        assets.open("config.properties").buffered().use { stream ->
            Properties().apply { load(stream) }.let {
                LocaleConfiguration(
                    baseUrl = it.getProperty("baseUrl").orEmpty(),
                    imageUrl = it.getProperty("imageUrl").orEmpty(),
                )
            }
        }
    }.getOrNull()
    return properties ?: throw FileNotFoundException("Please set baseUrl and imageUrl in config.properties")
}