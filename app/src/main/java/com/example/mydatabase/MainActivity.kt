package com.example.mydatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.mydatabase.sqlite.sqlitedbManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addDummySqliteDemoBtn:Button = findViewById(R.id.btn1Add)
        val sqliteDemoBtn: Button = findViewById(R.id.btn2Showdata)
        sqliteDemoBtn.setOnClickListener {
            startActivity(
                Intent(this, Database::class.java).putExtra(
                    BUTTON_CLICKED_KEY,
                    SQLITE_DEMO_BTN
                )
            )
        }

        addDummySqliteDemoBtn.setOnClickListener {
            insertDataInDBUsingSQLite()
            Toast.makeText(this, "Data added successfully", Toast.LENGTH_LONG).show()
        }
    }

    private fun insertDataInDBUsingSQLite() {

        val databaseManager =
            sqlitedbManager(this)


        databaseManager.insertValue(
            EmployeeDataClass(
                name = "Akash",
                contact = "9650528691",
                address = "Noida"
            )
        )
        databaseManager.insertValue(
            EmployeeDataClass(
                name = "Iron Man",
                contact = "155479742",
                address = "new york"
            )
        )
        databaseManager.insertValue(
            EmployeeDataClass(
                name = "Amarica",
                contact = "3125648845",
                address = "amarica"
            )
        )
    }
    companion object {
        const val BUTTON_CLICKED_KEY = "BUTTON_CLICKED"
        const val SQLITE_DEMO_BTN = "SQLITE_DEMO_BTN"
    }

}