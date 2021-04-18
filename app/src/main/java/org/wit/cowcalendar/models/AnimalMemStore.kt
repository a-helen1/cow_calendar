package org.wit.cowcalendar.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class AnimalMemStore : AnimalStore, AnkoLogger {

    val animals = ArrayList<AnimalModel>()

    override fun findAll(): List<AnimalModel> {
        return animals
    }

    override fun create(animal: AnimalModel) {
        animals.add(animal)
        logAll()
    }

    fun logAll() {
        animals.forEach{ info("${it}")}
    }
}