package com.esmac.android.pandora.data.remote.response

/**
 * Created by Hữu Nguyễn on 27/08/2020.
 * Email: huuntt1905@gmail.com.
 */
data class Directions(
    val routes: ArrayList<Route>?,
    val status: String?
) {
    class Route(val legs: Array<Leg>)

    class Leg(
        val end_address: String,
        val end_location: EndLocation,
        val start_address: String,
        val start_location: StartLocation,
        val duration: Duration,
        val distance: Distance,
        val steps: Array<Step>
    ) {
        class EndLocation(val lat: Double, val lng: Double)

        class StartLocation(val lat: Double, val lng: Double)

        class Duration(val value: Int, val text: String)

        class Distance(private val value: Int, val text: String) {
            val valueKm: Double
                get() = value.div(1000.0)
        }

        class Step(
            val end_location: EndLocation,
            val html_instructions: String,
            val start_location: StartLocation,
            val travel_mode: String,
            val maneuver: String
        )

    }
}