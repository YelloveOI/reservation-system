# Rezervační systém zasedacích místností
## Semestrální práce předmětu B6B36NSS.

**Členové týmu:**

Kryštof Müller, Lukáš Novák, Ivan Shalaev, Eduard Nurmukhametov

**Popis projektu:**

Jedná se o informační systém, který bude sloužit k rezervaci zasedacích místností, především pro firmy jakékoliv velikosti. Místnosti si však budou moci rezervovat i běžní občané pro různé společenské akce.

**Hlavní funkce:** 
- **Rezervace** místnosti na určitý den
- Informace o **obsazenosti** místností
- Informace o **dostupném vybavení** místností
- **Prioritizace** vybraných místností na základě obsazenosti ostatních místností
- **UI prostředí**

| **Užívatelé** | **Umožněné funkce** |
| ------ | ------ |
| **Zákazník** | **přehled** dostupných místností, **informace** o nich, možnost je **filtrovat** a jejich **rezervace** |
| **Zaměstnanec** | **přehled všech existujících místností**, jejich **blokování** a **rušení rezervací** |
| **Správce systému** | **přidávat/odebírat místnosti** a **přidávat nové uživatele** a nastavovat jejich **přístupová práva** |

**Určen pro:**

Firmy spravující kancelářskou budovu nebo budovy se zasedacími místnostmi.

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
