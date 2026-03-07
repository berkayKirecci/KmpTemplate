package com.example.kmptemplate.feature.post.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptemplate.base.NetworkHelper
import com.example.kmptemplate.base.NetworkHelperDelegate
import com.example.kmptemplate.feature.post.domain.usecase.GetPostsUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(private val getPosts: GetPostsUseCase) : ViewModel(),
    NetworkHelper by NetworkHelperDelegate() {

    val state: StateFlow<PostUiState>
        field = MutableStateFlow(PostUiState())

    init {
        loadPosts()
    }

    fun loadPosts() = viewModelScope.launch {
        getPosts().safeCollect {
            state.update { it.copy(posts = toImmutableList()) }
        }
    }
}