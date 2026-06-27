package com.razan.playlists.web;

import com.razan.playlists.api.AddSongRequest;
import com.razan.playlists.api.CreatePlaylistRequest;
import com.razan.playlists.api.PlaylistResponse;
import com.razan.playlists.core.PlaylistManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistControl {
    private final PlaylistManager playlistManager;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistResponse createPlaylist(@RequestBody CreatePlaylistRequest request) {
        return playlistManager.createPlaylist(request);
    }

    @PostMapping("/{playlistId}/songs")
    public PlaylistResponse addSongToPlaylist(
            @PathVariable Long playlistId,
            @RequestBody AddSongRequest request) {
        return playlistManager.addSongToPlaylist(playlistId, request);
    }

    @GetMapping
    public List<PlaylistResponse> getPlaylistsByUser(@RequestParam Long userId) {
        return playlistManager.getPlaylistsByUser(userId);
    }
}