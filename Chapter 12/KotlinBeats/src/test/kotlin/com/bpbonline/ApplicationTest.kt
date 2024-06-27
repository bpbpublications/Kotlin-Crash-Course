package com.bpbonline

import com.bpbonline.model.Song
import com.bpbonline.model.SongRequest
import com.bpbonline.model.songRepository
import com.bpbonline.plugins.configureRouting
import com.bpbonline.plugins.configureSerialization
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation


class ApplicationTest {
    private fun ApplicationTestBuilder.setUp(): io.ktor.client.HttpClient {
        songRepository.clear()
        application {
            configureRouting()
            configureSerialization()
        }
        return createClient {
            install(ClientContentNegotiation) {
                json()
            }
        }
    }

    @Test
    fun `Given there are no songs When GET Then return No songs found`() =
        testApplication {
            val httpClient = setUp()
            httpClient.get("/songs").apply {
                assertEquals(HttpStatusCode.OK, status)
                assertTrue(body<List<Song>>().isEmpty())
            }
        }

    @Test
    fun `Given a new song When POST a new song Then return Created with text 'Song added successfully' and song ID`() =
        testApplication {
            val httpClient = setUp()
            val songRequest = SongRequest(
                "New Title",
                "New Artist",
                "New Album",
                "New Genre",
                2020
            )

            httpClient.post("/songs") {
                contentType(ContentType.Application.Json)
                setBody(songRequest)
            }.apply {
                assertEquals(
                    HttpStatusCode.Created,
                    status
                )
                assertTrue(bodyAsText().contains("Song added successfully"))
            }
        }

    @Test
    fun `Given song exists When GET with valid ID Then return the song`() =
        testApplication {
            val httpClient = setUp()
            // Pre-populate songRepository with a test song
            val testSong = Song(
                "test-id",
                "Test Title",
                "Test Artist",
                "Test Album",
                "Test Genre",
                1999
            )
            songRepository.add(testSong)

            httpClient.get("/songs/test-id").apply {
                assertEquals(HttpStatusCode.OK, status)
                assertTrue(bodyAsText().contains("Test Title"))
            }
        }

    @Test
    fun `Given song exists When PUT Then update song and return OK `() =
        testApplication {
            val httpClient = setUp()
            val testSong = Song(
                "test-id",
                "Old Title",
                "Old Artist",
                "Old Album",
                "Old Genre",
                1990
            )
            songRepository.add(testSong)

            val updatedSongRequest = SongRequest(
                "Updated Title",
                "Updated Artist",
                "Updated Album",
                "Updated Genre",
                2021
            )

            httpClient.put("/songs/test-id") {
                contentType(ContentType.Application.Json)
                setBody(updatedSongRequest)
            }.apply {
                assertEquals(HttpStatusCode.OK, status)
                assertTrue(bodyAsText().contains(
                    "Song updated successfully"
                ))
            }

            val updatedSong = songRepository.find { it.id == "test-id" }
            assertEquals(updatedSongRequest.title, updatedSong?.title)
            assertEquals(updatedSongRequest.artist, updatedSong?.artist)
            assertEquals(updatedSongRequest.album, updatedSong?.album)
            assertEquals(updatedSongRequest.genre, updatedSong?.genre)
            assertEquals(updatedSongRequest.year, updatedSong?.year)
        }

    @Test
    fun `Given song exists When PATCH Then update genre and return OK`() =
        testApplication {
            val httpClient = setUp()
            // Pre-populate songRepository with a test song
            val testSong = Song(
                "test-id",
                "Some Title",
                "Some Artist",
                "Some Album",
                "Old Genre",
                2000
            )
            songRepository.add(testSong)

            httpClient.patch("/songs/test-id") {
                contentType(ContentType.Application.Json)
                setBody("""{"genre": "New Genre"}""")
            }.apply {
                assertEquals(HttpStatusCode.OK, status)
                assertTrue(bodyAsText().contains(
                    "Song genre updated successfully"
                ))
            }
        }

    @Test
    fun `Given song exists When DELETE with valid ID Then deletes song`() =
        testApplication {
            val httpClient = setUp()
            // Pre-populate songRepository with a test song
            val testSong = Song(
                "test-id",
                "Some Title",
                "Some Artist",
                "Some Album",
                "Some Genre",
                2000
            )
            songRepository.add(testSong)

            httpClient.delete("/songs/test-id").apply {
                assertEquals(HttpStatusCode.NoContent, status)
            }
        }

    @Test
    fun `Given song does not exists When DELETE Then returns OK status`() =
        testApplication {
            val httpClient = setUp()

            httpClient.delete("/songs/i-dont-exist").apply {
                assertEquals(HttpStatusCode.NoContent, status)
            }
        }
}

