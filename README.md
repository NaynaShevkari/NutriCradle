# NutriCradle

## Prerequisites
Softwares, libraries, and tools needed to run NutriCradle application:

Java: Ensure you have Java installed (recommend Java 8 or newer).
MySQL Server: Required for the database. Download MySQL
MySQL Connector/J: JDBC driver for MySQL. Dependency added in pom.xml file. Make sure you copy it while copying the project files into Intellij project.
JFreeChart: Used for generating charts. Dependency added in pom.xml file.
IntelliJ IDEA: For code compilation and execution.
Installation Directories.

Use default installation directories if you are downloading any of the above.

Download the zip file from canvas for MySQL dump and project source code.

Setting Up the Development Environment
Java Installation (Skip this step if Java is already installed): 
Install Java and set up the JAVA_HOME environment variable.

## Database Setup:
Install MySQL Server.
Create a database using the provided SQL dump file (nutritiontrackingdb.sql).
Ensure the MySQL service is running.

## IDE Setup:
Open IntelliJ IDEA.
Create new maven project with name 'NutriCradle'. Import the project filese by copying the src and pom.xml from the downloaded file. Make sure that, pom.xml is at the same level as src file.

## Running the Application
Compile the Project:
In IntelliJ IDEA, rebuild the project via Build > Rebuild Project.
Run the Application:
Navigate to the Main.java file, right-click on it, and select Run 'Main.main()'.
Ensure that your database server is running before launching the application.

GUI window will open to enter MySQL url, username, password to connect to the MySQL.
For MySQL url you can follow below steps:
Open MySQL workbench-> go to Home(icon in the top left corner) -> you can see the username, hostname and port number for the connection.
prepare the link by replacing hostname and portnumber.

jdbc:mysql://<hostname>:<port number>/nutritiontrackingdb.

This will extablish the connection between MySQL and JAVA.
After successful connection, a main login window will appear. Three types of users can use this application - admin, premium user and standard user.
The functionality vary as per the user. Below are existing credentials which you can use for login:

username: admin, password: aadmin -> administrator login
username: AliceSmith, password: alice123 -> premium user
username: johndoe, password: john123 -> standard user.

## Library Dependencies:
MySQL Connector/J(com.mysql:mysql-connector-j:8.3.0): JDBC driver for MySQL.
JFreeChart(org.jfree:jfreechart1.5.4 and org.jfree:jcommon:1.0.24): Used for generating charts. 


## Troubleshooting:
If any issues with external library like jfreechart, please make sure the project name matches with  <artifactId>project name </artifactId> in pom.xml file.