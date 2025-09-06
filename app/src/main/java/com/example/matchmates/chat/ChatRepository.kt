package com.example.matchmates.chat


import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

object ChatRepository {
    private val db = Firebase.firestore

    fun listenMessages(chatId: String, onUpdate: (List<Message>) -> Unit): ListenerRegistration {
        return db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("ChatRepo", "listen error", error)
                    return@addSnapshotListener
                }
                val msgs = snapshot?.documents?.mapNotNull { doc ->
                    val senderId = doc.getString("senderId") ?: return@mapNotNull null
                    val text = doc.getString("text") ?: ""
                    val timestamp = doc.getLong("timestamp") ?: 0L
                    Message(id = doc.id, senderId = senderId, text = text, timestamp = timestamp)
                } ?: emptyList()
                onUpdate(msgs)
            }
    }

    fun sendMessage(chatId: String, senderId: String, text: String) {
        val data = hashMapOf(
            "senderId" to senderId,
            "text" to text,
            "timestamp" to System.currentTimeMillis()
        )
        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(data)
            .addOnFailureListener { Log.e("ChatRepo", "send failed", it) }
    }
}
