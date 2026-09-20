CREATE TABLE users (
                       id VARCHAR(20) PRIMARY KEY,
                       full_name VARCHAR(150) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone VARCHAR(30),
                       is_logged BOOLEAN NOT NULL DEFAULT FALSE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'CLIENT'))
);

CREATE TABLE rooms (
                       room_id VARCHAR(20) PRIMARY KEY,
                       room_type VARCHAR(20) NOT NULL CHECK (room_type IN ('SINGLE', 'DOUBLE', 'SUITE')),
                       night_price NUMERIC(10, 2) NOT NULL CHECK (night_price >= 0),
                       capacity INTEGER NOT NULL CHECK (capacity > 0),
                       room_status VARCHAR(20) NOT NULL CHECK (room_status IN ('AVAILABLE', 'IN_REPAIR'))
);

CREATE TABLE reservations (
                              reservation_id VARCHAR(20) PRIMARY KEY,
                              client_id VARCHAR(20) NOT NULL REFERENCES users(id),
                              room_id VARCHAR(20) NOT NULL REFERENCES rooms(room_id),
                              check_in DATE NOT NULL,
                              check_out DATE NOT NULL,
                              nights BIGINT NOT NULL CHECK (nights > 0),
                              total NUMERIC(10, 2) NOT NULL CHECK (total >= 0),
                              status VARCHAR(20) NOT NULL CHECK (
                                  status IN ('ACCEPTED', 'REFUSED', 'CANCELED', 'CONFIRMED', 'COMPLETED')
                                  ),
                              person_numbers INTEGER NOT NULL CHECK (person_numbers > 0),
                              CHECK (check_out > check_in)
);

CREATE TABLE payments (
                          payment_id VARCHAR(50) PRIMARY KEY,
                          reservation_id VARCHAR(20) NOT NULL REFERENCES reservations(reservation_id),
                          amount_payed NUMERIC(10, 2) NOT NULL CHECK (amount_payed >= 0),
                          payment_method VARCHAR(50) NOT NULL,
                          payment_status VARCHAR(50) NOT NULL,
                          payment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE invoices (
                          invoice_id BIGSERIAL PRIMARY KEY,
                          payment_id VARCHAR(50) NOT NULL UNIQUE REFERENCES payments(payment_id),
                          invoice_status VARCHAR(50) NOT NULL,
                          tva NUMERIC(5, 2) NOT NULL CHECK (tva >= 0),
                          total NUMERIC(10, 2) NOT NULL CHECK (total >= 0),
                          total_ttc NUMERIC(10, 2) NOT NULL CHECK (total_ttc >= 0)
);