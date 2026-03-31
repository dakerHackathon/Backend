package com.daker.service;

import com.daker.domain.entity.mapping.Bookmark;
import com.daker.domain.entity.Hackathon;
import com.daker.domain.entity.User;
import com.daker.repository.BookmarkRepository;
import com.daker.repository.HackathonRepository;
import com.daker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HackathonService {
    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;
    private final BookmarkRepository bookmarkRepository;

    public void saveHackathon(long userId, long hackathonId) {
        User user = userRepository.findById(userId).get();
        Hackathon hackathon = hackathonRepository.findById(hackathonId).get();

        Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndHackathon(user, hackathon);
        if (bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
        }
        else {
            Bookmark newBookmark = Bookmark.builder()
                    .user(user).hackathon(hackathon).build();
            bookmarkRepository.save(newBookmark);
        }
    }
}
