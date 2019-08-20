package kz.dragau.larek.di.modules

import dagger.Module
import androidx.room.Room
import kz.dragau.larek.App
import kz.dragau.larek.models.db.Db
import dagger.Provides
import kz.dragau.larek.models.db.UserDao
import javax.inject.Singleton


@Module(includes = [ApplicationModule::class])
class RoomModule(private val mApplication: App){
    private val demoDatabase: Db = Room.databaseBuilder(mApplication, Db::class.java, "o2o")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): Db {
        return demoDatabase
    }

    @Singleton
    @Provides
    fun providesProductDao(demoDatabase: Db): UserDao {
        return demoDatabase.getUserDao()
    }
}