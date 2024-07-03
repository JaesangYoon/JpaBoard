## 프로젝트 수행 기간
2024.04 ~ 2024.05
## URL 
[http://54.180.249.100:8080/](http://54.180.249.100:8080/)
## ERD 
[https://lucid.app/lucidchart/17731ed2-b6ae-4356-8d58-10d2d4f704cd/edit?](https://lucid.app/lucidchart/17731ed2-b6ae-4356-8d58-10d2d4f704cd/edit?)

## 게시판을 주제로 선정한 이유

게시판은 그 자체로 회원 관리, 로그인 기능을 요구한다는 점에서 다양한 기술을 적용할 수 있는 프로젝트라고 생각합니다. ERD를 작성하고, DB 구조와 애플리케이션 구조를 일치시키는 과정에서 JPA에 대한 이해를 높일 수 있었습니다. 게시물의 CRUD 기능을 구현하고, 각 게시물에 속한 댓글과 답글을 체계적으로 관리하여 하나의 웹 애플리케이션을 완성하는 과정을 경험했습니다.

## 활용한 스프링 기술들
- **스프링 MVC**: 스프링 MVC 패턴을 이용해 HTTP 요청을 처리하고 적절한 응답을 반환하는 컨트롤러를 작성할 수 있습니다.
- **스프링 빈 관리**: 애너테이션을 통해 스프링 빈을 관리하고, 의존성 주입을 통해 서비스, 리포지토리 계층에서 유연하게 빈을 활용할 수 있습니다.
- **스프링 데이터 JPA**: PostRepository 인터페이스를 통해 스프링 데이터 JPA를 사용하는 방법을 알고 있습니다. 이를 통해 데이터베이스와의 상호작용을 추상화하고, 데이터 접근 코드를 간결하게 유지하는 방법을 이해하고 있습니다.
- **스프링 트랜잭션 관리**: `@Transactional` 애너테이션을 활용하여 여러 데이터베이스 작업을 하나의 트랜젝션으로 그룹화할 수 있습니다.


## 기능 설명
### 1. 메인 페이지
![screencapture-54-180-249-100-8080-2024-07-03-21_51_50](https://github.com/JaesangYoon/jpaboard/assets/89953091/eb24139a-2684-4a2e-90e3-6d1944e25638)

**1-1. 로그인 하지 않은 메인 페이지**

![홈](https://github.com/JaesangYoon/jpaboard/assets/89953091/7b47bdf3-f3c2-499e-89bc-baa7de48de8b)


- 로그인 여부에 따라 다른 뷰를 제공했습니다.
- 컨트롤러에서 세션 객체가 비어있는지를 확인하여 로그인 여부를 판단하도록 했습니다.


**1-2. 로그인 했을 때의 메인 페이지**

![홈_로그인](https://github.com/JaesangYoon/jpaboard/assets/89953091/b918f375-7d62-496b-b77c-f26a1631a443)

- 세션 객체에 저장된 회원의 이름을 출력하도록 했습니다.
- 로그인 했을 때는, 로그인 한 회원의 정보를 조회하는 ‘회원 정보’ 탭을 출력하도록 했습니다.

### 2. 로그인
![로그인](https://github.com/JaesangYoon/jpaboard/assets/89953091/1ece5e9c-9c5b-422a-a45d-fc122dac7e1a)

- 테스트용 계정
	- 이메일 주소: max@gmail.com
	- 비밀번호: 1234
- 스프링이 제공하는 Bean Validation을 활용하여 공백과 Null 체크를 통해 오류 메시지를 구현했습니다.
- 로그인 성공 시 세션 객체에 회원 데이터를 저장하여 이후 게시판 페이지 등에서 활용할 수 있도록 했습니다.


### 3. 회원가입
![회원가입](https://github.com/JaesangYoon/jpaboard/assets/89953091/fb571ac1-6d36-4f9b-951d-8aea5c798c8a)

- 로그인 페이지를 포함해 폼과 관련된 페이지는 PRG 패턴을 구현하여 새로고침해도 폼이 중복 제출되는 것을 막도록 했습니다.
- 로그인 폼과 마찬가지로, Bean Validation을 이용하여 폼이 정상적으로 입력되지 않은 상태에서 제출했을 때 사용자에게 문제가 되는 부분을 알리도록 했습니다.



### 4. 회원정보
<img width="1481" alt="회원정보" src="https://github.com/JaesangYoon/jpaboard/assets/89953091/58cf8671-0c5b-4d42-84db-91ded1b27597">

- 회원정보 화면에서는 세션 객체에 저장된 회원의 정보를 출력하도록 했습니다.

### 5. 비밀번호 찾기
![비밀번호 찾기](https://github.com/JaesangYoon/jpaboard/assets/89953091/54ed0330-1f36-41c4-8268-c28d2d304f59)

- 이메일 주소를 바탕으로 DB에서 비밀번호를 조회하여 출력하도록 했습니다.
- 테스트용 계정의 비밀번호를 찾은 예시입니다.

### 6. 게시판 홈
![게시판 홈](https://github.com/JaesangYoon/jpaboard/assets/89953091/7a0c3bf3-1c53-497f-8344-05cab91237d6)

- 게시판의 sidebar, footer, header는 타임리프의 모듈화 기능을 사용하여 통일성 있게 제작하였습니다. 이를 통해 공통 UI 요소를 재활용하고, 코드의 유지 보수를 용이하게 했습니다.
- 테스트 데이터를 300개 추가하여 페이지네이션이 정상적으로 작동하는지 확인할 수 있도록 했습니다.
- QueryDsl을 활용해 동적 쿼리를 구현했습니다. 게시글의 제목과 작성자의 이름을 기준으로 검색할 수 있습니다.

### 7. 게시물 조회
<img width="1154" alt="게시물 조회" src="https://github.com/JaesangYoon/jpaboard/assets/89953091/5feb203b-f0b0-44f6-9294-6dcf857b6a19">

- 조회수, 댓글 수(답글 포함), 카테고리를 확인할 수 있습니다.
- 각각의 댓글에는 답글을 달 수 있으며, 부모 댓글이 삭제되면 해당 댓글의 답글도 함께 삭제됩니다. 
	- 이를 위해 Post(게시글) 엔티티의 comments 속성에 orphanRemoval 옵션을 true로 설정했습니다.
- 자신이 작성한 게시글과 댓글에 대해서만 수정, 삭제 버튼이 보이도록 했습니다. 이를 위해 세션에 저장된 사용자 이름과 게시물 및 댓글 작성자 이름을 비교하는 방식을 사용했습니다.

### 8. 게시물 작성
![게시물 작성](https://github.com/JaesangYoon/jpaboard/assets/89953091/33bedca1-232c-4766-9196-bd75d39eadd4)
<img width="656" alt="게시물 작성_카테고리" src="https://github.com/JaesangYoon/jpaboard/assets/89953091/0cefa5db-4d1d-47fb-9285-24927f568e89">

- Bean Validation을 이용하여 카테고리, 제목, 본문 내용에 대해 공백이나 Null을 허용하지 않도록 설계했습니다.
- 게시물의 카테고리는 Enum을 이용하여 직접 정의했습니다.
- PRG 패턴을 적용하여 폼이 중복 제출되는 것을 막았습니다.

### 9. 게시물 수정
<img width="1282" alt="게시물 수정" src="https://github.com/JaesangYoon/jpaboard/assets/89953091/c27fbfa9-d3b1-4de0-9137-e753d871501f">

- 게시물 작성과 동일하게 Bean Validation을 이용하여 폼을 검증했습니다.


---

## 뷰 관련 

| 범주         | URL              | 파일명               | 기능         |
| ---------- | ---------------- | ----------------- | ---------- |
| **홈**      | /                | index.html        | 홈          |
|            | /                | loginHome.html    | 홈 (로그인 상태) |
| **회원 관련**  | /login/login     | login.html        | 로그인        |
|            | /member/signup   | registerForm.html | 회원가입       |
|            | /member/find     | findUser.html     | 비밀번호 찾기    |
|            | /member/info     | memberInfo.html   | 회원정보       |
| **게시판 관련** | /board/list      | boardList.html    | 게시판 조회     |
|            | /board/post/{id} | readPost.html     | 개별 게시물 조회  |
|            | /post/new        | newPost.html      | 게시물 작성     |
|            | /post/edit/{id}  | editPost.html     | 게시물 수정     |
|            | /post/search     | boardList.html    | 게시물 검색     |

## 계층별 프로젝트 파일 설명

### Controller 계층
- HomeController: 홈
- CommentController: 댓글 관리
- LoginController: 로그인
- MemberController: 회원 관리
- BoardController: 게시판 관리 (cf. 게시판 내에 개별 게시물 존재)
- PostController: 게시물 관리

### Service 계층
- BoardService: 게시판 (게시물 전체 조회 및 검색)
- CommentService: 댓글
- LoginService: 로그인
- MemberService: 회원
- PostService: 게시물 (개별 게시물에 대한 CRUD)

### Repository 계층 (DB Related)
- BoardJpaRepository: 순수 JPA를 사용한 게시판 Repository
- BoardRepository: Spring Data JPA를 사용한 게시판 Repository
- CommentRepository: Spring Data JPA를 사용한 댓글 Repository
- MemberRepository: 순수 JPA를 사용한 회원 Repository
- PostJpaRepository: 순수 JPA를 사용한 게시물 Repository
- PostRepository: Spring Data JPA를 사용한 게시물 Repository
