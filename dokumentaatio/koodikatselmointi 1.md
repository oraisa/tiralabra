Repositorio kloonattu 7.1 kello 14:55.

Set-luokka ei tällä hetkellä näytä tarkistavan,
onko lisättävä alkio jo joukossa. Yleensä Set-nimiset
kokoelmat ovat nimenomaan joukkoja, eli saman alkioin
lisääminen useamman kerran ei pitäisi vaikuttaa joukkoon.
Tietysti jos luokkaa käyttävä koodi ei missään vaiheessa
yritä lisätä samaa alkiota useamman kerran, tarkistuksen
voi jättää pois ohjelman nopeuttamiseksi. Myös Set-luokan
sijainti graph-nimisessä paketissa on vähän outoa, koska
ei joukko varsinaisisti liity verkkoihin.

Jos käyttää versioinhallintaa kuten gittiä kommentoitua
koodia ei kannata jättää lojumaan, vaan sen voi hyvin
poistaa. Jos koodia tarvitsee myöhemmin, vanhaan tilaan
pääsee git checkout-komennolla.

Testejä kannattaa olla enemmän, nyt ainakaan
FloydWarshall ja GraphCons-luokilla ei näytä olevan
ollenkaan testejä.

MAGICK_SEVENTY ei ole yhtään kuvaavampi nimi vakiolle
70 kuin pelkästään 70. Sille olisi hyvä keksiä parempi
nimi.

Muualta haettuihin esimerkkeihin on hyvä laittaa lähde.
Ainakin Belgian kaupunkeja kuvaava verkko näyttää olevan
tälläinen.

AdjacenyList-luokan nimessä on kirjoitusvirhe.
Adjacency näyttäisi olevan oikein, kuten
AdjacencyMatrix-luokan nimessä.

Tällä hetkellä koodi näyttää ensin laskevan verkon kaikkien solmujen väliset lyhimmät etäisydet ja sitten muodostavan pienemmän verkon, jossa on ainoastaan somut, joissa täytyy käydä. Voisiko olla nopeampaa laskea ainoastaa niiden solmujen, joissa täytyy käydä, väliset lyhimmät etäisyydet?
