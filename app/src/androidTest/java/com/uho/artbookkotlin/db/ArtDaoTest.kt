package com.uho.artbookkotlin.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.atilsamancioglu.artbookhilttesting.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var db: ArtDatabase

    @Inject
    @Named("testDao")
    lateinit var dao: ArtDao

    @Before
    fun setup(){

        // Create DB without Hilt
        /*db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = db.artDao()*/

        hiltRule.inject()
    }

    @After
    fun teardown(){
        db.close()
    }

    @Test
    fun insertArtTest() = runBlocking{
        val art = Art("Mona Lisa", "Da Vinci", 1700,"test.com", 1)
        dao.insertArt(art)

        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).contains(art)
    }

    @Test
    fun deleteArtTest() = runBlocking{
        val art = Art("Mona Lisa", "Da Vinci", 1700, "test.com",2)
        dao.insertArt(art)
        dao.delete(art)

        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).doesNotContain(art)
    }
}