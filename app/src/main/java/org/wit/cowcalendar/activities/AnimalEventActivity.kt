package org.wit.cowcalendar.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_animal.*
import kotlinx.android.synthetic.main.activity_animal.toolbarAdd
import kotlinx.android.synthetic.main.activity_animal_events.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel

class AnimalEventActivity : AppCompatActivity(), AnkoLogger {

  var animal = AnimalModel()
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal_events)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    app = application as MainApp

    animal = intent.extras?.getParcelable<AnimalModel>("animal_event")!!
    animalNo.setText(animal.animalNumber)
    if (animal.animalSex == 1) {
      animalSexTxt.setText(R.string.sex_male)
    }else {
      animalSexTxt.setText(R.string.sex_female)
    }
  }

}



