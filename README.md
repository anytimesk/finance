/src/main/resources/application-oauth.yml 추가해야 정상 동작함

application-oauth.yml 작성 방법
spring:
    security:
        oauth2:
            client:
                registration:
                    google:
                        client-id: google client id
                        client-secret: google client password
                        scope: profile,email
                        redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
                        authorization-grant-type: authorization_code
                provider:
                    google:
                        authorization-uri: https://accounts.google.com/o/oauth2/auth
                        token-uri: https://oauth2.googleapis.com/token
                        user-info-uri: https://www.googleapis.com/oauth2/v3/userinfo
