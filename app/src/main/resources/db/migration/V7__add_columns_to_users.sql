ALTER TABLE users ADD COLUMN first_name text NOT NULL default '';
ALTER TABLE users ADD COLUMN last_name text NOT NULL default '';
ALTER TABLE users ADD COLUMN email text NOT NULL default '';
ALTER TABLE users ADD COLUMN role text NOT NULL default 'USER';
