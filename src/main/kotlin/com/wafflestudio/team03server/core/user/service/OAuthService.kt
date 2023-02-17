package com.wafflestudio.team03server.core.user.service

import com.wafflestudio.team03server.common.Exception400
import com.wafflestudio.team03server.core.user.api.response.LoginResponse
import com.wafflestudio.team03server.core.user.utils.GoogleLoginStrategy
import com.wafflestudio.team03server.core.user.utils.KakaoLoginStrategy
import com.wafflestudio.team03server.core.user.utils.SocialLoginContext
import com.wafflestudio.team03server.core.user.utils.SocialLoginStrategy
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
        val strategy: SocialLoginStrategy = socialLoginStrategyMap[socialProvider]
            ?: let{ throw Exception400("지원하지 않는 소셜 로그인 플랫폼입니다.") }
        return SocialLoginContext().doLogin(strategy, authValue)
    }
}
