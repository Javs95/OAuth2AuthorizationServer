create table users (
	username varchar(50) primary key not null,
	password varchar(100) not null,
	enabled smallint not null default 1
);

create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_users_authorities_username foreign key (username) references users (username)
);

create table ids (
	id serial not null,
	username varchar(50) primary key not null,
	constraint fk_users_ids_username foreign key (username) references users (username)
);

create table oauth2_registered_client(
	id varchar(100) primary key not null,
	client_id varchar(100) not null,
	client_id_issued_at timestamp without time zone not null,
	client_secret varchar(200) not null,
	client_secret_expires_at timestamp without time zone,
	client_name varchar(200) not null,
	client_authentication_methods varchar(1000) not null,
	authorization_grant_types varchar(1000) not null,
	redirect_uris varchar(1000) not null,
	scopes varchar(1000) not null,
	client_settings varchar(2000) not null,
	token_settings varchar(2000) not null
);

create table movies (
	id serial primary key not null,
	name varchar(100) not null,
	year integer not null,
	description varchar(1000) not null
);

insert into users (username, password) values
('carlos.reyes@theksquaregroup.com', '$2a$10$1sQX19Kb86igELRBIeJrAeZGWZSpYXSV1EAxTL7mDOkQTJJYbEuWC'),
('guillermo.ceme@theksquaregroup.com', '$2a$10$vEX9Z3KphBjT3Eq2VsnqSuz9.8Hu1exHb23gg038xeQgwZzhVRYQ6'),
('julio.vargas@theksquaregroup.com', '$2a$10$RYJNWpmq8eTUAyYqxH5o1Ova7qMc0J8YLrUtiu7mWfLOg.kMOAPtG');

insert into authorities values
('carlos.reyes@theksquaregroup.com', 'ROLE_ADMIN'),
('guillermo.ceme@theksquaregroup.com', 'ROLE_MANAGER'),
('julio.vargas@theksquaregroup.com', 'ROLE_USER');

insert into ids (username) values
('carlos.reyes@theksquaregroup.com'),
('guillermo.ceme@theksquaregroup.com'),
('julio.vargas@theksquaregroup.com');

insert into oauth2_registered_client (id, client_id, client_id_issued_at, client_secret, client_name, client_authentication_methods, authorization_grant_types, redirect_uris, scopes, client_settings, token_settings) values
('daf916a3-e56d-4fd3-bfc5-3d09d72f654e', 'itk-client', '2022-02-15 05:09:25.289471', '{noop}secret', 'daf916a3-e56d-4fd3-bfc5-3d09d72f654e', 'client_secret_basic', 'refresh_token,authorization_code', 'http://10.5.0.5:8080/authorized,http://10.5.0.5:8080/login/oauth2/code/itk-client-oidc', 'trust,openid', '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}', '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

insert into movies (name, year, description) values
('Finding Nemo', 2003, 'The story of Finding Nemo centers on a widowed clownfish named Marlin and a blue tang fish named Dory traveling across the ocean to Sydney to find Marlin lost son, Nemo.'),
('The Social Network', 2010, 'On a fall night in 2003, Harvard undergrad and computer programming genius Mark Zuckerberg sits down at his computer and heatedly begins working on a new idea. In a fury of blogging and programming, what begins in his dorm room soon becomes a global social network and a revolution in communication.'),
('Back to the Future', 1985, 'Marty McFly, a 17 year old high school student gets lost in 1955 by an accident, 30 years back in time. With the help of his friend Dr. Emmet Brown, he is desperately trying to find his way back to the future in the year 1985. It becomes a battle against the clock.'),
('Pinocchio', 1940, 'Inventor Gepetto creates a wooden marionette called Pinocchio. His wish for Pinocchio to be a real boy is unexpectedly granted by a fairy. The fairy assigns Jiminy Cricket to act as Pinocchio conscience and keep him out of trouble. Jiminy is not too successful in this endeavor and most of the film is spent with Pinocchio deep in trouble.'),
('La La Land', 2016, 'Aspiring actress serves lattes to movie stars in between auditions and jazz musician Sebastian scrapes by playing cocktail-party gigs in dingy bars. But as success mounts, they are faced with decisions that fray the fragile fabric of their love affair, and the dreams they worked so hard to maintain in each other threaten to rip them apart.'),
('Gravity', 2013, 'Two astronauts work together to survive after an accident leaves them stranded in space. Dr. Ryan Stone is a brilliant medical engineer on her first shuttle mission, with veteran astronaut Matt Kowalski in command of his last flight before retiring.'),
('The Lion King', 1994, 'A young lion prince is cast out of his pride by his cruel uncle, who claims he killed his father. While the uncle rules with an iron paw, the prince grows up beyond the Savannah, living by a philosophy: No worries for the rest of your days. But when his past comes to haunt him, the young prince must decide his fate: Will he remain an outcast or face his demons and become what he needs to be?'),
('Beauty and the Beast', 1991, 'A young prince is cursed by an enchantress to remain a beast until he can learn to love another and earn their love in return. Meanwhile, a young provincial French girl longs for a different life. Her father stumbles upon the Beast castle and earns his wrath.');