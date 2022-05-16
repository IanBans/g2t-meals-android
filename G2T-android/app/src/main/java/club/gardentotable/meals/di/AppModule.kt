package club.gardentotable.meals.di

import android.content.Context
import club.gardentotable.meals.db.MemberDAO
import club.gardentotable.meals.db.MemberRoomDatabase
import club.gardentotable.meals.db.SlotDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context: Context): MemberRoomDatabase {
        return MemberRoomDatabase.getDatabase(context)

    }

    @Singleton
    @Provides
    fun provideSlotDao(database: MemberRoomDatabase): SlotDAO {
        return database.slotDAO()
    }

    @Singleton
    @Provides
    fun provideMemberDao(database: MemberRoomDatabase) : MemberDAO {
        return database.memberDAO()
    }
}