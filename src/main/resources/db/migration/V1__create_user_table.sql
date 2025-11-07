create table users
(
    id                      varchar(255) not null primary key,
    email                   varchar(255) not null,
    first_name              varchar(255) not null,
    last_name               varchar(255) not null,
    password                varchar(255) not null,
    verification_token      varchar,
    is_blocked              boolean not null default false,
    is_email_verified       boolean not null default false,
    profile_picture_url     varchar,
    created_at              timestamp(6) not null
);


comment on table users is 'Stores user account information including personal details, authentication data, and status flags.';

comment on column users.id is 'Unique identifier for the user (UUID or generated string).';
comment on column users.email is 'User’s email address used for login and verification.';
comment on column users.first_name is 'User’s first name.';
comment on column users.last_name is 'User’s last name.';
comment on column users.password is 'Hashed user password for authentication.';
comment on column users.verification_token is 'Token sent to user for email verification or password reset.';
comment on column users.is_blocked is 'Indicates whether the user account is blocked (true = blocked).';
comment on column users.is_email_verified is 'Indicates whether the user has verified their email address.';
comment on column users.profile_picture_url is 'URL of the user’s profile picture.';
comment on column users.created_at is 'Timestamp when the user account was created.';