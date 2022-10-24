package uz.gita.memorygamexp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygamexp.domain.repository.Repository
import uz.gita.memorygamexp.domain.repository.impl.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBind {

    @[Binds Singleton]
    fun getRepository(impl: RepositoryImpl): Repository

}