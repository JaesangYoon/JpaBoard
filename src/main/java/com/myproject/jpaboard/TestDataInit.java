package com.myproject.jpaboard;


import com.myproject.jpaboard.domain.*;
import com.myproject.jpaboard.web.form.PostForm;
import com.myproject.jpaboard.web.repository.BoardRepository;
import com.myproject.jpaboard.web.repository.MemberRepository;
import com.myproject.jpaboard.web.repository.PostJpaRepository;
import com.myproject.jpaboard.web.service.MemberService;
import com.myproject.jpaboard.web.service.PostService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
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
    private final Faker faker = new Faker(new Locale("ko")); // 한국어 데이터 생성을 위해 로케일 설정


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

//        for (int i = 0; i < 10; i++) {  // 10명의 회원 데이터를 생성한다고 가정
//            Member member2 = new Member();
//            member2.setEmail(faker.internet().emailAddress());
//            member2.setPassword(faker.internet().password());
//            member2.setName(faker.name().fullName());
//            member2.setAddress(new Address(
//                    faker.address().city(),
//                    faker.address().streetName(),
//                    faker.address().zipCode()
//            ));
//            memberService.join(member2);
//        }

    }

    /**
     * 게시물 테스트 데이터
     */
    public void postInit() {
        LocalDateTime startTime = LocalDateTime.now().minusMonths(1); // 현재로부터 1달 전

        for (int i = 1; i <= 20; i++) {
            PostForm postForm = new PostForm();

            // Faker를 사용하여 무작위 한국어 텍스트 생성
            String testContent = faker.lorem().paragraph(5); // Faker가 5개의 문장으로 구성된 무작위 한국어 문단을 생성

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

        // Max 계정 Post
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
        for (Long i = 1L; i <= 20L; i++) {
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


