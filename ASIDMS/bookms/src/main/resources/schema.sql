CREATE TABLE IF NOT EXISTS author (
  authorid BIGINT AUTO_INCREMENT PRIMARY KEY,
  author_name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS category (
  categoryid BIGINT AUTO_INCREMENT PRIMARY KEY,
  category_name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS subcategory (
  subcategoryid BIGINT AUTO_INCREMENT PRIMARY KEY,
  subcategory_name VARCHAR(255) UNIQUE,
  category_id BIGINT,
  CONSTRAINT fk_subcategory_category FOREIGN KEY (category_id) REFERENCES category(categoryid)
);

CREATE TABLE IF NOT EXISTS book (
  bookid BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255),
  price DECIMAL(10, 2),
  stock INT,
  author_id BIGINT,
  subcategory_id BIGINT,
  CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author(authorid),
  CONSTRAINT fk_book_subcategory FOREIGN KEY (subcategory_id) REFERENCES subcategory(subcategoryid)
);
