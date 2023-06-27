CREATE TABLE pub.weather_request
(
    id              SERIAL PRIMARY KEY,
    requestDateTime TIMESTAMP     NOT NULL,
    latitude        varchar,
    longitude       varchar
);