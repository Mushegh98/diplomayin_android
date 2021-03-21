package com.diplomayin.recognition.base.utils.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun Activity.checkPermissionsGranted(permissions: ArrayList<String>): Boolean {
    permissions.forEach {
        if ((PermissionChecker.checkCallingOrSelfPermission(
                        this,
                        it
                ) != PermissionChecker.PERMISSION_GRANTED)
        )
            return false
    }
    return true
}

fun Activity.hasPermission(permission: String): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return PermissionChecker.checkSelfPermission(
                this,
                permission
        ) == PermissionChecker.PERMISSION_GRANTED
    }
    return true
}

fun Activity.requestForPermissions(permissions: ArrayList<String>, requestCode: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.requestPermissions(
                permissions.toTypedArray(),
                requestCode
        )
    }
}

fun Context.hasNetwork(): Boolean {
    var isConnected = false
    val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        isConnected = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            @Suppress("DEPRECATION")
            connectivityManager.activeNetworkInfo?.run {
                isConnected = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }
    return isConnected
}

fun Context.makeToastLong(text: String) {
    if (text.isNotEmpty()) Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.makeToastShort(text: String) {
    if (text.isNotEmpty()) Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.closeKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(findViewById<View>(android.R.id.content).windowToken, 0)
}

/**
 * recyclerView.setDivider(R.drawable.recycler_view_divider)
 */
fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(
            this.context,
            DividerItemDecoration.VERTICAL
    )
    val drawable = ContextCompat.getDrawable(
            this.context,
            drawableRes
    )
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}




fun String.changeFormate(): String {
    return try {
        changeFormat(this)
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}


private fun changeFormat(oldDate: String): String {
    val newFormat = "dd.MMMM.yyyy' ' HH:mm:ss"
    val myUsedPatterns: MutableList<SimpleDateFormat> = java.util.ArrayList()
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        myUsedPatterns.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault()))
        myUsedPatterns.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SX", Locale.getDefault()))
        myUsedPatterns.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSX", Locale.getDefault()))
        myUsedPatterns.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault()))
    } else {
        myUsedPatterns.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()))
        myUsedPatterns.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.getDefault()))
        myUsedPatterns.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS", Locale.getDefault()))
        myUsedPatterns.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault()))
    }
    var d: Date? = null
    var simpleDateFormat: SimpleDateFormat? = null
    for (myPattern in myUsedPatterns) {
        try {
            d = myPattern.parse(oldDate)
            simpleDateFormat = myPattern
        } catch (e: ParseException) {
            // Loop
        }
    }
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
        val cal = Calendar.getInstance()
        cal.time = d
        cal.add(Calendar.HOUR_OF_DAY, 3)
        d = cal.time
    }
    simpleDateFormat!!.applyPattern(newFormat)
    return simpleDateFormat.format(d)
}

fun FragmentManager.addFragment(id : Int, fragment: Fragment, addToBackStack : Boolean = true){
    if(addToBackStack){
        this.beginTransaction().add(id,fragment,fragment::class.java.simpleName).addToBackStack("").commit()
    }else{
        this.beginTransaction().add(id,fragment,fragment::class.java.simpleName).commit()
    }
}

fun FragmentManager.replaceFragment(id : Int, fragment: Fragment, addToBackStack : Boolean = true){
    if(addToBackStack){
        this.beginTransaction().replace(id,fragment,fragment::class.java.simpleName).addToBackStack("").commit()
    }else{
        this.beginTransaction().replace(id,fragment,fragment::class.java.simpleName).commit()
    }
}
