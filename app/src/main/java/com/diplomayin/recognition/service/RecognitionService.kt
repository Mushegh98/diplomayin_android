package com.diplomayin.recognition.service

import android.app.*
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.diplomayin.data.dataservice.RetrofitService
import com.diplomayin.data.dataservice.appservice.preferenceservice.PreferenceService
import com.diplomayin.data.dataservice.appservice.preferenceservice.PreferenceServiceImpl
import com.diplomayin.data.dataservice.sqlservice.dao.RecognitionDao
import com.diplomayin.data.utils.Constants.Companion.BASE_URL
import com.diplomayin.data.utils.Constants.Companion.DETECTION_INTERVAL_IN_MILLISECONDS
import com.diplomayin.entities.roommodel.RecognitionEntity
import com.google.android.gms.location.ActivityRecognitionClient
import com.google.android.gms.location.DetectedActivity
import com.yashovardhan99.timeit.Stopwatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class RecognitionService : Service() {


private val TICK_INTERVAL: Long = 1000
val STOP_SERVICE_ACTION = "stop service"
var SENSOR_ACTIVITY_ID = 4521485
var handler = Handler(Looper.getMainLooper())
private var clientApi: RetrofitService? = null
private val preferencesService : PreferenceService by inject<PreferenceServiceImpl>()
private var prevState = -5
private val recognitionDao: RecognitionDao by inject<RecognitionDao>()

private var mActivityRecognitionClient: ActivityRecognitionClient? = null

private val stopwatch = Stopwatch()
private val job = Job()


//custom coutotine scope for asynch operations
private val ioScope = CoroutineScope(Dispatchers.IO + job)


override fun onCreate() {
    super.onCreate()
    stopwatch.clockDelay = TICK_INTERVAL
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        clientApi = retrofit.create(RetrofitService::class.java)
    startForeground(SENSOR_ACTIVITY_ID, getNotification())
}

override fun onBind(intent: Intent?): IBinder? {
    return null
}

override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    if (intent != null && intent.action != null && intent.action == STOP_SERVICE_ACTION) {
        stopForeground(true)
        stopSelfResult(startId)
        removeActivityUpdates()
        handler.removeCallbacksAndMessages(null)
    } else if (intent == null || !handleActivityEventIfPresent(intent)) {
        mActivityRecognitionClient = ActivityRecognitionClient(this)
        requestActivityUpdates()
    }
    return Service.START_STICKY
}


private fun handleActivityEventIfPresent(intent: Intent): Boolean {
    if (intent.extras == null) {
        return false
    }

    val state = intent.extras!!.getString("location:mock_activity_type") ?: return true
    var type = 3

    handler.postDelayed(object : Runnable {
        override fun run() {

            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            if(preferencesService.getStringData(getCurrentDate()) == null){
                if(hour == 0 && minute == 0){
                    // stop watch and start watch in 0
                    when(state){
                        "IN_VEHICLE" -> {
                            Toast.makeText(baseContext, "IN_VEHICLE", Toast.LENGTH_SHORT).show()
                            type = DetectedActivity.IN_VEHICLE
                            handlePrevStateInVehicle(stopwatch.elapsedTime)
                        }
                        "ON_BICYCLE" -> {
                            Toast.makeText(baseContext, "ON_BICYCLE", Toast.LENGTH_SHORT).show()
                            type = DetectedActivity.ON_BICYCLE
                            handlePrevStateOnBicycle(stopwatch.elapsedTime)
                        }
                        "ON_FOOT" -> {
                            Toast.makeText(baseContext, "ON_FOOT", Toast.LENGTH_SHORT).show()
                            type = DetectedActivity.ON_FOOT
                            handlePrevStateOnFoot(stopwatch.elapsedTime)
                        }
                        "STILL" -> {
                            Toast.makeText(baseContext, "STILL", Toast.LENGTH_SHORT).show()
                            type = DetectedActivity.STILL
                            handlePrevStateStill(stopwatch.elapsedTime)
                        }
                        "UNKNOWN" -> {
                            Toast.makeText(baseContext, "UNKNOWN", Toast.LENGTH_SHORT).show()
                            type = DetectedActivity.UNKNOWN
                            handlePrevStateUnknown(stopwatch.elapsedTime)
                        }
                        "TILTING" -> {
                            Toast.makeText(baseContext, "TILTING", Toast.LENGTH_SHORT).show()
                            type = DetectedActivity.TILTING
                            handlePrevStateTilting(stopwatch.elapsedTime)
                        }
                        "WALKING" -> {
                            Toast.makeText(baseContext, "WALKING", Toast.LENGTH_SHORT).show()
                            type = DetectedActivity.WALKING
                            handlePrevStateWalking(stopwatch.elapsedTime)
                        }
                        "RUNNING" -> {
                            Toast.makeText(baseContext, "RUNNING", Toast.LENGTH_SHORT).show()
                            type = DetectedActivity.RUNNING
                            handlePrevStateRunning(stopwatch.elapsedTime)
                        }
                    }

                    if (stopwatch.isStarted) {
                        stopwatch.stop()
                    }
                    prevState = -5
                    stopwatch.start()

                    //upload

                    ioScope.launch {
                        val listRecognition = recognitionDao.getRecognition()
                        listRecognition?.forEach {recognition->
                            clientApi?.addRecognition(HashMap<String,String>().apply {
                                preferencesService.getToken()?.let { it1 -> put("Authorization", it1) }
                            },recognition)
                            // delete db
                            recognitionDao.deleteRecognition(recognition)
                            preferencesService.setStringData(recognition.date,recognition.date)
                        }

                    }
                }
            }


            handler.postDelayed(this, 1000)
        }

    }, 0)


    when(state){
        "IN_VEHICLE" -> {
            Toast.makeText(baseContext, "IN_VEHICLE", Toast.LENGTH_SHORT).show()
            type = DetectedActivity.IN_VEHICLE
        }
        "ON_BICYCLE" -> {
            Toast.makeText(baseContext, "ON_BICYCLE", Toast.LENGTH_SHORT).show()
            type = DetectedActivity.ON_BICYCLE
        }
        "ON_FOOT" -> {
            Toast.makeText(baseContext, "ON_FOOT", Toast.LENGTH_SHORT).show()
            type = DetectedActivity.ON_FOOT
        }
        "STILL" -> {
            Toast.makeText(baseContext, "STILL", Toast.LENGTH_SHORT).show()
            type = DetectedActivity.STILL
        }
        "UNKNOWN" -> {
            Toast.makeText(baseContext, "UNKNOWN", Toast.LENGTH_SHORT).show()
            type = DetectedActivity.UNKNOWN
        }
        "TILTING" -> {
            Toast.makeText(baseContext, "TILTING", Toast.LENGTH_SHORT).show()
            type = DetectedActivity.TILTING
        }
        "WALKING" -> {
            Toast.makeText(baseContext, "WALKING", Toast.LENGTH_SHORT).show()
            type = DetectedActivity.WALKING
        }
        "RUNNING" -> {
            Toast.makeText(baseContext, "RUNNING", Toast.LENGTH_SHORT).show()
            type = DetectedActivity.RUNNING
        }

    }

    when (type) {
        DetectedActivity.IN_VEHICLE -> {
            if (prevState != DetectedActivity.IN_VEHICLE) {
                stopwatch.apply {
                    var prevTime = getParsedMillis(elapsedTime)
                    //TODO Upload data for prevState
                    when (prevState) {
                        DetectedActivity.ON_BICYCLE -> {
                            handlePrevStateOnBicycle(elapsedTime)
                        }
                        DetectedActivity.ON_FOOT -> {
                            handlePrevStateOnFoot(elapsedTime)
                        }
                        DetectedActivity.STILL -> {
                            handlePrevStateStill(elapsedTime)
                        }
                        DetectedActivity.UNKNOWN -> {
                            handlePrevStateUnknown(elapsedTime)
                        }
                        DetectedActivity.TILTING -> {
                            handlePrevStateTilting(elapsedTime)
                        }
                        DetectedActivity.WALKING -> {
                            handlePrevStateWalking(elapsedTime)
                        }
                        DetectedActivity.RUNNING -> {
                            handlePrevStateRunning(elapsedTime)
                        }
                    }
                }
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                prevState = DetectedActivity.IN_VEHICLE
                stopwatch.start()
            }
        }
        DetectedActivity.ON_BICYCLE -> {
            if (prevState != DetectedActivity.ON_BICYCLE) {
                stopwatch.apply {
                    var prevTime = getParsedMillis(elapsedTime)
                    //TODO Upload data for prevState
                    when (prevState) {
                        DetectedActivity.IN_VEHICLE -> {
                            handlePrevStateInVehicle(elapsedTime)
                        }
                        DetectedActivity.ON_FOOT -> {
                            handlePrevStateOnFoot(elapsedTime)
                        }
                        DetectedActivity.STILL -> {
                            handlePrevStateStill(elapsedTime)
                        }
                        DetectedActivity.UNKNOWN -> {
                            handlePrevStateUnknown(elapsedTime)
                        }
                        DetectedActivity.TILTING -> {
                            handlePrevStateTilting(elapsedTime)
                        }
                        DetectedActivity.WALKING -> {
                            handlePrevStateWalking(elapsedTime)
                        }
                        DetectedActivity.RUNNING -> {
                            handlePrevStateRunning(elapsedTime)
                        }
                    }
                }
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                prevState = DetectedActivity.ON_BICYCLE
                stopwatch.start()
            }
        }
        DetectedActivity.ON_FOOT -> {
            if (prevState != DetectedActivity.ON_FOOT) {
                stopwatch.apply {
                    var prevTime = getParsedMillis(elapsedTime)
                    //TODO Upload data for prevState
                    when (prevState) {
                        DetectedActivity.ON_BICYCLE -> {
                            handlePrevStateOnBicycle(elapsedTime)
                        }
                        DetectedActivity.IN_VEHICLE -> {
                            handlePrevStateInVehicle(elapsedTime)
                        }
                        DetectedActivity.STILL -> {
                            handlePrevStateStill(elapsedTime)
                        }
                        DetectedActivity.UNKNOWN -> {
                            handlePrevStateUnknown(elapsedTime)
                        }
                        DetectedActivity.TILTING -> {
                            handlePrevStateTilting(elapsedTime)
                        }
                        DetectedActivity.WALKING -> {
                            handlePrevStateWalking(elapsedTime)
                        }
                        DetectedActivity.RUNNING -> {
                            handlePrevStateRunning(elapsedTime)
                        }
                    }
                }
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                prevState = DetectedActivity.ON_FOOT
                stopwatch.start()
            }
        }
        DetectedActivity.STILL -> {
            if (prevState != DetectedActivity.STILL) {
                stopwatch.apply {
                    var prevTime = getParsedMillis(elapsedTime)
                    //TODO Upload data for prevState
                    when (prevState) {
                        DetectedActivity.ON_BICYCLE -> {
                            handlePrevStateOnBicycle(elapsedTime)
                        }
                        DetectedActivity.ON_FOOT -> {
                            handlePrevStateOnFoot(elapsedTime)
                        }
                        DetectedActivity.IN_VEHICLE -> {
                            handlePrevStateInVehicle(elapsedTime)
                        }
                        DetectedActivity.UNKNOWN -> {
                            handlePrevStateUnknown(elapsedTime)
                        }
                        DetectedActivity.TILTING -> {
                            handlePrevStateTilting(elapsedTime)
                        }
                        DetectedActivity.WALKING -> {
                            handlePrevStateWalking(elapsedTime)
                        }
                        DetectedActivity.RUNNING -> {
                            handlePrevStateRunning(elapsedTime)
                        }
                    }
                }
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                prevState = DetectedActivity.STILL
                stopwatch.start()
            }
        }
        DetectedActivity.UNKNOWN -> {
            if (prevState != DetectedActivity.UNKNOWN) {
                stopwatch.apply {
                    var prevTime = getParsedMillis(elapsedTime)
                    //TODO Upload data for prevState
                    when (prevState) {
                        DetectedActivity.ON_BICYCLE -> {
                            handlePrevStateOnBicycle(elapsedTime)
                        }
                        DetectedActivity.ON_FOOT -> {
                            handlePrevStateOnFoot(elapsedTime)
                        }
                        DetectedActivity.STILL -> {
                            handlePrevStateStill(elapsedTime)
                        }
                        DetectedActivity.IN_VEHICLE -> {
                            handlePrevStateInVehicle(elapsedTime)
                        }
                        DetectedActivity.TILTING -> {
                            handlePrevStateTilting(elapsedTime)
                        }
                        DetectedActivity.WALKING -> {
                            handlePrevStateWalking(elapsedTime)
                        }
                        DetectedActivity.RUNNING -> {
                            handlePrevStateRunning(elapsedTime)
                        }
                    }
                }
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                prevState = DetectedActivity.UNKNOWN
                stopwatch.start()
            }
        }
        DetectedActivity.TILTING -> {
            if (prevState != DetectedActivity.TILTING) {
                stopwatch.apply {
                    var prevTime = getParsedMillis(elapsedTime)
                    //TODO Upload data for prevState
                    when (prevState) {
                        DetectedActivity.ON_BICYCLE -> {
                            handlePrevStateOnBicycle(elapsedTime)
                        }
                        DetectedActivity.ON_FOOT -> {
                            handlePrevStateOnFoot(elapsedTime)
                        }
                        DetectedActivity.STILL -> {
                            handlePrevStateStill(elapsedTime)
                        }
                        DetectedActivity.UNKNOWN -> {
                            handlePrevStateUnknown(elapsedTime)
                        }
                        DetectedActivity.IN_VEHICLE -> {
                            handlePrevStateInVehicle(elapsedTime)
                        }
                        DetectedActivity.WALKING -> {
                            handlePrevStateWalking(elapsedTime)
                        }
                        DetectedActivity.RUNNING -> {
                            handlePrevStateRunning(elapsedTime)
                        }
                    }
                }
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                prevState = DetectedActivity.TILTING
                stopwatch.start()
            }
        }
        DetectedActivity.WALKING -> {
            if (prevState != DetectedActivity.WALKING) {
                stopwatch.apply {
                    var prevTime = getParsedMillis(elapsedTime)
                    //TODO Upload data for prevState
                    when (prevState) {
                        DetectedActivity.ON_BICYCLE -> {
                            handlePrevStateOnBicycle(elapsedTime)
                        }
                        DetectedActivity.ON_FOOT -> {
                            handlePrevStateOnFoot(elapsedTime)
                        }
                        DetectedActivity.STILL -> {
                            handlePrevStateStill(elapsedTime)
                        }
                        DetectedActivity.UNKNOWN -> {
                            handlePrevStateUnknown(elapsedTime)
                        }
                        DetectedActivity.TILTING -> {
                            handlePrevStateTilting(elapsedTime)
                        }
                        DetectedActivity.IN_VEHICLE -> {
                            handlePrevStateInVehicle(elapsedTime)
                        }
                        DetectedActivity.RUNNING -> {
                            handlePrevStateRunning(elapsedTime)
                        }
                    }
                }
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                prevState = DetectedActivity.WALKING
                stopwatch.start()
            }
        }
        DetectedActivity.RUNNING -> {
            if (prevState != DetectedActivity.RUNNING) {
                stopwatch.apply {
                    var prevTime = getParsedMillis(elapsedTime)
                    //TODO Upload data for prevState
                    when (prevState) {
                        DetectedActivity.ON_BICYCLE -> {
                            handlePrevStateOnBicycle(elapsedTime)
                        }
                        DetectedActivity.ON_FOOT -> {
                            handlePrevStateOnFoot(elapsedTime)
                        }
                        DetectedActivity.STILL -> {
                            handlePrevStateStill(elapsedTime)
                        }
                        DetectedActivity.UNKNOWN -> {
                            handlePrevStateUnknown(elapsedTime)
                        }
                        DetectedActivity.TILTING -> {
                            handlePrevStateTilting(elapsedTime)
                        }
                        DetectedActivity.WALKING -> {
                            handlePrevStateWalking(elapsedTime)
                        }
                        DetectedActivity.IN_VEHICLE -> {
                            handlePrevStateInVehicle(elapsedTime)
                        }
                    }
                }
                if (stopwatch.isStarted) {
                    stopwatch.stop()
                }
                prevState = DetectedActivity.RUNNING
                stopwatch.start()
            }
        }
    }

    return true
}


fun requestActivityUpdates() {
    val task = mActivityRecognitionClient!!.requestActivityUpdates(
            DETECTION_INTERVAL_IN_MILLISECONDS,
            getActivityDetectionPendingIntent()
    )
    task.addOnSuccessListener { }
    task.addOnFailureListener { }
}

/**
 * Removes activity recognition updates using
 * [ActivityRecognitionClient.removeActivityUpdates]. Registers success and
 * failure callbacks.
 */
fun removeActivityUpdates() {
    val task =
            mActivityRecognitionClient!!.removeActivityUpdates(getActivityDetectionPendingIntent())
    task.addOnSuccessListener { }
    task.addOnFailureListener { }
}

/**
 * Gets a PendingIntent to be sent for each activity detection.
 */
private fun getActivityDetectionPendingIntent(): PendingIntent? {
    val intent = Intent(this, RecognitionService::class.java)

    // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
    // requestActivityUpdates() and removeActivityUpdates().
    return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}

private fun getNotification(): Notification? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val nm = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(
                NotificationChannel(
                        "TweegoNotChannelId",
                        "TweegoChannelName",
                        NotificationManager.IMPORTANCE_DEFAULT
                )
        )
        val notificationBuilder = NotificationCompat.Builder(this, "TweegoNotChannelId")
        notificationBuilder.setOngoing(true)
                .setPriority(NotificationManager.IMPORTANCE_NONE)
                .build()
    } else {
        Notification()
    }
}


fun getParsedMillis(miliSecond: Long): String {
    val seconds = (miliSecond / 1000) % 60
    val minutes = (miliSecond / (1000 * 60) % 60)
    val hours = (miliSecond / (1000 * 60 * 60))
    val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    return time
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val currentDate = sdf.format(Date())
    return currentDate
}

fun handlePrevStateOnFoot(elapsedTime: Long){
    ioScope.launch {
        val recognitionEntity: RecognitionEntity? =
                recognitionDao.getRecognition(getCurrentDate())
        if (recognitionEntity == null) {
            recognitionDao.insertRecognition(
                    RecognitionEntity(
                            getCurrentDate(),
                            on_foot = elapsedTime,
                    )
            )
        } else {
            val onFootTime: Long? =
                    recognitionDao.getOnFoot(getCurrentDate())
            if (onFootTime != null) {
                recognitionDao.updateOnFoot(
                        on_foot = elapsedTime + onFootTime,
                        getCurrentDate()
                )
            }
        }
    }
}

fun handlePrevStateOnBicycle(elapsedTime: Long){
    ioScope.launch {
        val recognitionEntity: RecognitionEntity? =
                recognitionDao.getRecognition(getCurrentDate())
        if (recognitionEntity == null) {
            recognitionDao.insertRecognition(
                    RecognitionEntity(
                            getCurrentDate(),
                            on_bicycle = elapsedTime,
                    )
            )
        } else {
            val onBicycleTime: Long? =
                    recognitionDao.getOnBicycle(getCurrentDate())
            if (onBicycleTime != null) {
                recognitionDao.updateOnBicycle(
                        on_bicycle = elapsedTime + onBicycleTime,
                        getCurrentDate()
                )
            }
        }
    }
}

fun handlePrevStateStill(elapsedTime: Long){
    ioScope.launch {
        val recognitionEntity: RecognitionEntity? =
                recognitionDao.getRecognition(getCurrentDate())
        if (recognitionEntity == null) {
            recognitionDao.insertRecognition(
                    RecognitionEntity(
                            getCurrentDate(),
                            still = elapsedTime,
                    )
            )
        } else {
            val onStillTime: Long? =
                    recognitionDao.getStill(getCurrentDate())
            if (onStillTime != null) {
                recognitionDao.updateStill(
                        still = elapsedTime + onStillTime,
                        getCurrentDate()
                )
            }
        }
    }
}

fun handlePrevStateUnknown(elapsedTime: Long){
    ioScope.launch {
        val recognitionEntity: RecognitionEntity? =
                recognitionDao.getRecognition(getCurrentDate())
        if (recognitionEntity == null) {
            recognitionDao.insertRecognition(
                    RecognitionEntity(
                            getCurrentDate(),
                            unknown = elapsedTime,
                    )
            )
        } else {
            val unknownTime: Long? =
                    recognitionDao.getUnknown(getCurrentDate())
            if (unknownTime != null) {
                recognitionDao.updateUnknown(
                        unknown = elapsedTime + unknownTime,
                        getCurrentDate()
                )
            }
        }
    }
}

fun handlePrevStateTilting(elapsedTime: Long){
    ioScope.launch {
        val recognitionEntity: RecognitionEntity? =
                recognitionDao.getRecognition(getCurrentDate())
        if (recognitionEntity == null) {
            recognitionDao.insertRecognition(
                    RecognitionEntity(
                            getCurrentDate(),
                            tilting = elapsedTime,
                    )
            )
        } else {
            val tiltingTime: Long? =
                    recognitionDao.getTilting(getCurrentDate())
            if (tiltingTime != null) {
                recognitionDao.updateTilting(
                        tilting = elapsedTime + tiltingTime,
                        getCurrentDate()
                )
            }
        }
    }
}

fun handlePrevStateWalking(elapsedTime: Long){
    ioScope.launch {
        val recognitionEntity: RecognitionEntity? =
                recognitionDao.getRecognition(getCurrentDate())
        if (recognitionEntity == null) {
            recognitionDao.insertRecognition(
                    RecognitionEntity(
                            getCurrentDate(),
                            walking = elapsedTime,
                    )
            )
        } else {
            val walkingTime: Long? =
                    recognitionDao.getWalking(getCurrentDate())
            if (walkingTime != null) {
                recognitionDao.updateWalking(
                        walking = elapsedTime + walkingTime,
                        getCurrentDate()
                )
            }
        }
    }
}

fun handlePrevStateRunning(elapsedTime: Long){
    ioScope.launch {
        val recognitionEntity: RecognitionEntity? =
                recognitionDao.getRecognition(getCurrentDate())
        if (recognitionEntity == null) {
            recognitionDao.insertRecognition(
                    RecognitionEntity(
                            getCurrentDate(),
                            running = elapsedTime,
                    )
            )
        } else {
            val runningTime: Long? =
                    recognitionDao.getRunning(getCurrentDate())
            if (runningTime != null) {
                recognitionDao.updateRunning(
                        running = elapsedTime + runningTime,
                        getCurrentDate()
                )
            }
        }
    }
}

fun handlePrevStateInVehicle(elapsedTime: Long){
    ioScope.launch {
        val recognitionEntity: RecognitionEntity? =
                recognitionDao.getRecognition(getCurrentDate())
        if (recognitionEntity == null) {
            recognitionDao.insertRecognition(
                    RecognitionEntity(
                            getCurrentDate(),
                            in_vehicle = elapsedTime,
                    )
            )
        } else {
            val inVehicleTime: Long? =
                    recognitionDao.getInVehicle(getCurrentDate())
            if (inVehicleTime != null) {
                recognitionDao.updateInVehicle(
                        inVehice = elapsedTime + inVehicleTime,
                        getCurrentDate()
                )
            }
        }
    }
}
}