package org.wit.cowcalendar.activities

import org.jetbrains.anko.intentFor
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AnimalEventPresenter (val view: AnimalEventView) {
  var animal = AnimalModel()
  var event = EventModel()
  var app : MainApp

  init {
    app = view.application as MainApp
    animal = view.intent.extras?.getParcelable<AnimalModel>("animal_event")!!
    view.showEvents(animal)
  }

  fun getEvents() = app.events.findAll()

  fun doAddServeEvent(animalModel: AnimalModel, event: EventModel) {
    view.startActivityForResult(view.intentFor<AddServeActivity>()
      .putExtra("event_info", event)
      .putExtra("animal", animal), 0)
  }
}