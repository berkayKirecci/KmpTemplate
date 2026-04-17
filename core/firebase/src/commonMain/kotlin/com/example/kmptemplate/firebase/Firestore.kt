package com.example.kmptemplate.firebase

import kotlinx.coroutines.flow.Flow

/**
 * Platform-agnostic Firestore collection wrapper.
 * Create one instance per collection and inject it wherever needed.
 *
 *   Android → com.google.firebase:firebase-firestore
 *   iOS     → cocoapods.FirebaseFirestoreInternal (FIRFirestore)
 */
expect class Firestore(collectionName: String) {

    /**
     * Real-time stream of every document as a raw key-value map.
     * The "id" key is always injected from doc.id — Firestore never stores it inside doc.data.
     */
    fun documents(): Flow<List<Map<String, Any?>>>

    /**
     * Create or overwrite a document by [id].
     * Use this when you already know the ID (e.g. after calling [newId]).
     */
    suspend fun set(id: String, data: Map<String, Any?>)

    /**
     * Add a brand-new document with an auto-generated ID and return that ID.
     * Internally: id = newId(), then set(id, data + ("id" to id)).
     * This is the primary way to create a new Firestore document.
     */
    suspend fun add(data: Map<String, Any?>): String

    /** Update only the given [fields] of an existing document. */
    suspend fun update(id: String, fields: Map<String, Any?>)

    /** Delete the document with [id]. */
    suspend fun delete(id: String)

    /**
     * Fetch a single document by [id] as a raw key-value map, or null if it does not exist.
     * Prefer this over [documents] when you already know the document ID.
     */
    suspend fun get(id: String): Map<String, Any?>?

    /**
     * Generate a new unique document ID **without** performing a write.
     * Useful when you need the ID before writing (e.g. to embed it in the data).
     */
    fun newId(): String

    /**
     * Create or merge a document by [id]. Fields not present in [data] are preserved.
     * Intended for partial / atomic updates (e.g. combined with [incrementValue]).
     */
    suspend fun setMerge(id: String, data: Map<String, Any?>)

    /**
     * Returns a platform-native sentinel value that tells Firestore to atomically
     * increment a numeric field by [amount] on the server side.
     * Pass it as a platform-specific value inside [set], [update], or [setMerge] data maps.
     */
    fun incrementValue(amount: Int): Any

    /**
     * One-shot server-side query: returns only documents where [field] == [value].
     * Cheaper than [documents] + client-side filter — Firestore bills only matched docs.
     */
    suspend fun getWhere(field: String, value: Any): List<Map<String, Any?>>

    /**
     * Returns a platform-native sentinel that tells Firestore to **delete** a field entirely
     * from the document on the next [update] or [set] call.
     * Prefer this over writing `null` — deleted fields don't appear in future reads,
     * reducing document size and downstream bandwidth.
     */
    fun deleteField(): Any
}

/**
 * Safely reads a Firestore boolean field that may arrive as:
 *   - [Boolean] on Android (Firebase Android SDK returns native Kotlin Boolean)
 *   - [Long] 0/1 on iOS if an older normalizeNSNumber path was used
 * Always prefer [Boolean] to avoid ambiguity with integer 0/1.
 */
fun Any?.asBool(default: Boolean): Boolean = when (this) {
    is Boolean -> this
    is Long -> this != 0L
    is Int -> this != 0
    else -> default
}
