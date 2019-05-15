package com.binhnk.retrofitwithroom.di

import android.content.Context
import com.binhnk.retrofitwithroom.data.remote.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import okhttp3.Cache
import org.koin.dsl.module
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type

val networkModule = module {
    single { createOkHttpCache(get()) }
    single { createAppRetrofit(get()) }
    single { createApiService(get()) }
    single { CoroutinesErrorHandlingFactory() }
}


val gson: Gson = GsonBuilder().setLenient().create()

fun createOkHttpCache(context: Context): Cache =
    Cache(context.cacheDir, (10 * 1024 * 1024).toLong())


fun createAppRetrofit(
    coroutinesErrorHandlingFactory: CoroutinesErrorHandlingFactory
): Retrofit =
    Retrofit.Builder()
        .addCallAdapterFactory(coroutinesErrorHandlingFactory)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(ApiService.BASE_URL)
        .build()


fun createApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

/**
 * CoroutinesErrorHandlingFactory for coroutine
 */
class CoroutinesErrorHandlingFactory : CallAdapter.Factory() {

    private val instance = CoroutineCallAdapterFactory()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? =
        CoroutineCallAdapterWrapper(
            instance.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>
        )
}


class CoroutineCallAdapterWrapper<T>(
    private val wrapped: CallAdapter<T, Any>
) : CallAdapter<T, Deferred<T>> {

    override fun responseType(): Type = wrapped.responseType()

    override fun adapt(call: Call<T>): Deferred<T> {
        val deferred = CompletableDeferred<T>()

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }

        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, throwable: Throwable) {
                deferred.completeExceptionally(convertToBaseException(throwable))
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        deferred.complete(it)
                    }
                } else {
                    deferred.completeExceptionally(
                        BaseException.toHttpError(
                            response = response,
                            httpCode = response.code()
                        )
                    )
                }
            }
        })

        return deferred
    }
}


fun convertToBaseException(throwable: Throwable): BaseException =
    when (throwable) {
        is BaseException -> throwable

        is IOException -> BaseException.toNetworkError(throwable)

        is HttpException -> {
            val response = throwable.response()
            val httpCode = throwable.code()

            if (response.errorBody() == null) {
                BaseException.toHttpError(
                    httpCode = httpCode,
                    response = response
                )
            }

            val serverErrorResponseBody = try {
                response.errorBody()?.string() ?: ""
            } catch (e: Exception) {
                ""
            }

            val serverErrorResponse =
                try {
                    Gson().fromJson(serverErrorResponseBody, ServerErrorResponse::class.java)
                } catch (e: Exception) {
                    ServerErrorResponse()
                }

            if (serverErrorResponse != null) {
                BaseException.toServerError(
                    serverErrorResponse = serverErrorResponse,
                    httpCode = httpCode
                )
            } else {
                BaseException.toHttpError(
                    response = response,
                    httpCode = httpCode
                )
            }
        }

        else -> BaseException.toUnexpectedError(throwable)
    }

class BaseException(
    val errorType: ErrorType,
    val serverErrorResponse: ServerErrorResponse? = null,
    val response: Response<*>? = null,
    val httpCode: Int = 0,
    cause: Throwable? = null
) : RuntimeException(cause?.message, cause) {

    override val message: String?
        get() = when (errorType) {
            ErrorType.HTTP -> response?.message()

            ErrorType.NETWORK -> cause?.message

            ErrorType.SERVER -> serverErrorResponse?.message // TODO update real response

            ErrorType.UNEXPECTED -> cause?.message
        }

    companion object {
        fun toHttpError(response: Response<*>, httpCode: Int) =
            BaseException(
                errorType = ErrorType.HTTP,
                response = response,
                httpCode = httpCode
            )

        fun toNetworkError(cause: Throwable) =
            BaseException(
                errorType = ErrorType.NETWORK,
                cause = cause
            )

        fun toServerError(serverErrorResponse: ServerErrorResponse, httpCode: Int) =
            BaseException(
                errorType = ErrorType.SERVER,
                serverErrorResponse = serverErrorResponse,
                httpCode = httpCode
            )

        fun toUnexpectedError(cause: Throwable) =
            BaseException(
                errorType = ErrorType.UNEXPECTED,
                cause = cause
            )
    }
}

/**
 * Identifies the error type which triggered a [BaseException]
 */
enum class ErrorType {
    /**
     * An [IOException] occurred while communicating to the server.
     */
    NETWORK,

    /**
     * A non-2xx HTTP status code was received from the server.
     */
    HTTP,

    /**
     * A error server with code & message
     */
    SERVER,

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}

// TODO update server error response
data class ServerErrorResponse(
    @SerializedName("message") val message: String? = null
)
