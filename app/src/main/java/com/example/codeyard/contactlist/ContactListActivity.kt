package com.example.codeyard.contactlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import com.example.codeyard.contactdetails.ContactDetailsActivity
import com.example.codeyard.R
import com.example.codeyard.User
import com.example.codeyard.databinding.ActivityContactListBinding
import com.example.codeyard.displayToast
import com.example.codeyard.launchActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ContactListActivity @Inject constructor() : AppCompatActivity(), ContactListener,
    MenuProvider {

    private val viewModel: ContactListViewModel by viewModels()
    private val adapter: ContactAdapter by lazy { ContactAdapter(this) }

    private lateinit var binding: ActivityContactListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list)

        with(binding) {
            viewModel = viewModel
            listener = this@ContactListActivity
            userRecycleView.adapter = adapter
            lifecycleOwner = this@ContactListActivity
            toolbar.setNavigationOnClickListener {
                displayToast(getString(R.string.pending_functionality))
            }
        }

        setOptionMenu()

        with(viewModel) {
            getUsers()

            users.observe(this@ContactListActivity) {
                adapter.submitList(it)
                binding.errorMessage.isVisible = it.isEmpty()
            }

            viewState.observe(this@ContactListActivity) {
                when (it) {
                    is ContactListViewModel.ViewState.UsersLoading -> setProgressVisibility(true)
                    else -> setProgressVisibility(false)
                }
            }
        }
    }

    private fun setProgressVisibility(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

    private fun setOptionMenu() {
        val menuHost: MenuHost = this
        menuHost.addMenuProvider(this, this, Lifecycle.State.STARTED)
    }

    override fun onUserClicked(user: User) =
        launchActivity(ContactDetailsActivity::class.java, user)

    override fun onAddButtonClicked() = displayToast(getString(R.string.pending_functionality))

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_search, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        val searchMenuItem = menu.findItem(R.id.menuSearch)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchMenuItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank() && newText.length > 6)
                    displayToast(newText)
                return true
            }
        })
    }
}