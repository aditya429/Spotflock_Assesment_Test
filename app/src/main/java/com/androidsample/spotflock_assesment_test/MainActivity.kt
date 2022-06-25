package com.androidsample.spotflock_assesment_test


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.androidsample.spotflock_assesment_test.adapter.NoteRVAdapter
import com.androidsample.spotflock_assesment_test.databinding.ActivityMainBinding
import com.androidsample.spotflock_assesment_test.room.Person
import com.androidsample.spotflock_assesment_test.utils.RetrofitService
import com.androidsample.spotflock_assesment_test.viewmodel.PersonViewModal
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity(), NoteRVAdapter.NoteClickInterface,
    NoteRVAdapter.NoteClickDeleteInterface {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: PersonViewModal
    var itemsize: Int = 0


    private val retrofitService = RetrofitService.getInstance()
  //  val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val sharedPreferences: SharedPreferences = this.getSharedPreferences("local_data", Context.MODE_PRIVATE)
        //   viewModel = ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PersonViewModal::class.java)


        // on below line we are initializing our adapter class.
        val noteRVAdapter = NoteRVAdapter(this, this, this)

        // on below line we are setting
        // adapter to our recycler view.
       // notesRV.adapter = noteRVAdapter
        binding.recyclerview.adapter = noteRVAdapter

        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")


            // save into local
            it.forEach{
                    item ->

                Log.e("item",item.contact_name);

                val sharedIdValue = sharedPreferences.getInt("id_key",0)
                if (sharedIdValue==0){
                    viewModel.addNote(Person(item.contact_name,item.contact_number,item.contact_email,item.contact_type,item.contact_avatar))
                }
                else{
                    viewModel.updateNote(Person(item.contact_name,item.contact_number,item.contact_email,item.contact_type,item.contact_avatar))
                }



            }


          //  adapter.setMovieList(it)
        })

        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                // on below line we are updating our list.


                itemsize=it.size
                val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putInt("id_key",itemsize)
                editor.apply()
                editor.commit()
                noteRVAdapter.updateList(it)

            }
        })
        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getAllMovies()



        binding.idFAB.setOnClickListener {
            // adding a click listener for fab button
            // and opening a new intent to add a new note.
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        binding.filterIcon.setOnClickListener(View.OnClickListener {

// on below line we are creating a new bottom sheet dialog.
            val dialog = BottomSheetDialog(this)

            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.bottom_dialog, null)

            // on below line we are creating a variable for our button
            // which we are using to dismiss our dialog.
            val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)

            val alltext = view.findViewById<TextView>(R.id.alldata)
            val personaltext = view.findViewById<TextView>(R.id.personaldata)
            val businesstext = view.findViewById<TextView>(R.id.businessdata)


            alltext.setOnClickListener(View.OnClickListener {

                viewModel.allNotes.observe(this, Observer { list ->
                    list?.let {
                        // on below line we are updating our list.

                        noteRVAdapter.updateList(it)

                    }
                })
                dialog.dismiss()

            })

            personaltext.setOnClickListener(View.OnClickListener {

                viewModel.allNotesPersonal.observe(this, Observer { list ->
                    list?.let {
                        // on below line we are updating our list.


                        noteRVAdapter.updateList(it)

                    }
                })
                dialog.dismiss()

            })

            businesstext.setOnClickListener(View.OnClickListener {

                viewModel.allNotesBusiness.observe(this, Observer { list ->
                    list?.let {
                        // on below line we are updating our list.

                        noteRVAdapter.updateList(it)

                    }
                })

                dialog.dismiss()

            })

            // on below line we are adding on click listener
            // for our dismissing the dialog button.
            btnClose.setOnClickListener {
                // on below line we are calling a dismiss
                // method to close our dialog.
                dialog.dismiss()
            }
            // below line is use to set cancelable to avoid
            // closing of dialog box when clicking on the screen.
            dialog.setCancelable(false)

            // on below line we are setting
            // content view to our view.
            dialog.setContentView(view)

            // on below line we are calling
            // a show method to display a dialog.
            dialog.show()


        })
    }

    override fun onNoteClick(note: Person) {
        // opening a new intent and passing a data to it.
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.contactName)
        intent.putExtra("noteNumber", note.contactNumber)
        intent.putExtra("noteEmail", note.contactEmail)
        intent.putExtra("noteDataType", note.contactType)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Person) {
        // in on note click method we are calling delete
        // method from our view modal to delete our not.
        viewModel.deleteNote(note)
        // displaying a toast message
        Toast.makeText(this, "${note.contactName} Deleted", Toast.LENGTH_LONG).show()
    }
}