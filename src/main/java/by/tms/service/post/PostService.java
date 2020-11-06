package by.tms.service.post;

import by.tms.entity.Comment;
import by.tms.entity.Like;
import by.tms.entity.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAllPosts();
    Optional<Post> getPostByTitle(String title);
    Optional<Post> GetPostById(long id);

    void createPost(Post post);
    void deletePost(long postId);

    void setComment(Comment comment, long postId);
    void tryDeleteComment(long commentId, long postId);

    void setLike(Like like, long postId);
}
