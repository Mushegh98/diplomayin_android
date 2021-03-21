package com.diplomayin.data.dataservice.sqlservice.dao

import androidx.room.*
import com.diplomayin.entities.roommodel.RecognitionEntity

@Dao
interface RecognitionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecognition(recognitionEntity: RecognitionEntity)

    @Delete
    suspend fun deleteRecognition(recognitionEntity: RecognitionEntity)

    @Query("DELETE FROM recognition_table WHERE date = :date")
    suspend fun deleteRecognition(date : String)

    @Query("UPDATE recognition_table SET in_vehicle = :inVehice WHERE date = :date ")
    suspend fun updateInVehicle(inVehice : Long?, date: String)

    @Query("UPDATE recognition_table SET on_bicycle = :on_bicycle WHERE date = :date ")
    suspend fun updateOnBicycle(on_bicycle : Long?, date: String)

    @Query("UPDATE recognition_table SET on_foot = :on_foot WHERE date = :date ")
    suspend fun updateOnFoot(on_foot : Long?, date: String)

    @Query("UPDATE recognition_table SET running = :running WHERE date = :date ")
    suspend fun updateRunning(running : Long?, date: String)

    @Query("UPDATE recognition_table SET still = :still WHERE date = :date ")
    suspend fun updateStill(still : Long?, date: String)

    @Query("UPDATE recognition_table SET tilting = :tilting WHERE date = :date ")
    suspend fun updateTilting(tilting : Long?, date: String)

    @Query("UPDATE recognition_table SET unknown = :unknown WHERE date = :date ")
    suspend fun updateUnknown(unknown : Long?, date: String)

    @Query("UPDATE recognition_table SET walking = :walking WHERE date = :date ")
    suspend fun updateWalking(walking : Long?, date: String)

    @Update
    suspend fun updateRecognition(recognitionEntity: RecognitionEntity)

    @Query("SELECT * FROM recognition_table WHERE date = :date")
    suspend fun getRecognition(date : String) : RecognitionEntity?

    @Query("SELECT * FROM recognition_table")
    suspend fun getRecognition() : List<RecognitionEntity>?

    @Query("SELECT on_bicycle FROM recognition_table WHERE date = :date")
    suspend fun getOnBicycle(date : String) : Long?

    @Query("SELECT in_vehicle FROM recognition_table WHERE date = :date")
    suspend fun getInVehicle(date : String) : Long?

    @Query("SELECT on_foot FROM recognition_table WHERE date = :date")
    suspend fun getOnFoot(date : String) : Long?

    @Query("SELECT running FROM recognition_table WHERE date = :date")
    suspend fun getRunning(date : String) : Long?

    @Query("SELECT still FROM recognition_table WHERE date = :date")
    suspend fun getStill(date : String) : Long?

    @Query("SELECT tilting FROM recognition_table WHERE date = :date")
    suspend fun getTilting(date : String) : Long?

    @Query("SELECT unknown FROM recognition_table WHERE date = :date")
    suspend fun getUnknown(date : String) : Long?

    @Query("SELECT walking FROM recognition_table WHERE date = :date")
    suspend fun getWalking(date : String) : Long?
}