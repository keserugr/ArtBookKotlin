package com.uho.artbookkotlin.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.uho.artbookkotlin.R
import com.uho.artbookkotlin.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
class ArtFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtToArtDetails() {

        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ArtFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        // ViewActions.click() -> find the method
        // Click the fab button with Espresso
        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(click())

        Mockito.verify(navController).navigate(
            ArtFragmentDirections.actionArtFragmentToArtDetailFragment()
        )
    }
}