CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    levels VARCHAR

);

CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(255),
    product_s_desc VARCHAR(512),
    product_l_desc TEXT,
    product_actual_price INT ,
    product_sale_price   INT ,
    product_images VARCHAR(255),
    seller_id TEXT,
    quantity INT,
    order_count INT,
    color VARCHAR(255),
    brand VARCHAR(255),
    first_available DATE NOT NULL,
    created_date DATE NOT NULL,
    category_id INT REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS ecom_users (
    id SERIAL PRIMARY KEY,
    ecom_username VARCHAR(255),
    ecom_password VARCHAR(255),
    ecom_role VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS addresses (
    id SERIAL PRIMARY KEY,
    hno INT,
    line1 VARCHAR(255),
    line2 VARCHAR(255),
    city VARCHAR(255),
    address_state VARCHAR(255),
    country VARCHAR(255),
    pincode VARCHAR(255),
    phone VARCHAR(255),
    ecom_user INT REFERENCES ecom_users(id)
);

CREATE TABLE IF NOT EXISTS paymentinfos (
    id SERIAL PRIMARY KEY,
    card_name VARCHAR(255),
    card_no VARCHAR(255),
    card_cvv VARCHAR(255),
    card_doe VARCHAR(255),
    ecom_user INT REFERENCES ecom_users(id)
);

CREATE TABLE IF NOT EXISTS carts (
    id SERIAL PRIMARY KEY,
    products TEXT 
);

CREATE TABLE IF NOT EXISTS sellers (
    id SERIAL PRIMARY KEY,
    seller_name VARCHAR(255),
    seller_tin VARCHAR(255),
    seller_rating VARCHAR(20),
    seller_address VARCHAR,
    seller_phone VARCHAR(20),
    seller_email VARCHAR(255),
    seller_doj DATE NOT NULL,
    seller_status VARCHAR(20),
    seller_profile_pic VARCHAR(255),
    ecom_user INT REFERENCES ecom_users(id)
);

CREATE TABLE IF NOT EXISTS buyers (
    id SERIAL PRIMARY KEY,
    buyer_name VARCHAR(255),
    buyer_phone VARCHAR(20),
    buyer_email VARCHAR(255),
    buyer_payment_options VARCHAR,
    buyer_addresses VARCHAR,
    buyer_doj DATE NOT NULL,
    buyer_status VARCHAR(20),
    buyer_rating VARCHAR(20),
    buyer_profile_pic VARCHAR(255),
    ecom_user INT REFERENCES ecom_users(id) 
);



CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    ecom_user INT REFERENCES ecom_users(id),
    product_ids TEXT,
    price INT,
    mode_of_payment VARCHAR(255),
    date_of_order DATE NOT NULL,
    shipping_address VARCHAR,
    date_of_shipment DATE,
    date_of_delivery DATE,
    order_status VARCHAR(255),
    tracking_number VARCHAR(255)  
);


CREATE TABLE IF NOT EXISTS deals (
    id SERIAL PRIMARY KEY,
    deal_name TEXT,
    deal_desc TEXT,
    deal_url TEXT,
    deal_products TEXT,
    deal_image TEXT,
    deal_category TEXT,
    deal_start_date DATE,
    deal_end_date DATE


);

CREATE TABLE IF NOT EXISTS reviews (
    id SERIAL PRIMARY KEY,
    ecom_user INT REFERENCES ecom_users(id),
    ecom_username VARCHAR ,
    product_id INT REFERENCES products(id),
    review_details VARCHAR,
    review_rating INT
);

CREATE TABLE IF NOT EXISTS disputes (
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(id)


);

