package org.wit.cowcalendar.views.addScan

import android.util.Log
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AddScanPresenter (val view: AddScanView) {

  var animal = AnimalModel()
  var event = EventModel()
  var app: MainApp

  init {
    app = view.application as MainApp
    animal = view.intent.extras?.getParcelable<AnimalModel>("animal")!!
    event = view.intent.extras?.getParcelable<EventModel>("event_info")!!
    view.showEvents(animal, event)
  }

  fun doAddScan(eventType: String, okToServe: Boolean, treatmentRequired: Boolean, isPregnant: Boolean) {
    animal.lastEventType = eventType
    event.eventType = eventType
    if (isPregnant) {
      animal.isPregnant = true
      event.isPregnant = true
    }
    if (okToServe) {
      event.okToServe = true
      animal.okToServe = true
    }
    if (treatmentRequired) {
      animal.treatmentRequired = true
      animal.treatmentRequired = true
    }
    app.animals.update(animal)
    app.events.create(event)
    view.finish()
  }

}

