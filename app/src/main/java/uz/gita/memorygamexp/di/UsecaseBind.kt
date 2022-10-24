package uz.gita.memorygamexp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygamexp.domain.usecase.CategoryScreenUsecase
import uz.gita.memorygamexp.domain.usecase.GameScreenUsecase
import uz.gita.memorygamexp.domain.usecase.MultiPlayerScreenUsecase
import uz.gita.memorygamexp.domain.usecase.impl.CategoryScreenUsecaseImpl
import uz.gita.memorygamexp.domain.usecase.impl.GameScreenUsecaseImpl
import uz.gita.memorygamexp.domain.usecase.impl.MultiPlayerScreenUsecaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UsecaseBind {

    @[Binds Singleton]
    fun provideCategory(impl: CategoryScreenUsecaseImpl): CategoryScreenUsecase

    @[Binds Singleton]
    fun provideGame(impl: GameScreenUsecaseImpl): GameScreenUsecase

    @[Binds Singleton]
    fun provideMulti(impl: MultiPlayerScreenUsecaseImpl): MultiPlayerScreenUsecase


}