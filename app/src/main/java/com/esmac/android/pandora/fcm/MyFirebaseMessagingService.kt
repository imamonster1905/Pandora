package com.esmac.android.pandora.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.esmac.android.pandora.R
import com.esmac.android.pandora.data.local.preferance.AppPrefManger
import com.esmac.android.pandora.util.extention.TAG
import com.esmac.android.pandora.util.extention.logD
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import javax.inject.Inject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
//        EventBus.getDefault().register(this)
    }

//    override fun onDestroy() {
//        EventBus.getDefault().unregister(this)
//        super.onDestroy()
//    }

    @Inject
    lateinit var appPrefManger: AppPrefManger

    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        logD(TAG, "$remoteMessage")
        remoteMessage.notification?.let { logD(TAG, "Message Notification: $it") }
        remoteMessage.data.let { logD(TAG, "Message Data: $it") }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        showNotification(
            remoteMessage.data["title"].toString(),
            remoteMessage.data["body"].toString(),
            remoteMessage.data["resource_id"]?.toLong(),
            remoteMessage.collapseKey
        )
    }
    // [END receive_message]

    // [START on_new_token]
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {

        logD(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }
    // [END on_new_token]
    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance().beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        logD(TAG, "Short lived task is done.")
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String?) {
        token?.let { appPrefManger.setDeviceToken(token) }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param body FCM message body received.
     */
    private fun showNotification(title: String, body: String, resource_id: Long?, collapseKey: String?) {

        val NOTIFICATION_ID = 852
        val CHANNEL_ID = getString(R.string.default_notification_channel_id)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "DiCar Channel"
            val description = "DiCar Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200)
            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)
        }

        val bundle = Bundle()
        var navBuilder: PendingIntent? = null

       /*when (collapseKey) {
            Constants.CollapseKey.COLLAPSE_KEY_TRIP -> {
                bundle.putLong("tripId", resource_id ?: -1L)
                navBuilder = NavDeepLinkBuilder(this)
                    .setComponentName(MainActivity::class.java)
                    .setGraph(R.navigation.main_navigation)
                    .setDestination(R.id.tripDetailFragment)
                    .setArguments(bundle)
                    .createPendingIntent()
            }
            Constants.CollapseKey.COLLAPSE_KEY_MY_CAR -> {
                navBuilder = NavDeepLinkBuilder(this)
                    .setComponentName(MainActivity::class.java)
                    .setGraph(R.navigation.main_navigation)
                    .setDestination(R.id.mycarFragment)
                    .createPendingIntent()
            }
            Constants.CollapseKey.COLLAPSE_KEY_WALLET -> {
                navBuilder = NavDeepLinkBuilder(this)
                    .setComponentName(MainActivity::class.java)
                    .setGraph(R.navigation.main_navigation)
                    .setDestination(R.id.walletFragment)
                    .createPendingIntent()
            }
            Constants.CollapseKey.COLLAPSE_KEY_SYSTEM -> {
                navBuilder = NavDeepLinkBuilder(this)
                    .setComponentName(MainActivity::class.java)
                    .setGraph(R.navigation.main_navigation)
                    .setDestination(R.id.notificationFragment)
                    .createPendingIntent()
            }
        }*/
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setChannelId(CHANNEL_ID)
            .setContentTitle(body)
            .setContentText(title)
            .setContentIntent(navBuilder)
            .setAutoCancel(true)
            .setPriority(10)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}