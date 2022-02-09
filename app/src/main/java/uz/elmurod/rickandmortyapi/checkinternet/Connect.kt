package uz.elmurod.rickandmortyapi.checkinternet

import android.content.Context
import android.net.ConnectivityManager

class Connect {
    companion object {
        fun isConnectToInternet(context: Context): Boolean {
            val connectManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var networkInfo = connectManager.activeNetworkInfo

            return (networkInfo == null || !networkInfo.isConnected || !networkInfo.isAvailable)
        }

    }
}