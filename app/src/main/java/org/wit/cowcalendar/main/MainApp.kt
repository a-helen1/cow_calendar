package org.wit.cowcalendar.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.cowcalendar.models.AnimalJSONStore
import org.wit.cowcalendar.models.AnimalMemStore
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.AnimalStore

class MainApp : Application(), AnkoLogger {

 lateinit var animals:  AnimalStore

  override fun onCreate() {
    super.onCreate()
    animals = AnimalJSONStore(applicationContext)
    info("Cow Calendar Started")
  }
}