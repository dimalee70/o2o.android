package kz.dragau.larek

import kz.dragau.larek.BuildConfig

object Constants
{
    const val apiEndpoint = BuildConfig.apiEndpoint
    const val version = "1.0.0"
    const val lightTheme = "light"
    const val darkTheme = "dark"
    const val themePrefsKey = "current_theme"
    const val jwtPrefsKey = "current_token"
    const val userIdPrefsKey = "user_id"
    const val connectTimeout: Long = 25
    const val writeTimeout: Long = 25
    const val readTimeout: Long = 25
    const val progressDelay: Long = 10
}