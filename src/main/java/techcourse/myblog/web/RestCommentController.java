package techcourse.myblog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import techcourse.myblog.domain.comment.CommentDto;
import techcourse.myblog.service.CommentService;

import java.util.List;

@RestController
public class RestCommentController {
    private final CommentService commentService;

    public RestCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{articleId}")
    public List<CommentDto> findAllByArticleId(@PathVariable long articleId) {
        return commentService.findByArticleId(articleId);
    }
}
