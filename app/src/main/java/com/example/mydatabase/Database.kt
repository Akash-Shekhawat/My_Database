package com.example.mydatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydatabase.sqlite.sqlitedbManager

class Database : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    private lateinit var customAdapter: MyCustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)


        val intent = intent
        BUTTON_CLICKED = intent.getStringExtra(MainActivity.BUTTON_CLICKED_KEY).toString()

        initialiseRecyclerView()

    }

    private fun initialiseRecyclerView() {
        recyclerView = findViewById(R.id.rvEmpDetail)

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        setupListFromSQLite()
        recyclerView.adapter = customAdapter
        customAdapter.notifyDataSetChanged()
    }



    private fun setupListFromSQLite() {
        val databaseManager =
            sqlitedbManager(this)

        // get data from Database
        val employeeList = databaseManager.getAllEmpDataFromSQLiteDB()

        customAdapter =
            MyCustomAdapter(this, employeeList)
    }

    companion object {
        lateinit var BUTTON_CLICKED: String
    }

}