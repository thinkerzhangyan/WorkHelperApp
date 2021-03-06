package com.vance.permission.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.king.applib.base.BaseActivity
import com.vance.permission.R
import com.vance.permission.fragment.CustomPermissionFragment
import com.vance.permission.fragment.EasyPermissionsFragment
import com.vance.permission.fragment.NormalPermissionFragment
import com.vance.permission.fragment.PermissionsDispatcherFragment

/**
 *
 * @author VanceKing
 * @since 19-8-8.
 */

@Route(path = "/permission/main")
open class PerPermissionActivity : BaseActivity() {
    override fun getContentLayout(): Int {
        return R.layout.per_activity_permission
    }

    override fun initContentView() {
        super.initContentView()
        showFragment(CustomPermissionFragment.TAG)
        setViewClickListeners(R.id.tv_normal, R.id.tv_dispatcher, R.id.tv_easypermission, R.id.tv_custom_easypermission)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when (v?.id) {
            R.id.tv_normal -> showFragment(NormalPermissionFragment.TAG)
            R.id.tv_dispatcher -> showFragment(PermissionsDispatcherFragment.TAG)
            R.id.tv_easypermission -> showFragment(EasyPermissionsFragment.TAG)
            R.id.tv_custom_easypermission -> showFragment(CustomPermissionFragment.TAG)
        }
    }

    private fun showFragment(tag: String) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            when (tag) {
                PermissionsDispatcherFragment.TAG -> fragment = PermissionsDispatcherFragment.getInstance()
                EasyPermissionsFragment.TAG -> fragment = EasyPermissionsFragment.instance
                NormalPermissionFragment.TAG -> fragment = NormalPermissionFragment.getInstance()
                CustomPermissionFragment.TAG -> fragment = CustomPermissionFragment.instance
            }
        }
        fragment?.let {
            if (it.isVisible) {//当前正在显示
                return
            }
            hiddenFragments()
            if (!it.isAdded) {//判断是否已经添加到当前 Activity
                supportFragmentManager.beginTransaction()
                        .add(R.id.permission_container, it, tag)
                        .commitAllowingStateLoss()
            } else {
                supportFragmentManager.beginTransaction()
                        .show(fragment)
                        .commitAllowingStateLoss()
            }
        }

    }

    private fun hiddenFragments() {
        if (supportFragmentManager.fragments.isEmpty()) {
            return
        }
        val beginTransaction = supportFragmentManager.beginTransaction()
        for (fragment in supportFragmentManager.fragments) {
            if (!fragment.isHidden) {
                beginTransaction.hide(fragment)
            }
        }
        beginTransaction.commitAllowingStateLoss()
    }
}