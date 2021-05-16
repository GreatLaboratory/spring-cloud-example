- 같은 서비스를 여러 포트로 띄우는 방법
    1. 일단 처음 만든건 바로 run시킨다.
    2. intellij 오른쪽 상단에 드롭다운 눌러서 edit configuration으로 가서 인스턴스 복사한 후 vm option으로 -Dserver.port:9002를 준다.
    3. 명령어 mvn spring-boot:run -Dspring-boot.run.jvmArguments='-Dserver.port=9003' 을 실행한다.
    4. 프로젝트를 빌드해서 jar file이 있는 상태라면 명령어 java -jar -Dserver.port=9004 ./target/user-service-0.0.1-SNAPSHOT.jar  을 실행한다.