# 왜 kafka를 써야할까? 

- kafka는 일반적인 큐잉을 위한 오픈소스 인가? RabbitMQ처럼? 

---
- **kafka는 실시간 이벤트 스트리밍 플랫폼!**
    - LinkedIn에서 겪었던 다양한 어려움 (확장 용이성, 고성능 실시간 데이터 처리)
      - e.x. 채팅, 피드 등에서 필요한 **"실시간성" 처리 문제**
    - 고가용성, 고성능
      - 기존의 Sync 방식을 대체 "가능"할 수도 있는 수단
  - 일반적으로 규모가 작은 비즈니스에는 **비적합**
    - 일단 큐에 넣어두고, 필요에 따라 나중에 처리해도 되는 비즈니스
    - 라우팅 변경에 대한 가능성이 굉장히 큰 비즈니스 
      - 오히려 RabbitMQ가 적합!

---
- **대규모**의 실시간 이벤트/데이터 스트리밍이 필요한 비즈니스에 적합!
  - 트위터, 카카오 ,넷플릭스, 우버 ,...
- 대규모를 지원한다는 것 -> 소규모도 당연히 지원 -> 그렇다면 모두 다 kafka 쓰면되는 것 아닌가요?
- 그러나, 적합하지 않을 수 있다라는 의미 -> Over Spec.
  - -> High Engineering Learning Curbe, Kafka 전문 관리 인력 필요


# kafka의 기본 개념 1 (Producer, Consumer, COnsumer Group)

#### Producer
- 메세지를 발행하는 **"주체"**
  - **Produce** : 메세지를 발행하는 동작

#### Consumer
- 메세지를 소비(즉, 가져와서 처리)하는 **"주체"**
  - **Consume** : 메세지를 가져와서 처리하는 동작 

#### Consumer Group
- 메세지를 소비(즉, 가져와서 처리) 하는 **"주체 집단"**
- 겹치치 않고 하나의 컨슈머처럼 여러 개의 컨슈머를 그룹화 시켜서 하나의 컨슈머처럼 논리적으로 그룹화 하는 개념 
- Consumer Group 끼리는 토픽을 공유하지 않는다

# kafka의 기본 개념 2 (Kafka Cluster, Topic)
 
#### Producer
- **Kafka Cluster**에 존재하는 **Topic**에 메세지를 발행하는 **"주체"**
  -  **Produce** : 메세지를 kafka Cluster 에 존재하는 Topic에 발행하는 동작

#### Consumer
- **Kafka Cluster**에 존재하는 **Topic**에서 메세지를 소비 (즉, 가져와서 처리) 하는 **"주체"**
  - **Consume** : 메세지를 Kafka Cluster에 존재하는 Topic 으로부터 가져와서 이를 처리하는 동작


# kafka의 기본 개념 3 (Kafka Broker) (핵심!)
#### Producer
- **Kafka Cluster**에 존재하는 **Topic**이 포함된 **Kafka Broker**에 메세지를 발행하는 **"주체"**
    -  **Produce** : 메세지를 kafka Broker 내 Topic 에 발행하는 동작

#### Consumer
- **Kafka Cluster**내 **Topic**이 포함된 **Kafka Broker**로부터 메세지를 소비 (즉, 가져와서 처리) 하는 **"주체"**
    - **Consume** : 메세지를 Kafka Broker내 존재하는 Topic 으로부터 가져와서 이를 처리하는 동작


# kafka 의 기본개념 4 (Zookeeper)
## Apache Zookeeper(for hadoop Ecosystem)
- 분산 처리 시스템에서 분산 처리를 위한 코디네이터
  - 누가 리더인지, 어느 상황인지, 동기화 상태 등 관리
- e.x. 타조, 피그, 코끼리, ...

- **Kafka Cluster를 관리**
- Kafka Broker 의 상태를 관리하고 
- Cluster 내에 포함된 Topic 들을 관리하며
- 등록된 Consumer 정보를 관리하는 "주체"

register은 kafka Cluster 안에서 내부 토픽을 보고 어느 토픽이 우선시 해야하는지 Leader 정보들을 저장하고 그걸 Kafka Broker에 대해서 가지고 있고, 
컨슈머도 현재 어느 브로커에 어느 브로커에 어느 파티션에 붙어있고, 옳바른 정보를 주기 위해서 있다.











