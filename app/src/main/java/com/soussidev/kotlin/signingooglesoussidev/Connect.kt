package com.soussidev.kotlin.signingooglesoussidev

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.ImageView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.OptionalPendingResult
import com.google.android.gms.common.api.ResultCallback

import java.io.InputStream

/**
 * Created by Soussi on 12/09/2017.
 */

class Connect(private val activity: Context, private val tag: String) {
    var mProgressDialog: ProgressDialog? = null


    init {
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                //  .requestProfile()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(activity)
                .enableAutoManage(activity as FragmentActivity /* FragmentActivity */  /* OnConnectionFailedListener */) { connectionResult -> Log.d(tag, "onConnectionFailed:" + connectionResult) }
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()


    }


    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(activity)
            mProgressDialog!!.setMessage("Loading")
            mProgressDialog!!.isIndeterminate = true
        }

        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.hide()
        }
    }

    // download Google Account profile image, to complete profile
    class LoadProfileImage(internal var downloadedImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val url = urls[0]
            var icon: Bitmap? = null
            try {
                val `in` = java.net.URL(url).openStream()
                icon = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message)
                e.printStackTrace()
            }

            return icon
        }

        override fun onPostExecute(result: Bitmap) {
            downloadedImage.setImageBitmap(result)
        }
    }

    companion object {
        lateinit var mGoogleApiClient: GoogleApiClient
    }


}
