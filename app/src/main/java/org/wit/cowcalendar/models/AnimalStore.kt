package org.wit.cowcalendar.models

interface AnimalStore {
    fun findAll(): List<AnimalModel>
    fun create(animalModel: AnimalModel)
}