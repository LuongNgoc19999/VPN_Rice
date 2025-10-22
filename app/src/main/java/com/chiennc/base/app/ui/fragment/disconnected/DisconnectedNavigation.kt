package com.chiennc.base.app.ui.fragment.disconnected

import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation

class DisconnectedNavigation(val fragment: DisconnectedFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}