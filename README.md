## SimpleWeather

Это простой учебный проект, написанный на Kotlin и имеющий целью проработать использование указанных ниже библиотек. Поэтому визуальная часть сделана максимально простой. Имеется 2 кнопки, 2 экрана и строка поиска: в строке поиска можно ввести название города (по-английски), на первом экране отображается текущий прогноз погоды, на втором экране отображается история прогноза погоды, которая сохраняется в БД. 

### Архитектура

Программа создана на основе подхода Clean Architecture, presentation-слой сделан на базе MVP шаблона. В приложении корректно обрабатываются ситуации возникновения ошибок (в частности отсутствия доступа в интернет) и смены конфигурации. Для обработки ошибок, возникающих в цепочках RxJava, взята идея из статьи [Error handling in RxJava](https://rongi.github.io/kotlin-blog/rxjava/rx/2017/08/01/error-handling-in-rxjava.html). Для презентеров, интеракторов и классов репозиториев написаны unit-тесты.

### Основные библиотеки

* RxJava 2
* Dagger 2
* Moxy
* Cicerone
* Room
* Retrofit 2
