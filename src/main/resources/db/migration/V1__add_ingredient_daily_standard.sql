create table ingredient_daily_standard
(
    id             bigint auto_increment
        primary key,
    created_at     datetime        null,
    updated_at     datetime        null,
    maximum_amount decimal(34, 10) not null,
    minimum_amount decimal(34, 10) not null,
    unit           varchar(255)    not null,
    ingredient_id  bigint          not null,
    constraint ids__ingredient_id__fk
        foreign key (ingredient_id) references pill_ingredients (id)
);
