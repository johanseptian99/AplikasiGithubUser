package com.johan.septian.submision3githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.johan.septian.submision3githubuser.database.UsersContract.AUTHORITY_USER
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.CONTENT_URI_USERS
import com.johan.septian.submision3githubuser.database.UsersContract.UsersColumns.Companion.TABLE_USERS
import com.johan.septian.submision3githubuser.database.UsersHelper

class UsersProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var usersHelper: UsersHelper
        init {
            sUriMatcher.addURI(AUTHORITY_USER, TABLE_USERS, USER)


            sUriMatcher.addURI(AUTHORITY_USER, "$TABLE_USERS/#", USER_ID)
        }
    }

    override fun onCreate(): Boolean {
        usersHelper = UsersHelper.getDatabase(context as Context)
        usersHelper.open()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            USER -> usersHelper.queryAll()
            USER_ID -> usersHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USER_ID) {
            sUriMatcher.match(uri) -> usersHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_USERS, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (USER) {
            sUriMatcher.match(uri) -> usersHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_USERS, null)
        return Uri.parse("$CONTENT_URI_USERS/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        val updated: Int = when (USER_ID) {
            sUriMatcher.match(uri) -> usersHelper.update(uri.lastPathSegment.toString(),values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI_USERS, null)
        return updated
    }
}