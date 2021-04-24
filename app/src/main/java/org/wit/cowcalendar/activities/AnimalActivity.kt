package org.wit.cowcalendar.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_animal.*
import kotlinx.android.synthetic.main.card_animal.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel

class AnimalActivity : AppCompatActivity(), AnkoLogger {

  var animal = AnimalModel()
  lateinit var app : MainApp
  var edit = false;

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Animal Activity started..")
    app = application as MainApp

    if (intent.hasExtra("animal_edit")) {
      edit = true
      animal = intent.extras?.getParcelable<AnimalModel>("animal_edit")!!
      cowNo.setText(animal.animalNumber)
      if(animal.animalSex == 1)  {
        radioButtonMale.isChecked = true
      }else {
        radioButtonFemale.isChecked = true
      }
      btnAddCow.setText(R.string.save_animal)
    }

    val radioGroup = findViewById<RadioGroup>(R.id.radioGroup) as RadioGroup
    radioGroup.setOnCheckedChangeListener {group, ID ->
      when (ID) {
        R.id.radioButtonMale -> {
          animal.animalSex = 1
        }
        R.id.radioButtonFemale -> {
          animal.animalSex = 2
        }
      }
    }

    btnAddCow.setOnClickListener() {
      animal.animalNumber = cowNo.text.toString()
      if (animal.animalNumber.isEmpty()) {
        toast("Please enter a cow number")
      }else {
        if (edit) {
          app.animals.update(animal.copy())
        }else {
          app.animals.create(animal.copy())
        }
      }
        info("add button pressed: ${animal}")
        setResult(AppCompatActivity.RESULT_OK)
        finish()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_animal, menu)
    if (edit && menu != null ) menu.getItem(0).setVisible(true)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_delete -> {
        app.animals.delete(animal)
        finish()
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }
}