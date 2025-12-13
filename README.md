# Xtream API

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org)
[![Version](https://img.shields.io/badge/version-0.1.6-green.svg)](https://github.com/saifullah-nurani/XtreamApi)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**Xtream API** is a modern, type-safe Kotlin Multiplatform library designed to seamlessly interact with Xtream Codes API. It enables effortless retrieval of movies, TV series, live TV streams, and more across Android, iOS, and JVM platforms.

## ✨ Features

- 🎯 **Kotlin Multiplatform** - Write once, run on Android, iOS, and JVM
- 🔒 **Type-Safe** - Fully typed models with Kotlin serialization
- 🚀 **Coroutines Support** - Built with Kotlin Coroutines for asynchronous operations
- 📦 **Zero Dependencies** - Only uses Ktor and Kotlinx Serialization
- 🎨 **DSL-Based Configuration** - Clean and intuitive DSL for setup
- 🔄 **Automatic Retry** - Configurable retry logic for network requests
- ⚡ **Caching Support** - Built-in HTTP caching capabilities
- 🛡️ **Error Handling** - Comprehensive error handling with descriptive messages
- 📱 **Stream URL Builder** - Helper utilities for building streaming URLs

## 📋 Requirements

- **Kotlin**: 2.1.0 or higher
- **Android**: Min SDK 21, Target SDK 36
- **Gradle**: 8.0 or higher
- **JDK**: 21 or higher (for JVM target)

## 📦 Installation

### Gradle (Kotlin DSL)

Add the dependency to your `build.gradle.kts`:

#### For Android Projects

```kotlin
dependencies {
    implementation("io.github.saifullah-nurani:xtream-api:0.1.6")
}
```

#### For Kotlin Multiplatform Projects

```kotlin
kotlin {
    android()
    ios()
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.saifullah-nurani:xtream-api:0.1.6")
        }
    }
}
```

### Maven

```xml
<dependency>
    <groupId>io.github.saifullah-nurani</groupId>
    <artifactId>xtream-api</artifactId>
    <version>0.1.6</version>
</dependency>
```

## 🚀 Quick Start

### Basic Setup

```kotlin
import io.github.saifullah.xtream.Xtream

// Initialize the client
val xtream = Xtream {
    auth {
        protocol = "https"
        host = "your-server.com"
        port = 8080
        username = "your_username"
        password = "your_password"
    }
}
```

### Authentication

```kotlin
// Authenticate and get user/server info
val authResult = xtream.auth()
println("Server: ${authResult.serverInfo.url}")
println("User: ${authResult.userInfo.username}")
println("Status: ${authResult.userInfo.status}")
```

## 📖 Usage Examples

### Android

#### 1. Initialize in Application Class or ViewModel

```kotlin
import android.app.Application
import io.github.saifullah.xtream.Xtream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    lateinit var xtream: Xtream
        private set
    
    override fun onCreate() {
        super.onCreate()
        
        xtream = Xtream {
            auth {
                protocol = "https"
                host = "your-server.com"
                port = 8080
                username = "your_username"
                password = "your_password"
            }
            
            // Optional: Configure timeouts
            socketTimeoutMillis = 30000
            connectTimeoutMillis = 30000
            requestTimeoutMillis = 30000
            
            // Optional: Enable caching
            useCache = true
            
            // Optional: Configure retry
            maxRetries = 3
            retryDelayMillis = 1000
        }
    }
}
```

#### 2. Use in ViewModel

```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.saifullah.xtream.model.XtreamMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(private val xtream: Xtream) : ViewModel() {
    
    private val _movies = MutableStateFlow<List<XtreamMovie>>(emptyList())
    val movies: StateFlow<List<XtreamMovie>> = _movies
    
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    fun loadMovies() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            
            try {
                val moviesList = xtream.movie.getMovies()
                _movies.value = moviesList
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _loading.value = false
            }
        }
    }
    
    fun loadMovieDetail(streamId: Long) {
        viewModelScope.launch {
            try {
                val movieDetail = xtream.movie.getMovieDetail(streamId)
                // Handle movie detail
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
```

#### 3. Use in Activity/Fragment

```kotlin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory((application as MyApplication).xtream)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Observe movies
        lifecycleScope.launch {
            viewModel.movies.collect { movies ->
                // Update UI with movies
                updateMoviesList(movies)
            }
        }
        
        // Observe loading state
        lifecycleScope.launch {
            viewModel.loading.collect { isLoading ->
                // Show/hide loading indicator
                showLoading(isLoading)
            }
        }
        
        // Observe errors
        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    showError(it)
                }
            }
        }
        
        // Load movies
        viewModel.loadMovies()
    }
    
    private fun updateMoviesList(movies: List<XtreamMovie>) {
        // Update RecyclerView or similar
    }
    
    private fun showLoading(isLoading: Boolean) {
        // Show/hide progress bar
    }
    
    private fun showError(message: String) {
        // Show error message
    }
}
```

### Kotlin Multiplatform (KMM)

#### Shared Module Setup

```kotlin
// commonMain/src/commonMain/kotlin/XtreamClient.kt
import io.github.saifullah.xtream.Xtream

expect fun createXtreamClient(): Xtream

// androidMain/src/androidMain/kotlin/XtreamClient.android.kt
actual fun createXtreamClient(): Xtream {
    return Xtream {
        auth {
            protocol = "https"
            host = "your-server.com"
            port = 8080
            username = "your_username"
            password = "your_password"
        }
    }
}

// iosMain/src/iosMain/kotlin/XtreamClient.ios.kt
actual fun createXtreamClient(): Xtream {
    return Xtream {
        auth {
            protocol = "https"
            host = "your-server.com"
            port = 8080
            username = "your_username"
            password = "your_password"
        }
    }
}
```

#### Use in Shared ViewModel

```kotlin
// commonMain/src/commonMain/kotlin/MoviesViewModel.kt
import io.github.saifullah.xtream.Xtream
import io.github.saifullah.xtream.model.XtreamMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoviesViewModel(private val xtream: Xtream) {
    
    private val _movies = MutableStateFlow<List<XtreamMovie>>(emptyList())
    val movies: StateFlow<List<XtreamMovie>> = _movies.asStateFlow()
    
    suspend fun loadMovies() {
        try {
            _movies.value = xtream.movie.getMovies()
        } catch (e: Exception) {
            // Handle error
            println("Error loading movies: ${e.message}")
        }
    }
}
```

## 📚 API Reference

### Authentication

```kotlin
// Authenticate and get user/server information
val authResult = xtream.auth()

// Access server info
val serverUrl = authResult.serverInfo.url
val serverProtocol = authResult.serverInfo.serverProtocol
val timezone = authResult.serverInfo.timezone

// Access user info
val username = authResult.userInfo.username
val status = authResult.userInfo.status
val expDate = authResult.userInfo.expDate
```

### Movies

```kotlin
// Get all movies
val movies = xtream.movie.getMovies()

// Get movie detail
val movieDetail = xtream.movie.getMovieDetail(streamId = 123456)

// Get movie categories
val categories = xtream.movie.getMovieCategories()

// Get movies by category
val moviesInCategory = xtream.movie.getMoviesByCategory(categoryId = 10)
```

### TV Series

```kotlin
// Get all TV series
val tvSeries = xtream.tvSeries.getTvSeries()

// Get TV series detail
val seriesDetail = xtream.tvSeries.getTvSeriesDetail(seriesId = 789012)

// Get TV series detail (alternative format)
val seriesDetail2 = xtream.tvSeries.getTvSeriesDetail2(seriesId = 789012)

// Get TV series categories
val categories = xtream.tvSeries.getTvSeriesCategories()

// Get TV series by category
val seriesInCategory = xtream.tvSeries.getTvSeriesByCategory(categoryId = 5)
```

### Custom Requests

```kotlin
// Perform custom GET request
val response: MyCustomModel = xtream.custom.get {
    url("https://api.example.com/custom-endpoint")
}

// Get raw HTTP response
val httpResponse = xtream.custom.getHttpResponse {
    url("https://api.example.com/endpoint")
}
val statusCode = httpResponse.status.value
val body = httpResponse.bodyAsText()
```

### Stream URL Builder

```kotlin
import io.github.saifullah.xtream.url.StreamUrlBuilder
import io.github.saifullah.xtream.model.XtreamAuthCredentials

// Build movie stream URL
val movieUrl = StreamUrlBuilder.buildMovieUrl(
    protocol = "https",
    host = "server.com",
    username = "user",
    password = "pass",
    streamId = 123456,
    extension = "mp4"
)

// Build episode stream URL
val episodeUrl = StreamUrlBuilder.buildEpisodeUrl(
    protocol = "https",
    host = "server.com",
    username = "user",
    password = "pass",
    episodeId = 789012,
    extension = "mkv"
)

// Using credentials object
val credentials = XtreamAuthCredentials(
    protocol = "https",
    host = "server.com",
    port = 8080,
    username = "user",
    password = "pass"
)

val movieUrl = StreamUrlBuilder.buildMovieUrl(
    authCredentials = credentials,
    streamId = 123456,
    extension = "mp4"
)
```

## ⚙️ Configuration

### Advanced Configuration

```kotlin
val xtream = Xtream {
    // Authentication
    auth {
        protocol = "https"
        host = "your-server.com"
        port = 8080
        username = "your_username"
        password = "your_password"
    }
    
    // Network timeouts (in milliseconds)
    socketTimeoutMillis = 60000      // Default: 60000
    connectTimeoutMillis = 60000     // Default: 60000
    requestTimeoutMillis = 60000     // Default: 60000
    useTimeout = true                // Default: true
    
    // HTTP settings
    followRedirects = true           // Default: true
    expectSuccess = false            // Default: false
    
    // Caching
    useCache = true                  // Default: true
    
    // Retry configuration
    maxRetries = 3                   // Default: 0 (no retries)
    retryDelayMillis = 1000          // Default: 1000
    
    // Custom HTTP client (optional)
    // httpClient(OkHttp) {
    //     // Custom configuration
    // }
}
```

### Dynamic Credentials

```kotlin
import io.github.saifullah.xtream.model.XtreamAuthCredentials

// Use different credentials for a specific request
val customCredentials = XtreamAuthCredentials(
    protocol = "https",
    host = "other-server.com",
    port = 8080,
    username = "other_user",
    password = "other_pass"
)

val movies = xtream.movie.getMovies(credentials = customCredentials)
```

## 🛡️ Error Handling

All API methods throw exceptions that should be handled:

```kotlin
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.serialization.SerializationException

try {
    val movies = xtream.movie.getMovies()
} catch (e: ClientRequestException) {
    // HTTP 4xx errors
    println("Client error: ${e.response.status}")
} catch (e: ServerResponseException) {
    // HTTP 5xx errors
    println("Server error: ${e.response.status}")
} catch (e: SerializationException) {
    // JSON parsing errors
    println("Serialization error: ${e.message}")
} catch (e: Exception) {
    // Other errors
    println("Error: ${e.message}")
}
```

## 🔍 Data Models

### XtreamMovie

```kotlin
data class XtreamMovie(
    val streamId: Long,
    val title: String,
    val plot: String?,
    val rating: Float?,
    val releaseDate: String?,
    val streamIcon: String?,
    val genres: List<String>?,
    val casts: List<String>?,
    val crews: List<String>?,
    // ... more fields
)
```

### XtreamMovieDetail

```kotlin
data class XtreamMovieDetail(
    val info: Info,
    val movieData: MovieData
) {
    data class Info(
        val name: String,
        val description: String?,
        val rating: Float?,
        val duration: String?,
        val genres: List<String>?,
        // ... more fields
    )
    
    data class MovieData(
        val streamId: Long,
        val title: String,
        // ... more fields
    )
}
```

### XtreamTvSeries

```kotlin
data class XtreamTvSeries(
    val seriesId: Long,
    val title: String,
    val plot: String?,
    val cover: String?,
    val rating: Float?,
    // ... more fields
)
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Saifullah Nurani**

- GitHub: [@saifullah-nurani](https://github.com/saifullah-nurani)
- Email: donaldperryman04@gmail.com

## 🙏 Acknowledgments

- Built with [Ktor](https://ktor.io/)
- Powered by [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- Uses [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)

## 📞 Support

If you encounter any issues or have questions, please file an issue on the [GitHub repository](https://github.com/saifullah-nurani/XtreamApi/issues).

---

**Made with ❤️ using Kotlin Multiplatform**

