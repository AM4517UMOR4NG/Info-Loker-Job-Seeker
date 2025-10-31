package com.example.infoloker.data

import com.google.gson.annotations.SerializedName

// Minimal model matching Remotive API's job fields we care about
data class JobsResponse(
    @SerializedName("jobs") val jobs: List<Job>
)

data class Job(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("company_name") val companyName: String,
    @SerializedName("category") val category: String?,
    @SerializedName("job_type") val jobType: String?,
    @SerializedName("publication_date") val publicationDate: String?,
    @SerializedName("candidate_required_location") val location: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("description") val description: String?
)
