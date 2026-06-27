package com.razan.playlists.repo;

import com.razan.playlists.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepo extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUserId(Long userId);
}