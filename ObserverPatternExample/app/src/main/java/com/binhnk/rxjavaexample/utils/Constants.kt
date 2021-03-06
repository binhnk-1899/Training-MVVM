package com.binhnk.rxjavaexample.utils

class Constants {

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "DatumDatabase.db"
        const val MOCK_DATA = false

        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        const val CONNECT_TIMEOUT = 10L
        const val READ_TIMEOUT = 10L
        const val WRITE_TIMEOUT = 10L

        const val MENU_SORT_NAME_AZ = 0
        const val MENU_SORT_NAME_ZA = 1
        const val MENU_SORT_ID = 2

        const val STATE_CREATING = 0
        const val STATE_CREATE_FAILED = 1
        const val STATE_CREATE_SUCCESS = 2
    }
}