package org.wit.cowcalendar.views.animalList

import AnimalAdapter
import AnimalListener
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_animal_list.*
import org.wit.cowcalendar.R
import org.wit.cowcalendar.models.AnimalModel
import org.wit.cowcalendar.views.BaseView

class AnimalListView : BaseView(), AnimalListener {

  lateinit var presenter: AnimalListPresenter
  var animalList = listOf<AnimalModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal_list)

    toolbar.title = title
    setSupportActionBar(toolbar)

    presenter = AnimalListPresenter(this)

    val layoutManager =  LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = AnimalAdapter(presenter.getAnimals(), this)
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
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }
}

