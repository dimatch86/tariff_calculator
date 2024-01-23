# Проект Тарифный Калькулятор

Расчет стоимости доставки грузов в зависимости от веса, габаритов
упаковок входящих в состав груза, а также расстояния до пункта назначения.

## Используемые технологии

- Spring Boot 3
- Maven 3
- JUnit 5
- Swagger

## Локальный запуск

### Требования

Проект использует синтаксис Java 17. Для локального запуска вам потребуется
установленный JDK 17.

### Используя среду разработки IDEA

Откройте проект. Дождитесь индексации. Создайте конфигурацию запуска
или запустите main метод класса [app/src/main/java/ru/fastdelivery/Main.java](app/src/main/java/ru/fastdelivery/Main.java)

### Используя Docker

Вы можете создать Docker образ с приложением и запустить его.

Соберите проект:

```shell
./mvnw clean package
```

Создать образ:

```shell
docker build -t ru.fastdelivery:latest .
```

Запуск контейнера:

```shell
docker run -p 8081:8080 ru.fastdelivery:latest
```

### Используя JAR

Соберите проект:

```shell
./mvnw clean package
```

Запустите Jar файл:

```shell
java -jar app/target/app-1.0-SNAPSHOT.jar
```

## Тестирование кода

### Запуск тестов

Используя встроенный Maven Wrapper запустите
фазу тестов, при этом будет произведена проверка
стиля кода (checkstyle). Отчеты по checkstyle
найдете в файле
[target/site/checkstyle-aggregate.html](target/site/checkstyle-aggregate.html) 

Linux/macOs:

```shell
./mvnw clean test
```

Windows:

```shell
./mvnw.cmd clean test
```

## Swagger

При запущенном приложение вы можете
выполнять запросы используя интерфейс Swagger. 

http://localhost:8080/swagger-ui/index.html

## Структура приложения

Приложения разделено на maven модули, каждый
модуль отвечает за свою область применения:

- [app](app)

Содержит класс запуска приложения, настройки связывания бинов,
чтение значений из файла [application.yml](app/src/main/resources/application.yml)

- [domain](domain)

В модуле находятся все классы участвующие в бизнес логике, такие как Упаковка,
Вес, Валюта, Стоимость.

- [useCase](useCase)

В модуле находится все процессы бизнес логики использующие доменные
классы. Класс только зависит от модуля `domain`

- [web](web)

В модуле находится контроллеры, этот модуль единственный общается с
внешним миром. Содержит зависимости Spring Boot и `domain`, `useCase`.
