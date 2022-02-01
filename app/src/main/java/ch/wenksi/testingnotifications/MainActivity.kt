package ch.wenksi.testingnotifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContentView(R.layout.activity_main)
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
}