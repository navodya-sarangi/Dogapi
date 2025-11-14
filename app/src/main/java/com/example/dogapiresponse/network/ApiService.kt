package com.example.dogapiresponse.network

import com.example.dogapiresponse.model.DogApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("breeds/image/random/10")
    suspend fun getRandomDogImages(): DogApiResponse
}