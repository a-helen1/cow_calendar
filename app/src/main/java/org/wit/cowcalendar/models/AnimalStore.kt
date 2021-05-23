package org.wit.cowcalendar.models

interface AnimalStore {
    fun findAll(): List<AnimalModel>
    fun create(animal: AnimalModel)
    fun update(animal: AnimalModel)
    fun delete(animal: AnimalModel)
    fun findById(id: Int): AnimalModel?
}