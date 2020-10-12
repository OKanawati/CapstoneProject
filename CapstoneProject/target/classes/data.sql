INSERT INTO Role (rolename) VALUES ('ROLE_CUSTOMER');
INSERT INTO Role (rolename) VALUES ('ROLE_OWNER');

INSERT INTO User (first_Name, last_Name, email, encrypted_Password, phone_Number, address, enabled, dtype) 
	VALUES ('Greg', 'Jenkins', 'jenkins@gmail.com', '$2a$10$myb1kXeBRa0.j7pwf/Go5.ZTLqBDUjtwNzFLr1tRB93Dctc8zaM8y',
	'905-784-4532', '123 Test Street', 1, 'Owner');
	
INSERT INTO user_roles (users_id, roles_id) VALUES (1, 2);

INSERT INTO Shop (name, phone_number, address, brands, owner_id)
	VALUES('Big Fix', '1234567843', '123 Test Street', 'iPhone,Samsung', 1);
	
INSERT INTO Shop (name, phone_number, address, brands, owner_id)
	VALUES('Fix it Up', '345-453-3453', '231 Test Drive', 'LG,Samsung', 1);
	
INSERT INTO user_shop_list (owner_id, shop_list_id) VALUES (1, 1);
INSERT INTO user_shop_list (owner_id, shop_list_id) VALUES (1, 2);
	
