package kz.dragau.larek

import kz.dragau.larek.BuildConfig

object Constants
{
    const val IMAGE_DIRECTORY = "/o2o"
    const val GALLERY = 1
    const val CAMERA = 2
    const val selectFromCamera = "Сделать фото"
    const val selectFromGallery = "Выбрать из галереи"
    const val seletImageTitle = "Добавить фото"
    const val zoomFarWarning = "Слишком далеко"
    const val extraRevealCenterPadding = 40
    const val apiEndpoint = BuildConfig.apiEndpoint
    const val wsEndpoint = BuildConfig.wsEndpoint
    const val version = "1.0.0"
    const val lightTheme = "light"
    const val darkTheme = "dark"
    const val themePrefsKey = "current_theme"
    const val jwtPrefsKey = "current_token"
    const val userIdPrefsKey = "user_id"
    const val connectTimeout: Long = 25
    const val writeTimeout: Long = 40
    const val readTimeout: Long = 40
    const val progressDelay: Long = 10
    const val smsVerificationDelay: Long = 60
    const val verificationCode = "verificationCode"
}