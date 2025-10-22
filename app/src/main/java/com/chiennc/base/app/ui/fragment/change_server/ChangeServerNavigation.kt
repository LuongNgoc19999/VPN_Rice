package com.chiennc.base.app.ui.fragment.change_server

import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation

class ChangeServerNavigation(val fragment: ChangeServerFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}