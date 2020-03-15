package com.example.feaure_authorization.presentation.presenter

import com.example.core.exceptions.AuthException
import com.example.core.presentation.BasePresenter
import com.example.feaure_authorization.domain.AuthInteractor
import com.example.feaure_authorization.presentation.view.AuthView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class AuthPresenter @Inject constructor(
    private val authInteractor: AuthInteractor
) : BasePresenter<AuthView>() {

    init {
        viewState.setLoginButtonEnable(isEnable = false)
    }

    fun onUserDataChange(login: String?, password: String?) {
        viewState.setLoginButtonEnable(!login.isNullOrBlank() && !password.isNullOrBlank())
        viewState.hideError()
    }

    fun onLoginButtonClick(login: String, password: String) {
        authInteractor.authorize(login, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                           viewState.showSuccessAuthorizationMessage()
                           viewState.hideError()

                       }, { error ->
                           Timber.e(error)
                           when (error) {
                               is AuthException -> {
                                   viewState.setLoginButtonEnable(isEnable = false)
                                   viewState.showIncorrectDataError()
                               }
                               else -> {
                                   viewState.showTryLaterError()
                               }
                           }
                       })
            .let(compositeDisposable::add)
    }
}
