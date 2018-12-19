INSERT INTO account (name, password, editor)
VALUES ('test', 'test', false);
INSERT INTO account (name, password, editor)
VALUES ('soares', '123', true);
INSERT INTO account (name, password, editor)
VALUES ('patife', '123', false);
INSERT INTO account (name, password, editor)
VALUES ('zemamede', '123', true);
INSERT INTO artist (name)
VALUES ('sabao');
INSERT INTO artist (name)
VALUES ('azul');
INSERT INTO artist (name)
VALUES ('jesus');
INSERT INTO album (name, description, score)
VALUES ('fat', 'fatHeaven', 0.5);
INSERT INTO album (name, description, score)
VALUES ('skinny', 'boneLand', 2.0);
INSERT INTO album (name, description, score)
VALUES ('sup', 'fork', 4.5);
INSERT INTO artist_album (id, alb_id)
VALUES (1, 1);
INSERT INTO artist_album (id, alb_id)
VALUES (2, 2);
INSERT INTO artist_album (id, alb_id)
VALUES (3, 3);
INSERT INTO music (alb_id, name)
VALUES (1, 'THE TUBA SONG');
INSERT INTO music (alb_id, name)
VALUES (1, 'ALL THEM KGs FOR ME');
INSERT INTO music (alb_id, name)
VALUES (1, 'ITs GENETIC');
INSERT INTO music (alb_id, name)
VALUES (2, 'PUNCTURED LUNG ft RIB');
INSERT INTO music (alb_id, name)
VALUES (2, 'LIGHT');
INSERT INTO music (alb_id, name)
VALUES (2, 'AVOiDING RainDrops');
INSERT INTO music (alb_id, name)
VALUES (3, 'ALL UP HERE');
INSERT INTO music (alb_id, name)
VALUES (3, 'PUNY HUMANS');
INSERT INTO music (alb_id, name)
VALUES (3, 'Ive GOT THEM POWERS');
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
INSERT INTO review (alb_id, text, score)
VALUES (1, 'TO GREASY FOR ME', 0.2);
INSERT INTO review (alb_id, text, score)
VALUES (1, 'A Bit ON THE HEAVY SIDE', 1.2);
INSERT INTO review (alb_id, text, score)
VALUES (2, 'NOT MUCH TO GRAB', 2.0);
INSERT INTO review (alb_id, text, score)
VALUES (2, 'WHERE ARE YOU?', 3.0);
INSERT INTO review (alb_id, text, score)
VALUES (3, 'NOT GOOD! GO TO HELL, BEST SH*T - Devil', 2.0)