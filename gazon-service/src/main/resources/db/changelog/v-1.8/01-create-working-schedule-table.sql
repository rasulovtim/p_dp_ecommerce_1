CREATE TABLE working_schedule (
                                  id BIGSERIAL PRIMARY KEY,
                                  day_of_week VARCHAR(10) NOT NULL,
                                  from_time TIME NOT NULL,
                                  to_time TIME NOT NULL
);
