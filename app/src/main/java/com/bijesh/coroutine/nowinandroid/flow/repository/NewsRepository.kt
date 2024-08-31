package com.bijesh.coroutine.nowinandroid.flow.repository

import com.bijesh.coroutine.nowinandroid.flow.model.NewsResource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun monitor(): Flow<List<NewsResource>>
}