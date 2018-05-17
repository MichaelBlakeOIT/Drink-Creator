package cst.michael.drinkcreator.data.models

import android.provider.BaseColumns

object DrinkSQLObject {
    // Table contents are grouped together in an anonymous object.
    object DrinkSQL : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_SUBTITLE = "subtitle"
    }
}