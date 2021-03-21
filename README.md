# CommentsApi
댓글 API를 제공하여 누구나 갖다 쓸 수 있도록 합니다.


- GET /comment/{consumerID}/{sequenceID}
  - 댓글 리스트 보기
  - `consumerID`는 서비스 제공자의 고유 ID입니다.
  - `sequenceID`는 같은 서비스에서 댓글의 Area를 구분하는 키워드입니다. 문자열 어느 것이든 가능합니다.

- POST /comment/{consumerID}/{sequenceID}
  - 댓글 등록하기
