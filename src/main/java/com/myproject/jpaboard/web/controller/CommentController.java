package com.myproject.jpaboard.web.controller;


import com.myproject.jpaboard.domain.Comment;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.web.SessionConst;
import com.myproject.jpaboard.web.repository.CommentRepository;
import com.myproject.jpaboard.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {


    private final CommentRepository commentRepository;
    private final CommentService commentService;

    /**
     * 댓글 추가
     */
    @PostMapping("/add/{id}")
    public String addComment(@PathVariable Long id, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember, String commentContent) {

        // 로그인하지 않은 사용자는 댓글 추가할 수 없음
        if (loginMember == null) {
            return "redirect:/board/post/" + id;
        }

        log.info("commentContent={}", commentContent);

        Comment newComment = commentService.newComment(commentContent, id, loginMember);
        commentRepository.save(newComment);

        return "redirect:/board/post/" + id;
    }

    /**
     * 댓글 수정
     */
    @PostMapping("/edit/{cmtId}")
    public String editComment(@PathVariable Long cmtId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember, String editContent) {

        // 댓글 id로 게시글 찾기
        Comment findComment = commentRepository.findById(cmtId).orElseThrow(() -> new IllegalArgumentException("No Comment Found"));
        Long postId = findComment.getPost().getId();

        // 로그인하지 않은 사용자는 답글 추가할 수 없음
        if (loginMember == null) {
            return "redirect:/board/post/" + postId;
        }


        commentService.editComment(editContent, cmtId);

        return "redirect:/board/post/" + postId;
    }

    /**
     * 댓글 삭제
     */
    @PostMapping("/delete/{cmtId}")
    public String deleteReply(@PathVariable Long cmtId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember) {

        // 댓글 id로 게시글 찾기
        Comment findComment = commentRepository.findById(cmtId).orElseThrow(() -> new IllegalArgumentException("No Comment Found"));
        Long postId = findComment.getPost().getId();

        // 로그인하지 않은 사용자는 답글 추가할 수 없음
        if (loginMember == null) {
            return "redirect:/board/post/" + postId;
        }

        commentRepository.delete(findComment);

        return "redirect:/board/post/" + postId;

    }


    /**
     * 답글 추가
     */
    @PostMapping("/add/reply/{parentCmtId}")
    public String addReply(@PathVariable Long parentCmtId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember, String replyContent) {

        // 댓글 id로 게시글 찾기
        Comment parentComment = commentRepository.findById(parentCmtId).orElseThrow(() -> new IllegalArgumentException("No Comment Found"));
        Long postId = parentComment.getPost().getId();


        // 로그인하지 않은 사용자는 답글 추가할 수 없음
        if (loginMember == null) {
            return "redirect:/board/post/" + postId;
        }

        commentService.addReply(replyContent, postId, parentComment, loginMember);
        log.info("replyContent={}", replyContent);


        return "redirect:/board/post/" + postId;
    }

    /**
     *
     */




}
