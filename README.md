# TribalApp — Chuck Norris Categories

Pequeña app de demostración para **Android** que consume el endpoint público de Chuck Norris y lista las **categorías** usando **Clean Architecture**, **MVVM**, **Hilt (DI)**, **Coroutines**, **Retrofit/Moshi**, y **Jetpack Compose**. El proyecto está configurado con **Kotlin DSL**.

> API usada: `GET https://api.chucknorris.io/jokes/categories` → `List<String>`

<img width="312" height="800" alt="Screenshot_20250814_212718" src="https://github.com/user-attachments/assets/d655666c-4750-4fd6-b423-8813d7ce19ad" />


<img width="312" height="800" alt="Screenshot_20250814_221113" src="https://github.com/user-attachments/assets/11a73720-79b3-4621-8bbc-ab3be882f2bd" />



---

## 🎯 Objetivo de la prueba

* Patrón **MVVM**.
* Inyección de dependencias con **Dagger Hilt**.
* **Corrutinas**.
* Estructura basada en **Clean Architecture**.
* **Consumo de API** con Retrofit.
* (**Opcional**) **Kotlin Flow** para el manejo de estado.
* (**Opcional**) **Jetpack Compose** para UI moderna.

---

## 🚀 Stack

* **Kotlin** + **Coroutines**
* **Jetpack Compose** (Material 3)
* **Dagger Hilt** (DI)
* **Retrofit** + **Moshi** + **OkHttp**
* **Kotlin DSL** (Gradle)

---

## 🏗️ Arquitectura

**Clean Architecture + MVVM**

* **domain**: contratos (repositorios), casos de uso.
* **data**: fuentes de datos (remote), implementación de repositorios.
* **presentation**: ViewModel + UI (Compose).
* **di**: módulos de Hilt.

Diagrama simplificado:

```
Presentation (Compose + ViewModel)
        ↓
     UseCase (domain)
        ↓
   Repository (domain → data impl)
        ↓
   Remote API (Retrofit)
```

---

## 📂 Estructura de paquetes (resumen)

```
com.capullo.tribalapp
├─ App.kt                               # @HiltAndroidApp
├─ core/
│  ├─ data/
│  │  ├─ remote/
│  │  │  └─ ChuckNorrisRequestApiInt.kt # Retrofit interface
│  │  └─ repo/
│  │     └─ ChuckRepoImpl.kt            # Implementación del repo
│  ├─ di/
│  │  ├─ AppModule.kt                   # Retrofit/OkHttp/API/Repo @Singleton
│  │  └─ UseCaseModule.kt               # @Binds GetAllChuckUseCase
│  ├─ domain/
│  │  ├─ repo/
│  │  │  └─ ChuckRepo.kt
│  │  └─ usecase/
│  │     ├─ GetAllChuckUseCase.kt
│  │     └─ GetAllChuckUseCaseImpl.kt
│  └─ presentation/
│     ├─ categories/
│     │  ├─ CategoriesViewModel.kt      # MVVM + coroutines/flow
│     │  └─ CategoriesScreen.kt         # Compose UI
│     └─ MainActivity.kt                # @AndroidEntryPoint
```

---

## 🔧 Configuración / Requisitos

* **JDK 17**
* **Android Gradle Plugin 8.x**
* **compileSdk = 36**, **targetSdk = 36**, **minSdk = 35** (ajustable)
* Conexión a Internet (permiso `INTERNET` en el manifest)

### Gradle (Kotlin DSL)

**`app/build.gradle.kts` (fragmento de plugins y deps)**

```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    alias(libs.plugins.hilt.android) // ¡Aplicado en el módulo app!
}

dependencies {
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Retrofit / Moshi / OkHttp
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)

    // Coroutines + Lifecycle
    implementation(libs.coroutines.android)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // Hilt + Navigation Compose
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
}
```

**`libs.versions.toml` (alias clave, ejemplo)**

```toml
[versions]
hilt = "2.52"
retrofit = "2.11.0"
okhttp = "4.12.0"
moshi = "1.15.1"
coroutines = "1.9.0"
lifecycle = "2.8.4"
compose-bom = "2024.12.01"
activity-compose = "1.9.2"
hiltNavigationCompose = "1.2.0"
navigation = "2.8.9"

[libraries]
# Hilt
hilt-android   = { module = "com.google.dagger:hilt-android", version.ref = "hilt" }
hilt-compiler  = { module = "com.google.dagger:hilt-compiler", version.ref = "hilt" }

# Retrofit / Moshi / OkHttp
retrofit-core   = { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
retrofit-moshi  = { module = "com.squareup.retrofit2:converter-moshi", version.ref = "retrofit" }
okhttp-logging  = { module = "com.squareup.okhttp3:logging-interceptor", version.ref = "okhttp" }
moshi-kotlin    = { module = "com.squareup.moshi:moshi-kotlin", version.ref = "moshi" }

# Coroutines + Lifecycle
coroutines-android        = { module = "org.jetbrains.kotlinx:kotlinx-coroutines-android", version.ref = "coroutines" }
lifecycle-runtime-ktx     = { module = "androidx.lifecycle:lifecycle-runtime-ktx", version.ref = "lifecycle" }
lifecycle-viewmodel-ktx   = { module = "androidx.lifecycle:lifecycle-viewmodel-ktx", version.ref = "lifecycle" }
lifecycle-runtime-compose = { module = "androidx.lifecycle:lifecycle-runtime-compose", version.ref = "lifecycle" }

# Compose
androidx-compose-bom                = { module = "androidx.compose:compose-bom", version.ref = "compose-bom" }
androidx-ui                         = { module = "androidx.compose.ui:ui" }
androidx-ui-tooling                 = { module = "androidx.compose.ui:ui-tooling" }
androidx-ui-tooling-preview         = { module = "androidx.compose.ui:ui-tooling-preview" }
androidx-compose-material3          = { module = "androidx.compose.material3:material3" }
androidx-activity-compose           = { module = "androidx.activity:activity-compose", version.ref = "activity-compose" }
androidx-hilt-navigation-compose    = { module = "androidx.hilt:hilt-navigation-compose", version.ref = "hiltNavigationCompose" }
androidx-navigation-compose         = { module = "androidx.navigation:navigation-compose", version.ref = "navigation" }
```

### Hilt

* `@HiltAndroidApp` en `App.kt` y `android:name="com.capullo.tribalapp.App"` en el **manifest**.
* `@AndroidEntryPoint` en `MainActivity`.
* **Importante**: usar `javax.inject.*` (no `jakarta.inject.*`).

---

## ▶️ Cómo ejecutar

1. Clonar el repo y abrir en **Android Studio**.
2. **Sync Gradle**.
3. Ejecutar en un emulador/dispositivo (API 35+).

**CLI**

```bash
./gradlew clean :app:assembleDebug
```

---

## 🔌 API

* **Endpoint**: `GET /jokes/categories`
* **Base URL**: `https://api.chucknorris.io/` (config en `AppModule.BASE_URL`)

Interfaz Retrofit:

```kotlin
interface ChuckNorrisRequestApiInt {
    @GET("jokes/categories")
    suspend fun getCategories(): List<String>
}
```

---

## 🖼️ UI

La lista se muestra con **Cards** (Material 3), badge con la inicial de la categoría y acción de **Refresh** desde el `TopAppBar`.

---

## 🧰 Troubleshooting

* **Hilt plugin**: aplicar el plugin en el **módulo app** (no `apply false`).
* **Application @HiltAndroidApp**: declarar `android:name` en el manifest final (ver `merged_manifest`).
* **`jakarta.inject` vs `javax.inject`**: usar siempre **`javax.inject`**.
* **`javapoet NoSuchMethodError`**: forzar `com.squareup:javapoet:1.14.0` si algún processor trae versión antigua.
* **Repos de plugins**: `google()`, `mavenCentral()`, `gradlePluginPortal()` en `settings.gradle.kts`.

---

## 📄 Licencia

MIT — usar libremente para fines de evaluación y aprendizaje.

---

## ✍️ Autor

Hecho con <3 por Vanessa (Vane) Moreno para prueba técnica de Android.
