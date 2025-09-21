# Spring Boot & Kafka EDA 예제 프로젝트

이 프로젝트는 Spring Boot와 Kafka를 사용하여 기본적인 이벤트 기반 아키텍처(Event-Driven Architecture)를 구현한 예제입니다. REST API를 통해 메시지를 발행(Produce)하고, 해당 메시지를 구독(Consume)하여 처리하는 전체 흐름을 포함합니다. 또한, 메시지 처리 실패 시 Dead Letter Queue(DLQ)를 이용한 에러 핸들링 방법도 구현되어 있습니다.

## ✨ 주요 기능

-   **Kafka Producer**: REST API를 통해 Kafka 토픽으로 메시지를 전송합니다.
-   **Kafka Consumer**: 특정 토픽을 구독하여 메시지를 수신하고 처리합니다.
-   **Dead Letter Queue (DLQ)**: 메시지 처리 중 발생하는 에러를 핸들링하고, 실패한 메시지를 별도의 DLQ 토픽으로 보냅니다.
-   **Docker Compose**: Kafka와 Zookeeper를 로컬 환경에서 손쉽게 실행할 수 있도록 지원합니다.

---

## 🛠️ 시작하기

### 사전 요구사항

-   Java 17 이상
-   Gradle 7.x 이상
-   Docker 및 Docker Compose

### 1. 프로젝트 클론

```bash
git clone [your-repository-url]
cd [project-directory]
```

### 2. Docker를 사용하여 Kafka 실행하기

이 프로젝트는 `docker-compose.yml` 파일을 사용하여 Kafka와 Zookeeper를 컨테이너 환경에서 실행합니다.

프로젝트의 루트 디렉토리에서 아래 명령어를 실행하세요.

```bash
docker-compose up -d
```

이 명령어는 백그라운드에서 Kafka와 Zookeeper 컨테이너를 실행합니다. (`-d` 옵션)

> **[!] Kafka와 Zookeeper**
> Kafka는 메시지 큐 시스템의 상태 정보(브로커, 토픽, 파티션 등)를 관리하기 위해 Zookeeper를 사용합니다. `docker-compose`는 이 두 서비스를 함께 실행하고 연결해주는 편리한 도구입니다.



컨테이너가 정상적으로 실행되었는지 확인하려면 아래 명령어를 사용하세요.

```bash
docker ps
```

`kafka`와 `zookeeper` 두 개의 컨테이너가 `Up` 상태로 보이면 성공입니다.

### 3. Spring Boot 애플리케이션 실행

IntelliJ나 Eclipse 같은 IDE에서 프로젝트를 열고 메인 애플리케이션을 실행하거나, 터미널에서 아래 명령어를 사용하여 실행할 수 있습니다.

```bash
./gradlew bootRun
```

---

## 🚀 API 사용법

애플리케이션이 실행되면, 아래 API 엔드포인트를 통해 Kafka로 메시지를 보낼 수 있습니다.

### 사용자 생성 및 메시지 발행

-   **Method**: `POST`
-   **Endpoint**: `/users`
-   **Parameter**: `name` (String)

#### cURL 예제

터미널에서 아래 명령어를 실행하여 테스트할 수 있습니다.

```bash
curl -X POST http://localhost:8080/users -d "name=gemini"
```

#### 성공 시
1.  **Producer**: `Sending message to Kafka...` 로그가 콘솔에 출력됩니다.
2.  **Consumer**: `Received message...` 로그가 콘솔에 출력됩니다.

#### 실패 시 (예제 코드 기준)
1.  **Producer**: 메시지 발행 로그가 출력됩니다.
2.  **Consumer**: 메시지 수신 후, 에러 로그와 함께 설정된 횟수만큼 재시도가 발생합니다.
3.  **DLQ**: 최종 실패 후, 메시지는 `{원본토픽}.DLT` 토픽 (예: `user-created-topic.DLT`)으로 전송됩니다.