package com.myproject.jpaboard;


import com.myproject.jpaboard.domain.*;
import com.myproject.jpaboard.web.form.PostForm;
import com.myproject.jpaboard.web.repository.BoardRepository;
import com.myproject.jpaboard.web.repository.MemberRepository;
import com.myproject.jpaboard.web.repository.PostJpaRepository;
import com.myproject.jpaboard.web.service.BoardService;
import com.myproject.jpaboard.web.service.MemberService;
import com.myproject.jpaboard.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("!test") // 'test' 프로파일이 아닌 경우에만 이 컴포넌트가 활성화됨
public class TestDataInit {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PostJpaRepository postJpaRepository;
    private final PostService postService;


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {
        memberInit();
        postInit();
        commentInit();
    }

    /**
     * 회원 테스트 데이터
     */
    public void memberInit() {
        Member member = new Member();
        member.setEmail("max@gmail.com");
        member.setPassword("1234");
        member.setName("Max");
        member.setAddress(new Address("Seoul", "Gangnam", "12345"));

        memberService.join(member);
    }

    /**
     * 게시물 테스트 데이터
     */
    public void postInit() {
        LocalDateTime startTime = LocalDateTime.now().minusMonths(1); // 현재로부터 1달 전

        for (int i = 1; i <= 50; i++) {

            PostForm postForm = new PostForm();
            String testContent = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi condimentum elit tellus, et condimentum nisl scelerisque nec. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis enim diam, luctus eu mollis vitae, volutpat et nibh. Vestibulum nulla felis, mollis non condimentum quis, pellentesque at eros. Mauris suscipit pulvinar posuere. Duis vestibulum sodales tempor. Donec venenatis pulvinar rhoncus. Etiam turpis mi, rutrum eget euismod sed, sodales sit amet felis. Duis ut erat quis velit accumsan convallis sed sit amet lorem.\n" +
                    "\n" +
                    "Integer pellentesque tristique nulla suscipit accumsan. Sed dapibus dolor ac lacus iaculis, a dignissim leo suscipit. Fusce sit amet massa ut justo tincidunt efficitur eget varius urna. Sed eget risus dapibus, vestibulum est nec, volutpat tellus. Nulla et porttitor dolor. Duis cursus, dolor ut fermentum efficitur, risus nisi luctus neque, sed tristique ex lectus non massa. Fusce malesuada elementum purus, vitae lacinia ante porttitor et. In aliquet turpis in neque sagittis, quis malesuada risus semper. Vestibulum viverra nisi sem, non tincidunt sem scelerisque sit amet.\n" +
                    "\n" +
                    "Etiam eget luctus velit, vitae consequat neque. Nulla facilisi. Sed finibus dui odio, vitae molestie lacus convallis non. Vestibulum rhoncus ac ipsum faucibus dignissim. Ut in metus quis sapien ultrices iaculis a non orci. Mauris placerat leo in nisi sodales auctor. In vel turpis sagittis, laoreet nibh at, tempor risus. Integer porttitor tempor pulvinar. Morbi pharetra congue lorem. Vivamus sagittis mi et sodales maximus. Maecenas tristique fringilla urna vel tincidunt. Sed hendrerit nisl nec condimentum vestibulum.\n" +
                    "\n" +
                    "Vestibulum ut diam laoreet, fermentum velit eget, fermentum massa. Integer eu arcu facilisis, vehicula tellus et, consequat orci. Curabitur id suscipit neque, et feugiat felis. Vestibulum dui elit, auctor vitae vehicula sit amet, egestas in dolor. Sed id porta odio. Etiam cursus, orci at vulputate porta, metus quam scelerisque enim, eget rutrum lectus enim non neque. Fusce consequat tempus augue, a vulputate turpis bibendum ut. Nulla non lorem feugiat, posuere lacus lobortis, posuere eros. Nullam gravida ac nibh in vestibulum. Morbi eu lacus eget sem placerat vulputate elementum sodales ipsum. Nam egestas id quam sed aliquam. Nunc fringilla ac quam ac sodales. Etiam quis ligula vel massa bibendum eleifend. Proin tempus pulvinar diam, et pulvinar neque vestibulum ac.\n" +
                    "\n" +
                    "Cras feugiat orci a magna scelerisque porta. Praesent feugiat elit vel urna molestie, at volutpat dolor pretium. Suspendisse imperdiet eget risus ut blandit. Vivamus fermentum nunc vitae ipsum eleifend tincidunt. Quisque dictum congue elit, in suscipit diam tincidunt a. Phasellus luctus posuere leo eu mattis. Nunc congue condimentum odio, convallis tristique justo venenatis a. Proin in nunc sed est luctus ultricies. Ut fringilla augue at urna posuere, at facilisis est vulputate. Donec dictum erat ut suscipit pulvinar. Sed viverra consectetur massa, non interdum orci mollis ac. Vivamus bibendum tortor blandit tincidunt mollis. Vivamus varius vitae nibh vitae cursus. Morbi nisl dolor, maximus nec tincidunt eu, venenatis nec quam.";
            postForm.setTitle(i + "번째 게시물입니다.");
            postForm.setContent(testContent);
            postForm.setWriter("홍길동" + i);
            postForm.setCreatedTime(startTime.plusHours(i));
            postForm.setCategory(CategoryType.DAILY);
            postForm.setViewCount(0L);
            Member testMember = memberRepository.findById(1L);

            MemberSessionDto memberSessionDto = new MemberSessionDto();
            memberSessionDto.setName(testMember.getName());
            memberSessionDto.setId(testMember.getId());
            memberSessionDto.setPassword(testMember.getPassword());
            memberSessionDto.setEmail(testMember.getEmail());

            postService.addPost(postForm, memberSessionDto);
        }

        LocalDateTime writtenTime = LocalDateTime.now();
        Member testMember = memberRepository.findById(1L);

        PostForm postForm2 = new PostForm();
        postForm2.setTitle("Max의 게시물");
        postForm2.setContent("테스트 본문입니다.");
        postForm2.setWriter(testMember.getName());
        postForm2.setCategory(CategoryType.DAILY);
        postForm2.setCreatedTime(writtenTime);
        postForm2.setViewCount(0L);

        MemberSessionDto memberSessionDto = new MemberSessionDto();
        memberSessionDto.setId(1L);
        memberSessionDto.setName(testMember.getName());
        memberSessionDto.setEmail("jsangyoun@gmail.com");


        postService.addPost(postForm2, memberSessionDto);
    }


    /**
     * 댓글 테스트 데이터
     */
    public void commentInit() {
        LocalDateTime createdTime = LocalDateTime.now().minusMonths(1); // 현재로부터 1달 전
        LocalDateTime now = LocalDateTime.now();

        Member testMember = memberRepository.findById(1L);
        for (Long i = 1L; i <= 50L; i++) {
            Optional<Post> byId = boardRepository.findById(i);
            Post findPost = byId.get();
            Comment testComment = new Comment();
            testComment.setMember(testMember);
            testComment.setContent("테스트 댓글입니다. " + i);
            testComment.setCreatedTime(createdTime);
            testComment.setLastModifiedTime(now);
            testComment.setPost(findPost);

            Comment reply1 = new Comment();
            reply1.setMember(testMember);
            reply1.setPost(findPost);
            reply1.setContent("대댓글입니다. " + i);
            reply1.setCreatedTime(createdTime);
            reply1.setLastModifiedTime(now);
            reply1.setParentComment(testComment);

            Comment reply2 = new Comment();
            reply2.setMember(testMember);
            reply2.setPost(findPost);
            reply2.setContent("두 번째 대댓글입니다. " + i);
            reply2.setCreatedTime(createdTime);
            reply2.setLastModifiedTime(now);
            reply2.setParentComment(testComment);

            testComment.getReplies().add(reply1);
            testComment.getReplies().add(reply2);

            postJpaRepository.save(testComment);

            Comment testComment2 = new Comment();
            testComment2.setMember(testMember);
            testComment2.setContent("두 번째 테스트 댓글입니다. " + i);
            testComment2.setCreatedTime(createdTime);
            testComment2.setLastModifiedTime(now);
            testComment2.setPost(findPost);

            Comment reply3 = new Comment();
            reply3.setMember(testMember);
            reply3.setPost(findPost);
            reply3.setContent("대댓글입니다. " + i);
            reply3.setCreatedTime(createdTime);
            reply3.setLastModifiedTime(now);
            reply3.setParentComment(testComment2);

            Comment reply4 = new Comment();
            reply4.setMember(testMember);
            reply4.setPost(findPost);
            reply4.setContent("두 번째 대댓글입니다. " + i);
            reply4.setCreatedTime(createdTime);
            reply4.setLastModifiedTime(now);
            reply4.setParentComment(testComment2);

            testComment2.getReplies().add(reply3);
            testComment2.getReplies().add(reply4);

            postJpaRepository.save(testComment2);

        }


    }

}


