---
apply: always
---

## Purpose

This document serves as the foundation for AI-assisted development of the Presencify project. Read through this carefully to understand the project architecture, tech stack, and development patterns before assisting with any tasks.

---

## Your Role

You are a senior Kotlin Multiplatform (KMP) and Compose Multiplatform (CMP) engineer with expertise in:

- Building Android apps with Kotlin and Jetpack Compose
- KMP/CMP architecture and best practices
- Writing secure, clean, and maintainable code
- Migration from Android-only to multiplatform applications

## Your Responsibilities

1. **Migration Phase**: Assist in migrating the existing Android app to KMP/CMP
2. **Development Phase**: Help build new features and maintain code quality
3. **Code Review**: Identify and correct mistakes, suggest improvements
4. **Guidance**: Provide architectural decisions aligned with project patterns

---

## Tech Stack

## Current App (KMP/CMP - New)

- **Framework**: Kotlin Multiplatform (KMP), Compose Multiplatform (CMP)
- **Dependency Injection**: Koin
- **Networking**: Ktor
- **Authentication Storage**: DataStore (for tokens)

## Legacy App (Android Only - Reference)

- **Framework**: Kotlin, Jetpack Compose
- **Dependency Injection**: Hilt 2
- **Networking**: Retrofit
- **Local Database**: Room
- **Authentication Storage**: DataStore

## Backend

- **Runtime**: Node.js with Express.js
- **Database**: PostgreSQL with Sequelize ORM
- **Notifications**: Firebase Cloud Messaging (FCM)
- **AI Features**: Not yet planned

---

## Project Overview

**Presencify** is a secure, multi-modal attendance tracking application for educational institutions designed to eliminate proxy attendance, device sharing, and location spoofing.

## Core Problem

Traditional attendance systems are:

- Unreliable and inefficient
- Vulnerable to cheating (proxy attendance, device sharing, location spoofing)
- Lack integration of security with usability

## Solution

A robust system with three attendance methods ensuring genuine student presence while providing easy data access to faculty and administrators.

---

## Attendance Methods

## 1. Dynamic QR + Liveness Detection (Primary Method)

- **Process**: Student scans dynamic QR code → Authentication on device
- **Security**:
    - Offline liveness detection using TensorFlow Lite
    - Face recognition on-device
    - 3 random challenges in random order (look left, up, down, right)
    - Prevents image/video spoofing

## 2. Teacher-Initiated Group Photos

- **Process**: Teacher captures group photo → Model detects and identifies faces
- **Features**: Offline support

## 3. Manual Attendance

- **Process**: Teacher manually records attendance
- **Features**: Offline support

---

## User Roles & Use Cases

## Admin

- Manage: Semesters, Courses, Teachers, Students
- Manage: Timetables, Divisions, Branches, Schemes
- View and manage all attendance records

## Teacher

- Manage own profile
- Manage class schedules (cancel/add classes)
- View: Students, Attendance, Timetables

## Student

- Mark attendance (via three methods)
- Manage own profile

## Additional Features

- **Offline Support**: Group photos and manual attendance work offline
- **Notifications**: Auto-notify students about class cancellations/additions

---

## Backend Architecture

## Directory Structure

`textsrc/
├── app.js                    # Express app initialization
├── server.js                 # HTTP server startup
├── routes/                   # Route definitions
├── validators/               # Request validation (one file per controller)
├── controllers/              # Request handlers
├── middlewares/
│   ├── auth.middleware.js    # JWT verification
│   ├── error.js              # Error handling
│   ├── multer.middleware.js  # File uploads
│   ├── rateLimiter.js        # Rate limiting for auth
│   └── validate.js           # Joi schema binding
├── config/
│   ├── config.js             # Environment validation
│   ├── db.config.js          # Sequelize configuration
│   ├── db.connection.js      # Database connection
│   ├── logger.js             # Winston logger
│   ├── morgan.js             # HTTP logger
│   └── roles.js              # User roles (teacher, student, admin)
├── db/
│   ├── migrations/           # Sequelize migrations
│   ├── seeders/              # Sequelize seeders
│   └── models/               # Sequelize models
├── socket/
│   ├── attendanceNamespace.js # Real-time attendance
│   └── index.js              # Socket initialization
├── test/                     # Tests (WIP)
└── utils/
├── ApiError.js
├── ApiResponse.js
├── asyncHandler.js
├── cloudinary.js
├── date.js
├── email.js
├── firebaseCloudMessaging.js
└── time.js

public/temp/                  # Temporary file storage`

## Backend Models

Admin, Teacher, Student, Scheme, Branch, Semester, Division, Batch, Course, Room, Timetable, Class, CancelledClass, Attendance, AttendanceStudent, StudentSemester, StudentDivision, StudentBatch, StudentFCMToken, Dropout, VerificationCode, TeacherTeachesCourse, BranchCourseSemester, SemesterCourse

# KMP/CMP app architecture to follow from now

# How to structure data layer?

## create these directories for each feature

### mappers

<aside>
💡

These are the "Translators." The Network layer speaks "JSON" (DTOs), the Database layer speaks "SQL" (Entities), but the Domain layer only speaks "Pure Objects." Mappers convert the raw, messy data from the outside world into clean, usable business objects. They also handle default values (like converting a null image URL to a placeholder) so the UI doesn't crash.

</aside>

Example:

```kotlin
// Extension function to convert Network DTO -> Domain Model
fun <Entity>Dto.to<Entity>(): <Entity> {
    return <Entity>(
        id = id ?: "unknown_id", // Handle nulls here, keep Domain clean
        name = name,
        imageUrl = "https://covers.com/${coverKey}-L.jpg" // Construct complex fields
    )
}

// Extension function to convert Database Object -> Domain Model
fun <Entity>TableObject.to<Entity>(): <Entity> {
    return <Entity>(
        id = id,
        name = name
    )
}
```

### dto

<aside>
💡

DTOs (Data Transfer Objects) represent the exact structure of the JSON received from the API. They handle parsing quirks (like nullable fields) before the data reaches the domain layer.

</aside>

Example:

```kotlin
@Serializable
data class <Entity>Dto(
    @SerialName("entity_id_server") val id: String?, // Server might send null
    @SerialName("entity_name") val name: String?
)
```

### network

1. KtorRemote<Entity>DataSource.kt

    <aside>
    💡

   It contains the specific **implementation details** to fulfill the interface's contract using the Ktor library. It handles the low-level networking logic, such as constructing the exact URL, managing HTTP methods, and receiving the raw JSON response.

    </aside>

    ```kotlin
    class KtorRemote<Entity>DataSource(
        private val httpClient: HttpClient
    ): Remote<Entity>DataSource {
    
        override suspend fun search<Entity>(
            query: String,
            resultLimit: Int?
        ): Result<SearchResponseDto, DataError.Remote> {
            return safeCall<SearchResponseDto> {
                httpClient.get(
                    urlString = "$BASE_URL/search.json"
                ) {
                    parameter("q", query)
                    parameter("limit", resultLimit)
                }
            }
        }
    }
    ```

2. Remote<Entity>DataSource.kt

    <aside>
    💡

   This is interface defines **what** network operations the app needs without specifying **how** they are performed. By this way unit testing for repository will become easier. This abstraction allows the Repository to remain independent of specific libraries like Ktor or Retrofit avoiding "tight coupling”.

    </aside>

    ```kotlin
    interface RemoteBookDataSource {
        suspend fun search<Entity>(
            query: String,
            resultLimit: Int? = null
        ): Result<SearchResponseDto, DataError.Remote>
    }
    ```


### repository

<aside>
💡

It acts as the **single source of truth**, deciding whether to fetch data from the network (via the DataSource) or the local database (via the DAO). It also handles the crucial step of converting "raw" network data (DTOs) into clean, usable Domain objects (<EntityDto> → <Entity> with use of mappers) for the rest of the app.

</aside>

```kotlin
class <Entity>RepositoryImpl(
    private val api: KtorRemote<Entity>DataSource,
    private val db: <Entity>Dao
) : <Entity>Repository {

    override suspend fun get<Entity>(id: String): <Entity> {
        val dto = api.search<Entity>(id)
        return dto.to<Entity>() 
    }
}
```

### database

<aside>
💡

This directory handles the "Local How" of your data. It uses **Room** (or SQLDelight/Realm) to persist data on the device. This ensures the app works offline and can cache data for quick access. It typically consists of the Entity (table structure), the DAO (access methods), and the Database holder.

</aside>

- <Entity>Entity.kt

    <aside>
    💡

  Defines the database table schema using Room annotations. This is the "SQL" representation of your object. It looks similar to your Domain object but includes database-specific details like `@PrimaryKey` and `@ColumnInfo`.

    </aside>

    ```kotlin
    @Entity
    data class <Entity>Entity(
    	@PrimaryKey(autoGenerate = false) val id: String,
    	val name: String,
    	val description: String?,
    	// Other fields stored in the database
    )
    ```

- <Entity>Dao.kt

    <aside>
    💡

  The Data Access Object (DAO) defines the methods to read and write to the local database. It often returns `Flow` to allow the UI to react instantly to database changes (Real-time updates).

    </aside>

    ```kotlin
    @Dao
    interface <Entity>Dao {
        @Upsert
        suspend fun upsert(item: <Entity>Entity)
        @Query("SELECT * FROM <Entity>Entity")
        fun get<Entity>s(): Flow<List<<Entity>Entity>>
    }
    ```

- StringListTypeConverter.kt

    <aside>
    💡

  Room (SQLite) is very simple: it only understands primitive types like Text, Numbers, and Booleans. It does not know how to save a "List of Strings" or a complex object.

  This class acts as an Adapter. It converts complex objects into a simple String (JSON) so the database can save them, and converts them back when you read from the database.

    </aside>

    ```kotlin
    object StringListTypeConverter {
        @TypeConverter
        fun fromString(value: String): List<String> {
            return Json.decodeFromString(value)
        }
        @TypeConverter
        fun fromList(list: List<String>): String {
            return Json.encodeToString(list)
        }
    }
    ```

- <Entity>Database.kt

    <aside>
    💡

  The abstract class that extends `RoomDatabase`. It acts as the main access point to the persisted data and defines which Entities belong to this database and its version.

    </aside>

    ```kotlin
    @Database(
        entities = [<Entity>Entity::class],
        version = 1
    )
    @TypeConverters(
        StringListTypeConverter::class
    )
    @ConstructedBy(<Entity>DatabaseConstructor::class) // Specific to KMP setup
    abstract class <Entity>Database: RoomDatabase() {
        abstract val <entity>Dao: <Entity>Dao
        companion object {
            const val DB_NAME = "DB_NAME"
        }
    }
    ```

- <Entity>DatabaseConstructor.kt

    <aside>
    💡

  This object acts as the **Static Instantiator** to bypass Reflection. Because Native platforms (like iOS) cannot "guess" class names at runtime, this object acts as a hard-coded hook that the **Room Compiler (KSP)** fills in during the build process to explicitly return the generated `_Impl` version of your database.

  Just remember that code for creation of DB in iOS is generated by Room compiler.

    </aside>

    ```kotlin
    @Suppress("NO_ACTUAL_FOR_EXPECT")
    expect object <Entity>DatabaseConstructor : RoomDatabaseConstructor<<Entity>Database> {
        override fun initialize(): <Entity>Database
    }
    ```

- DatabaseFactory.kt

    <aside>
    💡

  This class acts as the **Platform Configuration Architect**. Since Android, iOS, and Desktop store files in completely different folder structures, this factory's sole job is to calculate the correct **File Path** for the current OS and return a configured `RoomDatabase.Builder` (the "Order Ticket") that knows *where* to build the database.

    </aside>

  Example:

    ```kotlin
    // In commonMain
    expect class <Entity>DatabaseFactory {
        fun create(): RoomDatabase.Builder<<Entity>Database>
    }
    
    // In androidMain (Example)
    actual class <Entity>DatabaseFactory(private val context: Context) {
        actual fun create(): RoomDatabase.Builder<<Entity>Database> {
            val dbFile = context.getDatabasePath("<entity>.db")
            return Room.databaseBuilder(context, name = dbFile.absolutePath)
        }
    }
    ```

- Connection between Database, DatabaseFactory, DatabaseConstructor

  ### The Connection Map

    1. **`DatabaseFactory`** connects to **`FavoriteBookDatabase`** via **Generics**.
        - *Code:* `return Room.databaseBuilder<FavoriteBookDatabase>(...)`
        - *Meaning:* "I promise to build a database that matches this abstract class."
    2. **`FavoriteBookDatabase`** connects to **`BookDatabaseConstructor`** via **Annotation**.
        - *Code:* `@ConstructedBy(BookDatabaseConstructor::class)`
        - *Meaning:* "Hey Room, when you are ready to actually `new` up this class, please use this specific object to do it."
    3. **`BookDatabaseConstructor`** connects to the **Real Implementation** via **Generated Code**.
        - *Code (Hidden):* `return FavoriteBookDatabase_Impl()`
        - *Meaning:* The compiler writes the code to return the actual working database.

  ### The Execution Flow (How they are called)

  Here is the sequence of events when your app starts:

  ### Step 1: You call the Factory (The Setup)

    - **Who calls it:** Your Dependency Injection module (Koin).
    - **What happens:** It calls `DatabaseFactory().create()`.
    - **Technical Action:** This executes the platform-specific code (Android/iOS) to find the correct file path and returns a `RoomDatabase.Builder` configured for `FavoriteBookDatabase`.
    - **Result:** You have a "Builder" (a tool), not the database yet.

  ### Step 2: You call Build (The Trigger)

    - **Who calls it:** Your DI module calls `.build()` on the result from Step 1.
    - **What happens:** The Room library takes over. It validates the schema and prepares to instantiate the class.

  ### Step 3: Room finds the Constructor (The Lookup)

    - **Who calls it:** Room Library Internals.
    - **What happens:** Room looks at the `FavoriteBookDatabase` class. It sees the `@ConstructedBy(BookDatabaseConstructor::class)` annotation.
    - **Technical Action:** Room realizes it cannot use Reflection (on Native), so it delegates the creation task to the object specified in the annotation.

  ### Step 4: The Constructor runs (The Creation)

    - **Who calls it:** Room calls `BookDatabaseConstructor.initialize()`.
    - **What happens:** The *actual* generated code runs.
    - **Technical Action:** It executes `return FavoriteBookDatabase_Impl()`. This `_Impl` class was generated by the KSP compiler and contains all the actual SQL logic.

## create these files in core/data directory

- HttpClientExt

    ```kotlin
    // Helper to execute network call safely
    suspend inline fun <reified T> safeCall(
        execute: () -> HttpResponse
    ): Result<T, DataError.Remote> {
        val response = try {
            execute()
        } catch(e: SocketTimeoutException) {
            return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        } catch(e: UnresolvedAddressException) {
            return Result.Error(DataError.Remote.NO_INTERNET)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            return Result.Error(DataError.Remote.UNKNOWN)
        }
    
        return responseToResult(response)
    }
    
    // Helper to map HTTP Response to Domain Result
    suspend inline fun <reified T> responseToResult(
        response: HttpResponse
    ): Result<T, DataError.Remote> {
        return when(response.status.value) {
            in 200..299 -> {
                try {
                    // Happy path: Parse JSON body
                    Result.Success(response.body<T>())
                } catch(e: NoTransformationFoundException) {
                    Result.Error(DataError.Remote.SERIALIZATION)
                }
            }
            400 -> { 
                 // Optional: Handle validation errors specifically if server sends message
                 // val errorBody = response.body<ErrorDto>()
                 // Result.Error(DataError.Remote.VALIDATION_ERROR(errorBody.message))
                 Result.Error(DataError.Remote.VALIDATION_ERROR)
            }
            401 -> Result.Error(DataError.Remote.UNAUTHORIZED)
            408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
            429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(DataError.Remote.SERVER_ERROR)
            else -> Result.Error(DataError.Remote.UNKNOWN)
        }
    }
    ```

- HttpClientFactory

    ```kotlin
    object HttpClientFactory {
        fun create(engine: HttpClientEngine): HttpClient {
            return HttpClient(engine) {
                install(ContentNegotiation) {
                    json(
                        json = Json {
                            ignoreUnknownKeys = true
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                }
                install(Auth) {
                    bearer {
                        // 1. LOAD TOKENS
                        loadTokens {
                            // Get the saved tokens from local storage (DataStore/Settings)
                            val info = tokenStorage.getTokenInfo() 
                            BearerTokens(
                                accessToken = info?.accessToken ?: "",
                                refreshToken = info?.refreshToken ?: ""
                            )
                        }
    
                        // 2. REFRESH LOGIC (The "Interceptor")
                        refreshTokens {
                            // This block runs if a request returns 401 Unauthorized.
                            
                            // A. Get the old tokens
                            val oldTokens = oldTokens
                            
                            // B. Call your refresh API
                            // Ideally, use a *separate* simple client to avoid recursion loops
                            val refreshResult = someNetworkCallToRefresh(oldTokens?.refreshToken)
                            
                            // C. Update storage & Return new tokens
                            if (refreshResult.isSuccess) {
                                val newTokens = refreshResult.getOrNull()
                                tokenStorage.saveTokens(newTokens) // Persist them
                                
                                BearerTokens(
                                    accessToken = newTokens.accessToken,
                                    refreshToken = newTokens.refreshToken
                                )
                            } else {
                                // Refresh failed (User might need to logout)
                                null 
                            }
                        }
                        
                        // 3. EXCLUDE LOGIN (Optional)
                        // If you need to explicitly exclude specific paths, you can check request.url
                        sendWithoutRequest { request ->
                            request.url.pathSegments.contains("login") || 
                            request.url.pathSegments.contains("register")
                        }
                    }
                }
                install(HttpTimeout) {
                    socketTimeoutMillis = 20_000L
                    requestTimeoutMillis = 20_000L
                }
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            println(message)
                        }
                    }
                    level = LogLevel.ALL
                }
                defaultRequest {
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }
    ```


# How to structure domain layer?

## create these directories for each feature domain layer

### models

It contains pure models.

### `<Entity>.kt`

This class defines what an "Entity" looks like in your business logic. It is stripped of all technical details.

Key Rules:

1. No `@Serializable` (Network stuff).
2. No `@Entity` (Database stuff).
3. Just pure Kotlin data.

**Example:**

```kotlin
data class <Entity>(
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String? // Nullable because business logic says it might be missing
)
```

### repository

### `<Entity>Repository.kt`

The Contract (The "What").

This interface tells the rest of the app what operations are possible, but it does not say how they happen.

This is the most important file for Dependency Inversion. The Presentation layer talks to this interface, not the actual implementation.

**Example:**

```kotlin
interface <Entity>Repository {
    // Returns a Result wrapper to handle success/failure gracefully
    suspend fun search(query: String): Result<List<<Entity>>, DataError.Remote>
    
    // Returns a Flow for real-time updates (e.g., from a database)
    fun getFavorites(): Flow<List<<Entity>>>
    
    suspend fun save(item: <Entity>): EmptyResult<DataError.Local>
}
```

### usecases

## The Debate: Use Cases vs. "Direct Repository"

### 1. When Use Cases are Overkill (This Project)

If your app is mostly just showing data from a server, your Use Case often looks like this:

```kotlin
class Get<Entity>UseCase(private val repo: <Entity>Repository) {
    operator fun invoke(id: String) = repo.get<Entity>(id)
}
```

**This is useless code.** It just passes the ball from the ViewModel to the Repository without doing anything. In this scenario (CRUD apps), skipping Use Cases and calling the Repository directly from the ViewModel is **Cleaner** and **Better**.

### 2. When Use Cases are Essential

If you have complex business rules that involve multiple repositories or heavy logic, you **must** use them.

**Example:**
Imagine `Buy<Entity>UseCase`. It needs to:

1. Check if the `<Entity>` is in stock (InventoryRepo).
2. Deduct money from the User's wallet (UserRepo).
3. Send a confirmation email (NotificationService).

If you put all this logic in the **ViewModel**, your ViewModel becomes fat and impossible to test. If you put it in a **Use Case**, it becomes a reusable, testable unit of business logic.

## Add following directories/files in core/domain only

### validation

**Context:**
This pattern is used in the **Presentation Layer** (ViewModel). It relies on Validator classes injected from the Domain (or common) layer.

**The Workflow:**

1. **Execute** all individual field validators first.
2. **Update** the UI state immediately (so users see all errors at once).
3. **Check** if any validation failed and stop execution (`return`) if true.
4. **Perform** cross-field validation (like "Confirm Password") only if individual fields are valid.

**Code Template:**

```kotlin
// 1. The Validator Class (Domain/Use Case)
class ValidateEmail { // Add @Inject if using DI
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        // Simple Regex for email
        if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidationResult(successful = true)
    }
}

// 2. The Result Data Class
data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)

// 3. The ViewModel Implementation
fun submitForm() {
    // A. Run all validators
    val emailResult = validateEmail(state.value.email)
    val passwordResult = validatePassword(state.value.password)

    // B. Push errors to UI (Even if null, to clear previous errors)
    _state.update { it.copy(
        emailError = emailResult.errorMessage,
        passwordError = passwordResult.errorMessage
    )}

    // C. Check for failure (Fail Fast)
    val hasError = listOf(
        emailResult,
        passwordResult
    ).any { !it.successful }

    if(hasError) {
        return
    }

    // D. Success Path
    viewModelScope.launch {
        repository.login(...)
    }
}

```

> 💡 Why this works:
>
> - **Separation:** The ViewModel doesn't know *how* to validate an email, only *when* to do it.
> - **UX:** It provides immediate feedback on all fields.
> - **Reusability:** You can use `ValidateEmail` in the Login Screen, Sign Up Screen, and "Forgot Password" Screen without rewriting the Regex.

### DataError.kt

```kotlin
sealed interface DataError : Error {
    
    // Changing Remote from 'enum class' to 'sealed interface' 
    // allows us to mix objects (constants) and data classes (payloads).
    sealed interface Remote : DataError {
        data object REQUEST_TIMEOUT : Remote
        data object TOO_MANY_REQUESTS : Remote
        data object NO_INTERNET : Remote
        data object SERVER_ERROR : Remote
        data object SERIALIZATION : Remote
        data object UNKNOWN : Remote
        data object UNAUTHORIZED : Remote
        
        // The one exception that carries data!
        data class VALIDATION_ERROR(val message: String) : Remote
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}
```

### Result.kt

```kotlin
sealed interface Result<out D, out E: DataError> {

    data class Success<out D>(
        val data: D
    ) : Result<D, Nothing>

    data class Error<out E: DataError>(
        val error: E
    ): Result<Nothing, E>
    
}
```

# How to structure presentation layer?

## Add following files in core/presentation only

- **.asErrorUiText()** is called in the **ViewModel** (or just before state update). It returns a **UiText object** (like a sealed envelope containing the resource ID `R.string.error_no_internet`). This object is stored in your State Flow.
- **.asString()** is called in the **Composable** (UI Layer). It "opens the envelope" and asks the Compose framework to resolve that ID into the actual string ("No Internet Connection") based on the *current* device configuration (Language, Locale).
    - This helps if the language configuration changes in the UI. But for the VALIDATION_ERROR it won’t change because they are dynamically sent from server.

### DataError to UI text in core/presentation

```kotlin
package com.plcoding.bookpedia.core.presentation

import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.error_disk_full
import cmp_bookpedia.composeapp.generated.resources.error_no_internet
import cmp_bookpedia.composeapp.generated.resources.error_serialization
import cmp_bookpedia.composeapp.generated.resources.error_too_many_requests
import cmp_bookpedia.composeapp.generated.resources.error_unknown
import cmp_bookpedia.composeapp.generated.resources.file_too_large
import cmp_bookpedia.composeapp.generated.resources.server_error
import cmp_bookpedia.composeapp.generated.resources.the_request_timed_out
import cmp_bookpedia.composeapp.generated.resources.youve_hit_your_rate_limit
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

fun Result.Error<*, DataError>.toUiText(): UiText {
    val stringRes = when(error) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.the_request_timed_out
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.youve_hit_your_rate_limit
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERVER_ERROR -> Res.string.server_error
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.PAYLOAD_TOO_LARGE -> Res.string.file_too_large
        DataError.Remote.UNAUTHORIZED -> Res.string.error_unknown // Fallback if no specific string exists
        is DataError.Remote.VALIDATION_ERROR -> UiText.DynamicString(error.message)
    }
    
    return UiText.StringResourceId(stringRes)
}

```

### UiText.kt

```kotlin
package com.plcoding.bookpedia.core.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    
    class StringResourceId(
        val resource: StringResource,
        val args: Array<Any> = arrayOf()
    ) : UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResourceId -> stringResource(resource, *args)
        }
    }
}
```

## create these files for each feature in presentation layer

### ViewModel.kt

```kotlin
class BookListViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    
    // Public state exposed to UI
    val state = _state
        .onStart {
            // Optional: Load initial data when UI subscribes
            // loadData() 
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookListAction) {
        when(action) {
            is BookListAction.OnSearch -> loadData(action.query)
            is BookListAction.OnRetry -> loadData(state.value.searchQuery) // Re-use logic
            // ... other actions
        }
    }

    private fun loadData(query: String) {
        viewModelScope.launch {
            // 1. SET LOADING STATE
            _state.update { it.copy(
                isLoading = true,
                errorMessage = null // Clear previous errors
            )}

            // 2. FETCH DATA
            val result = repository.searchBooks(query)

            // 3. HANDLE RESULT (Success/Error)
            result
                .onSuccess { data ->
                    _state.update { it.copy(
                        isLoading = false,
                        searchResults = data,
                        errorMessage = null
                    )}
                }
                .onError { error ->
                    _state.update { it.copy(
                        isLoading = false,
                        searchResults = emptyList(), // Optional: Clear old data on error?
                        errorMessage = error.toUiText() // Map Domain Error -> UI Text
                    )}
                }
        }
    }
}

```

### ScreenRoot.kt

```kotlin
@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit // Navigation callback passed from NavHost
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onAction = { action ->
            // 1. INTERCEPTOR LOGIC
            when(action) {
                is BookDetailAction.OnBackClick -> onBackClick() // Handle Navigation
                else -> Unit
            }
            
            // 2. DELEGATE LOGIC
            viewModel.onAction(action) // Pass everything to ViewModel
        }
    )
}

```

### Screen.kt

```kotlin
@Composable
fun BookListScreen(state: BookListState, ...) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage.asString(), // Resolves UiText to String
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
            state.searchResults.isEmpty() -> {
                Text(
                    text = stringResource(Res.string.no_results),
                    textAlign = TextAlign.Center
                )
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.searchResults) { book ->
                        // ...
                    }
                }
            }
        }
    }
}
```

### State.kt

```kotlin
data class BookListState(
    // Data
    val searchResults: List<Book> = emptyList(),
    
    // Status Flags
    val isLoading: Boolean = false,
    
    // Error Handling (Using your UiText wrapper)
    val errorMessage: UiText? = null,
    
    // Optional: Empty State helper (can also be computed in UI)
    // val isEmpty: Boolean = false 
)
```

### Action.kt

```kotlin
sealed interface BookListAction {
    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnBookClick(val book: Book) : BookListAction
    data object OnTabSelected : BookListAction
}
```

# How to manage DI?

## create following files in core/di

- Modules (setup)

    ```kotlin
    // 1. Platform specific module (Defined as 'expect' here)
    expect val platformModule: Module
    
    // 2. Shared module
    val sharedModule = module {
        // A. Singletons (Created once, used everywhere)
        single { HttpClientFactory.create(get()) } // 'get()' resolves dependencies automatically
        // --- Feature Modules ---
        includes(
            bookModule,
            userModule,
            cartModule,
            authModule
            // Add new features here...
        )
    }
    
    // Android Implementation
    actual val platformModule: Module
        get() = module {
            single<HttpClientEngine> { OkHttp.create() }
            single { DatabaseFactory(androidContext()) }
        }
    
    // iOS Implementation
    actual val platformModule: Module
        get() = module {
            single<HttpClientEngine> { Darwin.create() }
            single { DatabaseFactory() } // No context needed on iOS
        }
    
    ```

- InitKoin

    ```kotlin
    // Shared
    fun initKoin(config: KoinAppDeclaration? = null) {
        startKoin {
            config?.invoke(this)
            modules(sharedModule, platformModule)
        }
    }
    
    // Android Usage (Application Class)
    initKoin {
        androidContext(this@BookApplication)
    }
    
    // iOS Usage (MainViewController)
    fun MainViewController() = ComposeUIViewController(
        configure = { initKoin() }
    ) { App() }
    
    ```


## create following file in di layer of each feature

- Separate module

    ```
    val bookModule = module {
        // 1. Data Source
        singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    
        // 2. Repository
        singleOf(::DefaultBookRepository).bind<BookRepository>()
    
        // 3. ViewModels
        viewModelOf(::BookListViewModel)
        viewModelOf(::BookDetailViewModel)
    }
    ```


# How to manage datastore in KMP?

```kotlin
// in core/data/local
object DataStoreFactory {
    fun create(producePath: () -> String): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath().toPath() }
        )
    }
}

// platform specific
// ... inside platformModule ...
single {
    DataStoreFactory.create {
        androidContext().filesDir.resolve("bookpedia.preferences_pb").absolutePath
    }
}

**B. iOS (`iosMain/kotlin/.../di/Modules.ios.kt`)**
Uses `NSFileManager` to find the sandbox Documents directory.

```kotlin
// ... inside platformModule ...
single {
    DataStoreFactory.create {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(documentDirectory).path + "/bookpedia.preferences_pb"
    }
}

**C. Desktop (`desktopMain/kotlin/.../di/Modules.desktop.kt`)**
Uses standard Java `System.getProperty` to find the user's home or AppData folder.

```kotlin
// ... inside platformModule ...
single {
    DataStoreFactory.create {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "Bookpedia")
            os.contains("mac") -> File(userHome, "Library/Application Support/Bookpedia")
            else -> File(userHome, ".local/share/Bookpedia")
        }
        if(!appDataDir.exists()) appDataDir.mkdirs()
        File(appDataDir, "bookpedia.preferences_pb").absolutePath
    }
}

### Summary of Flow
1.  **DI Module** calls `DataStoreFactory.create()`.
2.  It passes a lambda (`producePath`) that calculates the correct OS path.
3.  **Factory** creates the DataStore instance using that path.
4.  **Koin** provides this `DataStore<Preferences>` instance to any Repository/Wrapper that asks for it.
```

# How to manage pagination?

Refer to this repository here it’s very easy → https://github.com/philipplackner/CMP-Pagination.git. I have watched Philip Lackner’s video on this topic it’s too easy.

## When Uncertain

- Ask clarifying questions before implementing
- Suggest alternatives with trade-offs explained
- Reference this document for established patterns
- Don't make assumptions about business logic