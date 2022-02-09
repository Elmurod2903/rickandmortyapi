package uz.elmurod.rickandmortyapi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.elmurod.rickandmortyapi.data.RAM
import uz.elmurod.rickandmortyapi.data.RemoteKeys
import uz.elmurod.rickandmortyapi.database.dao.RAMDao
import uz.elmurod.rickandmortyapi.database.dao.RemoteKeysDao

@Database(entities = [RAM::class, RemoteKeys::class], version = 4, exportSchema = false)
abstract class RAMDatabase : RoomDatabase() {
    abstract fun getRAMDao(): RAMDao
    abstract fun remoteKeyDao(): RemoteKeysDao
}