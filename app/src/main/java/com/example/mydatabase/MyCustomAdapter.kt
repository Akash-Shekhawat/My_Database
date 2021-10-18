package com.example.mydatabase

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mydatabase.sqlite.sqlitedbManager

class MyCustomAdapter (
    private val context: Context,
    private val employeeData: ArrayList<EmployeeDataClass>
) : RecyclerView.Adapter<MyCustomAdapter.ViewHolder>() {
    private val databaseManager =
        sqlitedbManager(context)



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val empDetailsConstraintLayout: ConstraintLayout =
            view.findViewById<ConstraintLayout>(R.id.empDetailsContraintLayout)
        val empDetailsEditConstraintLayout: ConstraintLayout =
            view.findViewById<ConstraintLayout>(R.id.editEmpDetailsConstraintLayout)


        val nameTextView: TextView = view.findViewById(R.id.tvEmpName)
        val contactTextView: TextView = view.findViewById(R.id.tvEmpContact)
        val addressTextView: TextView = view.findViewById(R.id.tvEmpAddress)
        val editDataImgBtn: ImageButton = view.findViewById(R.id.btnEditEmpDetails)
        val deleteDataImgBtn: ImageButton = view.findViewById<ImageButton>(R.id.btndel)


        val nameEditTextView: EditText = view.findViewById(R.id.tvEmpNameEdit)
        val contactEditTextView: EditText = view.findViewById(R.id.tvEmpContactEdit)
        val addressEditTextView: TextView = view.findViewById(R.id.tvEmpAddressEdit)

        val updateDetailsBtn: Button = view.findViewById(R.id.btnUpdateDetails)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = employeeData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val empId = employeeData[position].id
        val empName = employeeData[position].name!!
        val empContact = employeeData[position].contact!!
        val empAddress = employeeData[position].address!!

        holder.nameTextView.text = empName
        holder.contactTextView.text = empContact
        holder.addressTextView.text = empAddress

        holder.deleteDataImgBtn.setOnClickListener {
            deleteDataFromDB(empId, position)
        }

        holder.editDataImgBtn.setOnClickListener {
            updateUI(holder, empName, empContact, empAddress)

            holder.updateDetailsBtn.setOnClickListener {
                val updatedName = holder.nameEditTextView.text.toString()
                val updatedContact = holder.contactEditTextView.text.toString()
                val updatedAddress = holder.addressEditTextView.text.toString()

                Log.i("Data", "=== Name: $updatedName, Contact: $updatedContact, Address: $updatedAddress ===")

                updateDetailsInDB(
                    empId,
                    updatedName,
                    updatedContact,
                    updatedAddress,
                    position,
                    holder
                )
            }
        }
    }


    private fun updateUI(
        holder: ViewHolder,
        empName: String,
        empContact: String,
        empAddress: String
    ) {
        // Add the data to edit text
        holder.nameEditTextView.setText(empName)
        holder.contactEditTextView.setText(empContact)
        holder.addressEditTextView.setText(empAddress)


        holder.empDetailsConstraintLayout.visibility = View.INVISIBLE


        holder.empDetailsEditConstraintLayout.visibility = View.VISIBLE
    }

    private fun updateDetailsInDB(
        empId: Int,
        updatedName: String,
        updatedContact: String,
        updatedAddress: String,
        position: Int,
        holder: ViewHolder
    ) {

        // Update details using SQLite Manager
        val numOfRowUpdated =
            databaseManager.updateAnEmployeeData(
                empId,
                updatedName,
                updatedContact,
                updatedAddress
            )
        if (numOfRowUpdated > 0) {


            holder.empDetailsEditConstraintLayout.visibility = View.GONE

            holder.empDetailsConstraintLayout.visibility = View.VISIBLE

            Toast.makeText(
                context,
                "Employee with Id: $empId is updated",
                Toast.LENGTH_SHORT
            ).show()

            setItem(position, empId, updatedName, updatedContact, updatedAddress)

        } else {
            Toast.makeText(
                context,
                "There is a problem while updating the data",
                Toast.LENGTH_SHORT
            )
                .show()
        }

    }

    private fun deleteDataFromDB(empId: Int, position: Int) {
        // this will remove data from DB
        val numOfRowDeleted = databaseManager.deleteAnEmployeeData(empId)
        if (numOfRowDeleted > 0) {
            Toast.makeText(
                context,
                "Employee with Id: $empId is deleted",
                Toast.LENGTH_SHORT
            ).show()

            deleteItem(position)
        } else {
            Toast.makeText(
                context,
                "There is a problem while deleting the data",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Update the item in list
    private fun setItem(
        position: Int,
        empID: Int,
        empName: String,
        empContact: String,
        empAddress: String
    ) {
        employeeData[position] = EmployeeDataClass(empID, empName, empContact, empAddress)
        notifyDataSetChanged()
    }


    private fun deleteItem(position: Int) {
        employeeData.removeAt(position)
        notifyDataSetChanged()
    }
}