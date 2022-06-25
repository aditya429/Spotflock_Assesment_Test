package com.androidsample.spotflock_assesment_test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androidsample.spotflock_assesment_test.databinding.ActivityAddEditNoteBinding
import com.androidsample.spotflock_assesment_test.room.Person
import com.androidsample.spotflock_assesment_test.viewmodel.PersonViewModal
import com.rilixtech.widget.countrycodepicker.CountryCodePicker

class AddEditNoteActivity : AppCompatActivity() {
    // on below line we are creating
    // variables for our UI components.

    private lateinit var binding: ActivityAddEditNoteBinding

    // on below line we are creating variable for
    // viewmodal and and integer for our note id.
    lateinit var viewModal: PersonViewModal
    var noteID = -1;
    lateinit var selectedRadio: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // on below line we are initialing our view modal.
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(PersonViewModal::class.java)

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)
        binding.toolbar.setNavigationOnClickListener(View.OnClickListener {

            val intent = Intent(this@AddEditNoteActivity, MainActivity::class.java)

            startActivity(intent)
        })

       selectedRadio = "Personal"
        binding.ccp.registerPhoneNumberTextView(binding.edtPhone)


        // on below line we are getting data passed via an intent.
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            // on below line we are setting data to edit text.
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteMobile = intent.getStringExtra("noteNumber")
            val noteEmail = intent.getStringExtra("noteEmail")
            val noteContactType = intent.getStringExtra("noteDataType")




            if (noteMobile.toString().contains("+")) {
                noteMobile.toString().replace("+", "")
            }


            val sub: String = noteMobile.toString().substring(0, 2)
            val remainder: String = noteMobile.toString().substring(2)


            binding.ccp.setCountryForPhoneCode(sub.toInt())
            binding.edtPhone.setText(remainder)

            noteID = intent.getIntExtra("noteId", -1)
            binding.idBtn.setText("Update Contact")
            binding.idEdtNoteName.setText(noteTitle)
            binding.idEdtNoteEmail.setText(noteEmail)
            if (noteContactType.equals("Personal")) {
                binding.radioPersonal.isChecked = true
                selectedRadio = "Personal"
            } else {
                binding.radioBusiness.isChecked = true
                selectedRadio = "Business"

            }

            // noteEdt.setText(noteDescription)
        } else {
            binding.idBtn.setText("Save Contact")
        }

        // on below line we are adding
        // click listener to our save button.
        binding.idBtn.setOnClickListener {
            // on below line we are getting
            // title and desc from edit text.

            if (checkValidity(binding.ccp)) {
                val noteTitle = binding.idEdtNoteName.text.toString()
                val noteEmail = binding.idEdtNoteEmail.text.toString()

                // on below line we are checking the type
                // and then saving or updating the data.
                if (noteType.equals("Edit")) {
                    if (noteTitle.isNotEmpty() && binding.ccp.fullNumber.isNotEmpty()) {

                        val updatedNote = Person(
                            noteTitle,
                            binding.ccp.fullNumber,
                            noteEmail,
                            selectedRadio,
                            "https://www.seekpng.com/png/detail/966-9665493_my-profile-icon-blank-profile-image-circle.png"
                        )
                        updatedNote.id = noteID
                        viewModal.updateNote(updatedNote)
                        Toast.makeText(this, "Contact Updated..", Toast.LENGTH_LONG).show()
                    }
                } else {
                    if (noteTitle.isNotEmpty() && binding.ccp.fullNumber.isNotEmpty()) {

                        // if the string is not empty we are calling a
                        // add note method to add data to our room database.
                        viewModal.addNote(
                            Person(
                                noteTitle,
                                binding.ccp.fullNumber,
                                noteEmail,
                                selectedRadio,
                                "https://www.seekpng.com/png/detail/966-9665493_my-profile-icon-blank-profile-image-circle.png"
                            )
                        )
                        Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                    }
                }

                // opening the new activity on below line
                startActivity(Intent(applicationContext, MainActivity::class.java))
                this.finish()


            } else {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun checkValidity(ccp: CountryCodePicker): Boolean {

        return if (ccp.isValid) {
            Toast.makeText(this, "number " + ccp.fullNumber + " is valid.", Toast.LENGTH_SHORT)
                .show()
            true
        } else {
            false
        }

    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_personal ->
                    if (checked) {
                        // Pirates are the best
                        selectedRadio = "Personal"
                    }
                R.id.radio_business ->
                    if (checked) {
                        selectedRadio = "Business"
                        // Ninjas rule
                    }
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@AddEditNoteActivity, MainActivity::class.java)

        startActivity(intent)
    }

}
