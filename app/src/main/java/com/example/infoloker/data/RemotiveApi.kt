package com.example.infoloker.data

import retrofit2.http.GET
import retrofit2.http.Query

interface RemotiveApi {
    // Example: https://remotive.io/api/remote-jobs?search=android
    @GET("api/remote-jobs")
    suspend fun getJobs(@Query("search") search: String?): JobsResponse
}
