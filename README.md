Iprody Microservices New — это модульная микросервисная архитектура. 
Проект демонстрирует реализацию распределённой системы на основе микросервисов с использованием современных технологий и практик разработки.

Цели проекта
демонстрация принципов микросервисной архитектуры;
реализация взаимодействия между сервисами через REST API и/или асинхронные сообщения;
обеспечение масштабируемости и отказоустойчивости системы;
внедрение централизованной конфигурации и мониторинга;
автоматизация развёртывания с помощью Docker и Kubernetes.

Технологический стек:
Backend-Java 17/21 + Spring Boot 3.x
Микросервисы-Spring Cloud, Spring Cloud Netflix (Eureka, Zuul), Resilience4j
Конфигурация-Spring Cloud Config Server
Асинхронная коммуникация-Kafka / RabbitMQ
База данных-PostgreSQL, MongoDB (на сервис)
Кэширование-Redis
Мониторинг-Prometheus + Grafana, Spring Boot Actuator
Контейнеризация-Docker
Оркестрация-Kubernetes / Docker Compose
CI/CD-GitHub Actions / Jenkins

Функциональные возможности
регистрация и аутентификация пользователей (JWT);
создание, обновление и отслеживание заказов;
управление каталогом товаров (добавление, редактирование, поиск);
асинхронная отправка уведомлений о статусе заказа;
централизованная конфигурация сервисов;
обнаружение сервисов и балансировка нагрузки;
мониторинг метрик и логирование событий.

Структура репозитория
IprodyMicroservicesNew/
├── user-service/          # Микросервис пользователей
├── order-service/         # Микросервис заказов
├── product-service/       # Микросервис товаров
├── notification-service/   # Микросервис уведомлений
├── api-gateway/           # API Gateway
├── config-server/          # Конфигурационный сервер
├── service-discovery/      # Обнаружение сервисов
├── docker/                 # Dockerfile и docker-compose
├── kubernetes/             # YAML-манифесты для Kubernetes
├── scripts/               # Вспомогательные скрипты
├── docs/                  # Документация
└── README.md               # Описание проекта

Как начать работу:
Предварительные требования:
Java 17 или 21;
Maven 3.6+;
Docker 20+;
Kubernetes (опционально);
PostgreSQL, Redis, Kafka (или Docker Compose для запуска зависимостей).

Шаги по запуску:

Клонируйте репозиторий:
bash
git clone https://github.com/maratVard/IprodyMicroservicesNew.git
cd IprodyMicroservicesNew

Соберите все сервисы:
bash
mvn clean install

Запустите зависимости через Docker Compose:
bash
docker-compose up -d

Откройте API Gateway по адресу http://localhost:8080.

