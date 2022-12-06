alter table pill_ingredients
    drop key pi__name__uindex;

alter table pill_ingredients rename ingredients;

create unique index i__name__uindex
    on ingredients (name);

alter table ingredients
    add functional_content text;

alter table ingredients
    add precautions text;

alter table pills
    add image text;

alter table pills
    add price int;

