# Spring Boot Project

## Steps to Get Started

1. **Download the project** [here!](https://drive.google.com/file/d/1B15u2L4KT7uQgXKm9sBk3SDFdcagwKzQ/view?usp=sharing).
   
2. **Unzip the file**.

3. **Open the project** in your IDE using the `pom.xml` file.

4. **Update the database connection details** in `src/main/resources/application.properties` if required:

    ```properties
    spring.datasource.url=jdbc:mysql://host.docker.internal:3307/tdc-test
    spring.datasource.username=tdc-user
    spring.datasource.password=tdc-pw
    ```

5. **Run Maven clean install** to generate the JAR file:

    ```bash
    mvn clean install
    ```

The generated JAR file can then be used in Docker image creation.
