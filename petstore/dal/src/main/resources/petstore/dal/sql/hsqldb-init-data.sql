INSERT INTO user VALUES('j2ee','j2ee',null);
INSERT INTO user VALUES('admin','admin','admin');

INSERT INTO account VALUES('j2ee','yourname@yourdomain.com','ABC', 'XYX', 'OK', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'US',  '555-555-5555', '1234567', 'Visa', '2005-12-15');
INSERT INTO account VALUES('admin','yourname@yourdomain.com','ABC', 'XYX', 'OK', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'US',  '555-555-5555', '1234567', 'Visa', '2005-12-15');

INSERT INTO profile VALUES('j2ee','english','DOGS',1,1);
INSERT INTO profile VALUES('admin','english','DOGS',1,1);

INSERT INTO sequence VALUES('ordernum', 1000);
INSERT INTO sequence VALUES('orderitemnum', 1000);

INSERT INTO bannerdata VALUES ('FISH','<image src="../images/banner_fish.gif">');
INSERT INTO bannerdata VALUES ('CATS','<image src="../images/banner_cats.gif">');
INSERT INTO bannerdata VALUES ('DOGS','<image src="../images/banner_dogs.gif">');
INSERT INTO bannerdata VALUES ('REPTILES','<image src="../images/banner_reptiles.gif">');
INSERT INTO bannerdata VALUES ('BIRDS','<image src="../images/banner_birds.gif">');

INSERT INTO category VALUES ('FISH','Fish','cat_fish.jpg','Fish');
INSERT INTO category VALUES ('DOGS','Dogs','cat_dog.jpg','Dogs');
INSERT INTO category VALUES ('REPTILES','Reptiles','cat_reptile.jpg','Reptiles');
INSERT INTO category VALUES ('CATS','Cats','cat_cat.jpg','Cats');
INSERT INTO category VALUES ('BIRDS','Birds','cat_bird.jpg','Birds');

INSERT INTO product VALUES ('FI-SW-01','FISH','Angelfish','fish1.jpg','Salt Water fish from Australia');
INSERT INTO product VALUES ('FI-SW-02','FISH','Tiger Shark','fish4.jpg','Salt Water fish from Australia');
INSERT INTO product VALUES ('FI-FW-01','FISH', 'Koi','fish3.jpg','Fresh Water fish from Japan');
INSERT INTO product VALUES ('FI-FW-02','FISH', 'Goldfish','fish2.jpg','Fresh Water fish from China');
INSERT INTO product VALUES ('K9-BD-01','DOGS','Bulldog','dog2.jpg','Friendly dog from England');
INSERT INTO product VALUES ('K9-PO-02','DOGS','Poodle','dog6.jpg','Cute dog from France');
INSERT INTO product VALUES ('K9-DL-01','DOGS', 'Dalmation','dog5.jpg','Great dog for a Fire Station');
INSERT INTO product VALUES ('K9-RT-01','DOGS', 'Golden Retriever','dog1.jpg','Great family dog');
INSERT INTO product VALUES ('K9-RT-02','DOGS', 'Labrador Retriever','dog5.jpg','Great hunting dog');
INSERT INTO product VALUES ('K9-CW-01','DOGS', 'Chihuahua','dog4.jpg','Great companion dog');
INSERT INTO product VALUES ('RP-SN-01','REPTILES','Rattlesnake','lizard3.jpg','Doubles as a watch dog');
INSERT INTO product VALUES ('RP-LI-02','REPTILES','Iguana','lizard2.jpg','Friendly green friend');
INSERT INTO product VALUES ('FL-DSH-01','CATS','Manx','cat3.jpg','Great for reducing mouse populations');
INSERT INTO product VALUES ('FL-DLH-02','CATS','Persian','cat1.jpg','Friendly house cat, doubles as a princess');
INSERT INTO product VALUES ('AV-CB-01','BIRDS','Amazon Parrot','bird4.jpg','Great companion for up to 75 years');
INSERT INTO product VALUES ('AV-SB-02','BIRDS','Finch','bird1.jpg','Great stress reliever');

INSERT INTO supplier VALUES (1,'XYZ Pets','AC','600 Avon Way','','Los Angeles','CA','94024','212-947-0797');
INSERT INTO supplier VALUES (2,'ABC Pets','AC','700 Abalone Way','','San Francisco ','CA','94024','415-947-0797');

INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-1','FI-SW-01',16.50,10.00,1,'P','Large');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-2','FI-SW-01',16.50,10.00,1,'P','Small');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-3','FI-SW-02',18.50,12.00,1,'P','Toothless');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-4','FI-FW-01',18.50,12.00,1,'P','Spotted');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-5','FI-FW-01',18.50,12.00,1,'P','Spotless');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-6','K9-BD-01',18.50,12.00,1,'P','Male Adult');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-7','K9-BD-01',18.50,12.00,1,'P','Female Puppy');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-8','K9-PO-02',18.50,12.00,1,'P','Male Puppy');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-9','K9-DL-01',18.50,12.00,1,'P','Spotless Male Puppy');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-10','K9-DL-01',18.50,12.00,1,'P','Spotted Adult Female');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-11','RP-SN-01',18.50,12.00,1,'P','Venomless');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-12','RP-SN-01',18.50,12.00,1,'P','Rattleless');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-13','RP-LI-02',18.50,12.00,1,'P','Green Adult');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-14','FL-DSH-01',58.50,12.00,1,'P','Tailless');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-15','FL-DSH-01',23.50,12.00,1,'P','With tail');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-16','FL-DLH-02',93.50,12.00,1,'P','Adult Female');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-17','FL-DLH-02',93.50,12.00,1,'P','Adult Male');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-18','AV-CB-01',193.50,92.00,1,'P','Adult Male');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-19','AV-SB-02',15.50, 2.00,1,'P','Adult Male');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-20','FI-FW-02',5.50, 2.00,1,'P','Adult Male');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-21','FI-FW-02',5.29, 1.00,1,'P','Adult Female');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-22','K9-RT-02',135.50, 100.00,1,'P','Adult Male');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-23','K9-RT-02',145.49, 100.00,1,'P','Adult Female');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-24','K9-RT-02',255.50, 92.00,1,'P','Adult Male');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-25','K9-RT-02',325.29, 90.00,1,'P','Adult Female');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-26','K9-CW-01',125.50, 92.00,1,'P','Adult Male');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-27','K9-CW-01',155.29, 90.00,1,'P','Adult Female');
INSERT INTO item (itemid, productid, listprice, unitcost, supplier, status, attr1) VALUES('EST-28','K9-RT-01',155.29, 90.00,1,'P','Adult Female');

INSERT INTO inventory (itemid, qty ) VALUES ('EST-1',1);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-2',2);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-3',3);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-4',4);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-5',5);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-6',6);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-7',7);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-8',8);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-9',9);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-10',10);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-11',11);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-12',12);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-13',13);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-14',14);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-15',15);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-16',16);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-17',17);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-18',18);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-19',19);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-20',20);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-21',21);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-22',22);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-23',23);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-24',24);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-25',25);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-26',26);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-27',27);
INSERT INTO inventory (itemid, qty ) VALUES ('EST-28',28);

