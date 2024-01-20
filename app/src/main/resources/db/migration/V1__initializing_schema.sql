
CREATE TABLE "apartments" (
    id text NOT NULL,
    number_of_bedrooms integer NOT NULL,
    number_of_bathrooms integer NOT NULL,
    area numeric(3,2),
    has_parking BOOLEAN NOT NULL,
    price numeric(4,2) NOT NULL,
    description text,
    CONSTRAINT "apartments_pk" primary key ("id")
);
