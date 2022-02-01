package ch.wenksi.testingnotifications

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

const val TAG: String = "Notification"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "The token refreshed: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        var title = ""
        var body = ""
        var payload: Map<String, String>? = null

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            payload = remoteMessage.data
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Title: ${it.title}")
            Log.d(TAG, "Message Notification Body: ${it.body}")
            title = if (it.title == null) "" else it.title!!
            body = if (it.body == null) "" else it.body!!
        }

        Events.EVENTS.newNotification.postValue(
            Events.EVENTS.NotificationEvent(
                title,
                body,
                payload
            )
        )
    }
}