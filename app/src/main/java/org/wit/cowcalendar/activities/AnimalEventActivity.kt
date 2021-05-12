package org.wit.cowcalendar.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
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
  var x = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal_events)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    app = application as MainApp
    val events = resources.getStringArray(R.array.Events)

    animal = intent.extras?.getParcelable<AnimalModel>("animal_event")!!
    animalNo.text = animal.animalNumber
    if (animal.animalSex == 1) {
      animalSexTxt.setText(R.string.sex_male)
    }else {
      animalSexTxt.setText(R.string.sex_female)
    }

    val spinner = findViewById<Spinner>(R.id.eventSpinner)
    if (spinner != null) {
      val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, events)
      spinner.adapter = adapter

      spinner.onItemSelectedListener = object :
      AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

          x= p2
          Toast.makeText(applicationContext, p2.toString(), Toast.LENGTH_LONG).show()

        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
    }
  }

}



