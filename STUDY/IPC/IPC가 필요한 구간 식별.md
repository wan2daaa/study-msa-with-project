# IPC가 필요한 구간 식별


## IPC Pattern을 선택함에 있어 가능한 Options

### Sync

- **http** : 통신 방식이 유동적일 경우, 리소스에 크기 구애받지 않아도 되는 경우,
- **gRPC** : 통신 방식이 강하게 정해진 경우, 리소스 제한이 큰 경우

### Async

- **고성능 이벤트/데이터 플랫폼의 존재 유무**
- 비즈니스적으로 판단 필요
    - 민감한 비즈니스인지
    - 조금 응답 지연이 늦어도 괜찮은지
    - 트래픽이 몰리는 상황(불안정한 인프라)에서 어떻게든 수행해 줄만한 가치가 있는지
    - 비동기 통신을 안정적으로 처리할만한 인프라(인력)이 있는지


## banking-service

- **registerBankAccount API**
  - ... -> call membership-service (findMembershipByMemberId)
    - 특정 고객의 고객 상태가 정상인지 체크하기 위함

- **requestFirmbanking API**
  - ... -> call External Bank
    - 실제 외부 은행에 입/출금 요청을 하기 위함


### banking-service Sync 계획

1. docker-compose를 통해서 membership-service의 endpoint를 환경변수(env)로 등록
2. http call을 위한 기본 어댑터 구현, Port 인터페이스와 연동
3. dummy Bank Server 구현
4. API를 통한 IPC 적용 테스트



