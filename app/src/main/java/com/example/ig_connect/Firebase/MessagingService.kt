package com.example.ig_connect.Firebase



import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.Firebase.messaging.FirebaseMessagingService
import com.google.Firebase.messaging.RemoteMessage
import com.example.ig_connect.R
import com.example.ig_connect.activities.ChatActivity
import com.example.ig_connect.models_chat.User
import com.example.ig_connect.Utilities.Constants
import java.util.Random

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val user = User().apply {
            id = message.data[Constants.KEY_USER_ID]
            name = message.data[Constants.KEY_NAME]
            token = message.data[Constants.KEY_FCM_TOKEN]
        }

        val notificationId = Random().nextInt()
        val channelId = "chat_message"

        val intent = Intent(this, ChatActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(Constants.KEY_USER, user)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(user.name)
            .setContentText(message.data[Constants.KEY_MESSAGE])
            .setStyle(NotificationCompat.BigTextStyle().bigText(message.data[Constants.KEY_MESSAGE]))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Chat Message"
            val channelDescription = "This notification channel is used for chat message notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationManagerCompat = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManagerCompat.notify(notificationId, builder.build())
    }
}
