package com.razan.playlists.core;

import com.razan.playlists.entity.Song;
import com.razan.playlists.entity.Playlist;
import com.razan.playlists.repo.SongRepo;
import com.razan.playlists.repo.PlaylistRepo;
import com.razan.playlists.api.AddSongRequest;
import com.razan.playlists.api.CreatePlaylistRequest;
import com.razan.playlists.api.PlaylistResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistManagerImp implements PlaylistManager {

    private final PlaylistRepo playlistRepo;
    private final SongRepo songRepo;

    @Override
    @Transactional
    public PlaylistResponse createPlaylist(CreatePlaylistRequest request) {
        Playlist playlist = Playlist.builder()
                .name(request.getName())
                .userId(request.getUserId())
                .build();

        Playlist newPlaylist = playlistRepo.save(playlist);
        return mapToResponse(newPlaylist);
    }

    @Override
    @Transactional
    public PlaylistResponse addSongToPlaylist(Long playlistId, AddSongRequest request) {
        Playlist playlist;
        try {
            playlist = playlistRepo.findById(playlistId).get();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Playlist not found with id: " + playlistId);
        }

        Song song;
        try {
            song = songRepo.findById(request.getSongId()).get();
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Song not found with id: " + request.getSongId());
        }

        playlist.getSongs().add(song);
        Playlist result = playlistRepo.save(playlist);
        return mapToResponse(result);
    }

    @Override
    public List<PlaylistResponse> getPlaylistsByUser(Long userId) {
        List<Playlist> playlists = playlistRepo.findByUserId(userId);
        List<PlaylistResponse> responses = new ArrayList<>();

        for (Playlist playlist : playlists) {
            PlaylistResponse response = mapToResponse(playlist);
            responses.add(response);
        }
        return responses;
    }

    private PlaylistResponse mapToResponse(Playlist playlist) {
        return PlaylistResponse.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .userId(playlist.getUserId())
                .songs(playlist.getSongs())
                .build();
    }
}