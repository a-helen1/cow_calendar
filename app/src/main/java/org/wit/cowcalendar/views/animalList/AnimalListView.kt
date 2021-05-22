package org.wit.cowcalendar.views.animalList

import AnimalAdapter
import AnimalListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_animal_list.*
import org.wit.cowcalendar.R
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.views.BaseView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class AnimalListView : BaseView(), AnimalListener {

  lateinit var presenter: AnimalListPresenter
  var animalList = listOf<AnimalModel>()
  var animalDisplayList = mutableListOf<AnimalModel>()

  var x = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal_list)

    toolbar.title = title
    setSupportActionBar(toolbar)

    presenter = AnimalListPresenter(this)
    showAllAnimals()

    val filterList = resources.getStringArray(R.array.Animal_Filters)
    val spinner = findViewById<Spinner>(R.id.animalSpinner)

    if (spinner != null) {
      val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterList)
      spinner.adapter =adapter

      spinner.onItemSelectedListener = object  :
      AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          x = p2
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
    }

    btnFliterAnimals.setOnClickListener() {

      when (x) {
        0 -> showAllAnimals()
        1 -> showDueCalve()
        2 -> showDueServe()
        3 -> showDueScan()
      }

    }

  }

  fun showAllAnimals() {
    if (animalDisplayList.isNotEmpty()) {
      animalDisplayList.clear()
    }
    val allAnimals = presenter.getAnimals()
    for (item in allAnimals) {
      animalDisplayList.add(item)
    }
    showFilteredAnimals()
  }

  fun showDueCalve(){
    if (animalDisplayList.isNotEmpty()) {
      animalDisplayList.clear()
    }
    val allAnimals = presenter.getAnimals()
    for (item in allAnimals) {
      if (item.isPregnant)
        animalDisplayList.add(item)
    }
    showFilteredAnimals()
  }

  fun showDueServe() {
    if (animalDisplayList.isNotEmpty()) {
      animalDisplayList.clear()
    }
    val allAnimals = presenter.getAnimals()
    /*
    val today = LocalDate.now()

    fun daysCalved(date: String): Int {
      val calveDate = SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(date)
      val calveDate1 =calveDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
      val daysCalved = ChronoUnit.DAYS.between(calveDate1, today)
      Log.d("daysCalved", daysCalved.toString())
      return daysCalved.toInt()
    }
    */
    for (item in allAnimals) {
      if (!item.isPregnant) {
        animalDisplayList.add(item)
      }
      showFilteredAnimals()
    }
  }

  fun showDueScan(){
    if (animalDisplayList.isNotEmpty()) {
      animalDisplayList.clear()
    }
    val allAnimals = presenter.getAnimals()
    for (item in allAnimals) {
      if (item.lastEventType == "Serve")
        animalDisplayList.add(item)
    }
    showFilteredAnimals()
  }

  fun showFilteredAnimals(){
    val layoutManager =  LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = AnimalAdapter(animalDisplayList, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super .onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddAnimal()
      R.id.item_logout -> presenter.doLogout()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onAnimalClick(animal: AnimalModel) {
    presenter.doShowAnimalEvents(animal)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    showAllAnimals()
    showFilteredAnimals()
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }
}



