package com.binhnk.retrofitexample.listeners

import retrofit2.http.GET

interface APICallback {
    @GET()
    fun getAllUsers()
}