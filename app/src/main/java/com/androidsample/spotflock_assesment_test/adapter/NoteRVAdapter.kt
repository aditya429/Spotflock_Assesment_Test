package com.androidsample.spotflock_assesment_test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidsample.spotflock_assesment_test.MainActivity
import com.androidsample.spotflock_assesment_test.databinding.AdapterMovieBinding
import com.androidsample.spotflock_assesment_test.room.Person
import com.bumptech.glide.Glide

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: MainActivity,
    val noteClickInterface: MainActivity
) :
    RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    // on below line we are creating a
    // variable for our all notes list.
    private val allNotes = ArrayList<Person>()

   /* // on below line we are creating a view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // on below line we are creating an initializing all our
        // variables which we have added in layout file.
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNote)
        val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }*/

    class ViewHolder(val binding: AdapterMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.

        holder.binding.name.text = allNotes.get(position).contactName
        holder.binding.phone.text = allNotes.get(position).contactNumber

        Glide.with(holder.itemView.context).load(allNotes.get(position).contactAvatar).into(holder.binding.thumbnail)

        holder.binding.idIVDelete.setOnClickListener {
            // on below line we are calling a note click
            // interface and we are passing a position to it.
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }

        // on below line we are adding click listener
        // to our recycler view item.
        holder.itemView.setOnClickListener {
            // on below line we are calling a note click interface
            // and we are passing a position to it.
            noteClickInterface.onNoteClick(allNotes.get(position))
        }

    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our list size.
        return allNotes.size
    }

    // below method is use to update our list of notes.
    fun updateList(newList: List<Person>) {
        // on below line we are clearing
        // our notes array list
        allNotes.clear()
        // on below line we are adding a
        // new list to our all notes list.
        allNotes.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }

    interface NoteClickDeleteInterface {
        // creating a method for click
        // action on delete image view.
        fun onDeleteIconClick(note: Person)
    }

    interface NoteClickInterface {
        // creating a method for click action
        // on recycler view item for updating it.
        fun onNoteClick(note: Person)
    }
    }

