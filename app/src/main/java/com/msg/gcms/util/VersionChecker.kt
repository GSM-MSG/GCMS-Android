package com.msg.gcms.util

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class VersionChecker(private val activity: Activity, private val afterLogic: Unit) : InstallStateUpdatedListener {

    private var appUpdateManager: AppUpdateManager = AppUpdateManagerFactory.create(activity)
    private val MY_REQUEST_CODE = 500


    private var currentType = AppUpdateType.FLEXIBLE

    init {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { updateInfo ->
            // Check if update is available
            if (updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) { // UPDATE IS AVAILABLE
                if (updateInfo.updatePriority() == 5) { // Priority: 5 (Immediate update flow)
                    if (updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        startUpdate(updateInfo, AppUpdateType.IMMEDIATE)
                    }
                } else if (updateInfo.updatePriority() == 4) { // Priority: 4
                    val clientVersionStalenessDays = updateInfo.clientVersionStalenessDays()
                    if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 5 && updateInfo.isUpdateTypeAllowed(
                            AppUpdateType.IMMEDIATE
                        )
                    ) {
                        // Trigger IMMEDIATE flow
                        startUpdate(updateInfo, AppUpdateType.IMMEDIATE)
                    } else if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 3 && updateInfo.isUpdateTypeAllowed(
                            AppUpdateType.FLEXIBLE
                        )
                    ) {
                        // Trigger FLEXIBLE flow
                        startUpdate(updateInfo, AppUpdateType.FLEXIBLE)
                    }
                } else if (updateInfo.updatePriority() == 3) { // Priority: 3
                    val clientVersionStalenessDays = updateInfo.clientVersionStalenessDays()
                    if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 30 && updateInfo.isUpdateTypeAllowed(
                            AppUpdateType.IMMEDIATE
                        )
                    ) {
                        // Trigger IMMEDIATE flow
                        startUpdate(updateInfo, AppUpdateType.IMMEDIATE)
                    } else if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 15 && updateInfo.isUpdateTypeAllowed(
                            AppUpdateType.FLEXIBLE
                        )
                    ) {
                        // Trigger FLEXIBLE flow
                        startUpdate(updateInfo, AppUpdateType.FLEXIBLE)
                    }
                } else if (updateInfo.updatePriority() == 2) { // Priority: 2
                    val clientVersionStalenessDays = updateInfo.clientVersionStalenessDays()
                    if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 90 && updateInfo.isUpdateTypeAllowed(
                            AppUpdateType.IMMEDIATE
                        )
                    ) {
                        // Trigger IMMEDIATE flow
                        startUpdate(updateInfo, AppUpdateType.IMMEDIATE)
                    } else if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 30 && updateInfo.isUpdateTypeAllowed(
                            AppUpdateType.FLEXIBLE
                        )
                    ) {
                        // Trigger FLEXIBLE flow
                        startUpdate(updateInfo, AppUpdateType.FLEXIBLE)
                    }
                } else if (updateInfo.updatePriority() == 1) { // Priority: 1
                    // Trigger FLEXIBLE flow
                    startUpdate(updateInfo, AppUpdateType.FLEXIBLE)
                } else { // Priority: 0
                    // Do not show in-app update
                }
            } else {
                afterLogic
            }
        }
        appUpdateManager.registerListener(this)
    }

    // the logic that update version
    private fun startUpdate(info: AppUpdateInfo, type: Int) {
        currentType = type
        appUpdateManager.startUpdateFlowForResult(info, type, activity, MY_REQUEST_CODE)
    }

    fun onResume() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (currentType == AppUpdateType.FLEXIBLE) {
                // If the update is downloaded but not installed, notify the user to complete the update.
                if (info.installStatus() == InstallStatus.DOWNLOADED)
                    flexibleUpdateDownloadCompleted(afterLogic = afterLogic)
            } else if (currentType == AppUpdateType.IMMEDIATE) {
                // for AppUpdateType.IMMEDIATE only, already executing updater
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    startUpdate(info, AppUpdateType.IMMEDIATE)
                }
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != AppCompatActivity.RESULT_OK) {
                // If the update is cancelled or fails, you can request to start the update again.
                Log.e("ERROR", "Update flow failed! Result code: $resultCode")
            }
        }
    }

    fun onDestroy() {
        appUpdateManager.unregisterListener(this)
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            flexibleUpdateDownloadCompleted(afterLogic = afterLogic)
        }
    }

    private fun flexibleUpdateDownloadCompleted(afterLogic:Unit) {
        Toast.makeText(activity, "업데이트가 완료되었습니다.", Toast.LENGTH_SHORT).show()
        appUpdateManager.completeUpdate()
        afterLogic
    }
}