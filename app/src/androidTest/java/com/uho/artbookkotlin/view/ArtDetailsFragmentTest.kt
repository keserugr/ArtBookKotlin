package com.uho.artbookkotlin.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.atilsamancioglu.artbookhilttesting.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import com.uho.artbookkotlin.R
import com.uho.artbookkotlin.db.Art
import com.uho.artbookkotlin.launchFragmentInHiltContainer
import com.uho.artbookkotlin.repo.FakeArtRepositoryTest
import com.uho.artbookkotlin.viewmodel.ArtViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailToImageApiFragment(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.ivArtDetail)).perform(click())

        Mockito.verify(navController).navigate(
            ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment()
        )
    }

    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSave(){
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ){
            viewModel = testViewModel
        }

        // add texts in all edit texts and save all data test
        Espresso.onView(withId(R.id.etArtDetailName)).perform(replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.etArtistDetailName)).perform(replaceText("Da Vinci"))
        Espresso.onView(withId(R.id.etArtistDetailYear)).perform(replaceText("1500"))
        Espresso.onView(withId(R.id.btnSaveArt)).perform(click())

        assertThat(testViewModel.artList.getOrAwaitValueTest()).contains(
            Art("Mona Lisa", "Da Vinci", 1500, "")
        )
    }
}