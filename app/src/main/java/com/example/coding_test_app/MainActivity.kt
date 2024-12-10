package com.example.coding_test_app

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coding_test_app.contact.NewUser
import com.example.coding_test_app.contact.User
import com.example.coding_test_app.network.InRequestData
import com.example.coding_test_app.network.NetworkClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Getting Users
        fetchUsers()


        val addUser:FloatingActionButton = findViewById(R.id.floatingActionButton)
        addUser.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.add_user_custom_layout)

            val firstNameEditText:EditText = dialog.findViewById(R.id.editTextText)
            val lastNameEditText:EditText = dialog.findViewById(R.id.editTextText2)
            val emailEditText:EditText = dialog.findViewById(R.id.editTextTextEmailAddress)
            val passwordEditText:EditText = dialog.findViewById(R.id.editTextTextPassword)
            val roleIdEditText:EditText = dialog.findViewById(R.id.editTextNumber)
            val isActiveEditText:EditText = dialog.findViewById(R.id.editTextIsActive)
            val customButton:Button = dialog.findViewById(R.id.button)

            customButton.setOnClickListener {
                val firstName = firstNameEditText.text.toString()
                val lastName = lastNameEditText.text.toString()
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                val roleId = roleIdEditText.text.toString().toInt()
                val isActive = isActiveEditText.text.toString().toBoolean()

                // Create NewUser object
                val newUser = NewUser(firstName, lastName, email, password, roleId, isActive)

                // Call the add user API
                addUser(newUser)
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun fetchUsers() {
        NetworkClient.apiService.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        userAdapter = UserAdapter(users)
                        recyclerView.adapter = userAdapter
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load users", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addUser(newUser: NewUser) {
        NetworkClient.apiService.addUser(newUser).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "User added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Failed to add user", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}