package com.androidsample.spotflock_assesment_test.room

import androidx.lifecycle.LiveData
import androidx.room.*


// annotation for dao class.
@Dao
public interface PersonDao {

    // below is the insert method for
    // adding a new entry to our database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(note: Person) : Long

    // below is the delete method
    // for deleting our note.
    @Delete
    suspend fun delete(note: Person)

    // below is the method to read all the notes
    // from our database we have specified the query for it.
    // inside the query we are arranging it in ascending
    // order on below line and we are specifying
    // the table name from which
    // we have to get the data.
    @Query("Select * from contactsTable order by id ASC")
    fun getAllNotes(): LiveData<List<Person>>

    // below method is use to update the note.
    @Update(onConflict = OnConflictStrategy.REPLACE)
     fun update(note: Person)

    @Query("SELECT EXISTS (SELECT 1 FROM contactsTable WHERE id = :id)")
    fun exists(id: Int): Boolean


    @Query("SELECT EXISTS(SELECT * FROM contactsTable WHERE contact_name = :contact_name )")
    fun isRecordExistsUserId(contact_name: String?): Boolean

    @Transaction
    fun upsert(entity: Person) {
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Query("SELECT * FROM contactsTable WHERE contact_type=:date")
    fun getFilterRecords(date: String): LiveData<List<Person>>
}