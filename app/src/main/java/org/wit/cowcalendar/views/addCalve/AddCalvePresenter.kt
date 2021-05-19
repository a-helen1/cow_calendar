package org.wit.cowcalendar.views.addCalve

import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AddCalvePresenter (val view: AddCalveView) {
  var animal = AnimalModel()
  var event = EventModel()
  var app: MainApp

  init {
    app = view.application as MainApp
    animal = view.intent.extras?.getParcelable<AnimalModel>("animal")!!
    event = view.intent.extras?.getParcelable<EventModel>("event_info")!!
    view.showEvents(animal, event)
  }

  fun doAddCalve(calveSex: Int) {
    event.calveDate = event.eventDate
    event.calveSex = calveSex
    app.events.create(event)
    view.finish()
  }
}