# Viikko 3

## 31.12
Tänään lisättyäni testejä tiivistämiselle
huomasin, että tavun Huffmannin koodi voi olla yli
8 bittiä pitkä. Muutin BitPattern-luokkaa käyttämään
16-bittistä kokonaislukua koodauksen tallentamiseen,
ja muutin siihen liittyvät luokat käyttämään niitä.
Tässä sivussa huomasin, että Javan oikealle shiftaus
ei toimi halutusti negatiivisilla short-, tai
byte-muuttujilla. Tein tämänkin korjaavat metodit.
Näiden jälkeen byte-taulukon tiivistäminen ja purkaminen
on melkein valmis. Aikaa meni noin 4 tuntia.

## 1.1
Tänään laitoin koodin merkkaamaan tiivistetyn tiedoston
lopun erityisellä merkillä. Lisäksi aloitin tekemään
tiivistetyn byte-taulukon headerin kera tekevää metodia.
Tiivistetyn tiedoston päättymisen merkkaaminen
tarkoittaa, että monet CompressedFile-luokan testeistä
täytyy tehdä uusiksi. Aikaa meni noin 2,5 tuntia.

## 2.1
Tänään muutin niitä CompressedFile-luokan testejä,
joita täytyi muuttaa tiivistetyn datan päättymisen
merkkauksen vuoksi. Tein sinne myös lisää testejä
ja sain tiivistetyn byte-taulukon headerin kanssa
luomisen toimimaan. Nyt luokka pystyy tiivistämään
taulokon taulukoksi ja purkamaan tiivistetyn taulukon
alkuperäiseksi. Lisäsin myös Main-luokkaan koodia, joka
joko tiivistää komentoriviltä annetun tiedoston tai
purkaa sen tiedoston päätteen mukaan. Aikaa meni noin 3,5
tuntia.

## Epäselvää tai vaikeaa
Ovatko ByteArrayOutputStream ja ByteArrayInputStream
sellaisia tietorakenteita, jotka tällä kurssilla pitäisi
toteuttaa itse?

## Seuraavaksi
Seuraavaksi alan toteuttaa omaa minimikekoa ja muutan
käytetyn HashMapin taulukoksi. Niiden jälkeen teen
käyttöliittymästä järkevämmän ja viimeistään silloin
erotan käyttöliittymän ja kaiken muun omiksi paketeikseen.
Myös Huffmannin koodeja esittävät BitPattern-taulukot
muutetaan jossain vaiheessa omaksi luokakseen koodin
selkeyden vuoksi.

## Ajankäyttö
Aikaa meni yhteensä noin 10 tuntia.
