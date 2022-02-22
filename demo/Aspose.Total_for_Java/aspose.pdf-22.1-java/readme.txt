
Aspose.PDF for Java, Generate and manipulate PDF documents
==========================================================================

This distribution contains the following directories:

- lib      : the jar, compiled and ready to use version for JDK 1.8 or higher.
           : aspose.pdf-<version>.jar

- javadoc  : the documentation in html format related to Aspose.PDF for Java.

- xml	   : an XSD and XML files showing Aspose.Pdf product structure

- license  : Aspose end user agreement for product


To get started using Aspose.PDF for Java, please visit: https://docs.aspose.com/pdf/java/getting-started/

If com.aspose.pdf.License.setLicense method works longer them 100ms notice the following:
The library used for random number generation in Oracle’s JVM relies on /dev/random by default for UNIX platforms.
Although /dev/random is more secure, it’s recommended to use /dev/urandom if the default JVM configuration have delays. 
Or add devices that generate entropy for /dev/random.

Open the $JAVA_HOME/jre/lib/security/java.security file in a text editor.
Change the line “securerandom.source=file:/dev/random” to read: securerandom.source=file:/dev/./urandom
Save your change and exit the text editor.
Or
Set up system property “java.security.egd” which will override the securerandom.source setting.
-Djava.security.egd=file:/dev/./urandom


Required Software and Libraries
===============================

Optional dependencies of this project:

<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
</dependency>

<!-- required for SoundAnnotation class in java-11 environment -->
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.0</version>
</dependency>

https://www.aspose.com/
© Aspose 2002-2022. All Rights Reserved.