package org.wit.cowcalendar.activities

import org.jetbrains.anko.intentFor
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AnimalEventPresenter (val view: AnimalEventView) {
  var animal = AnimalModel()
  var event = EventModel()
  val animalEvents = mutableListOf<EventModel>()
  var app : MainApp

  init {
    app = view.application as MainApp
    animal = view.intent.extras?.getParcelable<AnimalModel>("animal_event")!!
    getEvents()
    view.showEvents(animal, animalEvents)
  }

  fun getEvents() {
    if (animalEvents.isNotEmpty()){
      animalEvents.clear()
    }
    val allEvents = app.events.findAll()
    for (item in allEvents) {
      if (item.animalId == animal.animalNumber.toInt()) {
        animalEvents.add(item)
      }
    }
  }

  fun doAddServeEvent(eventDate: String, eventType: String) {
    event.eventDate = eventDate
    event.eventType = eventType
    event.animalId = animal.animalNumber.toInt()
    view.startActivityForResult(view.intentFor<AddServeActivity>()
      .putExtra("event_info", event)
      .putExtra("animal", animal), 0)
  }
}