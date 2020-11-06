package by.tms.dao.post;

import by.tms.entity.Comment;
import by.tms.entity.Post;
import java.util.List;
import java.util.Optional;

public interface PostDao {
    List<Post> getAllPosts();
    Optional<Post> getPostByTitle(String title);
    Optional<Post> getPostById(long id);
    void createPost(Post post);
    void updatePost(Post post);
    void deletePost(long postId);

    boolean existPostByTitle(String title);
    boolean existPostById(long id);

    void addComment(Comment comment);
    void deleteComment(Comment comment);

    void addLike(Comment comment, long postId);
    void deleteLike(long commentId, long postId);
}
