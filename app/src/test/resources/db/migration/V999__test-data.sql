insert into apartments(
    id,
    number_of_bedrooms,
    number_of_bathrooms,
    area,
    has_parking,
    price,
    description) VALUES (
                            'F1rsT-@p4rtm3nt', 2, 1, 60.4, false, 1580.00,
                            'This is the first apartment');

insert into apartments(
    id,
    number_of_bedrooms,
    number_of_bathrooms,
    area,
    has_parking,
    price,
    description) VALUES (
                            'S3c0nd-@p4rtm3nt', 3, 2, 72.8, true, 2300.00,
                            'This is the second apartment');

insert into apartments(
    id,
    number_of_bedrooms,
    number_of_bathrooms,
    area,
    has_parking,
    price,
    description) VALUES (
                            'Th1rd-@p4rtm3nt', 1, 1, 48.2, false, 1500.00,
                            'This is the third apartment');

insert into users(
    id,
    username,
    password,
    first_name,
    last_name,
    email,
    role) values (
                     1, 'bob_singer', '$2a$10$0sfPq654571FEISx5SA04.6bniLkuYpGEH8exYDu9KrKWv21xKw9m', 'Bob', 'Singer', 'bob_singer@mail.com', 'USER');

insert into users(
    id,
    username,
    password,
    first_name,
    last_name,
    email,
    role) values (
                     2, 'alana_brook', '$2a$10$0sfPq654571FEISx5SA04.6bniLkuYpGEH8exYDu9KrKWv21xKw9m', 'Alana', 'Brook', 'alana_brook@mail.com', 'ADMIN');

