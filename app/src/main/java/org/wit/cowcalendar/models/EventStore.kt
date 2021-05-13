package org.wit.cowcalendar.models

interface EventStore {
  fun findAll(): MutableList<EventModel>
  fun create(event: EventModel): Long
}