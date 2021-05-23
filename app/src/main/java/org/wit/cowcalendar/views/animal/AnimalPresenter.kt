package org.wit.cowcalendar.views.animal

import org.wit.cowcalendar.activities.AnimalView
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.views.BasePresenter
import org.wit.cowcalendar.views.BaseView

class AnimalPresenter (view: BaseView) : BasePresenter(view) {
  //val view: AnimalView
  var animal = AnimalModel()
  var edit = false;

  init {
    app = view.application as MainApp
    if (view.intent.hasExtra("animal_edit")) {
      edit = true
      animal = view.intent.extras?.getParcelable<AnimalModel>("animal_edit")!!
      view.showAnimal(animal)
    }
  }

  fun doAddorSave(animalNo: String, animalSex: Int, animalDob: String ) {
    animal.animalNumber = animalNo.toInt()
    animal.animalSex = animalSex
    animal.animalDob = animalDob
    if (edit) {
      app.animals.update(animal)
    }else {
      app.animals.create(animal)
    }
    view?.finish()
  }

  fun doCancel() {
    view?.finish()
  }

  fun doDelete() {
    app.animals.delete(animal)
    view?.finish()
  }
}