package com.androidsample.spotflock_assesment_test.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.androidsample.spotflock_assesment_test.room.Person
import com.androidsample.spotflock_assesment_test.room.PersonDao
import java.util.concurrent.Flow

public class PersonRepository(private val notesDao: PersonDao) {

    // on below line we are creating a variable for our list
    // and we are getting all the notes from our DAO class.
    val allNotes: LiveData<List<Person>> = notesDao.getAllNotes()

    val allFilteredPersonal: LiveData<List<Person>> = notesDao.getFilterRecords("Personal")
    val allFilteredBusiness: LiveData<List<Person>> = notesDao.getFilterRecords("Business")


    // on below line we are creating an insert method
    // for adding the note to our database.
    suspend fun insert(note: Person) {
        notesDao.insert(note)
    }

    // on below line we are creating a delete method
    // for deleting our note from database.
    suspend fun delete(note: Person){
        notesDao.delete(note)
    }

    // on below line we are creating a update method for
    // updating our note from database.
    suspend fun update(note: Person){
        notesDao.update(note)
    }

    suspend fun exists(note: Person){
        notesDao.upsert(note)
    }



}