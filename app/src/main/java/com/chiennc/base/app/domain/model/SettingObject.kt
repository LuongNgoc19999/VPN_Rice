package com.chiennc.base.app.domain.model

import com.chiennc.base.R

enum class SettingObject(var image:Int, var text:Int) {
    FEEDBACK(R.drawable.ic_feedback, R.string.feedback),
    RATE_APP(R.drawable.ic_rate, R.string.rate_us),
    SHARE(R.drawable.ic_share, R.string.share_this_app),
    POLICY(R.drawable.ic_policy, R.string.privacy_policy)
}