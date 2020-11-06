package by.tms.service.post;

import by.tms.dao.post.PostDao;
import by.tms.entity.Comment;
import by.tms.entity.Like;
import by.tms.entity.Post;
import by.tms.service.post.exception.DuplicatePostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;



    @Override
    public List<Post> getAllPosts() {
        return postDao.getAllPosts();
    }
    @Override
    public Optional<Post> getPostByTitle(String title) {
        return postDao.getPostByTitle(title);
    }
    @Override
    public Optional<Post> GetPostById(long id) { return postDao.getPostById(id); }



    @Override
    public void createPost(Post post) {
        if (postDao.existPostByTitle(post.getTitle())) {
            throw new DuplicatePostException();
        }
        postDao.createPost(post);
    }
    @Override
    public void deletePost(long postId) {
        postDao.deletePost(postId);
    }



    @Override
    public void setComment(Comment comment, long postId) {
        Post post = postDao.getPostById(postId).orElseThrow(RuntimeException::new);
        post.getComments().add(comment);
        postDao.updatePost(post);
    }
    @Override
    public void tryDeleteComment(long commentId, long postId) {
        Post post = postDao.getPostById(postId).orElseThrow(RuntimeException::new);
        List<Comment> comments = post.getComments()
                .stream()
                .filter(comment -> comment.getId() != commentId)
                .collect(Collectors.toList());
        post.setComments(comments);
        postDao.updatePost(post);
    }



    @Override
    public void setLike(Like like, long postId) {
//        if (like == null) {
//            throw new IllegalArgumentException("Comment is null!");
//        }
//        if (postId <= 0) {
//            throw new IllegalArgumentException("Id is not correct!");
//        }
//        if (likeDao.containLike(like, postId)) {
//            likeDao.deleteLikeToPost(like, postId);
//            return;
//        }
//        likeDao.addLikeToPost(like, postId);
    }
}
