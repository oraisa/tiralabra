# Viikko 4

## 5.1
Tänään toteutin minimikeon HuffmanTreeNode-olioille. Nyt
Huffmannin koodit lasketaan sen avulla
PriorityQueue-luokan sijasta. Lisäksi muutin
Huffman-koodauksen optimaalisuuden testiä siten, että
enää ei tarkisteta yksittäisten tavujen koodausten
pituuksia vaan esimerkkimerkkijonon kokonaispituutta kun
se on koodattu Huffmannin koodauksella. Vaihdoin myös
esimerkin sellaiseen, jossa on aakkonen, joka
esiintyy vain kerran jonka voi ajatella olevan
pysäytysmerkki. Aikaa meni noin 2 tuntia.

## 7.1
Tänään tein koodikatselmoinnin. Aikaa meni noin tunti.

## 8.1
Tänään aloitin tekemään testejä isommilla tiedostoilla.
Siinä selvisi, että Huffmannin koodi voi olla yli 16
bittiä pitkä. Niiden tallentamiseen tarvitaan siis
uusi lähestymistapa. Aikaa meni noin 1,5 tuntia.

## 9.1
Tänään toteutin uuden lähestymistavan koodien
tallentamiseen. Nyt Huffmannin koodit muodostava
puu on kokoajan käytössä. Muutoksen seurauksena
ohjelma pystyy käsittelemään teoriassa mielivaltaisen
mittaisia Huffmannin koodeja. Tiivistetyn tiedoston
headeri on myös lyhyempi kuin ennen. Tämän lisäksi
siistin koodia jonkin verran. Aikaa meni noin 5 tuntia.


## Epäselvää tai vaikeaa
Täytyykö itse toteutetun keon toimia millä tahansa
alkioilla vai riittääkö että se toimii tässä tapauksessa
vain HuffmanTreeNode-luokan alkioilla? Täytyykö keon
toteuttaa mitään Javan valmiita rajapintoja?

Tuleeko testausdokumentissä käsitellä yksikkötestausta?

## Seuraavaksi
Seuraavaksi alan kirjoittamaan toteutus- ja
testausdokumentteja ja testaamaan ohjelman suorituskykyä,
jotka jäivät tältä viikolta väliin
ennalta arvaamattoman ongelman takia. Koodissa voi vielä
olla siistittävää ja jo testauksen vuoksi käyttöliittymää
täytyy parantamaan.

## Ajankayttö

Aikaa meni yhteensä 8,5 tuntia.
