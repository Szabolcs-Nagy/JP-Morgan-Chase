package com.jpmc.szabolcs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jpmc.szabolcs.viewmodel.AlbumAdapter
import com.jpmc.szabolcs.viewmodel.AlbumFragment
import com.jpmc.szabolcs.viewmodel.AlbumViewHolder
import com.jpmc.szabolcs.viewmodel.AlbumViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Fragment_Test {

    val albumFragment: AlbumFragment = AlbumFragment()
    val resId: Int = R.id.rv_albums
    val mRecyclerView: AlbumAdapter = AlbumAdapter()

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isFragmentVisible_onAppLaunch(){
        onView(withId(R.id.rv_albums)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        onView(withId(R.id.rv_albums))
            .perform(actionOnItemAtPosition<AlbumViewHolder>(4, click()))
//        onView(withId(R.id.title)).check((matches(withText(4.title))))
    }
}