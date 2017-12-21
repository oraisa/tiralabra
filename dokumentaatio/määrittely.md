# Määrittelydokumentti

## Algoritmi

Algoritmi tiivistää tiedoston korvaamalla kunkin aakkosen
vastaavalla Huffman-koodilla. Tiedoston palauttaminen
onnistuu lukemalla kunkin aakkosen koodi ja korvaamalla
koodit aakkosilla. Tätä varten koodit täytyy tietysti
kirjoittaa tiedostoon.

Tavoitteena on että ainakin megatavun kokoisen tar-tiedoston,
joka sisältää pääosin tekstiä, koko saadaan puolitettua. Algoritmin tulee kuitenkin toimia millä tahansa tiedostolla.

## Tietorakenteet
Kutakin aakkosta vastaavan koodin tallentamiseen tarvitaan
HashMap. Huffman-koodien määrittämisessä käytetään puuta,
mutta sitä ei välttämättä tarvitse toteuttaa erillisenä
tietorakenteena.

## Syöte ja tuloste
Ohjelma saa joko syötteenä tiivistettävän tiedoston ja
tulostaa tiivistetyn tiedoston tai saa syötteenä tiivistetyn
tiedoston ja tulostaa alkuperäisen tiedoston.

## Tavoiteaika- ja tilavaativuudet
Sekä tiedoston tiivistämisen että palauttamisen pitäisi
onnistua ajassa O(n) ja tilassa O(n), kun n on tiedoston
koko. Tiivistäessä tiedosto täytyy käydä läpi kaksi kertaa,
ja palauttaessa kerran.

Tiivistämisessä tehtävän Huffman-koodien määrityksen
aika- ja tilavaativuudet riippuvat aakkosten määrästä.
Kun m on aakkosten määrä, aikavaativuudeksi pitäisi saadaa
O(m^2), sillä jokaista aakkosta kohti täytyy käydä kaikki
aakkoset läpi kerran. Tilavatiivudeksi pitäisi tulla O(m).
m on kuitenkin merkittävän kokoisilla tiedostoilla paljon
pienempi kuin n, joten näillä vaativuuksilla ei luultavasti
ole merkitystä muilla kuin pienillä tiedostoilla.

## Lähteet
1. [http://math.mit.edu/~goemans/18310S15/huffman-notes.pdf](http://math.mit.edu/~goemans/18310S15/huffman-notes.pdf)
2. [https://en.wikipedia.org/wiki/Huffman_coding](https://en.wikipedia.org/wiki/Huffman_coding)
