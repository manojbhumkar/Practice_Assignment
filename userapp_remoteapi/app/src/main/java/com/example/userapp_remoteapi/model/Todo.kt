package com.example.userapp_remoteapi.model

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)