package com.androidsample.spotflock_assesment_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidsample.spotflock_assesment_test.model.Contact
import com.androidsample.spotflock_assesment_test.repo.MainRepository
import com.androidsample.spotflock_assesment_test.repo.PersonRepository
import com.androidsample.spotflock_assesment_test.room.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val movieList = MutableLiveData<List<Contact>>()
    val errorMessage = MutableLiveData<String>()



    // on below line we are creating a variable
    // for our all notes list and repository


    fun getAllMovies() {

        val response = repository.getAllContacts()
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