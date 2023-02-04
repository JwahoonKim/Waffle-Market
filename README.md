# 🥕 Waffle-Market 🥕
## 🥕 당근마켓 클론 코딩 와플마켓 API 서버
- 당근마켓 기능을 클론 코딩한 와플마켓 API 서버입니다.
- 단순 게시판 기능 외에 예약, 거래, 채팅 등의 부가 기능이 더해진 서비스를 구현해보면서 구성원들의 개발 실력 성장을 이루고자 했고,
서비스 기획에 인풋을 줄이기 위해 기존에 있는 서비스를 기반으로 클론 코딩을 하였습니다.
- [와플마켓 프론트엔드 링크](https://github.com/wafflestudio20-5/team3-web)
## 🎞️ 와플마켓 서비스 소개 영상 및 사진
### 🥕 [와플마켓 서비스 소개 영상 링크](https://www.youtube.com/watch?v=MHO3Fofvu3A) 🥕 

![A1 (300 DPI)](https://user-images.githubusercontent.com/72662822/216532231-59aa7003-b65b-4d4e-b122-8165b24ed710.png)


## 👨‍👩‍👧‍👦 누가 만들었나요?

|                           [김좌훈(JwahoonKim)](https://github.com/JwahoonKim)                                                                           |                                                    [김준형(leeeryboy)](https://github.com/leeeryboy)                                                    |                                    [곽민지(Joanne19-drive)](https://github.com/Joanne19-drive)                                  |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------------------------------:|
| <a href="https://github.com/JwahoonKim"><img src="https://avatars.githubusercontent.com/u/72662822?v=4" width="160px" style='border-radius: 50%'></a> | <a href="https://github.com/leeeryboy"><img src="https://avatars.githubusercontent.com/u/62370411?v=4" width="160px" style='border-radius: 50%'></a> | <a href="https://github.com/Joanne19-drive"><img src="https://avatars.githubusercontent.com/u/84649662?v=4" width="160px" style='border-radius: 50%'></a> |

## 🏛️ 서버 구조
![image](https://user-images.githubusercontent.com/72662822/216547325-5e281374-e3f4-405d-aa8c-4ac6b1c018c4.png)


## 📚 기술 스택
<div align=center> 
    <img src="https://img.shields.io/badge/kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white">
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/Spring data jpa-6DB33F?style=for-the-badge&logo=Databricks&logoColor=white">
    <img src="https://img.shields.io/badge/QueryDSL-0389CF?style=for-the-badge&logo=SingleStore&logoColor=white">
    <img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">
    <br>
    <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
    <img src="https://img.shields.io/badge/Mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
    <img src="https://img.shields.io/badge/amazon aws-232F3E?style=for-the-badge&logo=amazon aws&logoColor=white">
</div>


## 🧑‍💻Convention
- 프로젝트를 시작 전 아래와 같은 컨벤션을 정함으로써 코드 관리에 효율성을 높였습니다.


### 👔 Code Convention

- 팀원 간 코드 스타일을 맞추고 가독성을 높여 코드 리뷰를 원할하게 하기 위해 ktlint를 사용하였습니다.
- 제일 많이 쓰이는 [jlleitschuh/ktlint-gradle](https://github.com/jlleitschuh/ktlint-gradle)을 사용하였습니다.
- Github Action 을 이용하여 main 브랜치에 PR 올릴때마다 자동으로 스타일 검사를 하도록 설정하였습니다.

### 😎 Commit Convention
- 아래와 같은 커밋 컨벤션을 통해 서로의 작업물이 어떤 유형의 것인지 파악하기 쉽도록 하였습니다.
<img width="600" alt="스크린샷 2023-02-01 오후 11 26 44" src="https://user-images.githubusercontent.com/72662822/216070103-f68194a6-0ecb-4539-912b-f47107b4f500.png">

### ⛙ Git Branch 전략 및 Merge 전략
<img width="759" alt="스크린샷 2023-02-01 오후 11 43 46" src="https://user-images.githubusercontent.com/72662822/216074373-cc670c46-8f34-44c6-b153-06221d125330.png">

- Github-flow를 사용했습니다.
  - 크지 않은 사이즈의 토이프로젝트였기 때문에 간단한 Branching 전략인 Github-flow를 사용했습니다.
  - 각자의 작업물은 feature 브랜치를 만들어 관리했으며, 완성한 작업물은 main 브랜치에 Squash Merge 전략을 사용해 합쳤습니다.  
- 아래와 같은 근거로 Merge 전략은 Squash Merge 전략을 택했습니다.
  - 각자 작업한 결과물의 완성본이 main 브랜치의 관심사였던 점
  - PR을 날리는 단위를 작게 하자고 미리 협의한 점
  - 각자 작업물의 히스토리는 main 브랜치에 남기기보다는 로컬에 남겨 관리하는 것이 편하겠다고 판단한 점


## 📖 기술적인 이슈 및 개발 과정을 겪으며 정리한 글
1. [[좌훈] 채팅 읽은 사람 표시 기능 구현 및 어려웠던 점, 추후 개선해야할 사항](https://kjhoon0330.tistory.com/entry/Spring-%EC%B1%84%ED%8C%85-%EC%9D%BD%EC%9D%8C-%ED%99%95%EC%9D%B8-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0)
2. [[좌훈] 깃 머지 전략](https://kjhoon0330.tistory.com/entry/Git-Merge-%EC%A0%84%EB%9E%B5%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC)
3. [[좌훈] N + 1 문제 해결](https://kjhoon0330.tistory.com/entry/Spring-JPA-N-1-%EB%AC%B8%EC%A0%9C%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC)
4. [[좌훈] 채팅을 웹소켓으로 구현하며..](https://kjhoon0330.tistory.com/entry/Spring-Dev-%EC%9B%B9%EC%86%8C%EC%BC%93%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC)
5. [[준형] 이메일 인증 서버로 Redis를 쓰게된 계기](https://leeeryboy.tistory.com/2)
6. [[준형] 동네 주변 글은 어떻게 찾을 수 있었는가](https://leeeryboy.tistory.com/3)
7. [[준형] JWT Token은 어디에 저장해야할까?](https://leeeryboy.tistory.com/4)
8. [[민지] Docker와 Github Actions를 활용한 배포 과정](https://velog.io/@beinte0419/Spring-Boot-Docker-Github-Actions를-활용한-자동-배포)




## DB Diagram
<img width="1035" alt="스크린샷 2023-01-31 오후 5 04 13" src="https://user-images.githubusercontent.com/72662822/216069474-a4bd448a-ed4b-456a-bad7-9ceeb04fb220.png">



## 🎉 서로에게 남기는 롤링페이퍼
👨‍💻 좌훈이에게
```
새벽에도 멈추지 않는 pr로 항상 나를 놀라게 했던 새벽좌 좌훈 오빠ㅋㅋㅋ 
프로젝트 하면서 필요한 부분을 잘 캐치해서 팀원들에게 전달해줘서 항상 고마웠어.
기회가 된다면 다같이 계속 네트워킹하자ㅠㅠ
뒤에서 묵묵히 팀에 필요한게 무엇인지 생각하고 손수 만들어 제공하는 츤데레 좌훈이형
코드도 잘쓰고 블로그도 잘쓰는 형이랑 같이 플젝하면서 많이 배웠고 다음 학기에 자주 보자!
```

🧑‍💻 준형이에게
```
우리팀 막내이면서 팀장을 맡아준 준형이ㅋㅋㅋ 어려운거 뚝딱뚝딱 다 잘해줘서 고맙고 든든했당.
기회가 되면 또 같이 작업할 수 있었으면 좋겠고 담학기 수업 많이 겹쳐서 재밌겠다 같이 열심히 들어보자! 블로그도 열심히써라!
본인이 왜 팀장인지 모르겠다고 했지만 앞구르기 뒷구르기하고 봐도 든든한 팀장이었던 준형!
부족한 코드를 꼼꼼하게 리뷰해줘서 고맙고 앞으로의 개발 인생을 응원한다.
```

👩‍💻 민지에게
```
회사 일이랑 병행하느라 엄청 힘들었을텐데 맡은 부분 책임감있게 마무리해준거 너무 고마워!
더 자주 만나고 이야기해봤으면 좋았을 것 같은데 그러지 못해서 아쉽다ㅠ
갓기업 신입사원 축하하고 같은 팀으로 일할 수 있어서 너무 좋았다ㅠㅠ
경험삼아 지원한 대기업 그냥 가볍게 뚫어버린,, 그저 GOAT
회사 다니면서 합숙도 하느라고 시간 내기 어려웠을텐데 그동안 작업하느라 너무 고생했어!!
```
