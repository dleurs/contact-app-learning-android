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

class ContactFragment : Fragment(R.layout.fragment_contact) {

    private val viewModel: ContactViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, ContactViewModel.Factory(activity.application))
            .get(ContactViewModel::class.java)
    }

    private var viewModelAdapter: ContactAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<Contact>> { contacts ->
            contacts?.apply {
                viewModelAdapter?.contacts = contacts
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

        //binding.viewModel = viewModel


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


class ContactAdapter() : RecyclerView.Adapter<ContactViewHolder>() {

    /**
     * The videos that our Adapter will show
     */
    var contacts: List<Contact> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val withDataBinding: ContactItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ContactViewHolder.LAYOUT,
            parent,
            false)
        return ContactViewHolder(withDataBinding)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.contact = contacts[position]
        }
    }

}

class ContactViewHolder(val viewDataBinding: ContactItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.contact_item
    }
}