# 🐶 개퀴즈 - DevQuiz
### 개발자의, 개발자에 의한, 개발자를 위한, Computer Science Quiz!

## 🐾 Demo Video

## 🐾 Links
- [Github - Backend](https://github.com/spartaSpringTeamA6/dev-quiz-backend)
- [Github - Frontend]()
- [Notion](https://www.notion.so/jiisuniui/zip-Dev-zip-f532f433197c4484b7b313f84d262e97?pvs=4)
- [Figma](https://www.figma.com/file/UhJfxCFuEafa1Rv8p5M2u5/%EA%B0%9C%ED%80%B4%EC%A6%88?type=design&node-id=0%3A1&mode=design&t=WG7dtUihDxUf0ZpV-1)
- [ERD Cloud](https://www.erdcloud.com/d/CygGNgPaSZorq277t)
- [Swagger]()

## 🐾 Personal Role
| Name | Role                                |
|------|-------------------------------------|
| [최혁](https://github.com/Youkamii) | |
| [이지선](https://github.com/jiisuniui) | |
| [박상신](https://github.com/dmlal) | |
| [박정환](https://github.com/Junghwan1106) | |
| [안주환](https://github.com/rawfk) | |

## 🐾 Commit Convention
| Tag Name | Description |
|---|---|
| ✨ feat | 해당 파일에 새로운 기능이 생김 |
| 🎉 create | 없던 파일을 생성함, 초기 세팅 |
| ♻️ refactor | 코드 리팩토링 |
| 🐛 fix | 코드 수정 |
| 🚑 HOTFIX | 급하게 치명적인 버그를 고쳐야하는 경우 |
| 📌 chore | 빌드 업무 수정, 패키지 관리자 구성 등 업데이트, Production Code 변경 없음 |
| 🚚 rename | 파일 옮김/정리, 파일명 변경 |
| 🔥 remove | 기능/파일을 삭제 |
| ✅ test | 테스트 코드를 작성 |
| 💄 style | css |
| 💬 docs | md 파일, 문서 작업 |
| 💡 comment | 필요한 주석 추가 및 변경 |
| 🙈 gitfix | gitignore 수정 |
| 🔀 gitmerge | 브랜치 합병 |
| ⏪ gitrevert | 변경 내용 되돌리기 |

## 🐾 UI/UX

## 🐾 ERD

## 🐾 API 명세서
### 0. API 설계 기준
- Domain 기준으로 API 구조 설계
- 와이어 프레임을 참고로 순차적으로 작동하도록 설계 함

### 1. User API
| API Code | Name | Method | URL | Auth |
|---|---|---|---|---|

### 2. Quiz API
| API Code | Name | Method | URL | Auth |
|---|---|---|---|---|

### 3. Coin API
| API Code | Name | Method | URL | Auth |
|---|---|---|---|---|

### 4. Board API
| API Code | Name | Method | URL | Auth |
|---|---|---|---|---|

### 5. Comment API
| API Code | Name | Method | URL | Auth |
|---|---|---|---|---|

### 6. Team API
| API Code | Name | Method | URL | Auth |
|---|---|---|---|---|

## 🐾 File Structure
<details>
<summary>펼치기</summary>
<div markdown="1">

``` markdown
dev-quiz/
src
 ┣ main
 ┃ ┣ generated
 ┃ ┃ ┗ com
 ┃ ┃ ┃ ┗ sparta
 ┃ ┃ ┃ ┃ ┗ devquiz
 ┃ ┃ ┃ ┃ ┃ ┣ domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ board
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QBoard.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ coin
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QCoin.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ comment
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QComment.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QCommentLike.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QCommentLikeId.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ quiz
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QQuiz.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QUserQuiz.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ quizbatch
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ category
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QCategory.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ quiz
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QQuiz.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ quizchoice
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QQuizChoice.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ team
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QTeam.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QTeamUser.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QTeamUserId.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QSkill.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QUser.java
 ┃ ┃ ┃ ┃ ┃ ┗ global
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QBaseTimeEntity.java
 ┃ ┣ java
 ┃ ┃ ┗ com
 ┃ ┃ ┃ ┗ sparta
 ┃ ┃ ┃ ┃ ┗ devquiz
 ┃ ┃ ┃ ┃ ┃ ┣ domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ board
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ BoardController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ BoardCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ BoardUpdateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ BoardCreateResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ BoardDetailsResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ Board.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ BoardCustomException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ BoardExceptionCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ BoardRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ BoardResponseCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ BoardService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ coin
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CoinSaveRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinUseRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CoinGetInfoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinUseResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ Coin.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ enums
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinContent.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CoinCustomException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinExceptionCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinResponseCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ comment
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CommentController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CommentCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CommentUpdateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CommentCreateResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CommentDetailsResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CommentInfoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Comment.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CommentLike.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CommentLikeId.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CommentCustomException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CommentExceptionCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CommentLikeRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CommentRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CommentResponseCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CommentService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ quiz
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QuizController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizAnswerSubmitRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizRandomRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QuizUpdateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizAnswerSubmitResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizDetailInfoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizGetByUserResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizInfoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QuizRandomResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Quiz.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserQuiz.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ enums
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizCategory.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserQuizStatus.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizCustomException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QuizExceptionCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizUserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ QuizUserRepositoryCustom.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QuizUserRepositoryCustomImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QuizResponseCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ QuizService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ team
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamCreateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamDeleteUserRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamInviteUserRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamUpdateAdminRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamUpdateNameRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamCreateResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamGetResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamGetUserRankingResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamInfoDetailResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamInfoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Team.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamUser.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamUserId.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ enums
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamUserRole.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamCustomException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamExceptionCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamUserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamResponseCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ TeamService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TeamUserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ annotation
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserSkillEnum.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ AuthController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserQueryController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserUpdateRequest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ SkillResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserBoardsResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserCommentsResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserDetailResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserInfoResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserInvitationsResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserQuizzesResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserRankingResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserScoreResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserSkillResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserTeamsResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ WeekScoreResponse.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ Skill.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ enums
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ OauthType.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserRole.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserSkill.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserCustomException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserExceptionCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ SkillRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserResponseCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ command
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ AuthService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ query
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserQueryService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ validator
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ UserSkillValidator.java
 ┃ ┃ ┃ ┃ ┃ ┣ global
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ advice
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ GlobalExceptionHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ annotation
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ AuthUser.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ cors
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ WebMvcConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ BaseTimeEntity.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CustomException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ GlobalExceptionCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ jpa
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ JpaQueryConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ jwt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ TokenSet.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ JwtCustomException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ JwtExceptionCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ filter
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ JwtAuthorizationFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ JwtExceptionHandlerFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ JwtService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ oauth
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ handler
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ OAuth2LoginSuccessHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CookieOAuth2RequestRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CustomOAuth2UserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ userinfo
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GithubUserInfo.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GoogleUserInfo.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ OAuth2UserInfo.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CookieUtil.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ OAuth2Attributes.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ redis
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ RedisConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ RedisService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ CommonResponseDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ GlobalResponseCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ ResponseCode.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ scheduler
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ Scheduler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ security
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserDetailsImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ UserDetailsServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ WebSecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ swagger
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┗ DevQuizApplication.java
 ┃ ┗ resources
 ┃ ┃ ┗ application.yml
 ┗ test
 ┃ ┗ java
 ┃ ┃ ┗ com
 ┃ ┃ ┃ ┗ sparta
 ┃ ┃ ┃ ┃ ┗ devquiz
 ┃ ┃ ┃ ┃ ┃ ┣ domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ coin
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ CoinServiceTest.java
 ┃ ┃ ┃ ┃ ┃ ┗ DevQuizApplicationTests.java
```

</div>
</details>

## 🐾 Technical Decision

## 🐾 Trouble Shooting
