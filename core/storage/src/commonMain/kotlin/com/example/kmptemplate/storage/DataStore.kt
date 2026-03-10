package com.example.kmptemplate.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

expect class DataStore {
    fun preferences(): DataStore<Preferences>
}

fun createDataStore(producePath: () -> String) = PreferenceDataStoreFactory.createWithPath(
    produceFile = { producePath().toPath() }
)

internal const val dataStoreFileName = "kmptemplate.preferences_pb"

