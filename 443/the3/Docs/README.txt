+ You can find the javadoc in Javadoc folder.
+ I used a different database to test the code, so you may need to modify the
    "driver", "url", "database", "username", "password" strings in the
    RMIImplementation class.
+ I provided the .jar for the database connector I used.
+ I am using java10 and it seems that the finalize() and Integer(int) has been
    deprecated so I used -Xlint:deprecation flag with javac.
    
    D:\Hakan\repos\ceng443\HW3\src>javac *.java
    Note: Some input files use or override a deprecated API.
    Note: Recompile with -Xlint:deprecation for details.
    
    D:\Hakan\repos\ceng443\HW3\src>javac *.java -Xlint:deprecation
    ResultTableModel.java:100: warning: [deprecation] Integer(int) in Integer has been deprecated
        return new Integer(serializableResult.updateReturnValue);
               ^
    RMIImplementation.java:97: warning: [deprecation] finalize() in Object has been deprecated
      protected void finalize() throws Throwable {
                     ^
    2 warnings

    D:\Hakan\repos\ceng443\HW3\src>
 
+ To run the programs I ran the following commands in the directory where the
    *.class files exist.
    
    > javac *.java -Xlint:deprecation
    > rmiregistry
    > java -cp .;mysql-connector-java-8.0.11.jar RMIServer
    > java RMIClient