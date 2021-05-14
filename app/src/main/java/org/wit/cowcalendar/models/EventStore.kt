package org.wit.cowcalendar.models

interface EventStore {
  fun findAll(): MutableList<EventModel>
  fun create(event: EventModel)
  fun update(event: EventModel)
  fun delete(event: EventModel)
  fun findById(id:Long) :EventModel?
  fun clear ()
}