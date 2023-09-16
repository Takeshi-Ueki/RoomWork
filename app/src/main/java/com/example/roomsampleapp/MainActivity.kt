package com.example.roomsampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var dao: MemoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "memo.db"
        ).build()
        this.dao = this.db.memoDao()

        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener{
            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    val memo = Memo(id = 0, memo = "Test Memo")
                    dao.insert(memo)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "OK!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        this.dao.getAll().observe(this, Observer {
            println(it)
        })
    }
}