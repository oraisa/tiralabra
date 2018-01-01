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
