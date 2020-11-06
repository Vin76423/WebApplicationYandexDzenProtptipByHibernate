package by.tms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
//    private static final String DATE_PATTERN = "yyyy-MM-dd  HH:mm:ss";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String massage;
//    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    private User author;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "post_id", referencedColumnName = "id")
//    private List<Like> likes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<Comment> comments = new ArrayList<>();

//    public String showDate() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
//        return date.format(formatter);
//    }
}
