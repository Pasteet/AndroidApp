package com.example.labandroidapp

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "establishments") //establishments table
data class Establishment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val address: String,
    val type: String,
    @TypeConverters(MenuItemConverter::class) val menu: List<MenuItem> = listOf()
) : Parcelable { // works by converting data to parcels for storage and transfer, serialization
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(MenuItem.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(type)
        parcel.writeTypedList(menu)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Establishment> {
        override fun createFromParcel(parcel: Parcel): Establishment {
            return Establishment(parcel)
        }

        override fun newArray(size: Int): Array<Establishment?> {
            return arrayOfNulls(size)
        }
    }
}

data class MenuItem(
    val name: String,
    val price: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MenuItem> {
        override fun createFromParcel(parcel: Parcel): MenuItem {
            return MenuItem(parcel)
        }

        override fun newArray(size: Int): Array<MenuItem?> {
            return arrayOfNulls(size)
        }
    }
}

//Convert list of menu items to JSON
class MenuItemConverter {
    @TypeConverter
    fun fromMenuItemList(menu: List<MenuItem>): String {
        val gson = Gson()
        return gson.toJson(menu)
    }

    //Convert JSON to list of menu items objects
    @TypeConverter
    fun toMenuItemList(data: String): List<MenuItem> {
        val listType = object : TypeToken<List<MenuItem>>() {}.type
        return Gson().fromJson(data, listType)
    }
}
