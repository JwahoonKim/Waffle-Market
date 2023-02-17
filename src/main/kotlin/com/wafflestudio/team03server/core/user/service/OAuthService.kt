package com.wafflestudio.team03server.core.user.service

import com.wafflestudio.team03server.common.SocialLoginNotFoundException
import com.wafflestudio.team03server.core.user.api.response.LoginResponse
import com.wafflestudio.team03server.core.user.api.response.SimpleUserResponse
import com.wafflestudio.team03server.core.user.entity.User
import com.wafflestudio.team03server.core.user.repository.UserRepository
import com.wafflestudio.team03server.core.user.utils.GoogleLoginStrategy
import com.wafflestudio.team03server.core.user.utils.KakaoLoginStrategy
import com.wafflestudio.team03server.utils.KakaoOAuth
import org.springframework.stereotype.Service

private const val NEED_SIGNUP_MESSAGE = "회원가입이 필요합니다."

@Service
class OAuthService(
    private val googleLoginStrategy: GoogleLoginStrategy,
    private val kakaoLoginStrategy: KakaoLoginStrategy,
) {
    private val socialLoginStrategyMap = mapOf(
        "google" to googleLoginStrategy,
        "kakao" to kakaoLoginStrategy,
    )

    fun socialLogin(socialProvider: String, authValue: String): LoginResponse {
        return socialLoginStrategyMap[socialProvider]!!.login(authValue)
    }
}
