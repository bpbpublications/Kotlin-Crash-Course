package com.bpbonline.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Song(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val artist: String,
    val album: String? = null,
    val genre: String,
    val year: Int,
)

@Serializable
data class SongRequest(
    val title: String,
    val artist: String,
    val album: String? = null,
    val genre: String,
    val year: Int,
)

fun SongRequest.toSong(): Song = Song(
    title = title,
    artist = artist,
    album = album,
    genre = genre,
    year = year
)

fun Song.updateFromRequest(request: SongRequest): Song {
    return this.copy(
        title = request.title,
        artist = request.artist,
        album = request.album,
        genre = request.genre,
        year = request.year
    )
}

fun Song.applyGenreUpdate(genre: String): Song {
    return this.copy(genre = genre)
}

val songRepository = mutableListOf<Song>()