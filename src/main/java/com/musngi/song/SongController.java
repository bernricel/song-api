package com.musngi.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/musngi/songs")
public class SongController {

    @Autowired
    private SongRepository songRepository;

    @GetMapping
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    @PostMapping
    public Song addSong(@RequestBody Song song) {
        return songRepository.save(song);
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        Optional<Song> song = songRepository.findById(id);
        return song.orElse(null);
    }

    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        Optional<Song> existingSong = songRepository.findById(id);

        if (existingSong.isPresent()) {
            Song song = existingSong.get();
            song.setTitle(updatedSong.getTitle());
            song.setArtist(updatedSong.getArtist());
            song.setAlbum(updatedSong.getAlbum());
            song.setGenre(updatedSong.getGenre());
            song.setUrl(updatedSong.getUrl());
            return songRepository.save(song);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteSong(@PathVariable Long id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return "Song with ID " + id + " deleted.";
        }
        return "Song with ID " + id + " not found.";
    }

    @GetMapping("/search/{text}")
    public List<Song> searchSongs(@PathVariable String text) {
        return songRepository
                .findByTitleContainingIgnoreCaseOrArtistContainingIgnoreCaseOrAlbumContainingIgnoreCaseOrGenreContainingIgnoreCase(
                        text, text, text, text
                );
    }
}