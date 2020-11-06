package by.tms.dao.post;

import by.tms.entity.Comment;
import by.tms.entity.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PostDbDao implements PostDao {

    @Autowired
    private SessionFactory sessionFactory;


    // ......................................DSL.............................................................
    @Override
    public List<Post> getAllPosts() {
        Session currentSession = sessionFactory.getCurrentSession();
        List<Post> postList = currentSession.createQuery("from Post", Post.class).getResultList();
        return postList;
    }
    @Override
    public Optional<Post> getPostByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        String sql = "from Post p where p.title = :title";
        Optional<Post> optionalPost = currentSession.createQuery(sql, Post.class)
                .setParameter("title", title)
                .uniqueResultOptional();
        currentSession.clear();
        return optionalPost;
    }
    @Override
    public Optional<Post> getPostById(long postId) {
        if (postId < 1) {
            throw new IllegalArgumentException("PostId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        Post post = currentSession.get(Post.class, postId);
        currentSession.clear();
        return Optional.ofNullable(post);
    }


    // ......................................DML.............................................................
    @Override
    public void createPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(post);
        currentSession.flush();
        currentSession.clear();
    }
    @Override
    public void updatePost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.merge(post);
        currentSession.flush();
        currentSession.clear();
    }
    @Override
    public void deletePost(long postId) {
        if (postId < 1) {
            throw new IllegalArgumentException("PostId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();

//        Все-таки это запрос, прокаченный HQL но все же запрос, поэтому работает более локально в рамках одной таблицы
//        (если без джоинов, а для DМL оперций помоему Hibernate джойны и не использует), поютому в данном случае схватим
//        оштбку: org.postgresql.util.PSQLException: ОШИБКА: UPDATE или DELETE в таблице "posts" нарушает ограничение внешнего ключа
//        "fka22m6vdy2ysj0mh135mdycs1s" таблицы "comment".
//        Вывод: для комплексных DМL операций лучше использовать методы такие как delete(post), delete(post)
//        а так же мутация обьекта с последующим flush() (полагаю они разбивают комплексную операцию на составляющие внутрисебя
//        и выполняют средствами HQL) :

//        currentSession.createQuery("delete from Post p where p.id = :postId")
//                .setParameter("postId", postId).
//                executeUpdate();

        Post post = currentSession.get(Post.class, postId);
        currentSession.delete(post);
        currentSession.flush();
        currentSession.clear();
    }


    //......................................exist-scripts...............................................
    @Override
    public boolean existPostByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        String sql = "select count(*) from Post p where p.title = :title";
        long result = (Long) currentSession.createQuery(sql)
                .setParameter("title", title)
                .uniqueResult();
        currentSession.clear();
        return result > 0;
    }
    @Override
    public boolean existPostById(long postId) {
        if (postId < 1) {
            throw new IllegalArgumentException("PostId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        Post post = currentSession.get(Post.class, postId);
        currentSession.clear();
        return post != null;
    }




















    @Override
    public void addComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(comment);
        currentSession.flush();
        currentSession.clear();
    }

    @Override
    public void deleteComment(Comment comment) { //long commentId
//        if (commentId < 1) {
//            throw new IllegalArgumentException("CommentId is not correct!");
//        }
        if (comment == null) {
            throw new IllegalArgumentException("Comment is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(comment);
        currentSession.flush();
        currentSession.clear();

    }

    @Override
    public void addLike(Comment comment, long postId) {

    }

    @Override
    public void deleteLike(long commentId, long postId) {

    }
}
