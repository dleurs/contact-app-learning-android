package fr.dleurs.android.contactapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.databinding.ContactItemBinding
import fr.dleurs.android.contactapp.model.Contact
import fr.dleurs.android.contactapp.ui.main.OnClick
import timber.log.Timber

class ContactAdapter() : RecyclerView.Adapter<ContactViewHolder>(), OnClick {

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
            false
        )
        return ContactViewHolder(withDataBinding)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.contact = contacts[position]
            it.onClick = this
        }
    }

    override fun onItemClick(contact: Contact) {
        Timber.i("Item clicked !!!")
    }

}


class ContactViewHolder(val viewDataBinding: ContactItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.contact_item
    }
}