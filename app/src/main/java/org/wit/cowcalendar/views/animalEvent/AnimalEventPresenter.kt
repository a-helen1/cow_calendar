
package org.wit.cowcalendar.views.animalEvent

import android.util.Log
import org.jetbrains.anko.intentFor
import org.wit.cowcalendar.views.addServe.AddServeView
import org.wit.cowcalendar.activities.AnimalEventView
import org.wit.cowcalendar.activities.AnimalView
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel
import org.wit.cowcalendar.views.addCalve.AddCalveView
import org.wit.cowcalendar.views.addScan.AddScanView
import org.wit.cowcalendar.views.event.EventView
import java.text.SimpleDateFormat
import java.util.*

class AnimalEventPresenter (val view: AnimalEventView) {
  var animal = AnimalModel()
  var event = EventModel()
  val animalEvents = mutableListOf<EventModel>()
  var app : MainApp

  init {
    app = view.application as MainApp
    animal = view.intent.extras?.getParcelable<AnimalModel>("animal_event")!!
    getEvents()
    Log.d("HasRun", "init")
    view.showEvents(animal, animalEvents)
  }

  fun getEvents() {
    if (animalEvents.isNotEmpty()){
      animalEvents.clear()
    }
    val allEvents = app.events.findAll()
    for (item in allEvents) {
      if (item.animalId == animal.animalNumber) {
        animalEvents.add(item)
      }
    }
    //show events by most recent first
    animalEvents.sortWith(compareByDescending { SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(it.eventDate) })
  }

  //get updated animal status from model after adding events
  fun getUpdatedAnimal() {
    val foundAnimal: AnimalModel? = app.animals.findById(animal.animalNumber)
    if (foundAnimal != null) {
      animal = foundAnimal
    }
  }

  fun doDelete(animal: AnimalModel){
    getEvents()
    for (item in animalEvents) {
      app.events.delete(item)
      }
    app.animals.delete(animal)
    }

  fun edit(animal: AnimalModel){
    view.startActivityForResult(view.intentFor<AnimalView>()
      .putExtra("animal_edit", animal), 0)
  }

  fun doShowEvent(event: EventModel) {
    view.startActivityForResult(view.intentFor<EventView>()
      .putExtra("event_edit", event)
      .putExtra("event_animal", animal), 0)

  }

  fun doAddServeEvent(eventDate: String, eventType: String) {
    event.eventDate = eventDate
    event.eventType = eventType
    event.animalId = animal.animalNumber
    view.startActivityForResult(view.intentFor<AddServeView>()
      .putExtra("event_info", event)
      .putExtra("animal", animal), 0)
  }

  fun doAddCalveEvent(eventDate: String, eventType: String){
    event.eventDate = eventDate
    event.eventType = eventType
    event.animalId = animal.animalNumber
    view.startActivityForResult(view.intentFor<AddCalveView>()
      .putExtra("event_info", event)
      .putExtra("animal", animal), 0)
  }

  fun doAddDryOffEvent(eventDate: String, eventType: String){
    event.eventDate = eventDate
    event.eventType = eventType
    event.animalId = animal.animalNumber
    event.dryOffDate = eventDate
    animal.lastEventType = event.eventType
    app.animals.update(animal)
    app.events.create(event)
    getEvents()
    view.showEvents(animal, animalEvents)
  }

  fun doAddScanEvent (eventDate: String, eventType: String) {
    event.eventDate = eventDate
    event.eventType = eventType
    event.animalId = animal.animalNumber
    view.startActivityForResult(view.intentFor<AddScanView>()
      .putExtra("event_info", event)
      .putExtra("animal", animal), 0)
  }
}