package com.myproject.jpaboard.web.controller;

import com.myproject.jpaboard.domain.Comment;
import com.myproject.jpaboard.domain.Member;
import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.domain.Post;
import com.myproject.jpaboard.web.SessionConst;
import com.myproject.jpaboard.web.repository.BoardRepository;
import com.myproject.jpaboard.web.repository.CommentRepository;
import com.myproject.jpaboard.web.repository.PostRepository;
import com.myproject.jpaboard.web.service.BoardService;
import com.myproject.jpaboard.web.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardRepository boardRepository;
    private final PostService postService;
    private final CommentRepository commentRepository;

    /**
     * 게시판 조회
     */
    @GetMapping("/list")
    public String showBoard(Model model, Pageable pageable, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember, HttpServletRequest request) {

        Page<Post> allPosts = boardRepository.findAllByOrderByIdDesc(pageable);
        model.addAttribute("page", allPosts);

        if (loginMember == null) {
            return "boardList";
        }

        model.addAttribute("member", loginMember);
        model.addAttribute("currentPageUrl", request.getRequestURI());

        log.info("loginMember={}", loginMember.getName());

        return "boardList";
    }

    /**
     * 개별 게시물 조회
     */
    @GetMapping("/post/{id}")
    public String showPost(@PathVariable Long id, Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember, HttpServletRequest request) {

        Post findPost = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Post Found.")); // findPost 게시물 보여주기
        Long viewCount = postService.addViewCount(id);
        List<Comment> commentByPostId = commentRepository.findCommentByPostId(id);
        int commentNum = postService.countCommentNumByPostId(id);

        model.addAttribute("findPost", findPost);
        model.addAttribute("comments", commentByPostId);
        model.addAttribute("viewCount", viewCount);
        model.addAttribute("commentNum", commentNum);
        model.addAttribute("member", loginMember);
        model.addAttribute("currentPageUrl", request.getRequestURI());

        log.info("findPost={}", findPost);

        return "readPost";

    }
}
