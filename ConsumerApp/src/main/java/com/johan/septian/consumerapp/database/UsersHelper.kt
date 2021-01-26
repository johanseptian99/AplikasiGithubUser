package com.johan.septian.consumerapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.johan.septian.consumerapp.database.DatabaseHelper
import com.johan.septian.consumerapp.database.UsersContract.UsersColumns.Companion.ID_USERS
import com.johan.septian.consumerapp.database.UsersContract.UsersColumns.Companion.TABLE_USERS
import com.johan.septian.consumerapp.database.UsersContract.UsersColumns.Companion.USERNAME
import com.johan.septian.consumerapp.database.UsersContract.UsersColumns.Companion._ID
import java.sql.SQLException

class UsersHelper(context: Context){

    companion object {
        private const val DATABASE_TABLE = TABLE_USERS
        private lateinit var dataBaseHelper: DatabaseHelper
        private var INSTANCE: UsersHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getDatabase(context: Context): UsersHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UsersHelper(context)
            }
    }

    init {
        dataBaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC")
    }

    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$ID_USERS = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(user_id: String): Int {
        return database.delete(DATABASE_TABLE, "$ID_USERS = '$user_id'", null)
    }

    fun deleteByUsername(username: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$username'", null)
    }


    fun checkUsername(username: String): Boolean {
        database = dataBaseHelper.writableDatabase
        val selectId =
            "SELECT * FROM $DATABASE_TABLE WHERE $USERNAME =?"
        val cursor =
            database.rawQuery(selectId, arrayOf(username))
        var check = false
        if (cursor.moveToFirst()) {
            check = true
            var i = 0
            while (cursor.moveToNext()) {
                i++
            }
            Log.d(Constraints.TAG, String.format("%d records found", i))
        }
        cursor.close()
        return check
    }

}