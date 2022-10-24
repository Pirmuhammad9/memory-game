package uz.gita.memorygamexp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygamexp.data.local.preference.AppPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @[Provides Singleton]
    fun getPreference(@ApplicationContext context: Context): AppPreference = AppPreference(context)

}