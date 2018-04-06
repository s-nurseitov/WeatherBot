create schema WeatherBot;
create table WeatherBot.users(
id long primary key auto_increment,
chat_id varchar(255),
first_name varchar(100),
last_name varchar(100),
name varchar(100)
create_date timestamp default current_timestamp()
);
create table WeatherBot.weather(
id long primary key auto_increment,
datetime_ timestamp,
city varchar(100),
temp varchar(10),
humidity varchar(10),
country varchar(100),
pressure  varchar(10),
wind varchar(10),
wind_dir varhcar(100)
);
create table WeatherBot.request(
id  long primary key auto_increment,
user_id long,
weather_id long,
date_ timestamp
);
