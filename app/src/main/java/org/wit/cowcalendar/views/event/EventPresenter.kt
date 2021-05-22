package org.wit.cowcalendar.views.event

import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel
import org.wit.cowcalendar.views.BasePresenter

class EventPresenter (view: EventView) : BasePresenter(view) {
  var event = EventModel()
  var animal = AnimalModel()

  init {
    app = view.application as MainApp
    event = view.intent.extras?.getParcelable<EventModel>("event_edit")!!
    animal = view.intent.extras?.getParcelable<AnimalModel>("event_animal")!!
    view.showEvent(event, animal)
  }

  fun doDelete() {
    app.events.delete(event)
    view?.finish()
  }
}

