# Design doc

### Task "Бронювання квитків в кіно"

### Use cases

User can select film, and book one or more free places for it.
If all places are taken, then user cannot book places for the show.

Administrator of the cinema can add new films and set new film shows.

## Data objects

### Movie show

*Movie show - Сеансы;
*Film - фильм, фильм имеет много сеансов;

Movie show is described by the following fields:

- **Show id** - unique identifier of the show *(needed?)*
- **Film id** - identifier of the film
- **Start time** - time of the start of the show in the form SS:MM:HH DD-MM-YYYY
- **End time** - time of the end of the show
- **Free places** - list of free places *(goes to the other table)*
- **Price** - price of the ticket
- **Is active** - flag to show if the movie show is currently active
- **Age constraint** - optional age constraint

### Film

Film entity is used to display all running movies in the cinema.
Each movie has a set of planned movie shows. Movie is described by the 
following fields:

- **Film id** - unique identifier of the movie
- **Film name** - film name
- **Film description** - film description

### Customer

- **Customer id** - unique identifier of the customer
- **First name** - first name of customer
- **Last name** - last name of customer
- **Email** - email of the customer
- **Phone number** - customer's phone number

### Booking

Booking object is used to represent booking entity. 
It is described by the following fields.

- **Booking id** - unique identifier of the booking
- **Show id** - id of the show customer made booking for
- **Customer id** - id of the customer, who made the booking

## Database Schema

Description of database tables goes below

**films**

- film_id - INT - PRIMARY KEY
- film_name - VARCHAR
- film_desc - VARCHAR

**movie_shows**

*Question:* use **show_id** as primary key or a pair **{film_id, start_time}**?

I think former should be used, as cinema may have many show rooms, and it may
happen that films with same **film_id** start at the same time.

- **show_id** - INT - PRIMARY KEY
- **film_id** - INT - FOREIGN KEY to 'film_id' in 'films'
- **start_time** - DATETIME
- **end_time** - DATETIME
- **price** - INT
- **is_active** - BOOL
- **age_constr** - INT

**free_places**

**{show_id, place}** pair is PRIMARY KEY.

- **show_id** - INT - FOREIGN KEY to 'show_id' in 'movie_shows'
- **place** - INT

**customers**

- **customer_id** - INT
- **first_name** - VARCHAR
- **last_name** - VARCHAR
- **email** - VARCHAR
- **phone_number** - VARCHAR

**bookings**

- **booking_id** - INT
- **show_id** - INT
- **customer_id** - INT

## API

The API of the system should give the CUSTOMER following functionality

- Retrieve information about:
    - films running in the cinema
    - all movie shows currently running
    - all movie shows for film X
    - free places on the movie show X at time Y
- Book tickets for movie show
- Rescind booking for movie show

The API of the system should give the MANAGER following functionality

- Get booking info by customer phone/name
- Set booking to be done successfully (when customers come and take tickets)
- Set movie show to be inactive (movie show started, no booking further could be done,
all currently untaken bookings will be marked as unsuccessful)

The API of the system should give the ADMIN following functionality

- Add new film
- Add new movie show X for the film Y
- Delete film
- Delete movie show X for the film Y

## API design

For USER:

- GET    - cinema.com/api/films            - get list of all running films
- GET    - cinema.com/api/films/{filmName} - get all movie shows for film **${filmName}**
- GET    - cinema.com/api/shows            - get list of all running movie shows
- POST   - cinema.com/api/shows            - get all info (start/end time, places, price) on show
- POST   - cinema.com/api/bookings         - make a booking (customer info, film name, start time)
- DELETE - cinema.com/api/bookings         - delete a booking (phone + booking id)

For MANAGER:

- GET    - cinema.com/api/bookings/{phone} - get booking by phone
- PUT    - cinema.com/api/bookings/{id}    - mark booking as successful (tickets are given to customers)
- PUT    - cinema.com/api/shows/{id}       - mark movie show as inactive

For ADMIN:

- POST   - cinema.com/api/films            - add new film (film name, film description)
- PUT    - cinema.com/api/shows            - add new movie show to the film
- DELETE - cinema.com/api/films            - delete film (film name)
- DELETE - cinema.com/api/shows            - delete show (film name, start time)

## Application layers

## Patterns

### Builder

### Singleton

### Facade

### Specification
