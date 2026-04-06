# OTC Spring Boot API

This project is a Spring Boot equivalent of the legacy `OTC.Api` .NET Core project. It provides the same API endpoints, database interactions, and AES encryption/decryption middleware.

## Prerequisites & Installation

To run or develop this project on your local machine, you need to install the following:

1. **Java Development Kit (JDK)**
   - **Version:** JDK 17 or higher (The development environment is currently using JDK 25).
   - **Download:** You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use an OpenJDK distribution like [Adoptium Eclipse Temurin](https://adoptium.net/).
   - **Setup:** Ensure the `JAVA_HOME` environment variable is set and points to your JDK installation directory.

2. **Maven (Build Tool)**
   - **Version:** 3.8 or higher.
   - **Download:** [Apache Maven](https://maven.apache.org/download.cgi)
   - **Setup:** Add the `bin` directory of your Maven installation to your system's `PATH`. *(Note: The project also includes a local maven installation under the `maven/` directory if you prefer to use that).*

3. **IDE (Optional but Recommended)**
   - Use your preferred Java IDE: IntelliJ IDEA, Visual Studio Code (with the Extension Pack for Java), or Eclipse.

## Running Locally

1. Open a terminal / command prompt in the project root directory (`d:\OTCNEW\otc-spring-boot-api`).
2. Run the application using Maven:
   ```powershell
   mvn clean spring-boot:run
   ```
   *(If you don't have Maven on your PATH globally, use the provided binary: `.\maven\apache-maven-3.9.6\bin\mvn.cmd clean spring-boot:run`)*
3. The API will start and listen on port **8080**.
4. You can view the interactive API documentation via Swagger UI at: `http://localhost:8080/swagger-ui/index.html`

## Important Note regarding Lombok

*Lombok was intentionally removed from this project per user request.* All Data Transfer Objects (DTOs) and models use standard Java Getters, Setters, and Constructors. Do not add the `@Data` annotation or Lombok dependencies back to the `pom.xml`.

---

## Deployment Plan on IIS (Internet Information Services)

While Spring Boot runs on an embedded web server (Tomcat by default), you can easily deploy it behind IIS on a Windows Server. The recommended approach is to configure IIS as a **Reverse Proxy** using the **HttpPlatformHandler** module.

This module intercepts incoming HTTP requests to IIS (e.g., port 80/443) and forwards them to the embedded Tomcat server running the Spring Boot application on an internal port.

### Step 1: Server Preparation
1. **Install Java:** Install the Java Runtime Environment (JRE) or JDK on the Windows Server. Make a note of the installation path.
2. **Install IIS:** Ensure the Web Server (IIS) role is installed on the Windows Server.
3. **Install HttpPlatformHandler:** Download and install the [Microsoft HttpPlatformHandler v1.2](https://www.iis.net/downloads/microsoft/httpplatformhandler) for IIS.

### Step 2: Build the Application
On your development machine or CI/CD server, package the application into an executable "Fat JAR":
```powershell
mvn clean package
```
This command generates the `.jar` file in the `target/` directory (e.g., `api-0.0.1-SNAPSHOT.jar`).

### Step 3: Deployment Folder Setup
1. Create a directory on the Windows Server where the application will be hosted (e.g., `C:\inetpub\wwwroot\otc-spring-api`).
2. Copy the built `api-0.0.1-SNAPSHOT.jar` file into this directory.
3. Create a new file named `web.config` in the exact same directory with the following XML content:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <system.webServer>
        <handlers>
            <add name="httpPlatformHandler" path="*" verb="*" modules="httpPlatformHandler" resourceType="Unspecified" />
        </handlers>
        
        <!-- 
          * processPath: Absolute path to the Java executable.
          * arguments: Starts the JVM. 
                       Crucial: We pass server.port=%HTTP_PLATFORM_PORT% so Spring Boot 
                       listens on the dynamic port assigned by IIS.
        -->
        <httpPlatform processPath="C:\Program Files\Java\jdk-25\bin\java.exe"
                      arguments="-Dserver.port=%HTTP_PLATFORM_PORT% -jar &quot;.\api-0.0.1-SNAPSHOT.jar&quot;"
                      stdoutLogEnabled="true"
                      stdoutLogFile=".\logs\stdout"
                      startupTimeLimit="60">
        </httpPlatform>
    </system.webServer>
</configuration>
```
*Note: Make sure `processPath` points to the correct `java.exe` location on your server, and the `-jar` argument accurately reflects your jar filename. Create a `logs` folder inside the deployment directory if you want `stdoutLogEnabled` to write successfully.*

### Step 4: IIS Configuration
1. Open **Internet Information Services (IIS) Manager**.
2. Right-click on **Sites** and select **Add Website...** (or Add Application under an existing site).
3. Provide a Site name and set the **Physical path** to the directory created in Step 3.
4. Assign a Port (e.g., 80 or 443 depending on your SSL bindings).
5. Open **Application Pools**, locate the pool assigned to your new site, right-click, and select **Basic Settings**.
6. Change the **.NET CLR version** to **No Managed Code**. (Since IIS is only acting as a proxy, it doesn't need to load the .NET runtime).
7. Start the Website.

### How it works
When the first HTTP request hits the IIS website, the `HttpPlatformHandler` will launch `java.exe` using the arguments in `web.config`. A dynamic internal port is assigned by `%HTTP_PLATFORM_PORT%`, which Spring Boot picks up, allowing IIS to smoothly proxy all traffic to your secure Spring Boot application.
