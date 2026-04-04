package com.daker.service;

import com.daker.domain.dto.response.HackathonResponseDTO;
import com.daker.domain.entity.HackathonDetail;
import com.daker.domain.entity.Team;
import com.daker.domain.entity.mapping.Bookmark;
import com.daker.domain.entity.Hackathon;
import com.daker.domain.entity.User;
import com.daker.domain.entity.mapping.TeamHackathon;
import com.daker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class HackathonService {
    private final UserRepository userRepository;
    private final HackathonRepository hackathonRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TeamRepository teamRepository;
    private final TeamHackathonRepository teamHackathonRepository;

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

    public HackathonResponseDTO.getHackathonList hackathons(long userId){
        List<Hackathon> hackathon = hackathonRepository.findAll();
        User user = userRepository.findById(userId).get();
        List<Boolean> isStar = new ArrayList<>();
        for(int i = 0; i < hackathon.size(); i++) {
            Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndHackathon(user, hackathon.get(i));
            isStar.add(bookmark.isPresent());
        }

        List<HackathonResponseDTO.HackathonResponseDTOBuilder> result = new ArrayList<>();
        for(int i = 0; i < hackathon.size(); i++) {
            Hackathon currentHackathon = hackathon.get(i);
            boolean currentIsStar = isStar.get(i);

            result.add(HackathonResponseDTO.HackathonResponseDTOBuilder.builder()
                    .id(currentHackathon.getId())
                    .title(currentHackathon.getTitle())
                    .start_at(currentHackathon.getStartAt().toString())
                    .end_at(currentHackathon.getEndAt().toString())
                    .location(currentHackathon.getLocation())
                    .isStar(currentIsStar).build());
        }

        return HackathonResponseDTO.getHackathonList.builder()
                .hackathons(result).build();
    }


    public HackathonResponseDTO.getHackathonDetail hackathonDetail(long userId, long hackathonId) {
        List<Hackathon> hackathon = hackathonRepository.findAll();
        User user = userRepository.findById(userId).get();
        List<Boolean> isStar = new ArrayList<>();
        for(int i = 0; i < hackathon.size(); i++) {
            Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndHackathon(user, hackathon.get(i));
            isStar.add(bookmark.isPresent());
        }
        List<TeamHackathon> teams = teamHackathonRepository.findAll();





        return null;
    }
}
