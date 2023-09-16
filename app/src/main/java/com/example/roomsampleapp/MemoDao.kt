package com.example.roomsampleapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoDao {

    @Insert
    fun insert(memo: Memo)

    @Query("SELECT * FROM memo")
    fun getAll(): LiveData<List<Memo>>
}
