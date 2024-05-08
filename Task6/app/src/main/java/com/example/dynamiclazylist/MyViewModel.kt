package com.example.dynamiclazylist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyViewModel: ViewModel() {
    private val _favourite = MutableStateFlow(
        mutableListOf(
            Song(
                id = 1,
                title = "Just dance"
            ),
            Song(
                id = 2,
                title = "Summer"
            ),
            Song(
                id = 3,
                title = "Party shaker"
            ),
            Song(
                id = 4,
                title = "Danza kuduro"
            ),
        )
    )
    val favourite = _favourite.asStateFlow()

    fun addSong(song: Song) {
        _favourite.value.add(song)
    }

    fun deleteSong(song: Song) {
        _favourite.value.remove(song)
    }

    fun updateSong(id: Int, newTitle: String) {
        _favourite.value.find { it.id == id }?.title = newTitle
    }
}