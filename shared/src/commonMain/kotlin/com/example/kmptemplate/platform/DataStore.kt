package com.example.kmptemplate.platform

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

internal const val dataStoreFileName = "dice.preferences_pb"