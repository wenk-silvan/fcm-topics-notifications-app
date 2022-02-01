package ch.wenksi.testingnotifications

import androidx.lifecycle.MutableLiveData

class Events {
    object EVENTS {
        val newNotification: MutableLiveData<NotificationEvent> by lazy {
            MutableLiveData<NotificationEvent>()
        }

        data class NotificationEvent(
            val title: String,
            val description: String,
            val payload: Map<String, String>?
        )
    }
}