INSERT INTO Role (rolename) VALUES ('ROLE_CUSTOMER');
INSERT INTO Role (rolename) VALUES ('ROLE_OWNER');

INSERT INTO User (first_Name, last_Name, email, encrypted_Password, phone_Number, 
street, city, province, postal, enabled, dtype) 
	VALUES ('Greg', 'Jenkins', 'jenkins@gmail.com', '$2a$10$myb1kXeBRa0.j7pwf/Go5.ZTLqBDUjtwNzFLr1tRB93Dctc8zaM8y',
	'905-784-4532', '123 Test Street', 'Oakville', 'ON', 'L4T H73', 1, 'Owner');
	
INSERT INTO user_roles (users_id, roles_id) VALUES (1, 2);

INSERT INTO Brand(brand_Name) VALUES ('IPHONE');
INSERT INTO Brand(brand_Name) VALUES ('SAMSUNG');
INSERT INTO Brand(brand_Name) VALUES ('LG');

INSERT INTO Shop (name, phone_number, street, city, province, postal, owner_id)
	VALUES('Sheridan Computers', '(905) 567-7774', '5602 Tenth Line W #122', 'Mississauga', 'ON' ,
	'L5M 7L9', 1);
	
INSERT INTO Shop (name, phone_number, street, city, province, postal, owner_id)
	VALUES('Comet Squared', '(647) 542-6638', '675 The Chase #21', 'Mississauga', 'ON',
	'L5M 5Y7', 1);
	
INSERT INTO Shop (name, phone_number, street, city, province, postal, owner_id)
	VALUES ('PcNcell', '(905) 624-0297', '243 Queen St S', 'Mississauga', 'ON',
	'L5M 1L7', 1);
	
INSERT INTO shop_brands(shops_id, brands_id) VALUES (1, 1);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (1, 2);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (2, 2);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (2, 3);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (3, 2);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (3, 3);

INSERT INTO user_shop_list (owner_id, shop_list_id) VALUES (1, 1);
INSERT INTO user_shop_list (owner_id, shop_list_id) VALUES (1, 2);
INSERT INTO user_shop_list (owner_id, shop_list_id) VALUES (1, 3);
	
