package org.wit.cowcalendar.views.animalList

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.cowcalendar.activities.AnimalEventView
import org.wit.cowcalendar.activities.AnimalView
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel

class AnimalListPresenter (val view: AnimalListView) {

  var app : MainApp

  init {
    app = view.application as MainApp
  }

  fun getAnimals() = app.animals.findAll()

  fun doAddAnimal() {
    view.startActivityForResult<AnimalView>(0)
  }

  fun doEditAnimal(animal: AnimalModel) {
    view.startActivityForResult(view.intentFor<AnimalView>().putExtra("animal_edit", animal), 0)
  }

  fun doShowAnimalEvents(animal: AnimalModel) {
    view.startActivityForResult(view.intentFor<AnimalEventView>().putExtra("animal_event", animal), 0)
  }
}

