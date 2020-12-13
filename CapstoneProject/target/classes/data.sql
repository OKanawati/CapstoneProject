--Omar Kanawati and Thomas Forber

INSERT INTO Role (rolename) VALUES ('ROLE_CUSTOMER');
INSERT INTO Role (rolename) VALUES ('ROLE_OWNER');

INSERT INTO User (first_Name, last_Name, email, encrypted_Password, phone_Number, 
street, city, province, postal, enabled, dtype) 
	VALUES ('Greg', 'Jenkins', 'jenkins@gmail.com', '$2a$10$myb1kXeBRa0.j7pwf/Go5.ZTLqBDUjtwNzFLr1tRB93Dctc8zaM8y',
	'905-784-4532', '123 Test Street', 'Oakville', 'ON', 'L4T H73', 1, 'Owner');

INSERT INTO User (first_Name, last_Name, email, encrypted_Password, phone_Number, 
street, city, province, postal, enabled, dtype) 
	VALUES('Barbara', 'Thompson', 'bthompson@yahoo.com', '$2a$10$YXFQuoPD9VXegC9IvYaUKuBN/gor7FTNSj9BEMMYAcg5JO0TCJPJ.',
	'654-457-3553', '64 Dummy Road', 'Oakville', 'ON', 'L3N8T2', 1, 'Owner');
	
	
INSERT INTO user_roles (users_id, roles_id) VALUES (1, 2);
INSERT INTO user_roles (users_id, roles_id) VALUES (2, 2);


INSERT INTO Brand (id, brand_name) VALUES (1, 'ASUS');
INSERT INTO Brand (id, brand_name) VALUES (2, 'BLACKBERRY');
INSERT INTO Brand (id, brand_name) VALUES (3, 'ESSENTIAL PRODUCTS');
INSERT INTO Brand (id, brand_name) VALUES (4, 'HTC');
INSERT INTO Brand (id, brand_name) VALUES (5, 'HTC/GOOGLE');
INSERT INTO Brand (id, brand_name) VALUES (6, 'HUAWEI');
INSERT INTO Brand (id, brand_name) VALUES (7, 'HUAWEI/GOOGLE');
INSERT INTO Brand (id, brand_name) VALUES (8, 'LENOVO');
INSERT INTO Brand (id, brand_name) VALUES (9, 'LG');
INSERT INTO Brand (id, brand_name) VALUES (10, 'LG/GOOGLE');
INSERT INTO Brand (id, brand_name) VALUES (11, 'MEIZU');
INSERT INTO Brand (id, brand_name) VALUES (12, 'MOTOROLA');
INSERT INTO Brand (id, brand_name) VALUES (13, 'MOTOROLA/GOOGLE');
INSERT INTO Brand (id, brand_name) VALUES (14, 'NEXTBIT');
INSERT INTO Brand (id, brand_name) VALUES (15, 'MICROSOFT');
INSERT INTO Brand (id, brand_name) VALUES (16, 'HMD GLOBAL');
INSERT INTO Brand (id, brand_name) VALUES (17, 'NOKIA');
INSERT INTO Brand (id, brand_name) VALUES (18, 'ONEPLUS');
INSERT INTO Brand (id, brand_name) VALUES (19, 'OPPO');
INSERT INTO Brand (id, brand_name) VALUES (20, 'PANASONIC');
INSERT INTO Brand (id, brand_name) VALUES (21, 'GOOGLE');
INSERT INTO Brand (id, brand_name) VALUES (22, 'SAMSUNG');
INSERT INTO Brand (id, brand_name) VALUES (23, 'SAMSUNG/GOOGLE');
INSERT INTO Brand (id, brand_name) VALUES (24, 'RAZER');
INSERT INTO Brand (id, brand_name) VALUES (25, 'REALME');
INSERT INTO Brand (id, brand_name) VALUES (26, 'XIAOMI');
INSERT INTO Brand (id, brand_name) VALUES (27, 'SONY ERICSSON');
INSERT INTO Brand (id, brand_name) VALUES (28, 'SONY MOBILE');
INSERT INTO Brand (id, brand_name) VALUES (29, 'TCL');
INSERT INTO Brand (id, brand_name) VALUES (30, 'TECNO MOBILE');
INSERT INTO Brand (id, brand_name) VALUES (31, 'VIVO');
INSERT INTO Brand (id, brand_name) VALUES (32, 'ZTE');
INSERT INTO Brand (id, brand_name) VALUES (33, 'IPHONE');



INSERT INTO Shop (name, phone_number, street, city, province, postal, owner_id)
	VALUES('Comet Squared', '(647) 542-6638', '675 The Chase #21', 'Mississauga', 'ON',
	'L5M 5Y7', 1);
	
INSERT INTO Shop (name, phone_number, street, city, province, postal, owner_id)
	VALUES('Sheridan Computers', '(905) 567-7774', '5602 Tenth Line W #122', 'Mississauga', 'ON' ,
	'L5M 7L9', 2);
	
INSERT INTO Shop (name, phone_number, street, city, province, postal, owner_id)
	VALUES ('PcNcell', '(905) 624-0297', '243 Queen St S', 'Mississauga', 'ON',
	'L5M 1L7', 1);
	
INSERT INTO shop_brands(shops_id, brands_id) VALUES (1, 33);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (1, 22);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (2, 22);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (2, 9);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (3, 22);
INSERT INTO shop_brands(shops_id, brands_id) VALUES (3, 9);

INSERT INTO user_shop_list (owner_id, shop_list_id) VALUES (1, 1);
INSERT INTO user_shop_list (owner_id, shop_list_id) VALUES (2, 2);
INSERT INTO user_shop_list (owner_id, shop_list_id) VALUES (1, 3);

INSERT INTO Appointment (appointment_key, cust_email, cust_first_name, cust_last_name, date, time, device_brand, service_details,
status, shop_id)
	VALUES('USRHDAM0YH', 'west@gmail.com', 'Frank', 'West', '2020/11/3', '12:00', 'SAMSUNG', 
	'Screen is cracked. Battery will not charge.', 'COMPLETED', 2),
	('HY0MADHRSU', 'jhill@outlook.com', 'John', 'Hill', '2020/11/9', '9:00', 'LG',
	'Does not turn on.', 'REQUESTED', 2);

INSERT INTO shop_appointments (shop_id, appointments_id) VALUES (2, 1), (2, 2);
