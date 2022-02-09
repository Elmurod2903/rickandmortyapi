package uz.elmurod.rickandmortyapi.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import uz.elmurod.rickandmortyapi.database.RAMDatabase
import uz.elmurod.rickandmortyapi.database.dao.RAMDao
import uz.elmurod.rickandmortyapi.database.dao.RemoteKeysDao
import uz.elmurod.rickandmortyapi.network.api.RickAndMortyApi
import uz.elmurod.rickandmortyapi.network.plugs.PreferencePlug
import uz.elmurod.rickandmortyapi.network.plugs.RickAndMortyPlugs
import uz.elmurod.rickandmortyapi.network.port.PreferencePort
import uz.elmurod.rickandmortyapi.network.port.RickAndMortyPort
import uz.elmurod.rickandmortyapi.util.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun retrofit(
        moshi: Moshi
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun moshi(): Moshi {
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun rickAndMortyService(retrofit: Retrofit): RickAndMortyApi = retrofit.create()


    @Provides
    @Singleton
    fun rickAndMortyPlug(rickAndMorty: RickAndMortyPlugs): RickAndMortyPort = rickAndMorty

    @Singleton
    @Provides
    fun mainDatabase(@ApplicationContext context: Context): RAMDatabase {
        return Room.databaseBuilder(context, RAMDatabase::class.java, Constants.DB)
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun ramDao(database: RAMDatabase): RAMDao {
        return database.getRAMDao()
    }

    @Singleton
    @Provides
    fun remoteKeyDao(database: RAMDatabase): RemoteKeysDao {
        return database.remoteKeyDao()
    }

    @Provides
    fun preferencePlug(preferencePlug: PreferencePlug): PreferencePort = preferencePlug
}