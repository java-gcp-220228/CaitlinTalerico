insert into reimbursement_status (REIMB_STATUS_ID, REIMB_STATUS)
values
(101, 'Approved'),
(202, 'Pending'),
(303, 'Rejected');

insert into reimbursement_type (REIMB_TYPE_ID, REIMB_TYPE)
values
(10, 'Lodging'),
(20, 'Travel'),
(30, 'Food'),
(40, 'Other');

insert into user_roles (user_role_id, user_role)
values
(100, 'Management'),
(200, 'Finance'),
(300, 'HR'),
(400, 'IT'),
(500, 'Marketing'),
(600, 'Sales'),
(700, 'Quality Assurance');

INSERT INTO users (username, user_password, first_name, last_name, user_email, user_role_id)
values
('CatMom1', 'ilovemycats!', 'Angela', 'Martin', 'angela_martin1@dundermifflen.net', 200),
('oscar_m', '04jgi87uy', 'Oscar', 'Martinez', 'oscar12@dundermifflen.net', 200),
('cookie_monster', 'password', 'Kevin', 'Malone', 'kevin32@dundermifflin.net', 200),
('costa_toby', 'costarica2022', 'Toby', 'Flenderson', 'toby_flenderson@dundermiffin.com', 300),
('it_guy', 'P@55word', 'Nick', 'Glasseu', 'nick11@dundermifflin.net', 400),
('hip_hop', 'it4life', 'Sadiq', 'Vaidya', 'sadiq@dundermifflin.com', 400),
('PrincessKelly', 'kellyandryan', 'Kelly', 'Kapor', 'cute_kelly@dundermifflin.com', 500),
('BeetFarmer', 'battleStarGalactica1', 'Dwight', 'Schrute', 'dwight_schrute@dundermifflin.net', 600),
('jim_hal', 'P@mB33sly', 'Jim', 'Halpert', 'jim_halpert@dundermifflin.com', 600),
('not_creed', 'notapassword', 'Creed', 'Bratton', 'creed_bratt@dundermifflin.com', 700),
('number1boss', '12345', 'Michael', 'Scott', 'michael_scott@dundermifflin.net', 100);


insert into reimbursements 





