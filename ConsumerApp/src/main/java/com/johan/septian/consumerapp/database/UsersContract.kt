package com.johan.septian.consumerapp.database

import android.net.Uri
import android.provider.BaseColumns

object UsersContract {

    const val AUTHORITY_USER = "com.johan.septian.submision3githubuser"
    const val SCHEME_USER = "content"

    class UsersColumns : BaseColumns {
        companion object {
            const val TABLE_USERS = "user_table"
            const val _ID = "id"
            const val ID_USERS = "id_user"
            const val USERNAME = "username"
            const val AVATAR = "avatar"
            const val HTML = "html"
            const val TYPE = "type"

            val CONTENT_URI_USERS: Uri = Uri.Builder().scheme(SCHEME_USER)
                    .authority(AUTHORITY_USER)
                    .appendPath(TABLE_USERS)
                    .build()
        }
    }
}