package com.myproject.jpaboard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 댓글을 작성한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; // 댓글이 속한 게시글

    @Lob
    @Column(columnDefinition="LONGTEXT")
    private String content; // 댓글 내용

    @Column(updatable = false)
    private LocalDateTime createdTime = LocalDateTime.now(); // 생성 시간
    private LocalDateTime lastModifiedTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment; // 상위 댓글

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>(); // 하위 댓글 리스트

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", member=" + member +
                ", content='" + content + '\'' +
                ", createdTime=" + createdTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", replies=" + replies +
                '}';
    }
}
