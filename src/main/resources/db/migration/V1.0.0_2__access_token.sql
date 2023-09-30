CREATE TABLE "public"."access_token"
(
    "id"           varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "user_id"      varchar COLLATE "pg_catalog"."default"      NOT NULL,
    "access_token" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

CREATE INDEX "access_token_idx" ON "public"."access_token" USING btree (
    "access_token" COLLATE "pg_catalog"."default"
    "pg_catalog"."text_ops" ASC NULLS LAST
    );
