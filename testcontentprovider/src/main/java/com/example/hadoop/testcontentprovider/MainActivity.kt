package com.example.hadoop.testcontentprovider

import android.content.ContentValues
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
     var bookId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add_data.setOnClickListener{
            val uri = Uri.parse("content://com.example.hadoop.kotlincontentprovider.provider/book")
            val values = ContentValues()
            values.put("name", "second code")
            values.put("author", "zhang")
            values.put("pages", 1024)
            values.put("price", 15.16)
            val newUrl = contentResolver.insert(uri,values)
            bookId = newUrl.pathSegments.get(1)

        }
        query_data.setOnClickListener{
            val uri = Uri.parse("content://com.example.hadoop.kotlincontentprovider.provider/book")
            val cursor =  contentResolver.query(uri,null,null,null,null)
            while (cursor.moveToNext()){
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val author = cursor.getString(cursor.getColumnIndex("author"))
                val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                val price = cursor.getDouble(cursor.getColumnIndex("price"))
                Log.d("yy55gg", "---name--=" + name)
                Log.d("yy55gg", "---author--=" + author)
                Log.d("yy55gg", "---pages--=" + pages)
                Log.d("yy55gg", "---price--=" + price)
            }
            cursor.close()
        }

        updata_data.setOnClickListener{
            val uri = Uri.parse("content://com.example.hadoop.kotlincontentprovider.provider/book/" + bookId)
            val values = ContentValues()
            values.put("name", "first code")
            contentResolver.update(uri, values, null, null)
        }

        delete_data.setOnClickListener{
            val uri = Uri.parse("content://com.example.hadoop.kotlincontentprovider.provider/book/" + bookId)
            contentResolver.delete(uri,null, null)
        }
    }
}
