package com.msg.gcms.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.msg.gcms.R
import com.msg.gcms.presentation.view.splash.SplashActivity

class GCMSMessagingService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_NAME = "GCMS"
        private const val CHANNEL_DESCRIPTION = "GCMS 알림"
        private const val CHANNEL_ID = "Channel Id"
        private const val GROUP_NAME = "com.msg.gcms"
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        createNotificationChannel()
        createNotification(message.notification?.title, message.notification?.body)
    }

    private fun createNotification(title: String?, message: String?) {
        val messageId = System.currentTimeMillis().toInt()
        val intent = Intent(this, SplashActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, messageId, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_msg_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setGroup(GROUP_NAME)
            .setGroupSummary(true)
            .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(messageId, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = CHANNEL_DESCRIPTION

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)
    }
}