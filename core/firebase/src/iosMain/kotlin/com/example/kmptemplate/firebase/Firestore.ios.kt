@file:OptIn(ExperimentalForeignApi::class)

package com.example.kmptemplate.firebase

import cocoapods.FirebaseFirestoreInternal.FIRDocumentSnapshot
import cocoapods.FirebaseFirestoreInternal.FIRFieldValue
import cocoapods.FirebaseFirestoreInternal.FIRFirestore
import cocoapods.FirebaseFirestoreInternal.FIRQuery
import cocoapods.FirebaseFirestoreInternal.FIRQuerySnapshot
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSError
import platform.Foundation.NSNumber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class Firestore actual constructor(collectionName: String) {

    private val col = FIRFirestore.firestore().collectionWithPath(collectionName)

    actual fun documents(): Flow<List<Map<String, Any?>>> = callbackFlow {
        val registration = col.addSnapshotListener { snapshot: FIRQuerySnapshot?, error: NSError? ->
            if (error != null) {
                close(Exception(error.localizedDescription))
                return@addSnapshotListener
            }
            val docs = snapshot?.documents?.mapNotNull { raw ->
                (raw as? FIRDocumentSnapshot)?.let { doc ->
                    @Suppress("UNCHECKED_CAST")
                    val rawData = doc.data() as? Map<String, Any?> ?: return@mapNotNull null
                    buildMap<String, Any?> {
                        // Normalize NSNumber → Long so callers use a uniform type on all platforms
                        rawData.forEach { (k, v) -> put(k, v.normalizeNSNumber()) }
                        put("id", doc.documentID)
                    }
                }
            } ?: emptyList()
            trySend(docs)
        }
        awaitClose { registration.remove() }
    }

    actual suspend fun set(id: String, data: Map<String, Any?>) =
        suspendCancellableCoroutine { cont ->
            @Suppress("UNCHECKED_CAST")
            col.documentWithPath(id).setData(data as Map<Any?, *>) { error: NSError? ->
                if (error != null) cont.resumeWithException(Exception(error.localizedDescription))
                else cont.resume(Unit)
            }
        }

    actual suspend fun setMerge(id: String, data: Map<String, Any?>) =
        suspendCancellableCoroutine { cont ->
            @Suppress("UNCHECKED_CAST")
            col.documentWithPath(id)
                .setData(data as Map<Any?, *>, merge = true) { error: NSError? ->
                    if (error != null) cont.resumeWithException(Exception(error.localizedDescription))
                    else cont.resume(Unit)
                }
        }

    actual suspend fun add(data: Map<String, Any?>): String {
        val id = newId()
        set(id, data + ("id" to id))
        return id
    }

    actual suspend fun update(id: String, fields: Map<String, Any?>) =
        suspendCancellableCoroutine { cont ->
            @Suppress("UNCHECKED_CAST")
            col.documentWithPath(id).updateData(fields as Map<Any?, *>) { error: NSError? ->
                if (error != null) cont.resumeWithException(Exception(error.localizedDescription))
                else cont.resume(Unit)
            }
        }

    actual suspend fun delete(id: String) =
        suspendCancellableCoroutine { cont ->
            col.documentWithPath(id).deleteDocumentWithCompletion { error: NSError? ->
                if (error != null) cont.resumeWithException(Exception(error.localizedDescription))
                else cont.resume(Unit)
            }
        }

    actual fun newId(): String = col.documentWithAutoID().documentID

    actual suspend fun get(id: String): Map<String, Any?>? = suspendCancellableCoroutine { cont ->
        col.documentWithPath(id)
            .getDocumentWithCompletion { doc: FIRDocumentSnapshot?, error: NSError? ->
                if (error != null) {
                    cont.resumeWithException(Exception(error.localizedDescription))
                    return@getDocumentWithCompletion
                }
                val result = doc?.takeIf { it.exists() }?.let {
                    @Suppress("UNCHECKED_CAST")
                    val rawData = it.data() as? Map<String, Any?> ?: return@let null
                    buildMap<String, Any?> {
                        rawData.forEach { (k, v) -> put(k, v.normalizeNSNumber()) }
                        put("id", it.documentID)
                    }
                }
                cont.resume(result)
            }
    }

    actual fun incrementValue(amount: Int): Any =
        FIRFieldValue.fieldValueForIntegerIncrement(amount.toLong())

    actual fun deleteField(): Any = FIRFieldValue.fieldValueForDelete()

    actual suspend fun getWhere(field: String, value: Any): List<Map<String, Any?>> =
        suspendCancellableCoroutine { cont ->
            @Suppress("UNCHECKED_CAST")
            val query: FIRQuery = col.queryWhereField(field, isEqualTo = value)
            query.getDocumentsWithCompletion { snapshot: FIRQuerySnapshot?, error: NSError? ->
                if (error != null) {
                    cont.resumeWithException(Exception(error.localizedDescription))
                    return@getDocumentsWithCompletion
                }
                val docs = snapshot?.documents?.mapNotNull { raw ->
                    (raw as? FIRDocumentSnapshot)?.let { doc ->
                        @Suppress("UNCHECKED_CAST")
                        val rawData = doc.data() as? Map<String, Any?> ?: return@mapNotNull null
                        buildMap<String, Any?> {
                            rawData.forEach { (k, v) -> put(k, v.normalizeNSNumber()) }
                            put("id", doc.documentID)
                        }
                    }
                } ?: emptyList()
                cont.resume(docs)
            }
        }

    private fun Any?.normalizeNSNumber(): Any? {
        if (this !is NSNumber) return this
        return when (objCType()?.toKString()) {
            "B", "c" -> boolValue
            else -> longValue
        }
    }
}
