INSERT INTO account (name, password, editor)
VALUES ('test', 'test', false);
INSERT INTO account (name, password, editor)
VALUES ('soares', '123', true);
INSERT INTO account (name, password, editor)
VALUES ('patife', '123', false);
INSERT INTO account (name, password, editor)
VALUES ('zemamede', '123', true);
INSERT INTO artist (name)
VALUES ('Pink Floyd');
INSERT INTO album (name, description, score)
VALUES ('The Dark Side of the Moon', 'By condensing the sonic explorations of Meddle to actual songs and adding a lush, immaculate production to their trippiest instrumental sections, Pink Floyd inadvertently designed their commercial breakthrough with Dark Side of the Moon', 0);
INSERT INTO artist_album (id, alb_id)
VALUES (1, 1);
INSERT INTO music (alb_id, name)
VALUES (1, 'Speak to Me');
INSERT INTO music (alb_id, name)
VALUES (1, 'Breathe (In the Air)');
INSERT INTO music (alb_id, name)
VALUES (1, ' On the Run');
INSERT INTO music (alb_id, name)
VALUES (1, 'Time');
INSERT INTO music (alb_id, name)
VALUES (1, 'The Great Gig in the Sky');
INSERT INTO music (alb_id, name)
VALUES (1, 'Money');
INSERT INTO music (alb_id, name)
VALUES (1, 'Us and Them');
INSERT INTO music (alb_id, name)
VALUES (1, 'Any Colour You Like');
INSERT INTO music (alb_id, name)
VALUES (1, 'Brain Damage');
INSERT INTO music (alb_id, name)
VALUES (1, 'Eclipse');
INSERT INTO artist (name)
VALUES (' Rouxinol Faduncho');
INSERT INTO album (name, description, score)
VALUES ('Formidável Bigode', 'Title says it all', 0);
INSERT INTO artist_album (id, alb_id)
VALUES (2, 2);
INSERT INTO music (alb_id, name)
VALUES (2, 'Dar Um Bigode A Crise');
INSERT INTO music (alb_id, name)
VALUES (2, 'Meia Branca Meia Branca');
INSERT INTO music (alb_id, name)
VALUES (2, 'Nao Ha Dinheiro');
INSERT INTO music (alb_id, name)
VALUES (2, 'Ja Estas Com Os Copos');
INSERT INTO music (alb_id, name)
VALUES (2, 'Caes De Loica');
INSERT INTO music (alb_id, name)
VALUES (2, 'Fado Da Macacada');
INSERT INTO music (alb_id, name)
VALUES (2, 'Fado Anani Ananao');
INSERT INTO music (alb_id, name)
VALUES (2, 'Ai Caramba');
INSERT INTO music (alb_id, name)
VALUES (2, 'Tudo Isto E Fado');
INSERT INTO artist (name)
VALUES ('Queen');
INSERT INTO album (name, description, score)
VALUES ('A Night at the Opera', 'Queen were straining at the boundaries of hard rock and heavy metal on Sheer Heart Attack, but they broke down all the barricades on A Night at the Opera, a self-consciously ridiculous and overblown hard rock masterpiece', 0);
INSERT INTO artist_album (id, alb_id)
VALUES (3, 3);
INSERT INTO music (alb_id, name)
VALUES (3, 'Death on two legs');
INSERT INTO music (alb_id, name)
VALUES (3, 'Lazing on a Sunday Afternoon');
INSERT INTO music (alb_id, name)
VALUES (3, 'I''m in Love with My Car ');
INSERT INTO music (alb_id, name)
VALUES (3, 'You''re My Best Friend ');
INSERT INTO music (alb_id, name)
VALUES (3, '39');
INSERT INTO music (alb_id, name)
VALUES (3, 'Sweet Lady ');
INSERT INTO music (alb_id, name)
VALUES (3, 'Seaside Rendezvous');
INSERT INTO music (alb_id, name)
VALUES (3, 'The Prophet''s Song ');
INSERT INTO music (alb_id, name)
VALUES (3, 'Love of my life');
INSERT INTO music (alb_id, name)
VALUES (3, 'Good Company');
INSERT INTO music (alb_id, name)
VALUES (3, 'Bohemian Rhapsody');
INSERT INTO music (alb_id, name)
VALUES (3, 'God Save the Queen');
INSERT INTO notification (use_id, message)
VALUES (1, 'TEST NOTIFICATION');
INSERT INTO notification (use_id, message)
VALUES (2, 'THIS COURSE');
INSERT INTO notification (use_id, message)
VALUES (2, 'YOU SHALL NOT PASS');
INSERT INTO notification (use_id, message)
VALUES (3, 'YOU CAN GO');
INSERT INTO notification (use_id, message)
VALUES (3, 'No No No');
INSERT INTO notification (use_id, message)
VALUES (4, 'YOU HAVE THE LOICENCE?');