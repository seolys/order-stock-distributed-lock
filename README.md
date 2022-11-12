# 프로젝트 설명

- java11, SpringBoot
- Redis(Redisson)을 활용하여 재고량 동시성 문제 해결.
  <br>다건의 상품을 주문할 수 있고 재고가 부족한 경우에는 주문이 실패하도록 함.
  <br>트랜잭션 시작 전, 상품들에 락 획득 재고량 감소 및 트랜잭션 커밋 후 락을 해제.
  <br>다건의 상품들이 동시에 락을 획득할때 교착상태가 발생할 수 있기 때문에 락 Key 목록(상품번호)을 정렬 후 락을 획득하여 교착상태 방지.
- multi thread 상황에서 재고가 순차적으로 감소하는지 검증하는 단위테스트 추가.
  ```java
  @SpringBootTest
  @DisplayName("주문 분산 락 테스트")
  class OrderDistributedLockTest {
  
      public static final Integer STOCK_QUANTITY = 100;
  
      @Autowired private OrderService orderService;
      @Autowired private ItemRepository itemRepository;
      @Autowired private OrderDistributedLockTestSupport testSupport;
  
      private Item item1;
      private Item item2;
  
      @BeforeEach
      void setUp() {
          item1 = testSupport.save(buildItem(1L, STOCK_QUANTITY));
          item2 = testSupport.save(buildItem(2L, STOCK_QUANTITY));
      }
  
      @Test
      @DisplayName("재고 순차 감소 테스트")
      void registerOrder_success() throws InterruptedException {
          // given
          final int customerCount = STOCK_QUANTITY;
          final CountDownLatch countDownLatch = new CountDownLatch(customerCount);
          final var customers = IntStream.range(0, customerCount)
                  .mapToObj(this::buildMixedOrderItemList)
                  .map(registerOrderItems -> new Thread(new Customer(registerOrderItems, countDownLatch)))
                  .collect(Collectors.toList());
  
          // when
          customers.forEach(Thread::start);
  
          // then
          countDownLatch.await(10, TimeUnit.SECONDS);
          final var afterItem1 = itemRepository.findById(item1.getItemId()).get();
          final var afterItem2 = itemRepository.findById(item2.getItemId()).get();
          assertThat(afterItem1.getStockQuantity()).isZero();
          assertThat(afterItem2.getStockQuantity()).isZero();
      }
  
      private List<RegisterOrderItem> buildMixedOrderItemList(final Integer i) {
          // 상품 순서 섞기
          if (i % 2 == 0) {
              return List.of(buildRegisterOrderItem(item1, 1), buildRegisterOrderItem(item2, 1));
          } else {
              return List.of(buildRegisterOrderItem(item2, 1), buildRegisterOrderItem(item1, 1));
          }
      }
  
      private RegisterOrderItem buildRegisterOrderItem(final Item item, final long orderQuantity) {
          return RegisterOrderItem.of(item.getItemId(), item.getItemName(), item.getItemPrice(), orderQuantity);
      }
  
      @AllArgsConstructor
      private class Customer implements Runnable {
  
          private List<RegisterOrderItem> orderItems;
          private CountDownLatch countDownLatch;
  
          @Override
          public void run() {
              final var orderRequest = RegisterOrder.of(orderItems);
              orderService.registerOrder(orderRequest);
              countDownLatch.countDown();
          }
      }
  
  }
    ```
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
