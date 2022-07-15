-- password: admin
  INSERT INTO users (email, first_name, last_name, password, role, status)
  VALUES('admin@gmail.com', 'admin', 'Adminov', '$2a$12$9Ie8rDcJa.Puj6KZxPvdie.ENjI45A/B5L2PdVTvBOaZJU.W/INPq', 'ADMIN', 'ACTIVE');
-- password: user
  INSERT INTO users (email, first_name, last_name, password, role, status)
  VALUES('user@gmail.com', 'user', 'Userov', '$2a$12$btz8gudaSANZHIXTDNJXX.EAKu3mDMX.h76dnwgOIRIXT5G0ZneSC', 'USER', 'ACTIVE');
-- password: banned
  INSERT INTO users (email, first_name, last_name, password, role, status)
  VALUES('banned@gmail.com', 'banned', 'Banned', '$2a$12$jIalC7Xnh0LIkwST4bGA2.eiGnJR67SXEK8mQ/rS3Eca/zdNy8xkC', 'USER', 'BANNED');
