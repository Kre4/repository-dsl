create table student
(
    id   serial primary key,
    name varchar(128)
);

create table task_answers
(
    id           serial primary key,
    task_number  int not null,
    saved_answer text,
    student_id   int references student (id)
);
