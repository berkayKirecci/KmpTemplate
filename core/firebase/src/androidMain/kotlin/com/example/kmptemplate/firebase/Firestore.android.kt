package com.example.kmptemplate.firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class Firestore actual constructor(collectionName: String) {

    private val col = FirebaseFirestore.getInstance().collection(collectionName)

    actual fun documents(): Flow<List<Map<String, Any?>>> = callbackFlow {
        val registration = col.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error); return@addSnapshotListener
            }
            val docs = snapshot?.documents?.map { doc ->
                buildMap<String, Any?> {
                    doc.data?.forEach { (k, v) -> put(k, v) }
                    put("id", doc.id)
                }
            } ?: emptyList()
            trySend(docs)
        }
        awaitClose { registration.remove() }
    }

    actual suspend fun set(id: String, data: Map<String, Any?>) =
        suspendCancellableCoroutine { cont ->
            col.document(id).set(data)
                .addOnSuccessListener { cont.resume(Unit) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

    actual suspend fun add(data: Map<String, Any?>): String {
        val id = newId()
        set(id, data + ("id" to id))
        return id
    }

    actual suspend fun update(id: String, fields: Map<String, Any?>) =
        suspendCancellableCoroutine { cont ->
            col.document(id).update(fields)
                .addOnSuccessListener { cont.resume(Unit) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

    actual suspend fun delete(id: String) = suspendCancellableCoroutine { cont ->
        col.document(id).delete()
            .addOnSuccessListener { cont.resume(Unit) }
            .addOnFailureListener { cont.resumeWithException(it) }
    }

    actual fun newId(): String = col.document().id

    actual suspend fun get(id: String): Map<String, Any?>? = suspendCancellableCoroutine { cont ->
        col.document(id).get()
            .addOnSuccessListener { doc ->
                val result = if (doc.exists()) buildMap<String, Any?> {
                    doc.data?.forEach { (k, v) -> put(k, v) }
                    put("id", doc.id)
                } else null
                cont.resume(result)
            }
            .addOnFailureListener { cont.resumeWithException(it) }
    }

    actual suspend fun setMerge(id: String, data: Map<String, Any?>) =
        suspendCancellableCoroutine { cont ->
            col.document(id).set(data, SetOptions.merge())
                .addOnSuccessListener { cont.resume(Unit) }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

    actual fun incrementValue(amount: Int): Any = FieldValue.increment(amount.toLong())

    actual suspend fun getWhere(field: String, value: Any): List<Map<String, Any?>> =
        suspendCancellableCoroutine { cont ->
            col.whereEqualTo(field, value).get()
                .addOnSuccessListener { snapshot ->
                    val docs = snapshot.documents.map { doc ->
                        buildMap<String, Any?> {
                            doc.data?.forEach { (k, v) -> put(k, v) }
                            put("id", doc.id)
                        }
                    }
                    cont.resume(docs)
                }
                .addOnFailureListener { cont.resumeWithException(it) }
        }

    actual fun deleteField(): Any = FieldValue.delete()
}