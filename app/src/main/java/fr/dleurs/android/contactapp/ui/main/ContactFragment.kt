package fr.dleurs.android.contactapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.databinding.FragmentContactBinding
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.ui.ContactAdapter
import fr.dleurs.android.contactapp.utils.FabButtonInterface
import fr.dleurs.android.contactapp.viewmodel.ContactViewModel

const val EXTRA_CONTACT = "fr.dleurs.android.contactapp.CONTACT"

class ContactFragment() : Fragment(R.layout.fragment_contact) {


    private val contactAdapter: ContactAdapter = ContactAdapter()
    private val viewModel: ContactViewModel by activityViewModels()
    private lateinit var fabButtonInterface: FabButtonInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveContacts.observe(viewLifecycleOwner, Observer<List<Contact>> { contacts -> // this or viewLifecycleOwner ?
            contacts?.let {
                contactAdapter.contacts = it
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.let {
            instantiateFabButtonInterface(it)
        }

        val binding: FragmentContactBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_contact,
            container,
            false
        )
        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.setLifecycleOwner(viewLifecycleOwner)

        //binding.viewModel = viewModel no binding in viewModel

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
        }

        binding.root.findViewById<FloatingActionButton>(R.id.fab).apply {
            this.setOnClickListener {
                fabButtonInterface.goToCreateContactActivity()
            }
        }


        // Observer for the network error.
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

        return binding.root
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    private fun instantiateFabButtonInterface(context: FragmentActivity) {
        fabButtonInterface = context as FabButtonInterface
    }

}



