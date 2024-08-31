package com.bijesh.coroutine.nowinandroid.flow.fake

import android.content.Context
import com.bijesh.coroutine.R
import com.bijesh.coroutine.nowinandroid.flow.model.NewsResource
import com.bijesh.coroutine.nowinandroid.flow.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class FakeNewsResourceRepository(private val context:Context) : NewsRepository {
    override fun monitor(): Flow<List<NewsResource>> {
        context.resources.openRawResource(R.raw.data)
        TODO("Deserialize json and return news resources")
    }

}