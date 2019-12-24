package com.tzion.android.movies.data.source

import com.nhaarman.mockitokotlin2.mock
import com.tzion.android.movies.data.repository.Remote
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DataSourceFactoryTest {

    private val remote = mock<Remote>()
    private val dataSourceFactory = DataSourceFactory(remote)

    @Test
    fun `when getRemote, then return not null`() {
        assertNotNull(dataSourceFactory.getRemote())
    }

}