-- ----------------------------
-- Table structure for user
-- ----------------------------

CREATE TABLE "public"."user"
(
    "id"           varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "phone_number" varchar(50) COLLATE "pg_catalog"."default"  NOT NULL,
    "password"     varchar(100) COLLATE "pg_catalog"."default" NOT NULL
)
;

COMMENT
ON COLUMN "public"."user"."id" IS '用户ID';
COMMENT
ON COLUMN "public"."user"."password" IS '密码';
COMMENT
ON COLUMN "public"."user"."phone_number" IS '手机号码';
COMMENT
ON TABLE "public"."user" IS '用户信息表';

CREATE INDEX "user_mobile_idx" ON "public"."user" USING btree (
    "phone_number" COLLATE "pg_catalog"."default"
    "pg_catalog"."text_ops" ASC NULLS LAST
    );
ALTER TABLE "public"."user"
    ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");

CREATE TABLE "public"."reserve_info"
(
    "id"                  varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "booker"              varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "booker_phone_number" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "reserve_date"        date                                        NOT NULL,
    "time_slots"          varchar COLLATE "pg_catalog"."default"      NOT NULL,
    "tags"                varchar COLLATE "pg_catalog"."default",
    "room_name"           varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
    "price_per_person"    int8,
    "diner_count"         int4,
    "waitstaff_count"     int4,
    "comment"             varchar(255) COLLATE "pg_catalog"."default",
    "extra_details"       varchar COLLATE "pg_catalog"."default"
)
;

ALTER TABLE "public"."reserve_info"
    ADD CONSTRAINT "reserve_info_pkey" PRIMARY KEY ("id");
