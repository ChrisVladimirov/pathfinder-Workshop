package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.model.Comment;
import bg.softuni.pathfinder.model.User;
import bg.softuni.pathfinder.model.dto.CommentCreationDto;
import bg.softuni.pathfinder.model.views.CommentDisplayView;
import bg.softuni.pathfinder.repository.CommentRepository;
import bg.softuni.pathfinder.repository.RouteRepository;
import bg.softuni.pathfinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private RouteRepository routeRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public CommentService(RouteRepository routeRepository, UserRepository userRepository,
                          CommentRepository commentRepository) {
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public CommentDisplayView createComment(CommentCreationDto commentDto) {
        User author = userRepository.findByUsername(commentDto.getUsername()).get();

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setRoute(routeRepository.getById(commentDto.getRouteId()));
        comment.setAuthor(author);
        comment.setApproved(true);
        comment.setText(commentDto.getMessage());

        commentRepository.save(comment);

        return new CommentDisplayView(comment.getId(), author.getFullName(), comment.getText());
    }
}