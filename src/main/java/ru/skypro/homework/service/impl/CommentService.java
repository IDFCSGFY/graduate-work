package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository repository;
    private final AdService adService;
    private final UserProfileService userProfileService;
    private final CommentMapper mapper;
    private final JdbcUserDetailsManager manager;

    public CommentService(CommentRepository repository, AdService adService, UserProfileService userProfileService, CommentMapper mapper, JdbcUserDetailsManager manager) {
        this.repository = repository;
        this.adService = adService;
        this.userProfileService = userProfileService;
        this.mapper = mapper;
        this.manager = manager;
    }

    public Comments findAllByAdId(Integer adId) {
        List<CommentEntity> entityList = repository.findAllByAdId(adId);
        Comments comments = new Comments();
        comments.setResults(entityList.stream().map(mapper::toDto).collect(Collectors.toList()));
        comments.setCount(comments.getResults().size());
        return comments;
    }

    public Comment createComment(Authentication authentication, Integer adId, CreateOrUpdateComment updateComment) {
        CommentEntity entity = new CommentEntity(
                updateComment.getText(),
                Instant.now(),
                userProfileService.findUserByUsername(authentication.getName()),
                adService.findAdById(adId));
        repository.save(entity);
        return mapper.toDto(entity);
    }

    public void deleteCommentById(Authentication authentication, Integer id) {
        commentValidationCheck(id);
        initiatorValidationCheck(authentication, id);
        repository.deleteById(id);
    }

    public Comment updateCommentById(Authentication authentication, Integer id, CreateOrUpdateComment updateComment) {
        commentValidationCheck(id);
        initiatorValidationCheck(authentication, id);
        CommentEntity entity = repository.findById(id).get();
        entity.setText(updateComment.getText());
        return mapper.toDto(repository.save(entity));
    }

    private void initiatorValidationCheck(Authentication authentication, Integer id) {
        Integer initiatorId = userProfileService.findUserByUsername(authentication.getName()).getId();
        Integer commentAuthorId = repository.findById(id).get().getAuthor().getId();

        UserDetails details = manager.loadUserByUsername(authentication.getName());
        String initiatorRole = details.getAuthorities().stream().limit(1).toString();

        if (!commentAuthorId.equals(initiatorId) || initiatorRole.equals("ROLE_ADMIN")) {
            throw new IllegalArgumentException();
        }
    }

    private void commentValidationCheck(Integer id) {
        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
