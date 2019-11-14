Database configuration for wildfly
==================================

Update `standalone.xml` with the followings, after filling in the environment specific properties which are:
* `{jndi-name}` e.g.: java:/PotmDS
* `{pool-name}` e.g.: PotmDS
* `{postgresql-host}` e.g.: 192.168.1.15:5432
* `{db-name}` e.g.: potm-dev
* `{db-username}` e.g.: potm_dev
* `{db-password}` e.g.: potm_dev

Don't forget to deploy the driver (in this case `postgresql-42.2.8.jar`) through the Management console before updating the config file. 

```$xml
        <subsystem xmlns="urn:jboss:domain:datasources:5.0">
            <datasources>
                <datasource jndi-name="{jndi-name}" pool-name="{pool-name}">
                    <connection-url>jdbc:postgresql://{postgresql-host}/{db-name}</connection-url>
                    <driver-class>org.postgresql.Driver</driver-class>
                    <datasource-class>org.postgresql.ds.PGSimpleDataSource</datasource-class>
                    <connection-property name="url">
                        jdbc:postgresql://{postgresql-host}/{db-name}
                    </connection-property>
                    <driver>postgresql-42.2.8.jar</driver>
                    <security>
                        <user-name>{db-username}</user-name>
                        <password>{db-password}</password>
                    </security>
                    <validation>
                        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
                        <check-valid-connection-sql>select 1;</check-valid-connection-sql>
                        <background-validation>true</background-validation>
                        <background-validation-millis>2000</background-validation-millis>
                        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
                    </validation>
                </datasource>
            </datasources>
        </subsystem>
```