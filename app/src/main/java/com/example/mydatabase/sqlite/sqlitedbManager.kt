package com.example.mydatabase.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.mydatabase.EmployeeDataClass

class sqlitedbManager(private val context: Context) {
    private val databaseHelper =
        sqlitedbhelper(context)

    private val TAG = "DB_MANAGER"

    fun insertValue(employeeDataClass: EmployeeDataClass) {
        // get writable database as we want to write data
        val db = databaseHelper.writableDatabase



        val contentValues = ContentValues()
        contentValues.put(sqlitedbhelper.COLUMN_NAME, employeeDataClass.name)
        contentValues.put(sqlitedbhelper.COLUMN_CONTACT, employeeDataClass.contact)
        contentValues.put(sqlitedbhelper.COLUMN_ADDRESS, employeeDataClass.address)

        // insert is predefined function to insert values
        val id = db.insert(sqlitedbhelper.TABLE_NAME, null, contentValues)

        Log.i(TAG, "Data inserted with Id: $id")


        db.close()
    }


    @SuppressLint("Range")
    fun getAllEmpDataFromSQLiteDB(): ArrayList<EmployeeDataClass> {

        val db = databaseHelper.writableDatabase


        val selectQuery = "SELECT  * FROM " + sqlitedbhelper.TABLE_NAME
        val cursor = db.rawQuery(selectQuery, null)

        val employeesList: ArrayList<EmployeeDataClass> = arrayListOf()

        if (cursor.moveToFirst()) {
            do {

                val employee =
                    EmployeeDataClass()
                employee.id =
                    cursor.getInt(cursor.getColumnIndex(sqlitedbhelper.COLUMN_ID))
                employee.name =
                    cursor.getString(cursor.getColumnIndex(sqlitedbhelper.COLUMN_NAME))
                employee.contact =
                    cursor.getString(cursor.getColumnIndex(sqlitedbhelper.COLUMN_CONTACT))
                employee.address =
                    cursor.getString(cursor.getColumnIndex(sqlitedbhelper.COLUMN_ADDRESS))

                employeesList.add(employee)

            } while (cursor.moveToNext())
        }
        cursor.close()

        return employeesList
    }

    fun updateAnEmployeeData(empID: Int, name: String, contact: String, address: String): Int {

        val db = databaseHelper.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(sqlitedbhelper.COLUMN_NAME, name)
        contentValues.put(sqlitedbhelper.COLUMN_CONTACT, contact)
        contentValues.put(sqlitedbhelper.COLUMN_ADDRESS, address)

        return db.update(
            sqlitedbhelper.TABLE_NAME,
            contentValues,
            sqlitedbhelper.COLUMN_ID + "=?",
            arrayOf(empID.toString())
        )
    }

    fun deleteAnEmployeeData(empID: Int): Int {

        val db = databaseHelper.writableDatabase

        return db.delete(
            sqlitedbhelper.TABLE_NAME,
            sqlitedbhelper.COLUMN_ID + "=?",
            arrayOf(empID.toString())
        )
    }
}