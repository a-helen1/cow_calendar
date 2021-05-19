package org.wit.cowcalendar.views.addServe

import android.util.Log
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AddServePresenter (val view: AddServeView) {
  var animal = AnimalModel()
  var event = EventModel()
  var app: MainApp

  init {
    app = view.application as MainApp
    animal = view.intent.extras?.getParcelable<AnimalModel>("animal")!!
    event = view.intent.extras?.getParcelable<EventModel>("event_info")!!
    view.showEvents(animal, event)
  }

  fun doAddServe(sire: String) {
    event.sire = sire
    event.serveNo += 1
    app.events.create(event)
    view.finish()
  }
}