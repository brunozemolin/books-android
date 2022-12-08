package com.bzapps.booksapp.data.local.database

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class FirebaseInstance {

    fun uploadImageToFirebase(fileUri: Uri, imageUrl: (String) -> Unit) {
        val fileName = UUID.randomUUID().toString() +".jpg"

        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")

        refStorage.putFile(fileUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    imageUrl(it.toString())
                }
            }.addOnFailureListener { e ->
                print(e.message)
            }
    }
}