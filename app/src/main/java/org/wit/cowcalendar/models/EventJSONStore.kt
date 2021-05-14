package org.wit.cowcalendar.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.cowcalendar.helpers.exists
import org.wit.cowcalendar.helpers.read
import org.wit.cowcalendar.helpers.write
import java.util.*
import kotlin.collections.ArrayList

val JSON_EVENTS_FILE = "events.json"
val gsonEventBuilder = GsonBuilder().setPrettyPrinting().create()

val listTypeEvents = object  : TypeToken<java.util.ArrayList<EventModel>>() {}.type

fun generateRandomEventId(): Long {
  return Random().nextLong()
}

class EventJSONStore : EventStore, AnkoLogger {
  val context : Context
  var events = mutableListOf<EventModel>()

  constructor( context: Context) {
    this.context = context
    if (exists(context, JSON_EVENTS_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): MutableList<EventModel> {
    return events
  }

  override fun create(event: EventModel) {
    event.id = generateRandomEventId()
    events.add(event)
    serialise()
  }

  override fun update(event: EventModel) {
    /*
    val eventsList = findAll() as ArrayList<EventModel>
    var foundEvent: EventModel? = eventsList.find { p -> p.id == event.id }
    if (foundEvent != null ) {
      foundAnimal.animalNumber = animal.animalNumber
      foundAnimal.animalSex = animal.animalSex
      foundAnimal.animalDob = animal.animalDob
    }
    serialise()
    */
  }

  override fun delete(event: EventModel) {
    events.remove(event)
    serialise()
  }

  override fun findById(id: Long): EventModel? {
    TODO("Not yet implemented")
  }

  override fun clear() {
    TODO("Not yet implemented")
  }

  private fun serialise() {
    val jsonString = gsonEventBuilder.toJson(events, listTypeEvents)
    write(context, JSON_EVENTS_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_EVENTS_FILE)
    events = Gson().fromJson(jsonString, listTypeEvents)
  }
}