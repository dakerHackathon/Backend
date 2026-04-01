package com.daker.service;

import com.daker.domain.dto.response.RankResponseDTO;
import com.daker.domain.entity.User;
import com.daker.repository.UserRepository;
import com.daker.repository.UserTeamRepository;
import com.daker.util.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.daker.util.code.ErrorCode.BAD_REQUEST;
import static com.daker.util.code.ErrorCode.NOT_FOUND_404;

@Service
@RequiredArgsConstructor
@Transactional
public class RankService {

    private final UserRepository userRepository;
    private final UserTeamRepository userTeamRepository;

    public RankResponseDTO.TopDTO getRankings(String filter) {
        List<User> result = switch (filter) {
            case "part" -> userTeamRepository.getPartRankings();
            case "temp" -> userRepository.findTop10ByOrderByTemperatureDesc();
            case "win" -> userTeamRepository.getWinRankings();
            default -> throw new ApiException(BAD_REQUEST);
        };

        return RankResponseDTO.TopDTO.builder().ranks(
                result.stream().map((user) ->
                    RankResponseDTO.RankDTO.builder()
                            .id(user.getId())
                            .nickname(user.getNickname())
                            .point(user.getPoint())
                            .github(user.getGithub()).build()).toList())
                .build();
    }

    public RankResponseDTO.MyRankDTO getMyRanking(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(NOT_FOUND_404));

        return RankResponseDTO.MyRankDTO.builder()
                .temp(RankResponseDTO.RankPointDTO.builder()
                        .rank(userRepository.getTempRank(user) + 1)
                        .point(user.getTemperature()).build())
                .win(RankResponseDTO.RankPointDTO.builder()
                        .rank(userTeamRepository.getMyWinRank(user))
                        .point(userTeamRepository.getWinCount(user)).build())
                .part(RankResponseDTO.RankPointDTO.builder()
                        .rank(userTeamRepository.getMyPartRank(user))
                        .point(userTeamRepository.getPartCount(user)).build()).build();
    }

    public RankResponseDTO.TopAllDTO getTop3() {
        return RankResponseDTO.TopAllDTO.builder()
                .win(userTeamRepository.getWinRankings().stream()
                        .limit(3)
                        .map((user) -> RankResponseDTO.SimpleRankDTO.builder()
                                .nickname(user.getNickname())
                                .point(userTeamRepository.getWinCount(user)).build())
                        .toList())
                .temp(userRepository.findTop10ByOrderByTemperatureDesc().stream()
                        .limit(3)
                        .map((user) -> RankResponseDTO.SimpleRankDTO.builder()
                                .nickname(user.getNickname())
                                .point(user.getTemperature()).build())
                        .toList())
                .part(userTeamRepository.getPartRankings().stream()
                        .limit(3)
                        .map((user) -> RankResponseDTO.SimpleRankDTO.builder()
                                .nickname(user.getNickname())
                                .point(userTeamRepository.getPartCount(user)).build())
                        .toList())
                .build();
    }
}