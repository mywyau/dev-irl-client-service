# cashew

## connect to db
psql -h localhost -p 5450 -U cashew_user -d bookingdb

## db username
cashew_user

## password
cashew

## inserting some data
INSERT INTO desks (id, location, status)
VALUES
('desk1', 'Zone A', 'available'),
('desk2', 'Zone B', 'unavailable'),
('desk3', 'Zone A', 'available'),
('desk4', 'Zone C', 'available


## http requests
http GET http://localhost:8080/desks


http POST http://localhost:8080/desk/book userId="user1" deskId="desk1" startTime="2024-10-06T10:00:00" endTime="2024-10-06T14:00:00"


## inserting some data
INSERT INTO desks (id, location, status)
VALUES
('desk1', 'Zone A', 'available'),
('desk2', 'Zone B', 'unavailable'),
('desk3', 'Zone A', 'available'),
('desk4', 'Zone C', 'available

or call the POST endpoints correctly


## creating tables in sql

CREATE TABLE desks (
id VARCHAR(255) PRIMARY KEY,    -- Unique identifier for each desk
location VARCHAR(255) NOT NULL, -- Location of the desk
status VARCHAR(50) NOT NULL     -- Status of the desk (e.g., "available", "unavailable")
);

CREATE TABLE bookings (
id VARCHAR(255) PRIMARY KEY,   
user_id VARCHAR(255) NOT NULL,
desk_id VARCHAR(255) NOT NULL,    
start_time TIMESTAMP NOT NULL,    
end_time TIMESTAMP NOT NULL,    
FOREIGN KEY (desk_id) REFERENCES desks(id) ON DELETE SET NULL  -- Desk foreign key
);

## sql db initalise script - creates tables and data
psql -h localhost -p 5450 -U your_username -d bookingdb -f init.sql

## or using docker

docker exec -i postgres-container psql -U cashew_user -d bookingdb < init.sql

## using flyway db migrations

flyway -url=jdbc:postgresql://localhost:5450/bookingdb -user=cashew_user -password=cashew migrate
