package com.example.infoloker.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.infoloker.data.Job
import com.example.infoloker.data.Repository
import kotlinx.coroutines.launch

@Composable
fun JobListScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    onOpenDetail: (Job) -> Unit
) {
    var useRemote by remember { mutableStateOf(true) }
    var categoryFilter by remember { mutableStateOf<String?>(null) }
    var locationFilter by remember { mutableStateOf<String?>(null) }

    var jobs by remember { mutableStateOf<List<Job>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var allJobs by remember { mutableStateOf<List<Job>>(emptyList()) }

    val scope = rememberCoroutineScope()

    // Fetch filtered jobs when any filter/search/source changes
    LaunchedEffect(query, categoryFilter, locationFilter, useRemote) {
        loading = true
        error = null
        try {
            jobs = Repository.getJobs(useRemote = useRemote, query = query, category = categoryFilter, location = locationFilter)
        } catch (e: Exception) {
            error = e.message ?: "Unknown error"
        } finally {
            loading = false
        }
    }

    // Separately fetch the full list (no filters) to populate dropdown options
    LaunchedEffect(useRemote) {
        try {
            allJobs = Repository.getJobs(useRemote = useRemote, query = null, category = null, location = null)
        } catch (e: Exception) {
            // ignore - options will be empty
            allJobs = emptyList()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Controls row
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                label = { Text("Search") },
                modifier = Modifier.weight(1f)
            )

            Column {
                Text(text = "Remote")
                Switch(checked = useRemote, onCheckedChange = { useRemote = it })
            }
        }

        // Dropdown filters row (Category & Location)
        val categories = remember(allJobs) { allJobs.mapNotNull { it.category }.distinct().sorted() }
        val locations = remember(allJobs) { allJobs.mapNotNull { it.location }.distinct().sorted() }

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            var catExpanded by remember { mutableStateOf(false) }
            var locExpanded by remember { mutableStateOf(false) }

            // Category dropdown (styled)
            ExposedDropdownMenuBox(expanded = catExpanded, onExpandedChange = { catExpanded = !catExpanded }, modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = categoryFilter ?: "All",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = catExpanded) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = catExpanded, onDismissRequest = { catExpanded = false }) {
                    DropdownMenuItem(onClick = {
                        categoryFilter = null
                        catExpanded = false
                    }) { Text("All") }
                    categories.forEach { c ->
                        DropdownMenuItem(onClick = {
                            categoryFilter = c
                            catExpanded = false
                        }) { Text(c) }
                    }
                }
            }

            // Location dropdown (styled)
            ExposedDropdownMenuBox(expanded = locExpanded, onExpandedChange = { locExpanded = !locExpanded }, modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = locationFilter ?: "All",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Location") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = locExpanded) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = locExpanded, onDismissRequest = { locExpanded = false }) {
                    DropdownMenuItem(onClick = {
                        locationFilter = null
                        locExpanded = false
                    }) { Text("All") }
                    locations.forEach { l ->
                        DropdownMenuItem(onClick = {
                            locationFilter = l
                            locExpanded = false
                        }) { Text(l) }
                    }
                }
            }
        }

        // Clear filters button
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
            OutlinedButton(onClick = {
                categoryFilter = null
                locationFilter = null
            }) {
                Text("Clear filters")
            }
        }

        if (loading) {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        if (error != null) {
            Text(text = "Error: $error", color = MaterialTheme.colors.error, modifier = Modifier.padding(8.dp))
            Button(onClick = {
                scope.launch { jobs = Repository.getJobs(useRemote = useRemote, query = query, category = categoryFilter, location = locationFilter) }
            }, modifier = Modifier.padding(8.dp)) {
                Text("Retry")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(items = jobs, key = { it.id }) { job ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onOpenDetail(job) }) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = job.title, style = MaterialTheme.typography.subtitle1)
                        Text(text = job.companyName, style = MaterialTheme.typography.body2)
                        Text(text = job.location ?: "", style = MaterialTheme.typography.caption)
                    }
                }
            }
        }
    }
}
