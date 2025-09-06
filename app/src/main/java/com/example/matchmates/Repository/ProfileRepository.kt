package com.example.matchmates.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProfileRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val profileCollection = firestore.collection("profiles")

    suspend fun saveProfile(profile: Profile): Result<Unit> {
        return try {
            profileCollection.document(profile.username).set(profile).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun isProfileExists(uid: String): Boolean {
        return try {
            val snapshot = FirebaseFirestore.getInstance()
                .collection("profiles")
                .document(uid)
                .get()
                .await()
            snapshot.exists()
        } catch (e: Exception) {
            false
        }
    }
    suspend fun getProfilesByIds(userIds: List<String>): List<Profile> {
        return try {
            val snapshot = profileCollection.whereIn("username", userIds).get().await()
            snapshot.toObjects(Profile::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }


}
