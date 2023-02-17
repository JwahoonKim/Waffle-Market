package com.wafflestudio.team03server.core.user.utils

import com.wafflestudio.team03server.core.user.api.response.LoginResponse

class SocialLoginContext {
    fun doLogin(socialLoginStrategy: SocialLoginStrategy, authValue: String): LoginResponse {
        return socialLoginStrategy.login(authValue)
    }
}
