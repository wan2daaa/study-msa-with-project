# kafka Cluster를 구축해보자

## kafka를 구축해보기에 앞서 Zookeeper에 대해 고민해보자 

- Zookeeper는 Apache 재단에서 초기 Hadoop의 하위 프로젝트로 개발되었던, 분산 시스템 코디네이터
- 기능확장에 따라서, 분산 시스템에서 공통적으로 겪는 문제를 해결하는 **코디네이터로서** 프로젝트 분리

- **kafka Cluster 또한 분산 시스템으로서 Zookeeper 필요**

### kafka 에서 Zookeeper 가 하는 일
- Cluster 내에서 어떤 Broker가 **Leader Broker 인지** (**Controller 선정**)
  - 어느 Broker가 Partition/Replica 리더를 선출할지 선정
  - 어느 Broker가 리더인지 알아야 장애나 그런 상황에 있어서 어떤 것이 리더인지 알아야 데이터 정합성을 지킬 수 있기 때문
- Topic의 파티션 중에 어느 것이 **Leader Partition 인지** 정보 저장
  - 장애 시, 어느 파티션이 우선되는지 정보 관리
- Cluster 내 Broker 들의 메세징 Skew 관리 / Broker 내 Partition 들의 Skew 관리
  - Skew : 데이터가 분산되어 있는 정도
  

등의 코디네이터 역할 수행

## 실습 계획 

- 1 Broker
- 1 Zookeeper
- 1 Kafka-ui (Kafka Cluster의 상태를 확인 할 수 있는 UI Tool)

using Docker-compose

- Sample Producer (인입로그 , access Log : 요청에 대한 로그를 남김)
- Sample Consumer 


**Simple Logging Pipeline**

- Kafka 를 사용하여, Async 방식으로 로그를 발행하고, Consumer에서 stdout을 통해서 출력하는 Pipeline


### Logging Pipeline을 위한 Topic 설계, 생성
- partition 1
- replication-factor = 2
- min.insync.replica = 1

