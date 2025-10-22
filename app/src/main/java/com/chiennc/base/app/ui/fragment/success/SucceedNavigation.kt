package com.chiennc.base.app.ui.fragment.success

import com.chiennc.base.app.ui.base.BaseFragment
import com.chiennc.base.app.ui.base.BaseNavigation

class SucceedNavigation(val fragment: SucceedFragment) : BaseNavigation() {

    override fun fragment(): BaseFragment<*, *> {
        return fragment
    }
}