package com.ermilova.android.diary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment
import com.ermilova.android.diary.MyApplication
import com.ermilova.android.diary.databinding.ActivityMainBinding
import com.ermilova.android.diary.domain.EventModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

private const val jsonFile = "events.json"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        val navController = navHostFragment.navController
    }

    override fun onStart() {
        super.onStart()
        initDB()
    }

    private fun initDB() {
        val gson = Gson()
        val json: String = try {
            val inputStream: InputStream = requireNotNull(applicationContext).assets.open(jsonFile)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            ""
        }

        val typeToken = object : TypeToken<List<EventModel>>() {}.type
        val events = gson.fromJson<List<EventModel>>(json, typeToken)

        for (event in events) {
            lifecycle.coroutineScope.launch {
                withContext(Dispatchers.IO) {
                    (requireNotNull(application) as MyApplication).eventRepo.addEvent(event)
                }
            }
        }
    }
}