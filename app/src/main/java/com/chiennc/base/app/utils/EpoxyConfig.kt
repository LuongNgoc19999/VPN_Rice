package com.chiennc.base.app.utils

import com.airbnb.epoxy.EpoxyDataBindingLayouts
import com.airbnb.epoxy.EpoxyDataBindingPattern
import com.chiennc.base.R

//@EpoxyDataBindingLayouts(
//    value = [
//        R.layout.item_server
//    ]
//)
@EpoxyDataBindingPattern(rClass = R::class, layoutPrefix = "item_")
interface EpoxyConfig