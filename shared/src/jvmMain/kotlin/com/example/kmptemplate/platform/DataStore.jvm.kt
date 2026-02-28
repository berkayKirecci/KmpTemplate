package com.example.kmptemplate.platform

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual class DataStore {
    actual fun preferences(): DataStore<Preferences> = createDataStore(
        producePath = {
            val file = File(System.getProperty("java.io.tmpdir"), dataStoreFileName)
            file.absolutePath
        }
    )
}