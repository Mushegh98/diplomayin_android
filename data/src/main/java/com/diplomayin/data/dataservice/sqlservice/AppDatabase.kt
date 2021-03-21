package com.diplomayin.data.dataservice.sqlservice

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diplomayin.data.dataservice.sqlservice.dao.RecognitionDao
import com.diplomayin.entities.roommodel.RecognitionEntity

@Database(
    entities = [RecognitionEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
 abstract fun recognitionDao() : RecognitionDao
}