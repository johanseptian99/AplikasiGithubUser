package com.johan.septian.consumerapp.mapping

import android.database.Cursor
import com.johan.septian.consumerapp.database.UsersContract
import com.johan.septian.consumerapp.model.Repository
import com.johan.septian.consumerapp.model.Users

object MappingHelper {

    fun mapCursorUsersToList(usersCursor: Cursor?): ArrayList<Users> {
        val usersList = ArrayList<Users>()
        usersCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(UsersContract.UsersColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(UsersContract.UsersColumns.AVATAR))
                val html_url = getString(getColumnIndexOrThrow(UsersContract.UsersColumns.HTML))
                val type = getString(getColumnIndexOrThrow(UsersContract.UsersColumns.TYPE))
                val id_user = getInt(getColumnIndexOrThrow(UsersContract.UsersColumns.ID_USERS))
                usersList.add(Users(username,avatar,html_url,type,id_user))
            }
        }
        return usersList
    }

    fun mapCursorUsersToObject(usersCursor: Cursor?): Users {
        var usersList = Users("","","","",0)
        usersCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(UsersContract.UsersColumns.USERNAME))
                val avatar = getString(getColumnIndexOrThrow(UsersContract.UsersColumns.AVATAR))
                val html_url = getString(getColumnIndexOrThrow(UsersContract.UsersColumns.HTML))
                val type = getString(getColumnIndexOrThrow(UsersContract.UsersColumns.TYPE))
                val id_user = getInt(getColumnIndexOrThrow(UsersContract.UsersColumns.ID_USERS))
                usersList = Users(username,avatar,html_url,type,id_user)
            }
        }
        return usersList
    }

}