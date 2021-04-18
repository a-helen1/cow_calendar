package org.wit.cowcalendar.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.cowcalendar.models.AnimalMemStore
import org.wit.cowcalendar.models.AnimalModel

class MainApp : Application(), AnkoLogger {

  val animals = AnimalMemStore()

  override fun onCreate() {
    super.onCreate()
    info("Cow Calendar Started")
    //animals.add(AnimalModel("3221", "female"))
   // animals.add(AnimalModel("456", "female"))
    //animals.add(AnimalModel("875", "male"))
  }
}