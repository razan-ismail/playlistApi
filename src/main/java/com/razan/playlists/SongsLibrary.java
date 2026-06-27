package com.razan.playlists;

import com.razan.playlists.entity.Song;
import com.razan.playlists.repo.SongRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SongsLibrary implements CommandLineRunner {
    private final SongRepo songRepo;

    @Override
    public void run(String... args) throws Exception {
        if (songRepo.count() == 0) {
            Song s1 = Song.builder().title("Risk it All").artist("Bruno Mars").build();
            Song s2 = Song.builder().title("Tears Dry").artist("Amy Winehouse").build();
            Song s3 = Song.builder().title("Wayah").artist("Amr Diab").build();
            Song s4 = Song.builder().title("Mashaier").artist("Sherine").build();
            Song s5 = Song.builder().title("Cardigan").artist("Taylor Swift").build();
            songRepo.save(s1);
            songRepo.save(s2);
            songRepo.save(s3);
            songRepo.save(s4);
            songRepo.save(s5);
            System.out.println("5 sample songs loaded into the Songs Library!");
        }
    }
}