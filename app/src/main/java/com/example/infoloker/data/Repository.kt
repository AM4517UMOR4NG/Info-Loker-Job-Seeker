package com.example.infoloker.data

import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Repository that can fetch from Remotive (remote) or return mock data.
 * Provides a single getJobs(...) entry point that accepts optional filters.
 */
object Repository {
    private const val REMOTIVE_BASE = "https://remotive.io/"

    // Lazily create Retrofit service
    private val remotiveApi: RemotiveApi by lazy {
        val gson = GsonBuilder().create()
        Retrofit.Builder()
            .baseUrl(REMOTIVE_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RemotiveApi::class.java)
    }

    suspend fun getJobs(
        useRemote: Boolean = true,
        query: String? = null,
        category: String? = null,
        location: String? = null
    ): List<Job> {
        return if (useRemote) {
            try {
                val resp = remotiveApi.getJobs(query)
                var jobs = resp.jobs
                // Apply simple client-side filters if provided
                jobs = jobs.filter { job ->
                    val matchesCategory = category.isNullOrBlank() || (job.category?.contains(category, true) ?: false)
                    val matchesLocation = location.isNullOrBlank() || (job.location?.contains(location, true) ?: false)
                    val matchesQuery = query.isNullOrBlank() || job.title.contains(query!!, true) || job.companyName.contains(query, true)
                    matchesCategory && matchesLocation && matchesQuery
                }
                jobs
            } catch (e: Exception) {
                // On any network/parsing error, fall back to mock data
                getJobsMock().filterWith(query, category, location)
            }
        } else {
            // Mock path
            getJobsMock().filterWith(query, category, location)
        }
    }

    private suspend fun getJobsMock(): List<Job> {
        delay(200)

        return listOf(
            Job(1, "Android Developer", "ABC Tech", "Software", "Full Time", "2025-10-01", "Indonesia", "https://example.com/1", "Develop Android apps."),
            Job(2, "Backend Engineer", "DataCorp", "Engineering", "Full Time", "2025-09-25", "Remote", "https://example.com/2", "Work on backend services."),
            Job(3, "UI/UX Designer", "Creative Studio", "Design", "Contract", "2025-10-10", "Jakarta, Indonesia", "https://example.com/3", "Design beautiful interfaces."),
            Job(4, "Frontend Developer", "WebWorks", "Software", "Part Time", "2025-08-15", "Bandung, Indonesia", "https://example.com/4", "Implement web frontends."),
            Job(5, "Data Analyst", "Analytics Inc.", "Data", "Full Time", "2025-10-05", "Remote", "https://example.com/5", "Analyze datasets and prepare reports."),
            Job(6, "DevOps Engineer", "InfraOps", "Engineering", "Full Time", "2025-07-30", "Jakarta, Indonesia", "https://example.com/6", "Build and maintain infrastructure.")
        )
    }
}

// Extension helper for filtering mock lists
private fun List<Job>.filterWith(query: String?, category: String?, location: String?): List<Job> {
    return this.filter { job ->
        val matchesCategory = category.isNullOrBlank() || (job.category?.contains(category, true) ?: false)
        val matchesLocation = location.isNullOrBlank() || (job.location?.contains(location, true) ?: false)
        val matchesQuery = query.isNullOrBlank() || job.title.contains(query!!, true) || job.companyName.contains(query, true)
        matchesCategory && matchesLocation && matchesQuery
    }
}

