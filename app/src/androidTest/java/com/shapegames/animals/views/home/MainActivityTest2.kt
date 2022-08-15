package com.shapegames.animals.views.home


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.shapegames.animals.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest2 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        val view = onView(
            allOf(
                withId(R.id.view1),
                childAtPosition(
                    allOf(
                        withId(R.id.breed_dog),
                        childAtPosition(
                            withId(R.id.breed_dog_container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        view.perform(click())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.imageView),
                childAtPosition(
                    allOf(
                        withId(R.id.list_wallpaper_item),
                        childAtPosition(
                            withClassName(`is`("com.google.android.material.circularreveal.cardview.CircularRevealCardView")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.likedDogsIv),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val chip = onView(
            allOf(
                withId(R.id.chip), withText("pyrenees"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        chip.perform(click())

        val appCompatImageView3 = onView(
            allOf(
                withId(R.id.backIv),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView3.perform(click())

        val appCompatImageView4 = onView(
            allOf(
                withId(R.id.backIv),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment_content_main),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageView4.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
