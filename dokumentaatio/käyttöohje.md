Ohjelman jar-tiedosto löytyy tiedostosta build/tiivistys.jar.
Ohjelman voi suorittaa komennolla
```
java -jar tiivistys.jar
```
Tiedoston tiivistämiseksi täytyy tietysti antaa
tiivistettävän tiedoston nimi. Esimerkiksi
```
java -jar tiivistys.jar tiedosto.txt
```
Esimerkki laittaa tiivistetyn tiedoston työhakemistoon ja
antaa sen nimeksi tiedosto.txt.tiiv.
Tiedoston nimen voi vaihtaa -o-vivulla. Esimerkiksi
```
java -jar tiivistys.jar tiedosto.txt -o tiivistetty.tiiv
```
Jos annetulla tiedostolla on .tiiv-pääte, tiedosto puretaan
tiivistämisen sijasta. Esimerkiksi
```
java -jar tiivistys.jar tiedosto.txt.tiiv
```
Tämä laittaa puretun tiedoston työhakemistoon tiedostoon
nimeltä tiedosto.txt. Vanha samanniminen tiedosto
ylikirjoitetaan. Myös puretun tiedoston nimeä voi muuttaa
samalla tavalla kuin tiivitettävän tiedoston.

Jos ohjelmalle antaa -t-vivun, se tulostaa toimintaansa
liittyviä aikoja. Jotkin ajoista ovat ainoastaan
tiivistämisellä, jotkin vain purkamiselle. Tiivistettäessä
kaikki purkamiseen liittyvät ajat ovat nollia, kuten on
toisinpäin.

Jos ohjelmalle antaa --performance-tests-vivun, se ajaa
suorituskykytestauksia kaikilla annetun hakemiston
tiedostoilla. Esimerkiksi
```
java -jar testi-tiedostot --performance-tests
```
Tämä luo annettuun hakemistoon compressed-files-nimisen
hakemiston. Sen jälkeen se tiivistää ja purkaa jokaista
annetun hakemiston tiedostoa 20 kertaa. Tiivistetyt tiedostot
ja niistä puretut tiedostot sijoitetaan
compressed-files-hakemistoon. Lisäksi testauksen tulokset
tulevat tiedostoihin compression-test.csv ja
uncompression-test.csv. Niissä jokaisella taulukon rivillä
on yhden suorituskerran tulokset.
