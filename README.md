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

### 五、用法

请参见 ChinaTrust 接口文档。

### 六、参数解释

- **version: 版本**

* **reference: BSN 泰安链上生成的 DDC**
* **issuanceDate: 签发日期**
* **issuer: 签发人**
* **type: 类型**
* **credentialSubject: 证书**
* **metadata: 元数据**
* **privateKey: 私钥**
* **publicKey: 公钥**
* **didClient: did 客户端**
* **ddcClient: ddc 客户端**
* **tx: 交易哈希**
* **chainAccAddress: 链账户地址**

### 七、使用场景

使用场景举例一：供应链金融

- **背景**
- 对于大宗商品买卖时，中小企业往往没有足够的资金进行交易。需要向银行贷款，但却不容易提供可信的供应链数据给银行。同时需要多个参与方协同和凭证流转。
- **参与方**
  -* 买家
  -* 平台方
  -* 生产厂家
  -* 云仓
  -* 银行
- **解决方案及基本流程**
- 1、平台方在 DDC 官网https://ddc.bsnbase.com注册账号。并为买家、自己、云仓和银行创建泰安链的链账户。
- 2、买家在商品采购平台上以仓单质押融资的方式购买平台方的商品，下单后生成 2 个订单，买家和平台方的买卖订单、平台方和生产厂家的采购订单。平台方与生产厂
  家签约采购合同，然后买家和平台方签约商品合同。2 份合同生效后，买家预支付平台方 30%的货款，收到货款后平台方预支付生产厂家 30%的采购款。生产厂家收到预
  支付的采购款后发货，平台方收到货后存入云仓，这时使用文档签发接口生成仓单凭证。平台方使用文档移交接口将仓单凭证转移给银行，银行使用文档验证接口验证
  仓单凭证有效后放款。买家收到贷款后支付余款给平台方，平台方收到钱后支付生产厂家的余款。
- 3、买家给银行还款，银行确认收到还款后，平台方使用文档签发接口为买家生成提货单凭证，使用文档移交接口将提货单转移给云仓。平台方用提货单去云仓提货，云
  仓使用文档验证接口验证提货单有效后放货。货提走后云仓使用文档销毁接口销毁提货单。贷款全部还完后，银行使用文档销毁接口销毁仓单。

使用场景举例二：商家开发票

- **背景**
- 对于开数字发票的业务，缺乏可信可靠可验证的数据来支撑业务流程，同时需要多个参与方协同和凭证流转。
- **参与方**
  -* 税务局
  -* 商家
  -* 消费企业或个人
  -* 报销单位
- **解决方案及基本流程**
- 1、税务局在 DDC 官网https://ddc.bsnbase.com注册账号。并为自己和商家创建泰安链的链账户。
- 2、企业或个人在商家消费后，税务局使用文档签发接口为商家生成发票凭证，使用文档转移接口将发票转移给商家，商家将发票提供给企业消费企业或个人。
- 3、消费企业或个人将发票提供给报错单位进行报错，报错单位使用文档验证接口进行发票的有效性验证，最终提交给税务局进行抵税。税务局使用文档验证接口进行发
  票的有效性验证，商家使用文档转移接口将发票转移给税务局，税务完成相关业务后使用文档销毁接口对发票进行销毁。
