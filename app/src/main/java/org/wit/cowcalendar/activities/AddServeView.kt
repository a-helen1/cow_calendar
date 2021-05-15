package org.wit.cowcalendar.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ActionMode
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_serve.*
import kotlinx.android.synthetic.main.activity_add_serve.animalDobEvent
import kotlinx.android.synthetic.main.activity_add_serve.animalNo
import kotlinx.android.synthetic.main.activity_add_serve.animalSexTxt
import kotlinx.android.synthetic.main.activity_animal.*
import kotlinx.android.synthetic.main.activity_animal.toolbarAdd
import kotlinx.android.synthetic.main.activity_animal_events.*
import org.jetbrains.anko.AnkoLogger
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.models.EventModel

class AddServeView : AppCompatActivity(), AnkoLogger {

  lateinit var presenter: AddServePresenter
  var animal = AnimalModel()
  var event = EventModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_serve)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    presenter = AddServePresenter(this)

    btnAddServe.setOnClickListener() {
      presenter.doAddServe(sire.text.toString())
    }
  }

  fun showEvents(animal: AnimalModel, event: EventModel){
    animalNo.text = animal.animalNumber
    animalDobEvent.text = animal.animalDob
    addEventDate.text = event.eventDate
    if (animal.animalSex == 1) {
      animalSexTxt.setText(R.string.sex_male)
    }else {
      animalSexTxt.setText(R.string.sex_female)
    }
  }
 }



