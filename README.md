# Code 실행시 사전 설정 사항

## 1. http 또는 https 서비스 설정

-   application.yml active를 http로 설정하면 80포트의 http로 설정(Default)
    ```yml
    spring:
        profiles:
            active: local, http
    ```
-   application.yml active를 https로 설정하면 443포트의 https로 설정

    ```yml
    spring:
        profiles:
            active: local, https
    ```

-   이때는 https 사용할 ssl 인증서를 생성해야 함

    -   1.1 가상 인증서 발급법

        -   아래와 같이 shell에 명령 입력

        ```shell
        keytool -genkey -alias spring -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 4000
        ```

        ![image](./doc_img/local_ssl_key.png)

        -   MacOS 한글환경으로 인해 마지막 질문에 yes로 영문 입력시 오류 남
        -   한글로 <span style="color:red;font-weight:bold;">예</span>로 입력 영문은 경우는 <span style="color:red; font-weight:bold;">yes</span>입력

    -   1.2 Let's Encryption을 통해 무료 공개 인증서를 받는 법

        -   서비스를 할 서버와 도메인이 필요
        -   서비스를 할 서버에 접속 Docker를 사용 인증서를 발급 받음

        ```shell
        sudo docker run -it --rm --name certbot \
            -v '/etc/letsencrypt:/etc/letsencrypt' \
            -v '/var/lib/letsencrypt:/var/lib/letsencrypt' \
            certbot/certbot certonly -d '{보유한 도메인}' --manual --preferred-challenges dns --server https://acme-v02.api.letsencrypt.org/directory
        ```

        -   실행하면 아래와 같이 출력되며 중간 DNS txt값을 호스팅 업체에 설정해줘야 함
            ![image](./doc_img/letsencryption_ssl_key.png)

        -   Gabia 기준 : 타입은 TXT, 호스트는 \_acme-challenge (도메인주소는 뒤에 있어서 안붙임), 값/위치는 DNS txt값을 입력
            ![image](./doc_img/dns_setting.png)
        -   DNS 반영시간이 있기때문에 위 그림처럼 바로 Enter 누르면 안 됨

        (중략) <- 나중에 기록 1차 git upload

        -   생성된 pem 키를 변환

    -   2가지 방법중 keystore.p12로 생성된 파일 /src/main/resources 폴더 아래 복사
    -   application-https.yml 파일에 key-store-password에 키 생성시 비밀번호 입력
        ```yml
        server:
        port: 443
        ssl:
            enabled: true
            key-store: classpath:keystore.p12
            key-store-password: 123456
            key-store-type: PKCS12
            key-alias: spring
        ```
    -   key-alias는 생성 방법에 따라 아래 표처럼 생성 다름
        | 1.1 keytool로 임시키 생성 | 1.2 Let's Encryption 키 생성 |
        |-|-|
        |`key-alias: spring` | `key-alias: tomcat` |

---

## 2. Oauth2 사용 설정 필요(Kakao, Google등 OAuth2 제공자에서 OAuth2 애플리케이션을 생성해야 함)

-   아래는 Google Cloud의 API 서비스를 통해 프로젝트를 생성([Google OAuth2 문서](https://developers.google.com/identity/protocols/oauth2?hl=ko))
-   Code에는 application-oauth가 누락되어 있음
-   /src/main/resources 폴더 아래에 application-oauth.yml 아래와 같이 작성하여 추가해야 함

### application-oauth.yml 작성 방법

```yml
spring:
    security:
        oauth2:
            client:
                registration:
                    google:
                        client-id: #google client id
                        client-secret: #google client password
                        scope: profile,email
                        redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
                        authorization-grant-type: authorization_code
                provider:
                    google:
                        authorization-uri: https://accounts.google.com/o/oauth2/auth
                        token-uri: https://oauth2.googleapis.com/token
                        user-info-uri: https://www.googleapis.com/oauth2/v3/userinfo
```
