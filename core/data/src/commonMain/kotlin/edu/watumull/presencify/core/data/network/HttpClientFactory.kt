import edu.watumull.presencify.core.data.dto.auth.TokenDto
import edu.watumull.presencify.core.data.repository.auth.RoleRepository
import edu.watumull.presencify.core.data.repository.auth.TokenRepository
import edu.watumull.presencify.core.domain.model.auth.UserRole
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import edu.watumull.presencify.core.data.network.admin_auth.ApiEndpoints as AdminApiEndpoints
import edu.watumull.presencify.core.data.network.student_auth.ApiEndpoints as StudentApiEndpoints
import edu.watumull.presencify.core.data.network.teacher_auth.ApiEndpoints as TeacherApiEndpoints

class HttpClientFactory(
    private val tokenRepository: TokenRepository,
    private val roleRepository: RoleRepository
) {
    private fun createBasicClient(engine: HttpClientEngine): HttpClient {
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
                    loadTokens {
                        BearerTokens(
                            accessToken = tokenRepository.readAccessToken() ?: "",
                            refreshToken = tokenRepository.readRefreshToken() ?: ""
                        )
                    }

                    refreshTokens {
                        // Get the current refresh token
                        val refreshToken = tokenRepository.readRefreshToken() ?: return@refreshTokens null

                        // Get user role
                        val userRole = roleRepository.getUserRole().firstOrNull() ?: return@refreshTokens null

                        // Create basic client for refresh
                        val basicClient = createBasicClient(engine)

                        return@refreshTokens try {
                            val tokenDto = when (userRole) {
                                UserRole.STUDENT -> {
                                    basicClient.post(StudentApiEndpoints.REFRESH_TOKENS) {
                                        contentType(ContentType.Application.Json)
                                        setBody(mapOf("refreshToken" to refreshToken))
                                    }.body<TokenDto>()
                                }
                                UserRole.TEACHER -> {
                                    basicClient.post(TeacherApiEndpoints.REFRESH_TOKENS) {
                                        contentType(ContentType.Application.Json)
                                        setBody(mapOf("refreshToken" to refreshToken))
                                    }.body<TokenDto>()
                                }
                                UserRole.ADMIN -> {
                                    basicClient.post(AdminApiEndpoints.REFRESH_TOKENS) {
                                        contentType(ContentType.Application.Json)
                                        setBody(mapOf("refreshToken" to refreshToken))
                                    }.body<TokenDto>()
                                }
                            }

                            // Update tokens in repository
                            tokenRepository.saveAccessToken(tokenDto.accessToken)
                            tokenRepository.saveRefreshToken(tokenDto.refreshToken)

                            // Return new BearerTokens
                            BearerTokens(tokenDto.accessToken, tokenDto.refreshToken)
                        } catch (e: Exception) {
                            null
                        } finally {
                            basicClient.close()
                        }
                    }

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