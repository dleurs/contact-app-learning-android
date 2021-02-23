package fr.dleurs.android.contactapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.databinding.ContactItemBinding
import fr.dleurs.android.contactapp.databinding.FragmentContactBinding
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel

class ContactFragment(val ARG_SECTION_NUMBER: Int) : Fragment(R.layout.fragment_contact) {

    private val viewModel: ContactViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, ContactViewModel.Factory(activity.application))
            .get(ContactViewModel::class.java)
    }

    private val viewModelAdapter: ContactAdapter = ContactAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(ARG_SECTION_NUMBER);
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<Contact>> { contacts ->
            contacts?.let {
                viewModelAdapter.contacts = it
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentContactBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_contact,
            container,
            false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        //binding.viewModel = viewModel no binding in viewModel

       binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}



