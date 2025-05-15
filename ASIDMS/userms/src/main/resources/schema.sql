CREATE TABLE IF NOT EXISTS users (
  userid BIGINT NOT NULL AUTO_INCREMENT,
  email VARCHAR(255),
  fullname VARCHAR(255),
  shipping_order_id BIGINT,
  username VARCHAR(255),
  PRIMARY KEY (userid),
  CONSTRAINT UK6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
  CONSTRAINT UKr43af9ap4edm43mmtq01oddj6 UNIQUE (username)
);
