package com.diplomayin.data.dataservice.appservice.preferenceservice

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.util.HashSet

class PreferenceServiceImpl(private val context: Context) : PreferenceService {

    val sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE)

    inline fun <reified T> get(key: String): T? {
        val value = sharedPreferences.getString(key, null)
        return value?.let {
            val jsonAdapter: JsonAdapter<T> =
                Moshi.Builder().build().adapter(T::class.java)
            jsonAdapter.fromJson(it)
        }
    }

    inline fun <reified T> put(data: T, key: String): Boolean {
        val jsonAdapter: JsonAdapter<T> =
            Moshi.Builder().build().adapter(T::class.java)
        val jsonString = jsonAdapter.toJson(data)
        return sharedPreferences.edit().putString(key, jsonString).commit()
    }

    override fun getStringData(name: String): String? {
        return sharedPreferences?.getString(name + "str", "")
    }


    override fun setBooleanData(name: String, bool: Boolean): Boolean {
        val editor = sharedPreferences?.edit()
        editor?.remove(name + "bool")
        editor?.apply()
        editor?.putBoolean(name + "bool", bool)
        editor?.apply()
        return bool
    }


    override fun getBooleanData(name: String): Boolean? {
        return sharedPreferences?.getBoolean(name + "bool", false)
    }


    override fun setLongData(name: String, id: Long): Long {
        val editor = sharedPreferences?.edit()
        editor?.remove(name + "long")
        editor?.apply()
        editor?.putLong(name + "long", id)
        editor?.apply()
        return id
    }

    override fun getLongData(name: String): Long? {
        return sharedPreferences?.getLong(name + "long", 0)
    }

    override fun setStringData(name: String, `val`: String?): String {
        val editor = sharedPreferences?.edit()
        editor?.remove(name + "str")
        editor?.apply()
        editor?.putString(name + "str", `val`)
        editor?.apply()
        return name
    }

    override fun clear() {
        sharedPreferences!!.edit().clear().apply()
    }

    override fun setIntData(name: String, id: Int): Int {
        val editor = sharedPreferences?.edit()
        editor?.remove(name + "int")
        editor?.apply()
        editor?.putInt(name + "int", id)
        editor?.apply()
        return id
    }

    override fun getIntData(name: String): Int? {
        return sharedPreferences?.getInt(name + "int", 0)
    }


    override fun setStringSet(name: String, `val`: Set<String?>?) {
        val editor = sharedPreferences?.edit()
        editor?.remove(name + "strset")
        editor?.apply()
        editor?.putStringSet(name + "strset", `val`)
        editor?.apply()
    }

    override fun getStringSet(name: String): HashSet<String>? {
        return sharedPreferences?.getStringSet(name + "strset", HashSet())?.toHashSet()
    }

    override fun deleteStringData(name : String) {
        val editor = sharedPreferences?.edit()
        editor?.remove(name + "str")
        editor?.apply()
    }

    override fun setToken(token : String){
        val editor = sharedPreferences?.edit()
        editor?.remove("token")
        editor?.apply()
        editor?.putString("token",token)
        editor?.apply()
    }

    override fun getToken() : String?{
        return sharedPreferences?.getString("token","")
    }
}