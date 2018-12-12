package com.siddapps.android.refrigeratorsurprise.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.siddapps.android.refrigeratorsurprise.R
import com.siddapps.android.refrigeratorsurprise.application.RecipeApplication
import com.siddapps.android.refrigeratorsurprise.customviews.Toolbar
import com.siddapps.android.refrigeratorsurprise.ui.ingredients.IngredientsFragment
import com.siddapps.android.refrigeratorsurprise.utils.add
import com.siddapps.android.refrigeratorsurprise.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        drawer_layout.closeDrawers()

        when (item.itemId) {
            R.id.ingredients_menu -> {
//                supportFragmentManager.add {
//                    add(R.id.container, IngredientsFragment())
//                    addToBackStack(IngredientsFragment.TAG)
//                }
                for (i in 0..supportFragmentManager.backStackEntryCount) {
                    supportFragmentManager.popBackStack()
                }
                return true
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as RecipeApplication).recipeComponent.inject(this)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        actionbar?.setDisplayHomeAsUpEnabled(true)
        nav_view.setNavigationItemSelectedListener(this)

        supportFragmentManager.add {
            add(R.id.container, IngredientsFragment())
        }

        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                drawer_layout.hideKeyboard()
            }

            override fun onDrawerClosed(drawerView: View?) {
            }

            override fun onDrawerOpened(drawerView: View?) {
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}