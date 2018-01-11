# Toteutusdokumentti

## Ohjelman yleisrakenne
Ohjelma koostuu kolmesta paketista: oraisa.tiivistys.cli,
joka sisältää käyttöliittymän, oraisa.tiivistys.logic,
joka sissältää varsinaisen toiminnallisuuden ja oraisa.tiivistys.measuring, joka
sisältää apuluokkia suorituskyvyn mittaamiseen.
Käyttöliittymä sisältää Main-luokan, joka tulkitsee
ohjelman komentoriviparametrit ja lukuu tiivistettävän
tai purettavan tiedoston kovalevyltä. Tiivistämisen tai
purkamisen jälkeen Main-luokka kirjoittaa uuden tiedoston
levylle.

Varsinainen tiedon tiivistys tai tiivistyksen purku
tapahtuu oraisa.tiivistys.logic-paketissa
CompressedFile-luokan avulla. Luokkan olio luodaan joko
tiivistetyn tai tiivistämättömän tiedon avulla.
Luodun olion metodit palauttavat annetun tiedon
tiivistetyn tai tiivistämättömän vastineen.

Tietoa tiivistettäessä CompressedFile-luokka ensin laskee
tiedon tavujen esiintymiskerrat. Tämän tiedon avulla
lasketaan Huffmannin koodaus tiedostolle. Seuraavaksi
jokainen tiedon tavu vaihdetaan tavua vastaavaksi
koodiksi. Kun tähän lisätään koodauksen kertova otsake,
on tiivitetty tieto valmis.

Tiivistystä purkaessa CompressedFile-luokka ensin luo
koodauksen tiedoston otsakkeen perusteella. Tämän
jälkeen tiivistetty tieto käydään läpi ja jokainen
koodi vaihdetaan sitä vastaavaksi tavuksi. Näin saadaan
palautettua alkuperäinen tieto.

## Tiivistetyn tiedoston otsakkeen muoto
Huffmannin koodaus esitetään ohjelman sisällä puun
muodossa. Puussa lehdet sisältävät koodattavan tavun, ja
polku juuresta lehteen määrittää tavun koodin. Kun
polussa kuljetaan kaarta pitkin, koodiin lisätään yksi
bitti. Jos kaari kulkee alkusolmun vasempaan lapseen,
lisätään 1, jos se kulkee oikeaan, lisätään 0.

Puu kirjoitetaan tiivistetyn tiedoston otsakkeeseen
kirjoittamalla kaikkien solmujen binääriesitykset
esijärjestyksessä, lapset vasemmalta oikealle. Jos
solmu ei ole lehti, sen binääriesitys on pelkkä 0.
Lehden binääriesitys alkaa bitillä 1. Jos solmu ei koodaa
pysäytyskoodia, seuraava bitti on 0 ja loput 8 bittiä
ovat solmun koodaava tavu. Jos solmu koodaa
pysäytyskoodin, seuraava bitti on 1 ja loput 8 ovat
nollia. Otsakkeen loppuun tulee lisäksi
nollia siten, että koko otsakkeen pituus tavuina on
kokonaisluku.

## Aika- ja tilavaativuudet
### Tiedon tiivistys

### Tiivistyksen purku

## Puutteet ja parannusehdotukset

## Lähteet
