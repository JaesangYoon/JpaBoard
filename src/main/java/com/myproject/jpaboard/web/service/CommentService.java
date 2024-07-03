package com.myproject.jpaboard.web.service;

import com.myproject.jpaboard.domain.Comment;
import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.domain.Post;
import com.myproject.jpaboard.web.repository.BoardRepository;
import com.myproject.jpaboard.web.repository.CommentRepository;
import com.myproject.jpaboard.web.repository.MemberRepository;
import com.myproject.jpaboard.web.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public CommentService(MemberRepository memberRepository, PostRepository postRepository, BoardRepository boardRepository, CommentRepository commentRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * 댓글 추가
     */
    @Transactional
    public Comment newComment(String commentContent, Long postId, MemberSessionDto loginMember) {

        Member findMember = memberRepository.findById(loginMember.getId());
        Post findPost = boardRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("No Post Found"));
        LocalDateTime now = LocalDateTime.now();

        Comment comment = new Comment();
        comment.setMember(findMember);
        comment.setPost(findPost);
        comment.setContent(commentContent);
        comment.setCreatedTime(now);
        comment.setLastModifiedTime(now);
        comment.setParentComment(null);
        comment.setReplies(new ArrayList<>());

        return comment;
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public void editComment(String commentContent, Long cmtId) {
        Comment findComment = commentRepository.findById(cmtId).orElseThrow(() -> new IllegalStateException("No Comment Found"));
        LocalDateTime now = LocalDateTime.now();

        findComment.setLastModifiedTime(now);
        findComment.setContent(commentContent);

    }

    /**
     * 답글 추가
     */
    @Transactional
    public Comment addReply(String replyContent, Long postId, Comment parentComment, MemberSessionDto loginMember) {

        Member findMember = memberRepository.findById(loginMember.getId());
        Post findPost = boardRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("No Post Found"));
        LocalDateTime now = LocalDateTime.now();

        Comment reply = new Comment();
        reply.setMember(findMember);
        reply.setPost(findPost);
        reply.setContent(replyContent);
        reply.setCreatedTime(now);
        reply.setLastModifiedTime(now);
        reply.setParentComment(parentComment);
        reply.setReplies(null);

        parentComment.getReplies().add(reply);

        commentRepository.save(reply);

        return reply;
    }


}
