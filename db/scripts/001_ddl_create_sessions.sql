CREATE TABLE sessions (
  id SERIAL PRIMARY KEY,
  name text,
  picture bytea,
  description_film text,
  date_film date
);