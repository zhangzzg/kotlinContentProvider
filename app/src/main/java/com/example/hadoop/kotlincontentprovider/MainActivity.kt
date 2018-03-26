package com.example.hadoop.kotlincontentprovider

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var myDataBaseHelper:MyDataBaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myDataBaseHelper = MyDataBaseHelper(this@MainActivity, "BookStore.db", null, 2)
        create_database.setOnClickListener{
            myDataBaseHelper?.writableDatabase
        }

        add_data.setOnClickListener{
            val db = myDataBaseHelper?.writableDatabase
            val values = ContentValues()
            // 开始组装第一条数据
            values.put("name", "The Da Vinci Code")
            values.put("author", "Dan Brown")
            values.put("pages", 454)
            values.put("price", 16.96)
            db?.insert(MyDataBaseHelper.TABLE_BOOK,null,values)
        }

        query_data.setOnClickListener{
            val db = myDataBaseHelper?.writableDatabase
            val cursor  = db?.query(MyDataBaseHelper.TABLE_BOOK,null,null,null,null,null,null)
            if(cursor != null){
                while (cursor!!.moveToNext()){
                    // 遍历Cursor对象，取出数据并打印
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    Log.d("yy55gg", "book name is " + name)
                    Log.d("yy55gg", "book author is " + author)
                    Log.d("yy55gg", "book pages is " + pages)
                    Log.d("yy55gg", "book price is " + price)
                }
                cursor.close()
            }

        }
    }
}
