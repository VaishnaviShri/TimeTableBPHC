package com.example.timetablebphc.notifications

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.timetablebphc.R
import com.example.timetablebphc.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationIntent = Intent(context, MainActivity::class.java)

        val title = intent.getStringExtra("title")
        val message = intent.getStringExtra("message")

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(notificationIntent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = Notification.Builder(context)
        val notification = builder.setContentTitle(title)
            .setContentText(message)
            .setTicker(context.getString(R.string.notification_title))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent).build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val uniqueNotificationId = (Date().time / 1000L % Int.MAX_VALUE).toInt()
        notificationManager.notify(uniqueNotificationId, notification)
    }

    companion object {
        private const val CHANNEL_ID = "some_channel_id"
        private const val CHANNEL_NAME = "NotificationDemo"
    }

}