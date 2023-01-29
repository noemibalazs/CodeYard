package com.example.codeyard.contactlist

import com.example.codeyard.User

interface ContactListener {

    fun onUserClicked(user: User)

    fun onAddButtonClicked()
}