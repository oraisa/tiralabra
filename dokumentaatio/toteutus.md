# Toteutusdokumentti

## Ohjelman yleisrakenne
Ohjelma koostuu kolmesta paketista: oraisa.tiivistys.cli,
joka sisältää käyttöliittymän, oraisa.tiivistys.logic,
joka sisältää varsinaisen toiminnallisuuden ja oraisa.tiivistys.measuring, joka
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
Tiedon tiivistäminen tapahtuu yleisellä tasolla seuraavan
pseudokoodin mukaan. Aikavaativuuksissa n on datan
sisältämien tavujen määrä ja m on eri tavujen määrä.
```
frekvenssit = laskeTavujenFrekvenssit(data)
koodaus = laskeHuffmanKoodaus(frekvenssit)
koodaaData(koodaus, data)
```
laskeTavujenFrekvenssit-funktion aikavaativuus on selvästi
O(n). Se palauttaa jokaiselle mahdolliselle tavulle
frekvenssin, joten sen tilavaativuus on vakio.

laskeHuffmanKoodaus käyttää Solmu-tietorakennetta. Jokainen
solmu on joko lehti tai oksa. Lehdellä on koodattava
tavu ja tavun frekvenssi. Oksalla on vasen ja oikea lapsi.
Oksan frekvenssi on sen lasten frekvenssien summa. Solmujen
suuruusjärjestys minimikeossa on solmujen frekvenssien
suuruusjärjestys. Pysäytyskoodin frekvenssi on 1.
Funktion pseudokoodi on seuraavaa:
```
keko = new MinHeap()
jokaista tavua kohti:
    jos tavun frekvenssi != 0:
        keko.insert(Solmu.lehti(tavu, tavunFrekvenssi))

keko.insert(Solmu.pysäytysKoodi)

niin kauan kun keko.koko > 1
    pieninSolmu = keko.delMin()
    toiseksiPieninSolmu = keko.delMin()
    uusiSolmu = Solmu.oksa(pieninSolmu, toiseksiPieninSolmu)
    keko.insert(uusiSolmu)

return keko.getMin()
```
Tavuja vastaavia solmuja lisättäessä kekoon pahimmassa
tapauksessa kaikkia tavuja vastaava solmu joudutaan
lisäämään kekoon, joten osion aikaavaativuus on
O(m log(m)). Solmuja läpikäytäessä
jokaisella iteraatiolla keon solmujen määrä vähenee yhdellä,
joten iteraatioita tulee m - 1 kappaletta. Tämänkin osuuden
aikavaativuus on siis O(m log m). Koska solmujen muodostama
puu on paluuarvo, tilavaativuus on O(1).

koodaaData-funktio saa syötteeksi Huffmannin koodausta
kuvaavan puun ja koodattavan datan. Se käyttää BittiVirta-
tietorakennetta, johon voidaan lisätä bittejä yksi
kerrallaan. Lisääminen tapahtuu vakioajassa.
```
lehdet = new Solmu[]
käyPuuLäpi(juuri)

bittiVirta = new BittiVirta()
jokaista syötteen tavua kohti:
    koodaaTavu(lehdet[tavu])
koodaaTavu(pysäytysSolmu)
return bittiVirta.sisältö

käyPuuLäpi(solmu):
    jos solmu on lehti:
        jos solmu on pysäytyskoodi:
            pysäytysSolmu = solmu
        muuten:
            lehdet[solmu.tavu] = solmu
    muuten:
        käyPuuLäpi(solmu.vasenLapsi)
        käyPuuLäpi(solmu.oikeaLapsi)

koodaaTavu(solmu)
    jos solmu ei ole juuri:
        jos solmu on vanhempansa vasen lapsi:
            koodaaTavu(solmu.vanhempi)
            bittiVirta.kirjoitaBitti(1)
        muuten:
            koodaaTavu(solmu.vanhempi)
            bittiVirta.kirjoitaBitti(0)
```
Aluksi käyPuuLäpi-funktio selvittää, mistä tavuja ja
pysäytyskoodia vastaavat solmut löytyvät. Jokainen solmu
käydään läpi, joten aikavaativuus on O(m). Rekursiotasoja
tulee pahimmassa tapauksessa m kappaletta, joten
myös tilavaativuus on O(m).

Seuraavaksi jokainen syötteen tavu koodataan.
koodaaTavu-funktio käy läpi kaikki solmut koodattavaa
tavua vastaavasta solmusta juureen. Pahimmassa tapauksessa
solmu on korkeudella m - 1, joten aikavaativuus on O(m).
Koska funktiota kutsutaan jokaiselle syötteen tavulle,
kaikkien tavujen läpikäymisen aikavaativuus on O(nm).
koodaTavu-funktion pahimman tapauksen aikavaativuus ei
kuitenkaan voi olla yleinen, sillä Huffmannin koodauksessa
yleisimpien tavujen solmut ovat matalalla puussa.
Rekursiotasoja tulee pahimmassa tapauksessa m kappaletta,
joten tilavaativuus on O(m).

Yhteensä aikavaativuus on siis O(m) + O(nm) = O(nm).
Tilavaativuus = O(m) + O(m) = O(m). Bittivirran sisältö
on paluuarvo, joten sen koko ei vaikuta tilavaativuuteen.

Koko tiivistyksen aikavaativuus on siin O(n) + O(m log m) +
O(nm). Koska m on ylhäältä rajoitettu, kokonaisuudessa
saadaan O(n). Apufunktioden tilavaativuus on yhteensä
O(m). Huffmannin koodauksen sisältämän puun tilavaativuus on
myös O(m). Paluuarvon koko on syötteen koko kerrottuna
jollain vakiolla, joten sen koko on O(n). Tilavaativuus on
siis O(n).

### Tiivistyksen purku
Tiivistyksen purussa käytetään aikaisemmin kuvailtua
Solmua sekä BittiVirtaa, josta tässä pseudokoodissa voidaan
myös lukea. Syötteenä oleva tieto on pseudokoosissa
muuttujassa data.
 ```
input = new BittiVirta(data)
output = new BittiVirta()
juuri = lueSolmu(bittiVirta)

ikuisesti:
    solmu = juuri
    niin kauan kun solmu ei ole lehti:
        bitti = bittiVirta.lueBitti()
        jos bitti = 1
            solmu = solmu.vasenLapsi()
        muuten:
            solmu = solmu.oikeaLapsi()
    jos solmu.onPysäytysKoodi():
        return output.sisältö
    muuten:
        output.kirjoita(solmu.tavu)

lueSolmu(virta):
    bitti = virta.lueBitti()
    jos bitti on 0:
        vasenLapsi = lueSolmu(virta)
        oikeaLapsi = lueSolmu(virta)
        return Solmu.oksa(vasenLapsi, oikeaLapsi)
    muuten:
        bitti = virta.lueBitti()
        jos bitti = 0:
            tavu = virta.lueTavu()
            return Solmu.lehti(tavu)
        muuten:
            return Solmu.pysäytysKoodi()
```

Ensiksi lueSolmu-funktiolla luetaan tiivistetyn tiedon
alusta otsake, joka sisältää koodauksen. Kaikki otsakkeen
solmut luetaan, joten aikavaatimus on pahimmassa tapauksessa
O(m). Pahimmassa tapauksessa rekursiotasoja tulee m - 1,
joten tilavaativuus on O(m).

Ikuisessa silmukassa jokaista syötteen bittiä kohden tehdään
siirrytään puussa alaspäin, ja jokaista alkuperäisen
tiedoston tavua kohden kirjoitetaan yksi tavu tulosteeseen.
Aikavaativuus on siis O(n). Tilavaativuus on vakio.

Yhteensä aikavaativuus tiivistystä purkaessa on siis
O(m) + O(n). Koska m on ylhäältä rajoitettu, aikavaativuus
on siis O(n). Tilavaativuuteen vaikuttaa kahden vaiheen
lisäksi koodauksen sisältävän puun koko, joka on O(m), ja
tulosteen koko, joka on O(n). Yhteensä tilavaativuus on siis
myös O(n).

## Puutteet ja parannusehdotukset

Käyttöliittymässä on paljon parannettavaa. Se,
tiivistetäänkö vai puretaanko annettua tiedosto määräytyy
tiedoston päätteen mukaan. Olisi hyvä, jos sen voisi
määrittää vivulla. Performance test-toiminnossa voisi
määrittää tiivistysten ja purkujen toistojen määrät.
Komentoriviltä ei myöskään voi saada mitään käyttöohjeita.
-t-vivun kanssa ohjelma tulostaa tällä hetkellä sekä
tiivistämiseen että purkamiseen liittyviä aikoja riippumatta
siitä, puretaanko vai tiivistetäänkö.

Ohjelma ei tällä hetkellä pysty tiivistämään eikä purkamaan
mielivaltaisen kokoista tiedosto, sillä tiedoston pitäisi
sekä mahtua muistiin että javan taulukkoon, joka
käyttää 32-bittistä indeksiä. Taulukon voisi korvata
omalla taulukolla, jolla voisi käyttää suurempia indeksejä.
Muistin loppumisen voisi estää sillä, että koko tiedostoa
ei lueta kerralla muistiin. Tällöin tosin tiedosto tosin
pitäisi lukea levyltä kaksi kertaa tiivistettäessä.

Koodissa on myös varmasti paljon optimoitavaa.

## Lähteet

1.[https://web.stanford.edu/class/archive/cs/cs106b/cs106b.1126/handouts/220%20Huffman%20Encoding.pdf](https://web.stanford.edu/class/archive/cs/cs106b/cs106b.1126/handouts/220%20Huffman%20Encoding.pdf)
2.[http://math.mit.edu/~goemans/18310S15/huffman-notes.pdf](http://math.mit.edu/~goemans/18310S15/huffman-notes.pdf)
3.[https://en.wikipedia.org/wiki/Huffman_coding](https://en.wikipedia.org/wiki/Huffman_coding)
