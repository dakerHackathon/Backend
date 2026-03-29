package com.daker.service;

import com.daker.domain.dto.request.UserRequestDTO;
import com.daker.domain.dto.response.UserResponseDTO;
import com.daker.domain.entity.*;
import com.daker.repository.*;
import com.daker.util.code.ErrorCode;
import com.daker.util.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final UserTeamRepository userTeamRepository;
    private final MessageRepository messageRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TeamHackathonRepository teamHackathonRepository;
    private final TeamRepository teamRepository;
    private final SkillRepository skillRepository;

    public UserResponseDTO.LoginDTO login(UserRequestDTO.LoginDTO request) {
        User user = userRepository.findIdByLoginIdandPassword(request.getLoginId(), request.getPassword());

        return UserResponseDTO.LoginDTO.builder()
                .userId(user.getId())
                .build();
    }

    public void signup(UserRequestDTO.SignupDTO request) {
        User user = User.builder()
            .email(request.getEmail())
            .loginId(request.getLoginId())
            .password(request.getPassword())
            .name(request.getName())
            .nickname(request.getNickName()).build();
    
        userRepository.save(user);
    }

    public void withdrawalMembership(Long userId) {
        userRepository.deleteById(userId);
    }
    
    
    
    // 마이페이지
    public UserResponseDTO.MyPageDTO getMyPage(long userId) {
        UserResponseDTO.MyPageDTO result = new UserResponseDTO.MyPageDTO();

        User user = userRepository.findById(userId).get();
        result.setEmail(user.getEmail());
        result.setNickname(user.getNickname());
        result.setDescription(user.getDescription());
        result.setPortfolio(user.getPortfolio());
        result.setGithub(user.getGithub());
        result.setPoint(user.getPoint());
        result.setTemperature(user.getTemperature());
        result.setWinCount(0);
        result.setPartCount(0);
        
        int ranks = userRepository.getRank(user.getPoint()) + 1;
        result.setRank(ranks);

        int unreadCnt = messageRepository.countUnreadByReceiver(user);
        result.setUnread(unreadCnt);

        List<UserResponseDTO.SkillIdDTO> skills = new ArrayList<>();
        userSkillRepository.findSkillsByUser(user).forEach((userSkill) -> {
            skills.add(UserResponseDTO.SkillIdDTO.builder()
                    .id(userSkill.getSkill().getId()).build());
        });
        result.setStills(skills);

        List<UserResponseDTO.SaveHackathonDTO> saveHackathons = new ArrayList<>();
        bookmarkRepository.findHackathonByUser(user).forEach((hackathon) -> {
            saveHackathons.add(UserResponseDTO.SaveHackathonDTO.builder()
                    .hackathonId(hackathon.getId())
                    .hackathonName(hackathon.getTitle())
                    .end(hackathon.getEndAt().toString()).build());
        });
        result.setSave_hackathon(saveHackathons);

        List<UserTeam> teamList = userTeamRepository.findAllByUser(user);
        List<UserResponseDTO.PartHackathonDTO> partHackathons = new ArrayList<>();
        List<UserResponseDTO.PartTeamsDTO> teams = new ArrayList<>();
        teamList.forEach((ut) -> {
            Team team = ut.getTeam();
            teams.add(UserResponseDTO.PartTeamsDTO.builder()
                    .teamId(team.getId())
                    .teamName(team.getName())
                    .description(team.getDescription()).build());

            List<TeamHackathon> parts = teamHackathonRepository.findAllByTeam(team);
            parts.forEach((th) -> {
                Hackathon hackathon = th.getHackathon();

                partHackathons.add(UserResponseDTO.PartHackathonDTO.builder()
                        .hackathonId(hackathon.getId())
                        .hackathonName(hackathon.getTitle())
                        .start(hackathon.getStartAt().toString())
                        .end(hackathon.getEndAt().toString())
                        .position(ut.getPosition().getId()).build());

                result.setPartCount(result.getPartCount() + 1);
                if(th.getRanking() == 1) result.setWinCount(result.getWinCount() + 1);
            });
        });
        result.setTeams(teams);
        result.setPart_hackathon(partHackathons);

        return result;
    }

    public UserResponseDTO.UserInfoListDTO getTeamMembers(long userId, long teamId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_404));

        return UserResponseDTO.UserInfoListDTO.builder().users(
                    userTeamRepository.findAllUsersByTeam(team).stream()
                        .filter((member) -> member != user)
                        .map((member) -> UserResponseDTO.UserInfoDTO.builder()
                            .userId(member.getId())
                            .userName(member.getNickname())
                            .userEmail(member.getEmail()).build()
                        ).toList()).build();
    }

    public void editInfo(long userId, UserRequestDTO.EditInfoDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_404));
        user.setNickname(request.getNickName());
        user.setDescription(request.getDescription());
        user.setPortfolio(request.getPortfolio());
        user.setGithub(request.getGithub());

        userSkillRepository.findSkillsByUser(user).forEach((userSkill) -> userSkillRepository.delete(userSkill));
        request.getSkills().forEach((id) -> {
            Skill skill = skillRepository.findById(id.getId()).get();
            userSkillRepository.save(UserSkill.builder()
                    .user(user)
                    .skill(skill).build());
        });
    }

    public UserResponseDTO.UserInfoListDTO search(String query) {
        return UserResponseDTO.UserInfoListDTO.builder()
                .users(userRepository.search(query).stream().map((user) ->
                        UserResponseDTO.UserInfoDTO.builder()
                                .userId(user.getId())
                                .userName(user.getNickname())
                                .userEmail(user.getEmail()).build()).toList()).build();
    }
}
