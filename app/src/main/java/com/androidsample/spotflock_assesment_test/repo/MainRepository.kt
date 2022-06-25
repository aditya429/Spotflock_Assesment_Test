package com.androidsample.spotflock_assesment_test.repo

import com.androidsample.spotflock_assesment_test.utils.RetrofitService
import retrofit2.Response

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllContacts() = retrofitService.getAllContacts()
}