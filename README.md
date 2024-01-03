delivery-service 배달 플랫폼 강의 들으면서 개인 프로잭트로 공부했습니다.  
  
실행 방법. 프로잭트 zip 파일을 내려받는다.  
  
도커를 설치한다.  
mysqlwrokerbench를 설치한다.  
  
도커 컴포즈 zip파일을 풀어 inteliJ에서 실행 시킨다.  
이후 도커를 통해 3306포트를 실행 시켜준다.  
project명 service2(delivery service main 입니다.)에   
rabbitmq 모듈에 있는 도커 컴포즈 마저 실행 시켜준다.  
이후 15672 포트를 실행 시켜준다.  
  
deliveryErd 파일을 통해 MysqlWorkbench에다가   
db 이름을 delivery로 설정후 ERD들을 쿼리 문을 통해 만들어 준다.  
  
api application, storeadminApplication 등을 실행 시켜주면 된다.  
  
user 가입 후에는 jwt토큰이 발행 되는데 이는  
크롬 스토어에 있는 modify headers를 통해  
request란에 authorization-token를  
value란에 발행된 토큰 키 값을 넣어주면 된다.  
그래야지 ssesion이 유지가 되며   
가게 메뉴를 주문 할 수가 있다.  
