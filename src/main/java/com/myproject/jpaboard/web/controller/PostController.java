package com.myproject.jpaboard.web.controller;

import com.myproject.jpaboard.domain.MemberSessionDto;
import com.myproject.jpaboard.domain.Post;
import com.myproject.jpaboard.web.SessionConst;
import com.myproject.jpaboard.web.form.PostEditForm;
import com.myproject.jpaboard.web.form.PostForm;
import com.myproject.jpaboard.web.repository.BoardRepository;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    /**
     * 글쓰기 화면
     */
    @GetMapping("/new")
    public String showPost(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember, Model model, HttpServletRequest request) {

        if (loginMember == null) {
            return "redirect:/board/list";
        }
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("member", loginMember);


        return "newPost";
    }

    /**
     * 게시물 추가
     */
    @PostMapping("/new")
    public String addPost(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember, @Validated PostForm postForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "newPost";
        }
        postService.addPost(postForm, loginMember);

        log.info(postForm.getContent());

        return "redirect:/board/list";
    }

    /**
     * 게시물 수정
     */
    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) MemberSessionDto loginMember, Model model, HttpServletRequest request) {

        if (loginMember == null) {
            return "redirect:/board/list";
        }

        Post findPost = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Post Found."));// findPost 게시물 보여주기

        log.info("findPost={}", findPost);
        PostEditForm postEditForm = new PostEditForm();
        postEditForm.setTitle(findPost.getTitle());
        postEditForm.setContent(findPost.getContent());
        postEditForm.setCategory(findPost.getCategory());

        model.addAttribute("findPost", postEditForm);
        model.addAttribute("currentPageUrl", request.getRequestURI());


        return "editPost";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @Validated @ModelAttribute("findPost") PostEditForm editPost, BindingResult bindingResult) {

        log.info("editPost={}", editPost);

        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "editPost";
        }

        Post findPost = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Post Found."));

        findPost.setTitle(editPost.getTitle());
        findPost.setContent(editPost.getContent());
        findPost.setCategory(editPost.getCategory());
        findPost.setLastModifiedTime(LocalDateTime.now());

        postRepository.save(findPost);

        return "redirect:/board/post/" + id;
    }

    /**
     * 게시물 삭제
     */
    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {

        Post findPost = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Post Found."));

        postRepository.delete(findPost);
        return "redirect:/board/list";
    }

    /**
     * 게시물 검색
     */
    @GetMapping("/search")
    public String searchPost(@RequestParam String searchType, @RequestParam String searchKeyword, Pageable pageable, Model model) {


        Page<Post> searchedPosts = boardService.searchPosts(searchType, searchKeyword, pageable);
        log.info("searchedPosts={}", searchedPosts);
        model.addAttribute("page", searchedPosts);


        return "boardList";

    }
}
