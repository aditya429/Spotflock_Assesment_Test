package com.androidsample.spotflock_assesment_test.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// on below line we are specifying our table name
@Entity(tableName = "contactsTable")

// on below line we are specifying our column infor
// and inside that we are passing our column name

/*"contact_name": "Sai",
"contact_number": "+91-9123456789",
"contact_email": "abc@gmail.com",
"contact_type": "Personal",
"contact_avatar": "https://www.seekpng.com/png/detail/966-9665493_my-profile-icon-blank-profile-image-circle.png"*/
public class Person (@ColumnInfo(name = "contact_name")val contactName :String,@ColumnInfo(name = "contact_number")val contactNumber :String,
              @ColumnInfo(name = "contact_email")val contactEmail :String, @ColumnInfo(name = "contact_type")val contactType :String, @ColumnInfo(name = "contact_avatar")val contactAvatar :String) {
    // on below line we are specifying our key and
    // then auto generate as true and we are
    // specifying its initial value as 0
    @PrimaryKey(autoGenerate = true) var id = 0
}