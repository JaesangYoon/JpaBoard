package com.myproject.jpaboard.web.service;


import com.myproject.jpaboard.domain.Post;
import com.myproject.jpaboard.web.repository.BoardRepository;
import com.myproject.jpaboard.web.repository.PostRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostService postService;
    private final JPAQueryFactory queryFactory;
    private final PostRepository postRepository;

    /**
     * 게시물 조회
     */
    public List<Post> findAll() {
        return boardRepository.findAll();
    }

    public Optional<Post> findOne(Long id) {
        postService.addViewCount(id);
        return boardRepository.findById(id);
    }

    /**
     * 게시물 검색 (QueryDSL, 페이지네이션 미완성)
     */
//    public Page<Post> searchPosts(String searchType, String searchKeyword, Pageable pageable) {
//
//        Long totalCount;
//        List<Post> posts;
//
//        if ("title".equals(searchType)) {
//            // 전체 개수 구하기
//            totalCount = queryFactory.select(post.count())
//                    .from(post)
//                    .where(post.title.eq(searchKeyword))
//                    .fetchOne();
//
//            // 페이징된 결과 구하기
//            posts = queryFactory.selectFrom(post)
//                    .where(post.title.contains(searchKeyword))
//                    .orderBy(post.createdTime.desc())
//                    .offset(pageable.getOffset())
//                    .limit(pageable.getPageSize())
//                    .fetch();
//        } else if ("writer".equals(searchType)) {
//            totalCount = queryFactory.select(post.count())
//                    .from(post)
//                    .where(post.writer.eq(searchKeyword))
//                    .fetchOne();
//
//            posts = queryFactory.selectFrom(post)
//                    .where(post.writer.contains(searchKeyword))
//                    .orderBy(post.createdTime.desc())
//                    .offset(pageable.getOffset())
//                    .limit(pageable.getPageSize())
//                    .fetch();
//        } else {
//            totalCount = 0L;
//            posts = List.of(); // 빈 리스트 반환
//        }
//
//
//        return new PageImpl<>(posts, Pageable.unpaged(), totalCount);
//
//
//    }

    /**
     * 게시물 검색 (Spring Data JPA)
     */
    public Page<Post> searchPosts(String searchType, String searchKeyword, Pageable pageable) {
        if ("title".equalsIgnoreCase(searchType)) {
            return postRepository.findByTitleContainingIgnoreCaseOrderByCreatedTimeDesc(searchKeyword, pageable);
        } else if ("writer".equalsIgnoreCase(searchType)) {
            return postRepository.findByWriterContainingIgnoreCaseOrderByCreatedTimeDesc(searchKeyword, pageable);
        } else {
            return Page.empty(pageable);
        }
    }



}
