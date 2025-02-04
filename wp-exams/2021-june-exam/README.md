# Испитна задача

Потребно е да развиете апликација за менаџирање на натпревари која ќе овозможи прегледување, додавање, уредување и бришење на натпревари.

## Функционални барања

- **(20 поени)** Потребно е на патеките `/` и `/matches` да прикажете листа од сите креирани натпревари со користење на темплејтот `list.html`.
  - Имплементацијата на оваа функционалност може да ја проверите со тестот `test_list_20pt`.

- **(20 поени)** Потребно е да се  имплементира додавање на натпревари. При клик на копчето **Add new match** од темплејтот `list.html`, 
потребно е да се прикаже темплејтот `form.html` на патеката `/matches/add`, каде при клик на **Submit** ќе се креира и запише нов натпревар 
во базата на податоци. Потоа треба да се прикаже страната `/matches`.
  - Имплементацијата на оваа функционалност може да ја проверите со тестовите `test_create_10pt` и `test_create_mvc_10pt`.

- **(10 поени)** Потребно е да се  имплементира бришење на натпреварите. При клик на копчето **Delete** од темплејтот `list.html`, потребно е да 
се избрише натпреварот од базата на податоци. Потоа треба да се прикаже страната `/matches`.
  - Имплементацијата на оваа функционалност може да ја проверите со тестовите `test_delete_5pt` и `test_delete_mvc_5pt`.

- **(20 поени)** Потребно е да се  имплементира променување на натпревари. При клик на копчето **Edit** од темплејтот `list.html`, 
потребно е да се прикаже темплејтот `form.html` на патеката `/matches/[id]/edit`, при што во `<input>` елементите ќе се прикажат 
вредностите за ентитетот кој се променува. При клик на **Submit** треба да се запише промената на натпреварот во базата на податоци. 
Потоа треба да се прикаже страната `/matches`.
  - Имплементацијата на оваа функционалност може да ја проверите со тестовите `test_edit_10pt` и `test_edit_mvc_10pt`.

- **(20 поени)** Потребно е да се имплементира follow на натпревар. При клик на копчето **Follow match** од темплејтот `list.html`, 
потребно е да се зголеми бројот на следбеници на натпреварот за 1. Потоа треба да се прикаже страната `/matches`.
  
    Потребно е да конфигурирате најава на `/login` и одјава на `/logout`. Притоа, јавна треба да биде само страницата `/`, 
    додека сите останати страници треба да се видливи само за `ROLE_ADMIN`. Дополнително, кај`list.html` копчињата 
    **Edit**, **Delete** и **Add new match** треба да се видливи само за `ROLE_ADMIN`, додека пак копчето **Follow match** треба да е 
    видливо само за `ROLE_USER`.
  - Имплементацијата на оваа функционалност може да ја проверите со тестовите `test_security_urls_7pt`, `test_security_buttons_7pt`, 
  `test_follow_3pt` и `test_follow_mvc_3pt`.

- **(20 поени)** Потребно е да овозможите пребарување на натпреварите според цена помала од одредена цена и вид на натпреварот преку 
формата со `id="filter-form"` во темплејтот `list.html`. Резултатите од пребарувањето треба да се прикажат на истата страница, 
при што ќе се прикажат само натпреварите кои имаат цена помала од онаа која е внесена и припаѓаат во селектираниот вид на натпревар. 
Филтрирањето се извршува само според внесените полиња (ако се празни, се игнорира филтрирањето по тој критериум).
  - Имплементацијата на оваа функционалност може да ја проверите со тестовите `test_filter_10pt` и `test2_filter_service_10pt`.

**ВАЖНО:** Сите споменати тестови се наоѓаат во класата `SeleniumScenarioTest`.

## Поставување на решението
Тестовите мора задолжително да ги извршите, затоа што на тој начин го испраќате вашето решение за прегледување. За проверка 
дали успешно е поставено вашето решение пристепете на патеката [wp.finki.ukim.mk/submit/[index]](http://wp.finki.ukim.mk/submit/index),
каде наместо `[index]` ќе го наведете вашиот индекс, кој претходно ќе го поставите во `TODO` вредноста на текстот.

Тие ќе ви помогнат да ја проверите точноста на вашата имплементација, но и ќе испратат информации до нашиот сервер за тоа до 
каде функционира вашата апликација. Ќе се гледа кодот само на студентите кај кои барем една проверка (`ExamAssert`) ќе помине успешно.

**НЕ Е ДОЗВОЛЕНО МЕНУВАЊЕ НА ТЕСТОВИТЕ, освен внесување на вашиот индекс**

## Техничко упатство:
- Во пакетот `mk.ukim.finki.wp.exam.example.model` веќе се креирани класите кои го репрезентираат моделот.
  Потребно е да извршите нивно мапирање со соодветните JPA анотации за моделот успешно да се сними во базата на податоци.
  Притоа важат следните услови:
  - Еден натпревар се одржува на една локација, додека пак на една локација може да има повеќе натпревари во различен врменски период.
  - Идентификаторите за `Match` и `MatchLocation` треба да бидат генерирани.
- Во пакетот `mk.ukim.finki.wp.exam.example.service` се веќе дефинирани интерфејсите за сервисната логика.
  За секој од методите имате опис што треба да биде имплементирано. Потребно е да се имплементираат овие интерфејси во 
  соодветните класи во пакетот `mk.ukim.finki.wp.exam.example.service.impl`. Во коментарите на методите се објаснети
  дополнителни услови (доколку ги има) кои треба да ги исполни методот.
- Класите од пакетот `mk.ukim.finki.wp.exam.example.repository` треба да ги дополните со потребните методи кои ви се потребни 
за да ја овозможите функционалноста на имплементацијата на сервисниот слој. Тие треба да се изведат од класата `JpaRepository` од Spring Data.
- Потребно е да ја анотирате класата `DataInitializer` и нејзините соодветни методи, така што при стартување на апликацијата ќе се изврши методот `initData`.
- Во класата `mk.ukim.finki.wp.exam.example.web.ProductsController` се дефинирани сите методи кои се потребни за да се имплементира менаџирањето со натпреварите.
  За секој од методите имате опис што треба да биде имплементирано. Потребно е овие handler методи да ги мапирате со користење само на HTTP GET и POST барања.
- Дополнете ги темплејтите со соодветните **Thymeleaf** атрибути за да се постигнат бараните функционалности.
  Притоа, ако недостасуваат одредени елементи и атрибути, може да ги додадете, но **НЕ СМЕЕ** да ги менувате `id` и `class` својствата на тековните елементи.
  Во коментари се дадени описи за елементите кои треба да се повторуваат, како и кои методи од контролерот треба да се повикаат.
- Потребно е да конфигурирате најава и одјава на корисници со Spring Security во класата `SecurityConfig`.
  Во самата класа има опис што треба да биде имплементирано.