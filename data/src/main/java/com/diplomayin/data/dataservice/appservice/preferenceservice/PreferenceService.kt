package com.diplomayin.data.dataservice.appservice.preferenceservice

import java.util.HashSet

interface PreferenceService {
    fun getStringData(name: String): String?
    fun setBooleanData(name: String, bool: Boolean): Boolean
    fun getBooleanData(name: String): Boolean?
    fun setLongData(name: String, id: Long): Long
    fun getLongData(name: String): Long?
    fun setStringData(name: String, `val`: String?): String
    fun clear()
    fun setIntData(name: String, id: Int): Int
    fun getIntData(name: String): Int?
    fun setStringSet(name: String, `val`: Set<String?>?)
    fun getStringSet(name: String): HashSet<String>?
    fun deleteStringData(name : String)
}