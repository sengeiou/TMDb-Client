package com.example.feaure_authorization.domain

import com.example.core.data.account.AccountRepository
import com.example.core.data.session.SessionRepository
import com.example.core.di.FeatureScope
import com.example.core.prefs.UserPrefs
import io.reactivex.Completable
import javax.inject.Inject

@FeatureScope
class AuthInteractor @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val accountRepository: AccountRepository,
    private val userPrefs: UserPrefs
) {

    fun authorize(login: String, password: String): Completable {
        return sessionRepository.refreshSessionId(login, password)
            .flatMap { accountRepository.getAccountInfo() }
            .doOnSuccess { accountInfo->
                userPrefs.userLogin = login
                userPrefs.userPassword = password
                userPrefs.userName = accountInfo.name
                userPrefs.userId = accountInfo.id
            }
            .ignoreElement()
    }
}
