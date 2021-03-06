package com.example.feature_profile.presentation

import androidx.lifecycle.MutableLiveData
import com.example.core.data.session.SessionRepository
import com.example.core.presentation.BaseViewModel
import com.example.core.rxjava.SchedulersProvider
import com.example.core.util.ioToMain
import com.example.feature_mainscreen.MainScreenFragmentDirections
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    val liveState = MutableLiveData<ProfileViewState>(createInitialState())

    private fun createInitialState(): ProfileViewState {
        return ProfileViewState(
            userName = "Mick Wick",
            userMail = "examle@mail.com"
        )
    }

    fun onLogoutButtonClick() {
        sessionRepository.deleteSession()
            .ioToMain(schedulersProvider)
            .subscribe({
                           parentNavigateTo(
                               MainScreenFragmentDirections.actionMainScreenToAuth()
                           )
                       }, { error ->
                Timber.e(error)
            })
            .let(this::addDisposable)
    }
}
