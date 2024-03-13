package com.example.a1apps.repository.offlineDB
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MangaOfflineDao {

    //to show all the item in home fragment
    @Query("SELECT * FROM offline_manga")
     fun getAllFavoriteManga(): List<OfflineManga>

     //to add single item in db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertFavoriteManga(manga: OfflineManga)

     //to add  list of item in db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFavoriteManga(mangaList: List<OfflineManga>)

}
