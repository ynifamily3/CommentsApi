# CommentsApi
Comments 서비스의 API 서버입니다. CommentsFront에게 필요한 API르 제공해 줍니다.

## DB
- `EC2`에 통합된 `mongoDB`를 사용하다 `MongoDB Atlas`를 사용하고 있습니다.
  -  일정 사용량 무료, 클러스터 구성 용이, 접근 제어 용이

## Server
- `lightsail`을 사용하다 `EC2`로 이주. (`lightsail`에선 가끔 서버가 죽어서 안정적으로 서비스할 수 있는 `EC2`로 이주)
- `tmux`으로 `java -jar app.jar` 형태로 실행하였으나 여러 이슈 발생.
  - 메모리 부족으로 프로그램이 자주 killed된다.
  - 서버 이주 시 `java`환경을 셋팅해 주어야 한다.
- `Docker`로 이주
  - 서버 설정이 급격하게 편해짐 (프로세스가 `killed`되어도 재실행하는 등..)
  - `Blue-Green`배포 같은 방식 사용 가능

## Authentication
- 소셜 로그인만으로 진행
- jwt를 검증하여 올바른 계정 정보인지 확인하고 서버 측 액션을 수행합니다.
- 인증 서버가 분리되어 있음. [commentsAuth](https://github.com/ynifamily3/commentsAuth)
