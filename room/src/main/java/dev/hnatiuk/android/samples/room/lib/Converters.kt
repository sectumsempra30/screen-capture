package dev.hnatiuk.android.samples.room.lib

import androidx.room.TypeConverter

class Converters {

//    @TypeConverter
//    fun stringToChatStatus(value: String): ChatStatus {
//        return ChatStatus.valueOf(value)
//    }
//
//    @TypeConverter
//    fun chatStatusToString(value: ChatStatus): String {
//        return value.toString()
//    }

    @TypeConverter
    fun stringToListOfStrings(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun listOfStringsToString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}