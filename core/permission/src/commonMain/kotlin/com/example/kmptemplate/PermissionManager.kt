package com.example.kmptemplate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class PermissionManager(
    val permission: AppPermission,
    private val controller: PermissionsController,
    private val coroutineScope: CoroutineScope
) {
    var status by mutableStateOf(PermissionState.NotDetermined)
        internal set

    val isGranted: Boolean
        get() = status == PermissionState.Granted

    val isDenied: Boolean
        get() = status == PermissionState.Denied || status == PermissionState.DeniedAlways

    fun launchPermissionRequest() = coroutineScope.launch {
        try {
            controller.providePermission(permission.permission)
            status = PermissionState.Granted
        } catch (_: DeniedAlwaysException) {
            status = PermissionState.DeniedAlways
        } catch (_: DeniedException) {
            status = PermissionState.Denied
        }
    }

    fun openSettings() {
        controller.openAppSettings()
    }
}

@Composable
fun rememberPermissionManager(permission: AppPermission): PermissionManager {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    BindEffect(controller)

    val permissionManager = remember(permission, controller) {
        PermissionManager(permission, controller, coroutineScope)
    }

    LaunchedEffect(permissionManager) {
        permissionManager.status = controller.getPermissionState(permission.permission)
    }

    return permissionManager
}

enum class AppPermission(val permission: Permission) {
    NOTIFICATION(Permission.REMOTE_NOTIFICATION)
}