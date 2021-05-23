package org.wit.cowcalendar.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.cowcalendar.models.*

class MainApp : Application(), AnkoLogger {

 lateinit var animals:  AnimalStore
 lateinit var events: EventStore

  override fun onCreate() {
    super.onCreate()
    animals = AnimalJSONStore(applicationContext)
    events = EventJSONStore(applicationContext)
    info("Cow Calendar Started")
  }
}