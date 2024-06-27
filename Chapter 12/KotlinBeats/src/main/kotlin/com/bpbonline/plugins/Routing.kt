package com.bpbonline.plugins

import com.bpbonline.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/songs") {
            // GET all songs
            get {
                call.respond(songRepository)
            }
            post {
                val songRequest = call.receive<SongRequest>()
                val song = songRequest.toSong()
                songRepository.add(song)
                call.respondText(
                    "Song added successfully: ${song.id}",
                    status = HttpStatusCode.Created
                )
            }
            get("{id}") {
                val id =
                    call.parameters["id"]
                        ?: return@get call.respond(
                            HttpStatusCode.BadRequest,
                            "Missing id parameter",
                        )
                songRepository.find { it.id == id }
                    ?.let { song ->
                        call.respond(song)
                    } ?: call.respondText(
                    "Song not found with id $id",
                    status = HttpStatusCode.NotFound
                )
            }
            put("{id}") {
                val id = call.parameters["id"]
                    ?: return@put call.respond(
                        HttpStatusCode.BadRequest,
                        "Missing id parameter",
                    )
                val songToUpdateRequest = call.receive<SongRequest>()
                songRepository.replaceAll { existingSong ->
                    if (existingSong.id == id) existingSong.updateFromRequest(
                        songToUpdateRequest
                    )
                    else existingSong
                }
                call.respondText(
                    "Song updated successfully",
                    status = HttpStatusCode.OK,
                )
            }
            patch("{id}") {
                val id = call.parameters["id"]
                    ?: return@patch call.respond(
                        HttpStatusCode.BadRequest,
                        "Missing id parameter",
                    )
                val updates = call.receive<Map<String, String>>()
                val genre = updates["genre"]
                    ?: return@patch call.respond(
                        HttpStatusCode.BadRequest,
                        "Genre not specified",
                    )

                val index = songRepository.indexOfFirst { it.id == id }
                if (index != -1) {
                    val updatedSong = songRepository[index]
                        .applyGenreUpdate(genre)
                    songRepository[index] = updatedSong
                    call.respondText(
                        "Song genre updated successfully",
                        status = HttpStatusCode.OK
                    )
                } else {
                    call.respondText(
                        "Song not found with id $id",
                        status = HttpStatusCode.NotFound
                    )
                }
            }

            delete("{id}") {
                val id =
                    call.parameters["id"]
                        ?: return@delete call.respond(
                            HttpStatusCode.BadRequest,
                            "Missing id parameter",
                        )
                songRepository.removeIf { it.id == id }
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}
