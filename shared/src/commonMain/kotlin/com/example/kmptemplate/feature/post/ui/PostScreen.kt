package com.example.kmptemplate.feature.post.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmptemplate.base.BaseScreen
import com.example.kmptemplate.feature.post.domain.model.Post
import kotlinx.collections.immutable.ImmutableList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PostScreen(viewModel: PostViewModel = koinViewModel()) {
    BaseScreen(viewModel) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        PostList(state.posts)
    }
}

@Composable
private fun PostList(posts: ImmutableList<Post>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = posts, key = { it.id }) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
private fun PostItem(post: Post) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}