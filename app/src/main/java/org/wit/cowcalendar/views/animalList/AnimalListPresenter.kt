package org.wit.cowcalendar.views.animalList

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.cowcalendar.activities.AnimalEventView
import org.wit.cowcalendar.activities.AnimalView
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.views.BasePresenter
import org.wit.cowcalendar.views.BaseView
import org.wit.cowcalendar.views.VIEW

class AnimalListPresenter (view: BaseView) : BasePresenter(view) {

  fun getAnimals() = app.animals.findAll()

  fun doAddAnimal() {
    view?.navigateTo(VIEW.ANIMAL, 0)
  }

  fun doEditAnimal(animal: AnimalModel) {
  }

  fun doShowAnimalEvents(animal: AnimalModel) {
    view?.navigateTo(VIEW.EVENT, 0, "animal_event", animal)
  }

  fun doLogout(){
    view?.navigateTo(VIEW.LOGIN)
  }
}

