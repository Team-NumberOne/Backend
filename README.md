### 설치 apk 경로
https://github.com/Kusitms-NumberOne/Android/tree/develop/apk

# 🌟 서비스명

![메인 이미지](https://github.com/Kusitms-NumberOne/.github/blob/main/profile/image/main_image.png)

<br>

> ‘대피로’는 위기 상황이 발생했을때 정확하고 신속하게 대응할 수 있도록 돕는 안전•재난 알림 서비스입니다. 

<br>


## 서비스 소개
### 가장 안전한 길로 안내하는 통로, 안전 재난 알림 서비스 ‘대피로'
‘대피로’는 사용자 맞춤형 안전 재난 알림을 수신하고, 공동체의 안녕을 서로 챙겨줄 수 있는 사람들이 모이는 안전 재난 알림 서비스입니다.

+ 본 서비스는 모든 국민들이 1)재난 상황에 대한 정확한 행동요령를 전달 받아 적극적으로 대처할 수 있도록 도우며 재난문자가 제공하기 어려운 2)재난 상황에 대한 구체적인 정보를 공동체의 연결을 통해 확인할 수 있습니다.


<br>
📢 ‘대피로’는 모든 이용자들에게 정확한 상황과 대응 방법을 제공하여, 위기 상황에 대한 혼란과 불안을 해소할 수 있길 기대합니다.
<br>

### 서비스 포인트 

+ #### '대피로' 주요 기능 
    + 재난문자 수신 필터링을 통한 사용자 맞춤형 정보 전달
      ![기능1](https://github.com/Kusitms-NumberOne/.github/blob/main/profile/image/function1.png)
      
      <br>
      
    + 행동요령 및 주변 대피소 정보 제공을 통한 혼란 최소화
      ![기능2](https://github.com/Kusitms-NumberOne/.github/blob/main/profile/image/function2.png)
      
      <br>
      
    + 재난 상황 및 동네 안전 정보 공유가 가능한 커뮤니티
      ![기능3](https://github.com/Kusitms-NumberOne/.github/blob/main/profile/image/function3_1.png)
      
      <br>
      
    + 가족의 위험/안전 상태 실시간 확인으로 불안감 해소
      ![기능4](https://github.com/Kusitms-NumberOne/.github/blob/main/profile/image/function4.png)


### Infra Architecture

<img width="2518" alt="image" src="https://github.com/Team-NumberOne/Backend/assets/96612168/fa9b352c-85a8-4f70-a29b-69a837e7f56c">

### 재난문자 크롤링 로직
![image](https://github.com/nohy6630/readme_test/assets/129354455/c9fa3c8e-9999-4141-a076-045cf334df56)


## 💻 기술 스택

- Server
    - ![Spring Security](https://img.shields.io/badge/Spring%20Security-%236DB33F?logo=springsecurity&logoColor=white)
      ![Springboot](https://img.shields.io/badge/Springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
      ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=flat-square&logo=spring&logoColor=white)
    - ![MySQL](https://img.shields.io/badge/MySQL-%2300f.svg?style=flat-square&logo=mysql&logoColor=white)
      ![RDS](https://img.shields.io/badge/AWS%20RDS-527FFF?style=flat-square&logo=Amazon%20RDS&logoColor=white)
      ![S3](https://img.shields.io/badge/AWS%20S3-569A31?style=flat-square&logo=Amazon%20S3&logoColor=white)
      ![EC2](https://img.shields.io/badge/AWS%20EC2-FF9900?style=flat-square&logo=Amazon%20EC2&logoColor=white)
    - ![Docker](https://img.shields.io/badge/Docker-%230db7ed.svg?style=flat-square&logo=docker&logoColor=white)
      ![Nginx](https://img.shields.io/badge/Nginx-%23009639.svg?style=flat-square&logo=nginx&logoColor=white)
      ![Redis](https://img.shields.io/badge/Redis-%23DC382D?logo=redis&logoColor=white) 


## 🔍 기술 스택 선정 이유
### Backend

| 기술 스택 | 설명 |
|-----------|------|
| **Spring Boot** | 스프링 프레임워크의 웹 애플리케이션을 구축하기 위한 Spring Boot 이용 |
| **Redis** | 댓글 및 게시글의 좋아요의 API 사용 중 발생하는 동시성 문제에 잘 대응하기 위해 Redis 사용. Redis 인프라가 구축되어 있어 RDB단에서 lock을 거는 방법 대신 Redis 분산락 적용. 유효시간이 존재하는 refresh token과 access token을 Redis에 저장하여, 토큰 생명 주기를 나타내는 TTL 기능을 사용 |
| **Spring Data JPA + Querydsl** | Spring Data JPA로 관계지향적인 패러다임과 객체지향적인 패러다임 간 불일치를 매핑. Querydsl로 서비스에서 요구하는 복잡한 비즈니스 로직을 만족하기 위한 동적 쿼리 구현 |
| **Rest Template + Html Unit** | Rest Template로 공공데이터포털에서 제공하는 실시간 재난문자 API와의 통신. Html Unit으로 재난문자를 카테고리화하기 위한 국민재난안전포털 웹사이트 크롤링 |
| **JIB** | 자바 어플리케이션을 빠르게 컨테이너화 할 수 있도록 도와주는 구글 오픈 소스 사용. 도커 없이 Gradle 기반으로 빌드 수행하여 도커 이미지 빠르게 생성 및 Docker hub에 push |
| **Spring Security + JWT + OAuth2** | 웹 애플리케이션의 보안 기능 강화 및 사용자 인증 및 권한 관리를 위한 Spring Security 사용. JWT와 OAuth2로 세션 관리 없이 사용자 인증 및 정보 전송, 외부 소셜 서비스를 통한 안전한 로그인 구현 |
| **AWS** | AWS EC2로 가상 서버 빠르게 구축 및 관리. AWS RDS MySQL로 데이터베이스 인프라 구축. AWS S3로 대용량 파일 저장. AWS Cloud Watch로 로그 수집/제공 및 효과적인 모니터링 수행. AWS Secret Manager 로 안전하게 시스템 프로퍼티 관리 |

## 🔖 Naming Rules
- 파일 : CamelCase + SnakeCase
- 클래스명 : PascalCase
- 함수/변수명 : CamelCase

## 📑 Commit Convention
커밋 메세지는 **커밋 태그(이슈 번호): 커밋 내용** 으로 작성  
ex) `git commit -m "Feat(#8): 로그인 기능 구현`
|커밋태그|설명|
|:---:|---|
|Feat|신규 기능 구현 작업|
|Fix|버그 수정|
|Docs|문서 수정|
|Style|코드 스타일 변경|
|Design|UI 디자인 변경|
|Refactor|코드 리팩토링|
|Rename|변수, 클래스, 메소드, 패키지명 변경|
|Build|dependencies 추가 및 삭제|
|Chore|기타 변경사항(빌드 관련, 패키지 매니징, CI/CD, assets 등)|
|Test|테스트 코드 추가|

## 🐬 Git Flow 전략
![image](https://github.com/nohy6630/readme_test/assets/129354455/c39dfa00-4d8a-4d7b-b0f5-262ec544b12a)
|브랜치명|설명|
|:---:|---|
|main|출시 또는 배포 가능한 코드의 브랜치|
|dev|다음 버전을 개발하는 브랜치|
|feat/[이름]|기능을 개발하는 브랜치|
|fix/[이름]|버그를 수정하는 브랜치|

