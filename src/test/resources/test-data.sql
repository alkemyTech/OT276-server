insert into `role`(role_id, name, description, created_at, is_active) values (1, 'ROLE_ADMIN', 'Role for administrators' , CURRENT_DATE(), 1);
insert into `role`(role_id, name, description, created_at, is_active) values (2, 'ROLE_USER', 'Role for regular users' , CURRENT_DATE(), 1);

insert into `user` (user_id, first_name, last_name, email, password, photo, role_id, is_active, created_at) values (1, 'Admin', 'Admin', 'admin@somosmas.org', '$2a$10$Bz9kmnLhJb.xWc78LNymcugT0//TZRzNedtXgnfMWyYPVwuBcNlIG', 'https://static-url.com/avatar.jpg', 1, true, CURRENT_DATE());
insert into `user` (user_id, first_name, last_name, email, password, photo, role_id, is_active, created_at) values (2, 'John', 'Doe', 'jdoe@somosmas.org', '$2a$10$npmFpCbBFdtXjGGhgc1ste1quwGaF4MvkgYk9N2rBDFkOrV5Lj6M6', 'https://static-url.com/avatar.jpg', 2, true, CURRENT_DATE());
insert into `user` (user_id, first_name, last_name, email, password, photo, role_id, is_active, created_at) values (3, 'Jane', 'Smith', 'jsmmith@somosmas.org', '$2a$10$npmFpCbBFdtXjGGhgc1ste1quwGaF4MvkgYk9N2rBDFkOrV5Lj6M6', 'https://static-url.com/avatar.jpg', 2, true, CURRENT_DATE());

insert into organization(organization_id, name, image, address, phone, email, welcome_text, about_us_text, facebook, linkedin, instagram, created_at, is_active, contact_text)
values (1,
        'Somos Mas',
        'https://cohorte-agosto-38d749a7.s3.amazonaws.com/1661519321851-LOGO-SOMOS_MAS.png',
        'La Cava',
        1160112988,
        'somosfundacionmas@gmail.com',
        '¡Bienvenido a ONG Somos Más! Trabajamos para transformar la realidad de los que más lo necesitan.',
        'Desde 1997 en Somos Más trabajamos con los chicos y chicas,
        mamás y papás, abuelos y vecinos del barrio La Cava generando
        procesos de crecimiento y de inserción social. Uniendo las manos de
        todas las familias, las que viven en el barrio y las que viven fuera de
        él, es que podemos pensar, crear y garantizar estos procesos. Somos
        una asociación civil sin fines de lucro que se creó en 1997 con la
        intención de dar alimento a las familias del barrio. Con el tiempo
        fuimos involucrándonos con la comunidad y agrandando y mejorando
        nuestra capacidad de trabajo. Hoy somos un centro comunitario que
        acompaña a más de 700 personas a través de las áreas de educación, deportes, primera infancia, salud, alimentación y trabajo
        social.',
        'Somos_Mas',
        'Somos Mas',
        'SomosMas',
        CURRENT_DATE(),
        1,
        '¡Gracias por contactarte con Somos Más! A la brevedad vamos a estar respondiendo a tu consulta.');

insert into category(category_id, name, description, image, created_at, is_active) values(1,'politica','for test','foo',CURRENT_DATE(),1);

insert into slide (slide_id, image_url, text, slide_order, organization_id, is_active, created_at) values(1, 'image url', 'text', 1, 1, 1, CURRENT_DATE());
