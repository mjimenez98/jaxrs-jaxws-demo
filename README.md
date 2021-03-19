# jaxrs-jaxws-demo

Demo of Java RESTful (JAX-RS) and SOAP-based (JAX-WS) web services using a Spring MVC app to consume this services.

# Installation

## Modules

1. Go to `File`
2. `Project Structure...`
3. `Modules`
4. Set `jaxrs-jaxws-demo`, `jaxrs-jaxws-demo/albums-service`, `jaxrs-jaxws-demo/client`
   and `jaxrs-jaxws-demo/logs-service` as modules
   
## Database

1. Open a mysql console by running `mysql -u USERNAME -pPASSWORD` in your Terminal. `USERNAME` and `PASSWORD` should
   be replaced with your own credentials
2. Copy the schema found in `albums-service/src/main/resources/schema.sql`
3. Run it in the mysql console
4. Open `albums-service/src/main/java/db/DBConnect`
5. Replace `DBUSER` and `DBPASS` with your own credentials 
   
## SOAP Configuration for Client

1. Open Maven tab
2. Select `client`
3. Execute Maven goal (m logo in Maven toolbar)
4. Enter `.\mvn compile`
5. Execute a new Maven goal
6. Enter `mvn generate sources`
