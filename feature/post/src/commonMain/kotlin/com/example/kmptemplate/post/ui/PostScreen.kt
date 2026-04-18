package com.example.kmptemplate.post.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.kmptemplate.ads.BannerAd
import com.example.kmptemplate.ads.InterstitialAd
import com.example.kmptemplate.designsystem.BaseScreen
import com.example.kmptemplate.post.domain.model.Post
import kotlinx.collections.immutable.ImmutableList

@Composable
fun PostScreen(
    viewModel: PostViewModel,
    onNavigateToDetail: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BaseScreen(viewModel) {
        BannerAd()
        PostList(state.posts, onNavigateToDetail = onNavigateToDetail)
        InterstitialAd()
    }
}

@Composable
private fun PostList(posts: ImmutableList<Post>, onNavigateToDetail: () -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items = posts, key = { it.id }) { post ->
            PostItem(
                post = post,
                onNavigateToDetail = onNavigateToDetail
            )
        }
    }
}

@Composable
private fun PostItem(post: Post, onNavigateToDetail: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onNavigateToDetail() }) {
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