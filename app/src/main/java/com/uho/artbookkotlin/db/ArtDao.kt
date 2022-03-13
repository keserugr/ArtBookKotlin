package com.uho.artbookkotlin.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uho.artbookkotlin.db.Art

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: Art)

    @Delete
    suspend fun delete(art: Art)

    @Query("SELECT * FROM arts")
    fun observeArts(): LiveData<List<Art>>
}