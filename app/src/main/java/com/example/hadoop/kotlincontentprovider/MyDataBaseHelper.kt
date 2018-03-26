package com.example.hadoop.kotlincontentprovider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by 张仲光 on 2018/3/4.
 */
class MyDataBaseHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREAT_BOOK)
        db?.execSQL(CREATE_CATEGORY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists "+TABLE_BOOK)
        db?.execSQL("drop table if exists "+TABLE_CATEGORY)
        this.onCreate(db)
    }

    companion object {
        @JvmStatic
        val TABLE_BOOK = "Book"
        @JvmStatic
        val TABLE_CATEGORY = "Category"  //name,pages,author,price
        val CREAT_BOOK = "create table "+TABLE_BOOK + "(" +
                "id integer primary key autoincrement," +
                "author text," +
                "price real," +
                "pages integer," +
                "name text)"
        val CREATE_CATEGORY = ("create table Category ("
                + "id integer primary key autoincrement, "
                + "category_name text, "
                + "category_code integer)")

    }
}