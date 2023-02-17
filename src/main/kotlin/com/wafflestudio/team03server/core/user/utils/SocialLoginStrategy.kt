package com.wafflestudio.team03server.core.user.utils

import com.wafflestudio.team03server.common.SocialLoginNotFoundException
import com.wafflestudio.team03server.core.user.api.response.LoginResponse
import com.wafflestudio.team03server.core.user.api.response.SimpleUserResponse
import com.wafflestudio.team03server.core.user.entity.User
import com.wafflestudio.team03server.core.user.repository.UserRepository
import com.wafflestudio.team03server.core.user.service.AuthToken
import com.wafflestudio.team03server.core.user.service.AuthTokenService
import com.wafflestudio.team03server.utils.KakaoOAuth
import org.springframework.stereotype.Component

private const val NEED_SIGNUP_MESSAGE = "회원가입이 필요합니다."

interface SocialLoginStrategy {
    fun login(authValue: String): LoginResponse
}

@Component
class GoogleLoginStrategy(
    private val userRepository: UserRepository,
    private val authTokenService: AuthTokenService,
): SocialLoginStrategy {
    override fun login(authValue: String): LoginResponse {
        val (findUser, jwtToken) = getUserAndJwtTokenByEmail(authValue)
        return LoginResponse(jwtToken.accessToken, jwtToken.refreshToken, SimpleUserResponse.of(findUser))
    }

    private fun getUserAndJwtTokenByEmail(email: String): Pair<User, AuthToken> {
        val findUser = userRepository.findByEmail(email) ?: throw SocialLoginNotFoundException(
            NEED_SIGNUP_MESSAGE,
            email,
        )
        val authToken = authTokenService.generateAccessTokenAndRefreshToken(findUser.email, findUser)
        return Pair(findUser, authToken)
    }
}

@Component
class KakaoLoginStrategy(
    private val userRepository: UserRepository,
    private val authTokenService: AuthTokenService,
    private val kakaoOAuth: KakaoOAuth,
): SocialLoginStrategy {
    override fun login(authValue: String): LoginResponse {
        val accessToken: String = kakaoOAuth.getKaKaoAccessToken(authValue)
        val (connected_at, _, kakaoUserAccount) = kakaoOAuth.getKakaoUserInfo(accessToken)
        val (findUser, jwtToken) = getUserAndJwtTokenByEmail(kakaoUserAccount.email)
        return LoginResponse(jwtToken.accessToken, jwtToken.refreshToken, SimpleUserResponse.of(findUser))
    }

    private fun getUserAndJwtTokenByEmail(email: String): Pair<User, AuthToken> {
        val findUser = userRepository.findByEmail(email) ?: throw SocialLoginNotFoundException(
            NEED_SIGNUP_MESSAGE,
            email,
        )
        val authToken = authTokenService.generateAccessTokenAndRefreshToken(findUser.email, findUser)
        return Pair(findUser, authToken)
    }
}

