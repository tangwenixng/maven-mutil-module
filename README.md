## Maven构建多模块项目
---
### 父模块simple-parent

1、 simple-parent/pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.twx.multi</groupId>
    <artifactId>simple-parent</artifactId>
    <packaging>pom</packaging>
    <version>1.0</version>
    <name>Multi Chapter Simple Parent Project</name>

    <modules>
        <module>simple-weather</module>
        <module>simple-webapp</module>
    </modules>

    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```
 注意：父模块的packaging是```<packaging>pom</packaging>```
 依赖的junit会传递给子模块，所以子模块中不必再显示的依赖junit了。

2、 `<modules>`引用了接下来要创建的子模块`simple-weather`和`simple-webapp`

---

### 子模块simple-weather

1、 simple-weather/pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.twx.multi</groupId>
        <artifactId>simple-parent</artifactId>
        <version>1.0</version>
    </parent>
    <artifactId>simple-weather</artifactId>
    <packaging>jar</packaging>

    <name>Multi Chapter Simple Weather API</name>

    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <!-- 跳过测试 -->
                        <testFailureIgnore>true</testFailureIgnore>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>

    <dependencies>
        
    </dependencies>
</project>
```

注意：子模块的`parent`是指向父模块；另外，子模块的`packaging`变成了我们熟悉的jar

2、 在此模块下新建src/main/java/com/twx/weather/WeatherService.java
```java
package com.twx.weather;

public class WebService{

    public WebService(){}

    public String getWeather(){
        return "fine";
    }
}
```

---

### 子模块simple-webapp

1、 simple-webapp/pom.xml
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.twx.multi</groupId>
        <artifactId>simple-parent</artifactId>
        <version>1.0</version>
    </parent>

    <artifactId>simple-webapp</artifactId>
    <packaging>jar</packaging>
    <name>simple-webapp Maven Webapp</name>
    <dependencies>
        <dependency>
            <groupId>com.twx.multi</groupId>
            <artifactId>simple-weather</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
    <build>
        <finalName>simple-webapp</finalName>
    </build>
</project>
```

 注意：`parent`还是simple-parent；但是注意了，`dependency`依赖了子模块simple-weather。

2、 然后创建一个com.twx.webapp.WeatherCall类调用 子模块simple-weather中的WeatherService
```java
package com.twx.webapp;

import com.twx.weather.WeatherService;

public class WeatherCall{
    public static void main(String[] args){
        WeatherService service = new WeatherService();
        String str = service.getWeather();
        System.out.println(str);
    }
}
```

## 执行

在simple-parent目录下，输入`mvn clean package`,再target目录就可以看见生成好的jar包了