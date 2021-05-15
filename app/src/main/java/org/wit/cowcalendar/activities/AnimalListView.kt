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

class AnimalListView : AppCompatActivity(), AnimalListener {

  lateinit var presenter: AnimalListPresenter

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
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onAnimalClick(animal: AnimalModel) {
    presenter.doShowAnimalEvents(animal)
    //startActivityForResult(intentFor<AnimalActivity>().putExtra("animal_edit", animal), 0)
    //startActivityForResult(intentFor<AnimalEventActivity>().putExtra("animal_event", animal), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }
}

