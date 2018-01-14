# Testaus

## Suorituskykytestaus

Suorituskykytestausta tehtiin kaksi osaa. Ensin
tämän kuvan eri kokoja tiivistettin ja purettiin.
![Huffmannin koodauksen visualisointi](https://upload.wikimedia.org/wikipedia/commons/a/a0/Huffman_coding_visualisation.svg)
Kuvien koot olivat
[320x240](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Huffman_coding_visualisation.svg/320px-Huffman_coding_visualisation.svg.png),
[512x384](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Huffman_coding_visualisation.svg/512px-Huffman_coding_visualisation.svg.png),
[800x600](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Huffman_coding_visualisation.svg/800px-Huffman_coding_visualisation.svg.png),
[1024x768](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Huffman_coding_visualisation.svg/1024px-Huffman_coding_visualisation.svg.png) ja
[1280x960](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/Huffman_coding_visualisation.svg/1280px-Huffman_coding_visualisation.svg.png).
Ennen tiivistystä png-muotoiset kuvat muutettiin
bmp-muotoisiksi, jotta ne eivät olisi valmiiksi
tiivistettyjä. Tiivistys tapahtui ohjelman performance-test
toiminnolla. Ohjelman saamat tulokset lyötyvät kansiosta
dokumentaatio/suorituskykytestaus. Tiedostojen nimet ovat
kuvat-purku.csv ja kuvat-tiivistys.csv.

http://corpus.canterbury.ac.nz/descriptions/

Myös neljää muuta tiedostoa tiivistettiin ja purettiin.
Tiedostot ovat kuvan
![](https://upload.wikimedia.org/wikipedia/commons/7/74/Huffman_coding_example.svg)

[277x133](https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Huffman_coding_example.svg/277px-Huffman_coding_example.svg.png)
kokoinen versio png- ja bmp-tiedostoina,
[Hamlet](https://www.gutenberg.org/files/1524/1524-0.txt)
sekä
[Sota ja rauha](https://www.gutenberg.org/files/2600/2600-0.txt)
englanniksi. Tulokset ovat alla.

| Tiedosto | Koko | Koko tiivistettynä | Pienennys| Koodauksen kesto | Koodauksen purun kesto |
|:--------:|:----:|:------------------:|:--------:|:----------------:|:----:|
| Hamlet   |205 KB|126 KB|39 %|11 ms|10 ms|
| Sota ja rauha|3,4 MB|2 MB|42 %|156 ms|145 ms|
| Kuva bmp |148 KB| 22KB|85 %|1,6 ms|2 ms|
| Kuva png |4 KB|4 KB|-8 %|< 1 ms| < 1 ms|

Kaikki ajat ovat keskiarvoja 20 mittauksesta. Koot ovat pyöristyksiä,
pienennys on laskettu tarkoista koista. Huffmannin koodauksen laskemiseen
meni lähes kaikilla mittauskerroilla alle millisekunti. Koodaus viittaa
tiedoston tavujen korvaamiseen niiden Huffmannnin koodeilla ja koodauksen
purku koodien korvaamiseen niitä vastaavilla tavuilla.
