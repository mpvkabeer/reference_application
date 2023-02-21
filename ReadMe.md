This is a Sample Project that demonstrate a SpringBoot application.

This application has both Backend (SpringBoot having few APIs) and FrontEnd (UI)

Using this sample, you can learn about following technologies 

1. SpringBoot
2. MVC Architecture
3. Database concepts
4. APIs for CRUD operations
5. Hibernate
6. Thymeleaf for UI
7. CRUD Operations with ElasticSearch 
8. Utilities for String, File and JSON 


Software Used in this project:
1. Java 19
2. Spring Tool Suite 4 (STS4 in Eclipse IDE)
3. MySQL Server 8 (MySQL Workbench is required for creating DB) 
4. ElasticSearch 8.6.0

Optional Software for Dev Testing:
1. Chrome extension - "Multi ElasticSeach Head"
2. Postman app 

https://www.postman.com/downloads/

Download and Install JDK 19 from Orcale:
https://download.oracle.com/java/19/latest/jdk-19_windows-x64_bin.msi
(For installing other versions of Java, visit https://www.oracle.com/java/technologies/downloads)

"Spring Tools 4" can be plugged into Eclipse IDE, Visual Studio IDE and IntelliJ IDE. I prefer Eclipse IDE.

Download and Install Eclipse IDE
https://www.eclipse.org/downloads/download.php?file=/oomph/epp/2022-12/R/eclipse-inst-jre-win64.exe

Download and install "Spring Tools 4" from Eclipse Marketplace (Open Eclipse => Help menu => Eclipse Marketplace)
(Alternatively you can download "Spring Tools 4" jar file from https://spring.io/tools and manually import into Eclipse)

Download and Install MySQL Server and MySQL Workbench:
https://dev.mysql.com/downloads/installer/

Download and Install ElasticSearch 8.6.0 from https://www.elastic.co/guide/en/elasticsearch/reference/current/zip-windows.html
(Detailed steps for Installing ElasticSearch 8.6.0 is given in ReadMe-ElasticSearch.md file)


Download and Install "Multi ElasticSeach Head" Chrome extension from Chrome Webstore
Download and Install Postman App from https://www.postman.com/downloads/

==============================================================================================

*Steps to Setup this app in your machine*
1. Install above software in your machine
2. Download and Extract this sample project
3. Open Eclipse or other IDE (where you have installed STS 4) and Import this project (select the option 'Existing Maven project into workspace' in the wizard)
4. Open MySQL Workbench Open create a schema referencedb (or any name of your wish)
5. Open db folder of the this project and run the sql file in MySQL Workbench.  This will create 2 tables in the referencedb Database (Schema)
6. Right click in the project title in the STS and click on 'Build Path'=> 'Configure Build Path' and check if java version an classpath are correct
7. open application.properties file and give the right credentials (username, paasword, hostname, port and database name) for the MySQL  
8. Right click in the project title in the STS and click on 'Run As'=> 'Java Application' and then select the main class file "ReferenceApplication" in the popup window.
9. Wait for few seconds, Check in the console window if you get any error. If no errors then fine
10. To test the APIs, open any Rest Client call http://localhost:8080/users (GET method). If you get list of users in the result, it is working fine
11. To test the UI, Open http://localhost:8080/welcome in any browser

=========================================================================================================================

*Any questions, Contact Ahamed Kabeer: +91 9843527241 or hakabeer@gmail.com*

==========================================================================================================================

References:

Spring Start Project:
https://start.spring.io/

ES CRUD Example:
https://www.pixeltrice.com/spring-boot-elasticsearch-crud-example/


