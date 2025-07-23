package com.gmribas.cstv.ui.matches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gmribas.cstv.repository.dto.MatchResponseDTO

@Composable
fun MatchesScreen(
    matches: List<MatchResponseDTO>,
    onItemClick: (MatchResponseDTO) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
//            items(matches) {
//                Text(text = it)
//            }
        }
    }
}
