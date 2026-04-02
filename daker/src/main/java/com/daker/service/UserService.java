package com.daker.service;

import com.daker.domain.dto.request.UserRequestDTO;
import com.daker.domain.dto.response.UserResponseDTO;
import com.daker.domain.entity.*;
import com.daker.domain.entity.mapping.TeamEnter;
import com.daker.domain.entity.mapping.TeamHackathon;
import com.daker.domain.entity.mapping.UserSkill;
import com.daker.domain.entity.mapping.UserTeam;
import com.daker.repository.*;
import com.daker.util.code.ErrorCode;
import com.daker.util.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.daker.util.code.ErrorCode.*;

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
    private final TeamEnterRepository teamEnterRepository;
    private final TemperatureSetRepository temperatureSetRepository;

    public UserResponseDTO.LoginDTO login(UserRequestDTO.LoginDTO request) {
        User user = userRepository.findIdByLoginIdandPassword(request.getLoginId(), request.getPassword());

        return UserResponseDTO.LoginDTO.builder()
                .userId(user.getId())
                .userNickname(user.getNickname())
                .userEmail(user.getEmail())
                .build();
    }

    public void signup(UserRequestDTO.SignupDTO request) {
        Optional<User> dup1 = userRepository.findByEmail(request.getEmail());
        if(dup1.isPresent()) throw new ApiException(EmailDuplicate);
        Optional<User> dup2 = userRepository.findByLoginId(request.getLoginId());
        if(dup2.isPresent()) throw new ApiException(LoginIdDuplicate);

        User user = User.builder()
            .email(request.getEmail())
            .loginId(request.getLoginId())
            .password(request.getPassword())
            .name(request.getName())
            .nickname(request.getNickName())
            .point(0)
            .temperature(37f).build();
    
        userRepository.save(user);
    }

    public void withdrawalMembership(Long userId) {
        userRepository.deleteById(userId);
    }


    // 팀즈
    public UserResponseDTO.InvitationListDTO getTeams(long userId, String type) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));

        List<TeamEnter> invitations = new ArrayList<>();
        if(type.equals("0")) invitations = teamEnterRepository.findAllBySender(user);
        else invitations = teamEnterRepository.findAllBySenderAndType(user, Integer.parseInt(type));

        List<UserResponseDTO.InvitationDTO> invitationDTOS = new ArrayList<>();
        for(TeamEnter te : invitations) {
            User sender = te.getSender();

            UserResponseDTO.InvitationSenderDTO senderDTO = UserResponseDTO.InvitationSenderDTO.builder()
                    .userId(sender.getId())
                    .userName(sender.getName())
                    .teamName(te.getTeam().getName()).build();

            UserResponseDTO.InvitationDTO data = UserResponseDTO.InvitationDTO.builder()
                    .invitationId(te.getId())
                    .content(te.getContent())
                    .type(te.getType())
                    .sender(senderDTO)
                    .position(te.getPosition().getId())
                    .created_at(te.getCreated_at()).build();
            invitationDTOS.add(data);
        }

        return UserResponseDTO.InvitationListDTO.builder()
                .invitations(invitationDTOS).build();
    }

    public void deleteInvitation(long userId, long invitationId) {
        TeamEnter invitation = teamEnterRepository.findById(invitationId).orElseThrow(() -> new ApiException(NOT_FOUND_404));
        teamEnterRepository.delete(invitation);
    }
    
    
    // 마이페이지
    public UserResponseDTO.MyPageDTO getMyPage(long userId) {
        UserResponseDTO.MyPageDTO result = new UserResponseDTO.MyPageDTO();

        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
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
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(TEAM_NOT_FOUND_404));

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
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        user.setNickname(request.getNickName());
        user.setDescription(request.getDescription());
        user.setPortfolio(request.getPortfolio());
        user.setGithub(request.getGithub());

        userSkillRepository.findSkillsByUser(user).forEach((userSkill) -> userSkillRepository.delete(userSkill));
        request.getSkills().forEach((id) -> {
            Skill skill = skillRepository.findById(id).get();
            userSkillRepository.save(UserSkill.builder()
                    .user(user)
                    .skill(skill).build());
        });
    }

    public UserResponseDTO.SkillsDTO getSkills() {
        return UserResponseDTO.SkillsDTO.builder()
                .skills(skillRepository.findAll().stream()
                .map(skill -> UserResponseDTO.SkillDTO.builder()
                        .id(skill.getId())
                        .name(skill.getName()).build())
                .toList()).build();
    }


    // 온도 측정
    public UserResponseDTO.TemperatureSetListDTO getTemperatureSetting(long userId, long teamId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(TEAM_NOT_FOUND_404));
        UserTeam userTeams = userTeamRepository.findByUserAndTeam(user, team).orElseThrow(() -> new ApiException(NOT_FOUND_404));

        return UserResponseDTO.TemperatureSetListDTO.builder()
                .members(userTeamRepository.findAllUsersByTeam(userTeams.getTeam()).stream()
                        .filter((u) -> u != user)
                        .map((u) -> UserResponseDTO.TemperatureSetDTO.builder()
                                .userId(u.getId())
                                .userNickName(u.getNickname())
                                .userEmail(u.getEmail())
                                .canSet(temperatureSetRepository.findByFromUserAndToUserAndTeam(user, u, team).isEmpty()).build())
                        .toList()).build();
    }

    public void setTemperature(long userId, long teamId, UserRequestDTO.SetTemperatureDTO request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        User target = userRepository.findById(request.getUserId()).orElseThrow(() -> new ApiException(USER_NOT_FOUND_404));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ApiException(TEAM_NOT_FOUND_404));
        userTeamRepository.findByUserAndTeam(user, team).orElseThrow(() -> new ApiException(BAD_REQUEST));
        userTeamRepository.findByUserAndTeam(target, team).orElseThrow(() -> new ApiException(BAD_REQUEST));
        if(temperatureSetRepository.findByFromUserAndToUserAndTeam(user, target, team).isPresent()) throw new ApiException(BEFORE_TEMPERATURE_SET);

        temperatureSetRepository.save(TemperatureSet.builder()
                .fromUser(user)
                .toUser(target)
                .team(team)
                .value(request.isPlus()).build());

        target.setTemperature(target.getTemperature() + (request.isPlus() ? 0.3f : -0.1f));
        userRepository.save(target);
    }


    // 검색
    public UserResponseDTO.UserInfoListDTO search(String query) {
        return UserResponseDTO.UserInfoListDTO.builder()
                .users(userRepository.search(query).stream().map((user) ->
                        UserResponseDTO.UserInfoDTO.builder()
                                .userId(user.getId())
                                .userName(user.getNickname())
                                .userEmail(user.getEmail()).build()).toList()).build();
    }
}
