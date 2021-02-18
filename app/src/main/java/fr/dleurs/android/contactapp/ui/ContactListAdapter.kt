package fr.dleurs.android.contactapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.dleurs.android.contactapp.R
import fr.dleurs.android.contactapp.model.Contact

class ContactListAdapter() : ListAdapter<Contact, ContactListAdapter.ContactViewHolder>(
        CONTACTS_COMPARATOR
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstNameItemView: TextView = itemView.findViewById(R.id.recycler_view_item_name)

        var data: Contact? = null

        init {
/*            deleteContact.setOnClickListener {
                data?.let {
                    contactInterface.onItemClick(it)
                }
            }*/
        }

        fun bind(item: Contact?) {
            // assign it to `data` so it can be used with `setOnClickListener`
            data = item
            firstNameItemView.text = item?.firstName

        }

        companion object {
            fun create(parent: ViewGroup): ContactViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view_item, parent, false)
                return ContactViewHolder(view)
            }
        }
    }

    companion object {
        private val CONTACTS_COMPARATOR = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return (oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName && oldItem.mail == newItem.mail)
            }
        }
    }
}