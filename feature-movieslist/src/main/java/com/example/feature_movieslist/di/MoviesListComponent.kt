package com.example.feature_movieslist.di

import com.example.core.di.CoreComponent
import com.example.feature_movieslist.presentation.movies.MoviesListFragment
import dagger.Component

@Component(dependencies = [CoreComponent::class], modules = [MoviesListModule::class])
@MoviesListScope
interface MoviesListComponent {

    @Component.Factory
    interface Factory {

        fun create(coreComponent: CoreComponent): MoviesListComponent
    }

    fun inject(fragment: MoviesListFragment)
}
