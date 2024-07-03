package com.myproject.jpaboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String writer;

    @Lob
    @Column(columnDefinition="LONGTEXT")
    private String content;

    @Column(updatable = false)
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY) // 다쪽이 연관관계의 주인
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();


    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", createdTime=" + createdTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", viewCount=" + viewCount +
                ", category=" + category +
                '}';
    }
}
