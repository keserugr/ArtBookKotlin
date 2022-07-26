package com.uho.artbookkotlin.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.atilsamancioglu.artbookhilttesting.getOrAwaitValue
import com.uho.artbookkotlin.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import com.uho.artbookkotlin.repo.FakeArtRepository
import com.uho.artbookkotlin.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup(){
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("Mona Lisa","Picasso", "")
        val value = viewModel.insertedArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("","Picasso", "1800")
        val value = viewModel.insertedArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artist name returns error`(){
        viewModel.makeArt("Mona Lisa","", "1800")
        val value = viewModel.insertedArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `check year and return error if the year is not integer`(){
        viewModel.makeArt("Mona Lisa", "Picasso", "asd")
        val value = viewModel.insertedArtMessage.getOrAwaitValue()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}