package com.esmac.android.pandora.network

import com.esmac.android.pandora.data.remote.response.Directions
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Hữu Nguyễn on 16/03/2020.
 * Email: huuntt1905@gmail.com.
 */
interface GoogleMapApiService {

    companion object {
        var BASE_URL = "https://maps.googleapis.com/maps/api/"
    }

    @GET("directions/json")
    fun googleMapDirections(
        @Query("origin") origin: String? = null,
        @Query("destination") destination: String? = null,
        @Query("mode") mode: String? = "driving",
        @Query("language") language: String? = "vi",
        @Query("key") key: String? = "AIzaSyD0gkSFDsoUt4frljYOgfIlctVrjLvVvxI"

    ): Observable<Directions>
}