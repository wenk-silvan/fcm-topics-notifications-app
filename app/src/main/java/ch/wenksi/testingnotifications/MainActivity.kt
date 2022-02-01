package ch.wenksi.testingnotifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_main)
        observeNotification()
        subscribeToTopic("project-alpha")
        subscribeToTopic("project-beta")
    }

    private fun subscribeToTopic(topic: String) {
        Firebase.messaging.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = "Subscribed to $topic"
                if (!task.isSuccessful) {
                    msg = "Couldn't subscribe to $topic"
                }
                Log.d("Notifications", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }
    }

    private fun observeNotification() {
        Events.EVENTS.newNotification.observe(this) {
            val items = arrayListOf(it.description)
            it.payload?.forEach { entry -> items.add("${entry.key}: ${entry.value}") }

            MaterialAlertDialogBuilder(this@MainActivity)
                .setTitle(it.title)
                .setItems(items.toTypedArray()) { _, _ -> }
                .setNeutralButton("Ok") { _, _ -> }
                .show()
        }
    }
}