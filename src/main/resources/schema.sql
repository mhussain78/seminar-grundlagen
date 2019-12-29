CREATE TABLE IF NOT EXISTS SEMINARTEILNEHMER
(
    ID              int          NOT NULL AUTO_INCREMENT,
    MATRIKEL_NUMMER bigint,
    NAME            varchar(255) NOT NULL,
    EMAIL           varchar(255),
    SEMINAR         varchar(255),
    PRIMARY KEY (ID),
    UNIQUE (MATRIKEL_NUMMER)
);