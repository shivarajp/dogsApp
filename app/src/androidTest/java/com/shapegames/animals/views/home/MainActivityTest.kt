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


    /**
     * [HomeFragment] Strategy
     * - First we will check static text/views visibility
     * - Validate if breed list is not empty.
     * - Check if the icons (Like) are displayed
     * - Check if click on like is taking to [LikedDogsFragment]
     * - From LikedDogsFragment press back
     * - Validate if it is taking back to [HomeFragment] and list is not empty
     */

    @Test
    fun test_home_fragment() {

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withText("Choose your favourite breed"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))

        sleep(500)

        assertListNotEmpty(R.id.breedsRv)

        sleep(500)

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(500)

        onView(withText("Liked Dogs"))
            .check(matches(isDisplayed()))

        sleep(500)

        onView(withId(R.id.backIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(1000)

        assertListNotEmpty(R.id.breedsRv)

        sleep(500)

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withText("Choose your favourite breed"))
            .check(matches(isDisplayed()))

        sleep(500)

        //Working
        onView(withId(R.id.breedsRv))
            // scrollTo will fail the test if no item matches.
            .perform(RecyclerViewActions.actionOnItemAtPosition<BreedsViewHolder>(1, click()))

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))

        sleep(500)

        onView(withId(R.id.backIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(500)

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withText("Choose your favourite breed"))
            .check(matches(isDisplayed()))

        sleep(500)

        assertListNotEmpty(R.id.breedsRv)

        sleep(500)

    }


    /**
     * [DogsDetailsFragment] Strategy
     * - First we will Validate breed list is not empty.
     * - Click on first breed.
     * - Validate it is navigating to [DogsDetailsFragment]
     * - Validate static title/icons are visible
     * - Click on Liked icon.
     * - Validate if it is navigating to [LikedDogsFragment]
     * - Click on back button on [LikedDogsFragment]
     * - Validate if it is navigating to [DogsDetailsFragment]
     * - Validate list is not empty.
     * - Like image at index 1 & Validate if red filled heart icon updated.
     * - unLike image at index 1 & Validate if unfilled heart icon updated.
     */

    @Test
    fun test_dog_details_fragment() {

        sleep(100)

        assertListNotEmpty(R.id.breedsRv)

        sleep(200)

        //Working
        onView(withId(R.id.breedsRv))
            // scrollTo will fail the test if no item matches.
            .perform(RecyclerViewActions.actionOnItemAtPosition<BreedsViewHolder>(1, click()))

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))

        sleep(1000)

        assertListNotEmpty(R.id.dogsRv)

        sleep(200)

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(1000)

        onView(withId(R.id.backIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(1000)

        assertListNotEmpty(R.id.dogsRv)

        sleep(200)

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))


        //Check if like/unlike is working
        //----------------------------------------------------
        sleep(500)

        assertDrawableDisplayedAtPosition(
            R.id.dogsRv,
            1, R.id.likeIv, R.drawable.heart_empty
        )

        sleep(200)

        clickListItem(R.id.dogsRv, 1)

        sleep(200)

        assertDrawableDisplayedAtPosition(
            R.id.dogsRv,
            1, R.id.likeIv, R.drawable.heart_filled
        )

        sleep(500)

        clickListItem(R.id.dogsRv, 1)

        sleep(500)

        assertDrawableDisplayedAtPosition(
            R.id.dogsRv,
            1, R.id.likeIv, R.drawable.heart_empty
        )

        sleep(500)

        onView(withId(R.id.backIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(2000)

        assertListNotEmpty(R.id.breedsRv)

        sleep(100)

        //----------------------------------------------------

    }


    /**
     * [LikedDogsFragment] Strategy
     * - First we will Validate breed list is not empty.
     * - Click on first breed.
     * - Validate it is navigating to [DogsDetailsFragment]
     * - Validate static title/icons are visible
     * - Like image at index 1 & Validate if red filled heart icon updated.
     *
     * - Click on like icon.
     * - Validate if it is navigating to [LikedDogsFragment]
     * - Validate if list is showing liked image
     *
     * - Click on back button on [LikedDogsFragment]
     * - Validate if it is navigating to [DogsDetailsFragment]
     * - unLike image at index 1 & Validate if unfilled heart icon updated.
     *
     * - Click on like icon.
     * - Validate if unliked image is removed.
     */

    @Test
    fun test_liked_dogs_fragment() {
        sleep(2000)

        assertListNotEmpty(R.id.breedsRv)

        sleep(500)

        clickListItem(R.id.breedsRv, 1)

        sleep(500)

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed()))
        sleep(500)

        clickListItem(R.id.dogsRv, 1)

        sleep(200)

        assertDrawableDisplayedAtPosition(
            R.id.dogsRv,
            1, R.id.likeIv, R.drawable.heart_filled
        )

        sleep(200)

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(500)

        onView(withText("Liked Dogs"))
            .check(matches(isDisplayed()))

        sleep(200)

        assertListNotEmpty(R.id.likedDogsRv)

        sleep(500)

        onView(withId(R.id.backIv))
            .check(matches(isDisplayed()))


        onView(withId(R.id.backIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(500)

        onView(withText("Dog Breeds"))
            .check(matches(isDisplayed()))

        sleep(500)

        clickListItem(R.id.dogsRv, 1)

        sleep(200)

        assertDrawableDisplayedAtPosition(
            R.id.dogsRv,
            1, R.id.likeIv, R.drawable.heart_empty
        )

        sleep(500)

        onView(withId(R.id.likedDogsIv))
            .check(matches(isDisplayed())).perform(click())

        sleep(500)

        assertListItemCount(R.id.likedDogsRv, 0)

        sleep(500)
    }

}
