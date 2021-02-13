insert into public.users (id, email, password) values (10, 'mahmut@gmail.com', '$2a$10$4UKe.ThIL3oPGtmA4NIWpek8hMZs3oAxM2Cc/cooIvVrUz2Am6BFC');
insert into public.users (id, email, password) values (11, 'sinan@gmail.com', '$2a$10$TbOq3MIYvBPhm9Dqsi3yHOc5n6gBgPaLaHtw7ugaK1D3pygwLTv2y');
insert into public.users (id, email, password) values (12, 'aydin@gmail.com', '$2a$10$Fb08cTAe1XRa7brON3FTw.OAy/rH4iyyLBP7D6Sn6xSO.9mz7/fOe');


insert into public.todos (id, user_id, date, title) values (4, 10, '2021-06-22 00:00:00.000000', 'Title  - 1 -1');
insert into public.todos (id, user_id, date, title) values (5, 10, '2021-03-22 20:11:00.000000', 'Title  - 2 - 2');
insert into public.todos (id, user_id, date, title) values (6, 10, '2021-03-24 20:11:00.000000', 'Title  - 3 - 3');

insert into public.todos (id, user_id, date, title) values (7, 12, '2021-06-22 00:00:00.000000', 'Title  - 1');
insert into public.todos (id, user_id, date, title) values (8, 12, '2021-03-22 20:11:00.000000', 'Title  - 2');
insert into public.todos (id, user_id, date, title) values (9, 12, '2021-03-24 20:11:00.000000', 'Title  - 3');