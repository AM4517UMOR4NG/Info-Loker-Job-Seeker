package com.example.infoloker.data

import kotlinx.coroutines.delay

/**
 * Simple in-memory repository with mock job data so the app can demonstrate
 * searching and filtering without a network dependency.
 */
object Repository {
    suspend fun getJobs(): List<Job> {
        // Simulate network latency
        delay(300)

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
