package uz.elmurod.rickandmortyapi.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.elmurod.rickandmortyapi.data.RAM

@Dao
interface RAMDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<RAM>)

    @Query("SELECT * FROM ram")
    fun getAllRAM(): PagingSource<Int, RAM>

    @Query("DELETE FROM ram")
    fun clearAll()
}