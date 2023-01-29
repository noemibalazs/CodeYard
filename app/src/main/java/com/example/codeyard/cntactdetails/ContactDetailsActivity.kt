package com.example.codeyard.cntactdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.Glide
import com.example.codeyard.*
import com.example.codeyard.databinding.ActivityContactDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactDetailsActivity @Inject constructor() : AppCompatActivity(), MenuProvider {

    private lateinit var binding: ActivityContactDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_details)
        binding.lifecycleOwner = this@ContactDetailsActivity

        val bundle = intent.getBundleExtra(INTENT_KEY)
        val user = bundle?.getParcelable<User>(BUNDLE_KEY) as User
        populateUI(user)

        setOptionMenu()
    }

    private fun populateUI(user: User) {
        println("User: $user")
        binding.apply {
            Glide.with(this@ContactDetailsActivity)
                .load(user.picture.medium)
                .into(userPicture)

            userName.text =
                getString(R.string.user_name_label, user.name.firstName, user.name.lastName)

            userMobileNumber.text = user.mobileNumber
            userWorkNumber.text = user.phoneNumber
            userEmailAddress.text = user.email
        }
    }

    private fun setOptionMenu() {
        val menuHost: MenuHost = this
        menuHost.addMenuProvider(this, this, Lifecycle.State.STARTED)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_hamburger, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
}