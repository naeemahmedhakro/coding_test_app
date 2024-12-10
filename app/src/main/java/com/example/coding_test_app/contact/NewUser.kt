package com.example.coding_test_app.contact

data class NewUser(
    val firstName: String,
    val lastName: String,
    val emailId: String,
    val password: String,
    val roleId: Int,
    val isActive: Boolean
)
