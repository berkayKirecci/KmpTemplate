package com.example.kmptemplate.feature.post.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmptemplate.base.NetworkHelper
import com.example.kmptemplate.base.NetworkHelperDelegate
import com.example.kmptemplate.feature.post.domain.usecase.GetPostsUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(private val getPosts: GetPostsUseCase) : ViewModel(),
    NetworkHelper by NetworkHelperDelegate() {

    private val _state = MutableStateFlow(PostUiState())
    val state: StateFlow<PostUiState> = _state.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() = viewModelScope.launch {
        getPosts().safeCollect {
            _state.update { it.copy(posts = toImmutableList()) }
        }
    }
}