package com.uho.artbookkotlin.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.atilsamancioglu.artbookhilttesting.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import com.uho.artbookkotlin.R
import com.uho.artbookkotlin.adapter.ImageRecyclerAdapter
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
class ImageApiFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImageTest(){
        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "test.com"
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
            viewModel = testViewModel
            imageRecyclerAdapter.images = listOf(selectedImageUrl)
        }

        Espresso.onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewolder>(
                0,click()
            )
        )

        Mockito.verify(navController).popBackStack()

        assertThat(testViewModel.selectedImageURL.getOrAwaitValueTest()).isEqualTo(selectedImageUrl)
    }

}