/*Create admin user
Login: admin
Password: admin*/
INSERT INTO "users" VALUES (nextval('user_id_seq'), 'admin@admin.com', 'admin','$2a$10$pAyTZU8ABRw7WbX9.omZ6Oshxb.Ks7GvsJc.vtYZ67uZUurtPzD0q','admin', 'admin');
INSERT INTO "authority" VALUES (nextval('authority_id_seq'), currval('user_id_seq'),'ROLE_ADMIN');
