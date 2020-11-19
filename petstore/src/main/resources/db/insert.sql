SET FOREIGN_KEY_CHECKS =0;

truncate table pet;
truncate table store;

INSERT into store (`id`,`name`,`location`,`contact_no`)
VALUES (21, 'super store','nassarawa', 09020339503);

INSERT INTO pet (`id`,`name`,`colour`,`breed`,`age`,`pet_sex`,`store_id`)
VALUES (31,'jill','blue','parrot',6,'MALE',21),
(32,'jack','black','dog',2,'MALE',21),
(33,'brown','blue','human',3,'FEMALE',21),
(34,'sally','green','pig',5,'FEMALE',21),
(35,'mill','grey','rabbit',14,'MALE',21),
(36,'ross','purple','cat',3,'FEMALE',21),
(37,'germ','ash','lion','6','MALE',21),
(38,'jill','blue','tiger','6','MALE',21);



 SET FOREIGN_KEY_CHECKS = 1;
