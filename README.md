### 一、介绍

ChinaTrust 供众多有凭证场景的业务系统集成的中间件，辅助其业务系统结合区块链技术向数字信息化转型，提高业务在各环节的执行效率，保障凭证数据在各环节流转过程中的信息安全。
ChinaTrust 旨在定义一套标准的中间件供更多的行业应用使用，助力于传统的业务平台结合区块链技术向数字信息化转型，提高业务在各环节的执行效率，保证数据在各个环节的流转过程中的安全，减少业务对接过程。

### 二、使用说明

ChinaTrust 的核心代码都在 SDK（Java 语言开发） 内，为了便于不同语言开发的业务平台集成，所以我们对 SDK 进行了一次包装输出了相应的一些 API，业务平台如果也是 Java 语言实现，则建议内部直接集成 SDK；如果是其它语言实现，可以通过调用 API 的方式进行快速接入。

### 三、准备事项

- **安装 Java 1.8 or later**

* **构建 mvn clean install -Dmaven.test.skip=true**

* **启动 java -jar china-trust-server-0.0.1.jar**

### 四、依赖配置

```
      <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>okhttp</artifactId>
            <version>4.9.0</version>
        </dependency>
        <dependency>
            <groupId>com.squareup.okhttp3</groupId>
            <artifactId>logging-interceptor</artifactId>
            <version>4.9.0</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>

        <dependency>
            <groupId>com.reddate</groupId>
            <artifactId>china-trust-sdk</artifactId>
            <version>0.0.1</version>
            <exclusions>
                <exclusion>
                    <artifactId>slf4j-log4j12</artifactId>
                    <groupId>org.slf4j</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.reddate</groupId>
            <artifactId>ddc.did.sdk</artifactId>
            <version>1.0</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.fisco-bcos</groupId>
                    <artifactId>web3sdk</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.reddate.ddc</groupId>
            <artifactId>ddc-sdk-taian</artifactId>
            <version>1.0-SNAPSHOT</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>net.sf.oval</groupId>
            <artifactId>oval</artifactId>
            <version>${oval.version}</version>
        </dependency>
        <dependency>
            <groupId>org.fisco.bcos</groupId>
            <artifactId>web3sdk</artifactId>
            <version>2.4.0</version>
            <scope>system</scope>
            <systemPath>${pom.basedir}/libs/web3sdk.jar</systemPath>
        </dependency>
```
