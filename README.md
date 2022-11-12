# 프로젝트 설명

- java11, SpringBoot
- Redis(Redisson)을 활용하여 재고량 동시성 문제 해결.
  <br>다건의 상품을 주문할 수 있고 재고가 부족한 경우에는 주문이 실패하도록 함.
  <br>트랜잭션 시작 전, 상품들에 락 획득 재고량 감소 및 트랜잭션 커밋 후 락을 해제.
  <br>다건의 상품들이 동시에 락을 획득할때 교착상태가 발생할 수 있기 때문에 락 Key 목록(상품번호)을 정렬 후 락을 획득하여 교착상태 방지.
- multi thread 상황에서 재고가 순차적으로 감소하는지 검증하는 단위테스트 추가.

# 프로젝트 실행방법

## 1. 첨부된 jar파일 실행

```
java -jar app.jar
```

## 2. jar파일 생성 후 실행

```
./gradlew clean :module-boot-command:buildNeeded --stacktrace --info --refresh-dependencies -x test
java -jar ./module-boot-command/build/libs/module-boot-command-*.jar
```
