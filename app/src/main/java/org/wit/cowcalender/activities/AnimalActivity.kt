package org.wit.cowcalender.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_animal.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.internals.AnkoInternals.createAnkoContext
import org.jetbrains.anko.toast
import org.wit.cowcalender.R
import org.wit.cowcalender.main.MainApp
import org.wit.cowcalender.models.AnimalModel

class AnimalActivity : AppCompatActivity(), AnkoLogger {

  var animal = AnimalModel()
  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal)
    app = application as MainApp

    val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
    radioGroup?.setOnCheckedChangeListener { group, checkedId ->
      var animalSex = if (R.id.radioButtonMale == checkedId) "male" else "female"
      animal.animalSex = animalSex
    }

    btnAddCow.setOnClickListener() {

      animal.animalNumber = cowNo.text.toString()

      if (animal.animalNumber.isNotEmpty()) {
        app.animals.add(animal.copy())
        info("add button pressed: ${animal.animalNumber}")
        for (i in app!!.animals.indices) {
          info("Animal[$i]:${app!!.animals[i]}")
        }
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      } else {
        toast("Please enter a cow number")
      }
    }
  }
}