package com.razan.playlists.core;

import com.razan.playlists.api.AddSongRequest;
import com.razan.playlists.api.CreatePlaylistRequest;
import com.razan.playlists.api.PlaylistResponse;
import java.util.List;

public interface PlaylistManager {
    PlaylistResponse createPlaylist(CreatePlaylistRequest request);
    PlaylistResponse addSongToPlaylist(Long playlistId, AddSongRequest request);
    List<PlaylistResponse> getPlaylistsByUser(Long userId);
}