package com.johan.septian.submision3githubuser.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.AVATAR
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.HTML
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.ID_USERS
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.TABLE_USERS
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.TYPE
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.USERNAME
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion._ID

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "db_githubuser"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USERS = "CREATE TABLE $TABLE_USERS" +
                "($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$ID_USERS TEXT NOT NULL, " +
                "$USERNAME TEXT NOT NULL," +
                "$AVATAR TEXT NOT NULL," +
                "$HTML TEXT NOT NULL," +
                "$TYPE TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }


}