CREATE TABLE "users" (
                        id                      int8                NOT NULL UNIQUE,
                        email                   TEXT                NOT NULL UNIQUE,
                        username                TEXT                NOT NULL UNIQUE,
                        password                TEXT                NOT NULL,
                        first_name              TEXT                NOT NULL,
                        last_name               TEXT,
                        enabled                 BOOLEAN             NOT NULL DEFAULT TRUE, -- включен ли аккаунт
                        account_non_expired     BOOLEAN             NOT NULL DEFAULT TRUE, -- время действия аккаунта не истекло
                        account_non_locked      BOOLEAN             NOT NULL DEFAULT TRUE, -- аккаунт не заблокирован
                        credentials_non_expired BOOLEAN             NOT NULL DEFAULT TRUE,  -- не истекло ли время действия пароля
                        CONSTRAINT user_pk PRIMARY KEY (id)
) WITH (
      OIDS=FALSE
    );

CREATE SEQUENCE "user_id_seq" START 1;

CREATE TABLE  "authority" (
                        id        int8,
                        user_id   int8  NOT NULL,
                        authority TEXT  NOT NULL,
                        CONSTRAINT authority_pk    PRIMARY KEY (id)
) WITH (
      OIDS=FALSE
    );

CREATE SEQUENCE "authority_id_seq" START 1;
ALTER TABLE "authority" ADD CONSTRAINT authority_fk_user FOREIGN KEY (user_id) REFERENCES "users"(id);
