package com.androidsample.spotflock_assesment_test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidsample.spotflock_assesment_test.model.Contact
import com.androidsample.spotflock_assesment_test.repo.MainRepository
import com.androidsample.spotflock_assesment_test.repo.PersonRepository
import com.androidsample.spotflock_assesment_test.room.NoteDatabase
import com.androidsample.spotflock_assesment_test.room.Person
import com.androidsample.spotflock_assesment_test.utils.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonViewModal (application: Application) :AndroidViewModel(application) {

    // on below line we are creating a variable
    // for our all notes list and repository
    val allNotes : LiveData<List<Person>>
    val allNotesPersonal : LiveData<List<Person>>

    val allNotesBusiness : LiveData<List<Person>>

    val repository : PersonRepository
    val movieList = MutableLiveData<List<Contact>>()
    val errorMessage = MutableLiveData<String>()
    val repositoryMain: MainRepository


    // on below line we are initializing
    // our dao, repository and all notes
    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = PersonRepository(dao)
        repositoryMain = MainRepository(RetrofitService.retrofitService!!)
        allNotes = repository.allNotes
        allNotesPersonal =repository.allFilteredPersonal
        allNotesBusiness=repository.allFilteredBusiness

    }

    // on below line we are creating a new method for deleting a note. In this we are
    // calling a delete method from our repository to delete our note.
    fun deleteNote (note: Person) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    // on below line we are creating a new method for updating a note. In this we are
    // calling a update method from our repository to update our note.
    fun updateNote(note: Person) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }


    // on below line we are creating a new method for adding a new note to our database
    // we are calling a method from our repository to add a new note.
    fun addNote(note: Person) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }





    fun getAllMovies() {



        val response = repositoryMain.getAllContacts()
        response.enqueue(object : Callback<List<Contact>> {
            override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                movieList.postValue(response.body())


            }

            override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}