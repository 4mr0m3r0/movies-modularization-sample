package com.tzion.featuresopenmovies.data.source

import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.*
import org.junit.Test

class DataSourceFactoryTest {

    private val remote = mock<Remote>()
    private val dataSourceFactory = DataSourceFactory(remote)

    @Test
    fun `when getRemote, then return not null`() {
        assertNotNull(dataSourceFactory.getRemote())
    }

}