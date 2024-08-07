package com.example.newyorktimes

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newyorktimes.adapter.StoriesAdapter
import com.example.newyorktimes.api_service.StoriesRepository
import com.example.newyorktimes.view_model.StoriesViewModel
import com.example.newyorktimes.view_model.StoriesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StoriesViewModel
    private lateinit var adapter: StoriesAdapter

    private lateinit var searchView: SearchView
    private lateinit var sectionSpinner: Spinner
    private lateinit var viewSwitch: Switch

    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val repository = StoriesRepository()
        val viewModelFactory = StoriesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(StoriesViewModel::class.java)

        setupRecyclerView()
        setupSearchAndFilter()

        val apiKey = "ySyINpvISG8C652Mq442Dg60Aj4YXbV1"
        viewModel.fetchTopStories(apiKey)

        viewModel.stories.observe(this, { stories ->
            adapter.submitList(stories)
        })

        viewModel.error.observe(this, { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = StoriesAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSearchAndFilter() {
        searchView = findViewById(R.id.search_view)
        sectionSpinner = findViewById(R.id.section_spinner)
        viewSwitch = findViewById(R.id.view_switch)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchStories(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchStories(it) }
                return true
            }
        })

        sectionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val section = parent?.getItemAtPosition(position).toString()
                viewModel.filterStoriesBySection(section)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.filterStoriesBySection("All")
            }
        }

        viewSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Switch to Card view layout manager
                recyclerView.layoutManager = GridLayoutManager(this, 2)
            } else {
                // Switch to List view layout manager
                recyclerView.layoutManager = LinearLayoutManager(this)
            }
        }
    }

}
