package com.capullo.tribalapp.core.data.remote

import retrofit2.http.GET

interface ChuckNorrisRequestApiInt {

   /* https://api.chucknorris.io/jokes/categories */
   @GET("jokes/categories")
   suspend fun getCategories(): List<String>

}