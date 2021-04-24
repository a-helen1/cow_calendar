package org.wit.cowcalendar.activities

import AnimalAdapter
import AnimalListener
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_animal_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.cowcalendar.R
import org.wit.cowcalendar.main.MainApp
import org.wit.cowcalendar.models.AnimalModel

class AnimalListActivity : AppCompatActivity(), AnimalListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal_list)
    app = application as MainApp

    toolbar.title = title
    setSupportActionBar(toolbar)

    val layoutManager =  LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    loadAnimals()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super .onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_add -> startActivityForResult<AnimalActivity>(0)
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onAnimalClick(animal: AnimalModel) {
    startActivityForResult(intentFor<AnimalActivity>().putExtra("animal_edit", animal), 0)
  }

  private fun loadAnimals() {
    showAnimals(app.animals.findAll())
  }

  fun showAnimals (animals: List<AnimalModel>) {
    recyclerView.adapter = AnimalAdapter(animals, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadAnimals()
    super.onActivityResult(requestCode, resultCode, data)
  }
}

