package uz.elmurod.rickandmortyapi.checkinternet

import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

import androidx.core.app.ActivityCompat.startActivityForResult
import dagger.hilt.android.AndroidEntryPoint
import uz.elmurod.rickandmortyapi.R
import uz.elmurod.rickandmortyapi.checkinternet.Connect.Companion.isConnectToInternet

import java.lang.NullPointerException

@AndroidEntryPoint
class NetworkChangeListener(private val activity: Activity, context: Context) :
    BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (!isConnectToInternet(context)) {
                // true
                alertDialog?.dismiss()
            } else {
                // false
                alertDialog.show()
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    private val alertDialog by lazy(LazyThreadSafetyMode.NONE) {
        AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(R.layout.no_internet_dialog)
            .setPositiveButton(
                activity.getString(R.string.yes)
            ) { _, _ ->
                startActivityForResult(
                    activity,
                    Intent(Settings.ACTION_WIFI_SETTINGS),
                    0, null
                )
            }
            .setNegativeButton(
                activity.getString(R.string.no)
            ) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

}