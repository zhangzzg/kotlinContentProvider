package com.example.hadoop.kotlincontentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

/**
 * Created by 张仲光 on 2018/3/7.
 */
class MyProviderContent :ContentProvider() {
    val uriMaccher:UriMatcher? = Holder.getUriMaccher()
    var myDataBaseHelper: MyDataBaseHelper? = null

    companion object {
        val TABLE1_DIR = 0
        val TABLE1_ITEM = 1
        val TABLE2_DIR = 2
        val TABLE2_ITEM = 3
        val authority = "com.example.hadoop.kotlincontentprovider.provider"
    }

    object Holder{
        fun getUriMaccher():UriMatcher{
            val uriMaccher = UriMatcher(UriMatcher.NO_MATCH)
            uriMaccher.addURI(authority,"book",TABLE1_DIR)
            uriMaccher.addURI(authority,"book/#",TABLE1_ITEM)
            uriMaccher.addURI(authority,"category",TABLE2_DIR)
            uriMaccher.addURI(authority,"category/#",TABLE2_ITEM)
            return uriMaccher
        }
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        val db  = myDataBaseHelper?.writableDatabase
        var uriReturn:Uri? = null
        when(uriMaccher?.match(uri)){
            TABLE1_DIR,TABLE1_ITEM -> {
                val newBookId = db?.insert(MyDataBaseHelper.TABLE_BOOK,null,values)
                uriReturn = Uri.parse("content://" + authority + "/book/"+ newBookId)
            }

            TABLE2_DIR,TABLE2_ITEM -> {
                val newCategoryId = db?.insert(MyDataBaseHelper.TABLE_CATEGORY,null,values)
                uriReturn = Uri.parse("content://" + authority + "/category/"+ newCategoryId)
            }
        }
       return uriReturn
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val db  = myDataBaseHelper?.readableDatabase
        var cursor:Cursor? = null
        when(uriMaccher?.match(uri)){

        TABLE1_DIR -> {
            cursor = db?.query(MyDataBaseHelper.TABLE_BOOK,projection,selection,selectionArgs,null,null,sortOrder)
        }
        TABLE1_ITEM -> {
            val bookId = uri?.pathSegments?.get(1)
            cursor = db?.query(MyDataBaseHelper.TABLE_BOOK,projection,"id = ?", arrayOf(bookId),null,null,sortOrder)
        }
        TABLE2_DIR -> {
            cursor = db?.query(MyDataBaseHelper.TABLE_CATEGORY,projection,selection,selectionArgs,null,null,sortOrder)
        }
        TABLE2_ITEM -> {
            val categoryId = uri?.pathSegments?.get(1)
            cursor = db?.query(MyDataBaseHelper.TABLE_CATEGORY,projection,"id = ?", arrayOf(categoryId),null,null,sortOrder)
        }
    }
        return cursor
    }

    //创建数据库
    override fun onCreate(): Boolean {
        myDataBaseHelper = MyDataBaseHelper(context,"BookStore.db",null,2)
        return true
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db  = myDataBaseHelper?.writableDatabase
        var updataRows = 0
        when(uriMaccher?.match(uri)){
            TABLE1_DIR -> {
                updataRows = db!!.update(MyDataBaseHelper.TABLE_BOOK,values,selection,selectionArgs)
            }

            TABLE1_ITEM -> {
                val bookId = uri?.pathSegments?.get(1)
                updataRows = db!!.update(MyDataBaseHelper.TABLE_BOOK,values,"id = ?", arrayOf(bookId))
            }

            TABLE2_DIR -> {
                updataRows = db!!.update(MyDataBaseHelper.TABLE_CATEGORY,values,selection,selectionArgs)
            }

            TABLE2_ITEM -> {
                val categoryId = uri?.pathSegments?.get(1)
                updataRows = db!!.update(MyDataBaseHelper.TABLE_CATEGORY,values,"id = ?", arrayOf(categoryId))
            }
        }
        return updataRows
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db  = myDataBaseHelper?.writableDatabase
        var deleteRows = 0
        when(uriMaccher?.match(uri)){
            TABLE1_DIR -> {
                deleteRows = db!!.delete(MyDataBaseHelper.TABLE_BOOK,selection,selectionArgs)
            }

            TABLE1_ITEM -> {
                val bookId = uri?.pathSegments?.get(1)
                deleteRows = db!!.delete(MyDataBaseHelper.TABLE_BOOK,"id = ?", arrayOf(bookId))
            }

            TABLE2_DIR -> {
                deleteRows = db!!.delete(MyDataBaseHelper.TABLE_CATEGORY,selection,selectionArgs)
            }

            TABLE2_ITEM -> {
                val categoryId = uri?.pathSegments?.get(1)
                deleteRows = db!!.delete(MyDataBaseHelper.TABLE_CATEGORY,"id = ?", arrayOf(categoryId))
            }
        }
        return deleteRows
    }

    //返回MIME
    override fun getType(uri: Uri?): String {
        when(uriMaccher?.match(uri)){
            TABLE1_DIR -> {
                return "vnd.android.cursor.dir/vnd.com.example.hadoop.kotlincontentprovider.provider.book"
            }
            TABLE1_ITEM -> {
                return "vnd.android.cursor.item/vnd.com.example.hadoop.kotlincontentprovider.provider.book"
            }
            TABLE2_DIR -> {
                return "vnd.android.cursor.dir/vnd.com.example.hadoop.kotlincontentprovider.provider.category"
            }
            TABLE2_ITEM -> {
                return "vnd.android.cursor.item/vnd.com.example.hadoop.kotlincontentprovider.provider.category"
            }
        }
        return "";
    }
}