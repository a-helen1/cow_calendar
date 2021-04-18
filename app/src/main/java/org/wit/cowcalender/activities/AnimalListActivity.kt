package org.wit.cowcalender.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_animal_list.*
import kotlinx.android.synthetic.main.card_animal.view.*
import org.wit.cowcalender.R
import org.wit.cowcalender.main.MainApp
import org.wit.cowcalender.models.AnimalModel

class AnimalListActivity : AppCompatActivity() {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_animal_list)
    app = application as MainApp

    val layoutManager =  LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = AnimalAdapter(app.animals)
  }
}

class AnimalAdapter constructor(private var animals: List<AnimalModel>):
    RecyclerView.Adapter<AnimalAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
      return MainHolder(
          LayoutInflater.from(parent?.context).inflate(
              R.layout.card_animal,
              parent,
              false
          )
      )
    }

    override fun onBindViewHolder(holder: MainHolder, Position: Int) {
      val animal = animals[holder.adapterPosition]
      holder.bind(animal)
    }

    override fun getItemCount(): Int = animals.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

      fun bind(animal:AnimalModel){
        itemView.animalNumber.text = animal.animalNumber
        itemView.animalSex.text = animal.animalSex
    }
  }
}