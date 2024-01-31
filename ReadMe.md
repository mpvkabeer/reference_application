This is a Sample Project that demonstrate a SpringBoot application.

This application has both Backend (SpringBoot having few APIs) and FrontEnd (UI) codes

Using this reference project, you can learn about following technologies 

01. SpringBoot
02. MVC Architecture
03. Database concepts
04. APIs for CRUD operations
05. Hibernate
06. Thymeleaf for UI
07. CRUD Operations with ElasticSearch
08. Utilities for String, CSV, File and JSON 
09. Spring Security
10. JWT Concepts


Software Used in this project:
1. Java 19
2. Spring Tool Suite 4 (STS4 in Eclipse IDE)
3. MySQL Server 8 (MySQL Workbench is required for creating DB) 
4. ElasticSearch 8.6.2

Note: All these software versions are latest as on Feb 17, 2023.  Enjoy the benefits.

Optional Software for Dev Testing:
1. Chrome extension - "Multi ElasticSeach Head"
2. Postman app
3. GitBash or Git Desktop

Download and Install JDK 19 from Orcale:
https://download.oracle.com/java/19/archive/jdk-19.0.2_windows-x64_bin.msi
(For installing other versions of Java, visit https://www.oracle.com/java/technologies/downloads)

"Spring Tools 4" can be plugged into Eclipse IDE, Visual Studio IDE and IntelliJ IDE. I prefer Eclipse IDE.

Download and Install Eclipse IDE
https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2022-12/R/eclipse-inst-jre-win64.exe

Download and install "Spring Tools 4" from Eclipse Marketplace (Open Eclipse => Help menu => Eclipse Marketplace)
(Alternatively you can download "Spring Tools 4" jar file from https://spring.io/tools and manually import into Eclipse)

Download and Install MySQL Server and MySQL Workbench:
https://dev.mysql.com/downloads/installer/

Download and Install ElasticSearch 8.6.2 from https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.6.2-windows-x86_64.zip
(Detailed steps for Installing ElasticSearch 8.6.2 is given in ReadMe-ElasticSearch.md file)


Download and Install "Multi ElasticSeach Head" Chrome extension from Chrome Webstore

Download and Install Postman App from https://www.postman.com/downloads/

Download and Install Git Desktop app from  https://central.github.com/deployments/desktop/desktop/latest/win32

==================================================================================================================================

*Steps to Setup this app in your machine*
1. Install above software in your machine
2. Download and Extract this sample project
3. Open Eclipse or other IDE (where you have installed STS 4) and Import this project (select the option 'Existing Maven project into workspace' in the wizard)
4. Open MySQL Workbench Open create a schema referencedb (or any name of your wish)
5. Open db folder of the this project and run the sql file in MySQL Workbench.  This will create 2 tables in the referencedb Database (Schema)
6. Right click on the project title in the STS and click on 'Build Path'=> 'Configure Build Path' and check if java version an classpath are correct. Please make sure Java version shown in Library tab is jdk-19. If not, please edit it and set it as jdk-19.
7. Also Set the right Compliance Level in Eclipse. To do this, Right click on the project title, Build Path -> Configure Build Path -> Java Compiler -> Select Compliance Level as 19
8. Open application.properties file and give the right credentials (username, paasword, hostname, port and database name) for the MySQL and Elasticsearch
9. This project demonstrate the CRUD operation for ES. So we will create an index named "products" in ES. To do this, Open "Multi ElasticSeach Head" and connect to ES. In "Any Request" tab, type "http://localhost:9200/" in the Query URL section, and enter "products" in the Query parameter section. Select type as "PUT", type {} as request body., and finally click on "Request Button"
10. To run the project, Right click in the project title in the STS and click on 'Run As'=> 'Java Application' and then select the main class file "ReferenceApplication" in the popup window.
11. Wait for few seconds, Check in the console window if you get any error. If no errors then fine.
12. To view the Application Home page Open http://localhost:8080/ in browser.
13. To test the APIs for Database operations, open any Rest Client call http://localhost:8080/api/users (GET method). If you get list of users in the result, it is working fine. Test the othr APIs also.
14. To test the Database CRUD operations via UI, Open http://localhost:8080/welcome in any browser
15. To test the ElasticSearch CRUD operations via UI, Open http://localhost:8080/product_store in any browser
16. Done
============================================================================================

*Any questions, Contact Ahamed Kabeer: +91 9843527241 or hakabeer@gmail.com*

==========================================================================================================================

References:

Spring Start Project:
https://start.spring.io/

DB CRUD Example:
https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

ES CRUD Example:
https://www.pixeltrice.com/spring-boot-elasticsearch-crud-example/

SpringSecurity (Login) Example:
https://github.com/RameshMF/registration-login-springboot-security-thymeleaf

https://medium.com/@2015-2-60-004/multiple-spring-security-configurations-form-based-token-based-authentication-c65ffbeabd07#:~:text=We%20can%20create%20multiple%20security,matching%20pattern%2C%20add%20securityMatcher%20annotation.

Bug Fixes:
https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-refresh.html#refresh_wait_for-force-refresh
