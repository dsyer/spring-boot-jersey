This project provides Jersey autoconfiguration for a Spring Boot
application. Feedback is more than welcome, especially in the form of
pull requests.

## Getting Started

Include this project as a dependency in a Spring Boot web
application. If you are using a snapshot version you will need to
build it locally first, or point your Maven settings at
"http://repo.spring.io".  

First create a Spring Boot application with `@EnableAutoConfiguration`:

```java
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
```

Then include a `@Component` of type `ResourceConfig` (from Jersey) that sets up the
Jersey container, e.g.

```java
@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(Endpoint.class);
	}

}
```

And then define your REST endpoints using Spring beans annotated with
JAX-RS, e.g.

```java
@Component
@Path("/hello")
public class Endpoint {

	@GET
	public String message() {
		return "Hello";
	}

}
```

That's it! Since the `Endpoint` is a Spring `@Component` its lifecycle
is managed by Spring and you can `@Autowired` dependencies and inject
external configuration with `@Value`. The Jersey servlet will be
registered and mapped to "/*" by default. You can change the mapping
by adding `@ApplicationPath` to your `ResourceConfig`.

Because it is a Spring Boot app you can run it from the main method or
from an executable JAR.  You can refer to the
[Jersey Sample](spring-boot-sample-jersey) for a detailed example (the
sample is a deployable, executable WAR).

### Example Build Configuration

Example `pom.xml` (excluding repository declarations):

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

    <groupId>org.demo</groupId>
	<artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.0.0.BUILD-SNAPSHOT</version>
	</parent>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-jersey</artifactId>
			<version>1.0.0.BUILD-SNAPSHOT</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
		</dependency>
	</dependencies>

	<properties>
		<start-class>demo.Application</start-class>
        <java.version>1.7</java.version>
	</properties>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
