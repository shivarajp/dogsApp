package com.shapegames.animals.views.home


import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertDrawableDisplayedAtPosition
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListItemCount
import com.adevinta.android.barista.assertion.BaristaListAssertions.assertListNotEmpty
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.shapegames.animals.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun test_like_dog_working() {
        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withText("Choose your favourite breed"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))

        //Working
        onView(withId(R.id.breedsRv))
            // scrollTo will fail the test if no item matches.
            .perform(RecyclerViewActions.actionOnItemAtPosition<BreedsViewHolder>(2, click()))

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))

        clickListItem(R.id.dogsRv, 4)
        sleep(1000)

        clickListItem(R.id.dogsRv, 8)
        sleep(1000)

        clickListItem(R.id.dogsRv, 1)
        sleep(1000)

        clickListItem(R.id.dogsRv, 7)

        sleep(2000)


        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed())).perform(click())

        assertListNotEmpty(R.id.likedDogsRv)
        //assertListItemCount(R.id.likedDogsRv, 4)

        /* //Working
         onView(withId(R.id.dogsRv))
             // scrollTo will fail the test if no item matches.
             .perform(RecyclerViewActions.actionOnItemAtPosition<BreedsViewHolder>(1, click()))

         //Working
         onView(withId(R.id.dogsRv))
             // scrollTo will fail the test if no item matches.
             .perform(RecyclerViewActions.actionOnItemAtPosition<BreedsViewHolder>(2, click()))

         onView(withId(R.id.likedDogsIv))
             .check(matches(isDisplayed())).perform(click())*/


    }

    @Test
    fun test_dogs_list_loaded() {

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withText("Choose your favourite breed"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))

        sleep(2000)

        assertListNotEmpty(R.id.likedDogsRv)

        sleep(2000)

        clickListItem(R.id.breedsRv, 4)

        /*//Working
        onView(withId(R.id.breedsRv))
            // scrollTo will fail the test if no item matches.
            .perform(RecyclerViewActions.actionOnItemAtPosition<BreedsViewHolder>(2, click()))
        */
    }

    @Test
    fun test_breeds_list_loaded() {
        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withText("Choose your favourite breed"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))

        //Working
        onView(withId(R.id.breedsRv))
            // scrollTo will fail the test if no item matches.
            .perform(RecyclerViewActions.actionOnItemAtPosition<BreedsViewHolder>(2, click()))

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))

        //Working
        onView(withId(R.id.dogsRv))
            // scrollTo will fail the test if no item matches.
            .perform(RecyclerViewActions.actionOnItemAtPosition<BreedsViewHolder>(2, click()))

    }



}
