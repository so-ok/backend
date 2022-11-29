create table attentions
(
    id         bigint auto_increment
        primary key,
    created_at datetime     null,
    updated_at datetime     null,
    name       varchar(255) null,
    constraint a__name__uindex
        unique (name)
);

create table pill_ingredients
(
    id           bigint auto_increment
        primary key,
    created_at   datetime     null,
    updated_at   datetime     null,
    default_unit varchar(255) null,
    name         varchar(255) null,
    constraint pi__name__uindex
        unique (name)
);

create table attention_ingredient_mapping
(
    id            bigint auto_increment
        primary key,
    created_at    datetime null,
    updated_at    datetime null,
    attention_id  bigint   null,
    ingredient_id bigint   null,
    constraint aim__attention_id__fk
        foreign key (attention_id) references attentions (id),
    constraint aim__ingredient_id__fk
        foreign key (ingredient_id) references pill_ingredients (id)
);

create table pills
(
    id         bigint auto_increment
        primary key,
    created_at datetime     null,
    updated_at datetime     null,
    name       varchar(255) null
);

create table attention_pill_mapping
(
    id           bigint auto_increment
        primary key,
    created_at   datetime null,
    updated_at   datetime null,
    attention_id bigint   null,
    pill_id      bigint   null,
    constraint apm__pill_id__fk
        foreign key (pill_id) references pills (id),
    constraint apm__attention_id__fk
        foreign key (attention_id) references attentions (id)
);

create table pill_ingredient_mapping
(
    id            bigint auto_increment
        primary key,
    created_at    datetime        null,
    updated_at    datetime        null,
    amount        decimal(34, 10) null,
    unit          varchar(255)    null,
    ingredient_id bigint          null,
    pill_id       bigint          null,
    constraint pim__pi_id__fk
        foreign key (pill_id) references pills (id),
    constraint pim__ingredient_id__fk
        foreign key (ingredient_id) references pill_ingredients (id)
);

create table sook_user
(
    id             bigint auto_increment
        primary key,
    created_at     datetime     null,
    updated_at     datetime     null,
    email          varchar(255) not null,
    enabled        bit          not null,
    image          varchar(255) null,
    last_login     datetime     null,
    oauth_provider varchar(255) not null,
    password       varchar(255) null,
    user_id        varchar(255) not null,
    username       varchar(255) not null,
    constraint sook_user__user_id__uindex
        unique (user_id),
    constraint sook_user__email__uindex
        unique (email),
    constraint sook_user__user_id__and__oauth_provider__uindex
        unique (user_id, oauth_provider)
);

create index sook_user_username_index
    on sook_user (username);

create table sook_user_roles
(
    user_id bigint       not null,
    roles   varchar(255) null,
    constraint sook_user_roles__user_id__fk
        foreign key (user_id) references sook_user (id)
);
