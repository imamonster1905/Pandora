package com.esmac.android.pandora.network

import com.esmac.android.pandora.data.remote.request.LoginRequest
import com.esmac.android.pandora.data.remote.request.RegisterRequest
import com.esmac.android.pandora.data.remote.response.Notification
import com.esmac.android.pandora.data.remote.response.User
import com.esmac.android.pandora.data.remote.response.base.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * Created by Hữu Nguyễn on 16/03/2020.
 * Email: huuntt1905@gmail.com.
 */
interface ApiService {
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Observable<BaseResponse<String>>

    @POST("auth/register")
    fun register(@Body registerRequest: RegisterRequest): Observable<BaseResponse<User>>

    @GET("me")
    fun getProfile(): Observable<BaseResponse<User>>

//    @Multipart
//    @POST("users")
//    fun updateProfile(
//        @Part cover: MultipartBody.Part?,
//        @Part avatar: MultipartBody.Part?,
//        @Part("_method") _method: RequestBody?,
//        @Part("name") name: RequestBody?,
//        @Part("email") email: RequestBody?,
//        @Part("phone_number") phone_number: RequestBody?
//    ): Observable<BaseResponse<User>>

    @GET("notifications")
    suspend fun getNotifications(@QueryMap hashMap: HashMap<String, Any> = HashMap()): BaseResponse<List<Notification>>


//    @Multipart
//    @POST("upload")
//    fun postCarUploadImage(
//        @Part image: MultipartBody.Part?,
//        @Part("type") phone_number: RequestBody?
//    ): Observable<BaseResponse<CarImage>>
}