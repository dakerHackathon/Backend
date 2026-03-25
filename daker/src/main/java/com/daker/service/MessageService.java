package com.daker.service;

import com.daker.domain.dto.request.MessageRequestDTO;
import com.daker.domain.dto.response.MessageResponseDTO;
import com.daker.domain.entity.Message;
import com.daker.domain.entity.User;
import com.daker.repository.MessageRepository;
import com.daker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageResponseDTO.MessagesDTO getMessages(long userId) {
        User user = userRepository.findById(userId).get();
        List<Message> list = messageRepository.findByReceiverId(user);

        List<MessageResponseDTO.MessagesSimpleInfo> result = list.stream()
                .map(message -> {
                    MessageResponseDTO.MessagesSimpleInfo info = MessageResponseDTO.MessagesSimpleInfo.builder()
                            .id(message.getId())
                            .content(message.getContent())
                            .sender(message.getReceiver().getNickname())
                            .send_at(message.getSendAt().toString())
                            .isRead(message.getIsRead())
                            .isStar(message.getIsStar()).build();

                    return info;
                }).toList();

        return MessageResponseDTO.MessagesDTO.builder()
                .messages(result).build();
    }

    public void starMessage(MessageRequestDTO.MessageIdDTO request) {
        Message message = messageRepository.findById(request.getMessageId()).get();
        message.setIsStar(!message.getIsStar());
    }

    public void sendMessage(long userId, MessageRequestDTO.sendMessageDTO request) {
        User user = userRepository.findById(userId).get();
        Message message = Message.builder()
                .sender(user)
                .receiver(userRepository.findByEmail(request.getEmail()))
                .title(request.getTitle())
                .content(request.getContent())
                .sendAt(LocalDateTime.now())
                .isRead(false)
                .isStar(false).build();

        messageRepository.save(message);
    }

    public void deleteMessage(long messageId) {
        Message message = messageRepository.findById(messageId).get();
        messageRepository.delete(message);
    }
}
