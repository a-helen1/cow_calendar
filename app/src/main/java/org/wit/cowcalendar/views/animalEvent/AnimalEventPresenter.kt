package org.wit.cowcalendar.views.animalEvent

import org.jetbrains.anko.intentFor
import org.wit.cowcalendar.views.addServe.AddServeView
import org.wit.cowcalendar.activities.AnimalEventView
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel
import org.wit.cowcalendar.views.addCalve.AddCalveView

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
    view.startActivityForResult(view.intentFor<AddServeView>()
      .putExtra("event_info", event)
      .putExtra("animal", animal), 0)
  }

  fun doAddCalveEvent(eventDate: String, eventType: String){
    event.eventDate = eventDate
    event.eventType = eventType
    event.animalId = animal.animalNumber.toInt()
    view.startActivityForResult(view.intentFor<AddCalveView>()
      .putExtra("event_info", event)
      .putExtra("animal", animal), 0)

  }
}