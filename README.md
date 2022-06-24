# Rezervační systém zasedacích místností
## Semestrální práce předmětu B6B36NSS.

**Členové týmu:**

Kryštof Müller, Lukáš Novák, Ivan Shalaev, Eduard Nurmukhametov

## Návod na použití:
1) **Spouštění kafky**
   1) bin\windows\zookeeper-server-start.bat config\zookeeper.properties
   2) bin\windows\kafka-server-start.bat config\server.properties
2) **Spouštění Redis**
   1) redis-server.exe
3) **Spouštění Back-Endu**
   1) Řádné spuštění v IntelliJIDEA
4) **Spouštění Front-Endu**
   1) npm install
   2) cd ./node_modules/
   3) npm start

## Požadavky
1) Redis cache: APIGW service
2) Databaze: user-service, room-reservation-service, invoice-service
3) Kafka: room-reservation-service, invoice-service
4) Security pomoci API Keys: APIGW service (kliče jsou uloženy do cahce)
5) Interceptor: room-reservation-service
6) REST: user-service, room-reservation-service, invoice-service
7) Heroku: ano
8) Architektura: mikroservisní
9) Elasticsearch: neni
10) Patterny: Saga-Choreography, DI, Facade, API-Gateway, Event-Sourcing, Databaze-per-Service, API-Keys Authentication

## Popis aplikace, motivace:

Jedná se o informační systém, který bude sloužit k rezervaci zasedacích místností,
především pro firmy jakékoliv velikosti. Firma v systému zadá místnosti, které je možné si
jednoduše rezervovat dle časové dostupnosti, požadované velikosti či kapacitě v tomto
informačním systému. Motivací projektu je vytvoření jednoduché organizační aplikace pro
tento případ.

## Strategický záměr (stav „TO BE“)

Budoucí stav aplikace je takový, že si vlastník systému naplní databázi vlastními daty,
kterými jsou především místnosti a informace o nich. Tyto místnosti jdou poté rezervovat.
Místnosti se mohou lišit obsazeností, kapacitou, dostupným vybavením. Tyto informace jsou
uživateli poskytnuty a lze dle nich filtrovat. Systém bude postaven na mikroservisní
architektuře s využitím moderních technologií doprovázející tento implementační přístup.

## Obchodní přínos Stav „AS IS“

V současné době máme tzv. monolitní Back-End aplikaci splňující základní business
požadavky

## Seznam uživatelů

Systém bude rozlišovat tři možné uživatele:

- **Zákazník** - představuje uživatele se zájmem o rezervaci místnosti. Má mít možnost
místnosti rezervovat a rušit své rezervace, filtrovat je na základě vlastností, zobrazit
si o nich informace a přehled o jejich dostupnosti.
- **Zaměstnanec** - pracovník starající se o správu rezervací. Má moci vše, co Zákazník
a navíc vytvářet a rušit rezervace ostatním uživatelům, mít přehled o všech
existujících místnostech a blokovat je například při poruše vybavení nebo z důvodu
státních svátků.
- **Správce systému** - vše, co Zaměstnanec a navíc možnost přidávat nové uživatele,
nastavovat jim role v systému a přidávat/odebírat existující místnosti.



