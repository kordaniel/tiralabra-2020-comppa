# Viikkoraportit

## [Viikko 5](#viikko-5)
Tällä viikolla tein ainoastaan vertaisarvionnin. Aikaa käytin tähän noin 3h.

Erinäisistä syistä nyt parin viikon aikana en ole onnistunut löytämään tarpeeksi aikaa ja motivaatiota edistää projektia. Haluan kuitenkin tehdä kurssin loppuun ja toivottavasti hyväksyttävästi, joten nyt viikolla 6 aion jatkaa projektia. Koska mikään ei ole oikein edennyt, niin viikon 3 raportti pätee yhä.

Sain vertaisarvionnissa ihan hyvän linkin siitä, miten voisin tallentaa tuon huffmanin puun. Yritän implementoida sen ensin itsenäisesti, ja jos sitten koen asialle vielä tarvetta, niin voisin yrittää varata zoom-palaverin (kuten ohjaajan palautteessa viikolla 3 luki).  

## [Viikko 4](#viikko-4)
Ei edistystä. Puoli tuntia käytetty testien tekemiseen.

## [Viikko 3](#viikko-3)
Tällä viikolla oli tarkoitus viimeistellä HuffMan-koodaus ja saada sille aikaan testit. Koska päätin jo tässä vaiheessa keskittyä käsittelemeään tiedostoja binäärimuodossa tekstimuodon sijaan, niin kävikin niin, että en saanut sitä ihan valmiiksi. Huffman-koodaus kyllä toimii, kuten myös koodauksen purku, seuraavaksi pitää keksiä miten merkkien prefix-/frekvenssi-puun saisi sellaiseen muotoon, että voisin luoda pakattuja tiedostoja siten, että ne saisi myös purettua. Sen sijaan loin Bitarray-luokan, eli bittivektorin, jonka avulla voidaan käsitellä yksittäisiä bittejä. Nyt Huffman-koodaus tehdään täysin käyttämättä merkkijonoja. Bitarray-luokka on aika valmis nyt ja sen testikattavuus kävi jo 100-prosentissa, mutta ihan viikon lopussa jouduin laajentamaan luokkaa hieman ja testikattavuus laski. Tällä hetkellä luokan BitArray testikattavuus on siis noin 70%. Testien avulla onnistuin myös löytämään bugin tästä luokasta. Tarkistin, onko tietty bitti asetettu tekemällä ensin bittisiirron niin, että tietty bitti siirretään laitaan, jonka jälkeen sille tehdään and (&)-operaatio maskin kanssa, jonka jälkeen tarkistin onko tuloksena suurempi kuin 0, milloin oletin bitin olevan asetettu. Tämähän ei tietenkään toiminutkaan, kun javan longit ovat unsigned-tyyppisiä. Muutin vertailun tarkistamaan onko tuloksena saatu luku eri kuin 0, ja nyt kaikki käsittääkseni toimii. Tämän lisäksi loin ByteBuffer luokan, jolle on myös olemassa kattavat yksikkötestit. Luokkaa ei vielä käytetä mihinkään, mutta tarkoitusena on käyttää sitä tavujen rakentamiseen tiivistetyn huffman-koodin tallentamista varten.  

Pakkausta ja purkua on testattu niin, että ensin luetaan tiedosto muistiin, sitten pakataan kaikki tavut Huffman-koodatuksi bittijonoksi jonka jälkeen tämä bittijono puretaan ja tallennetaan tavut uudelleen eri nimiseksi tiedostoksi. Pakattua bittijonoa ei siis vielä tallenneta tiedostoon, ainoastaan alkuperäistä tiedostoa vastaava puretty tavu-taulukko. Pakkaaminen ja purku on testattu toimiviksi sekä Javan puolella vertailemalla tavu-taulukoita, kuten myös linuxin puolella käyttämällä cmp- ja diff-binäärejä. Tekstimuotoisia tiedostoja sain menemään (muistaakseni) noin 30-70% pienempään tilaan, tämähän tietysti riippuu eri merkkien frekvensseistä, kuinka tasainen jakauma niillä on. Toisaalta testasin myös esim pdf-tiedostojen pakkausta, jolloin tilansäästö oli vain muutamia prosentteja.  

#### Mietintöjä
* Käsittääkseni bittivektorini on aika tehokas, ja käytännössä kaikki metodit toimivat O(1)-ajassa. Ihan varma en tästä kuitenkaan ole, joten tätä pitää miettiä. Etenkin siihen miten O-notaatio suhtautuu taulukon kasvattamiseen, jos tila loppuu kesken.
* Jossain vaiheessa mieleeni juolahti sellainen asia, että kenties bittejä ja tavuja voisi säilöä jonkunlaisessa linkitetyssä listassa, josta voisi myös luoda tavuja aina kun sen pituus kasvaa kahdeksaan bittiin. Tämä voisi ehkä olla tehokkaampaa. Kenties teen tällaisen version kunhan saan tämän nykyisen ensin valmiiksi, ja vertailen näiden tehokkuuseroja?
* Ehkä kannattaisi siirtyä käyttämään vain booleaneita merkitsemään eri bittien tilaa (1 vai 0), tällöin muistinkäyttö lisääntyisi, mutta bittioperaatioilta säästyisi ja koodi voisi toimia nopeammin?
* Tavuja voisi ehkä luoda eri prosessissa rinnakkain samalla kun lasketaan huffman-koodausta, mikä saattaisi parantaa suorituskykyä?
* Miten voisin tehostaa tavujen indeksointia eri taulukoissa. Javan byte kun voi olla negatiivinen, toisin kuin taulukon indeksi. Nyt tavuja muutetaan kokonaisuluvuiksi ja jossain vaiheessa taas takaisin tavuiksi, mikä ei varmaan ole kovin tehokasta.  

Suurin kysymys tällä hetkellä on se, että miten tuo Huffman-puu kannattaa tallentaa tiedostoon, sitä kun tarvitaan purkamiseen. Tämä on pakko selvittää ensi viikolla, kenties ohjaajalla olisi tarjota joku hyvä vinkki? :)  


Tällä viikolla käytin varmaan reilut 12h ohjelman eteen. Huomaan tässä vaiheessa, että 3/7 viikkoa on jo mennyt, enkä ole saanut kovin paljon koodia aikaiseksi joten ensi viikon ensisijaiseksi tavoitteeksi voisi asettaa ajankäytön lisääminen. Toisisijaiseksi tavoitteeksi on pakko asettaa Huffman-koodaukselle testien luomien sekä tosiaan tuon puun tallennuksen selvittämien. Tämän jälkeen voi varmaan keskittyä suorituskyvyn optimointiin.  


## [Viikko 2](#viikko-2)
Tällä viikolla jaottelin kooodia pakkauksiin, configuroin gradlea, checkstyle on konfiguroitu gradleen ja lisäksi gradlen binääri on mukana repossa. Yllättävän vaikea asia saada kaikki toimimaan, ohjeita on vaikea löytää kun gradlen eri versiot ilmeisesti toimivat hieman eri tavoin. Taistelin myös pitkään JAVA:n versioiden kanssa fuksiläppärillä. Nyt gradle käyttää javan versiota 11, joka on pitkäaikaistuettu ja ilmeisesti tuorein versio, mitä kannattaa käyttää. Tällöin tietysti Netbeans sekosi täysin ja siirryin IntelliJ IDEA:n käyttäjäksi, hetken kesti opetella sen käyttöä ja miten konfiguroida se, mutta nyt kaikki toimii.  

Toiminnallisuuden osalta niin koodasin luokan, jonka avulla voidaan lukea ja kirjoittaa tiedostoja binäärimuodossa. Eli siis ohjelma tulee käyttämään primitiivisiä byte-taulukoita. Tämä on oikeastaan ainut "valmis" palanen koko ohjelmasta.  

Muutoin, niin jaottelin koodia pakkauksiin ja muutin huffman-koodauksen käyttämään tavuja. Lasken tavujen frekvenssejä taulukkoon, mikä tietysti käyttää positiivisia kokonaislukuja indeksinä. Siten joudun tällä hetkellä muuttamaan tavut kokonaisluvuiksi ja sitten myöhemmin takaisin tavuiksi. Tätä pitää vielä miettiä, että voisiko tätä tehostaa. Opin myös sen, että joudun implementoimaan jonkunlaisen bittivektorin, minkä avulla voin rakentaa huffman-koodauksen prefixejä.

Testejä en ole vielä tehnyt, sillä ainut "valmis" palikka on tiedostojen lukeminen ja kirjoittaminen ja ne funktiot ovat sen verran yksinkertaisia toiminnallisuudeltaan ja toisaalta monimutkaisia testata, että en mielelläni käyttäisi siihen aikaa, jos sitä ei vaadita kurssin puolelta.  

Käytin tällä viikolla liian vähän aikaa projektiin, ja käytetystä ajasta aivan liian suuren osan gradlen ja kehitysympäristön kanssa taistelemiseen. Yhtensä olen varmaan käyttänyt noin 7-8h projektin edistämiseen.

Ensi viikon tavoitteeksi olisi varmaan hyvä asettaa Huffman-koodauksen viimeisteleminen sekä testien luominen sen eri funktioille. Jos tähän ei kulu aivan liikaa aikaa, niin tietysti ehtisin tehdä jotain muutakin. Tähän liittyen olisi kysymys; onkohan parempi strategia lähteä tekemään omia tietorakenteita tässä vaiheessa ja sitten myöhemmin katsoa muita tiivistämisalgoritmeja, vai kannattaakohan minun toimia toisin päin, eli että ensin implementoin jonkun toisen algoritmin käyttäen javan valmiita tietorakenteita, jonka jälkeen vasta lähden toteuttamaan omiani? Tällöin saisin helposti vertailutuloksia ohjelman tehokkuudesta käyttäen javan tietorakenteita, mitä vastaan voisin verrata omiani, mutta tietysti riski sille etten ehdi koodata kaikkia tietorakenteita itse kasvaa. Varmaan teillä ohjaajille on kokemusta aiheesta? :)

## [Viikko 1](#viikko-1)
Tällä viikolla luin kurssimateriaaleja, valitsin aiheen, alustin projektin käyttäen git:iä sekä gradlea. Päätin siis valita aiheeksi tiedon tiivistämisen ja olen lukenut määrittelydokumentissa listaamiani lähteitä. Jouduin myös kertaamaan tira-kurssin materiaalia kekojen osalta, sillä kävin kurssin jo syksyllä 2018. Aloitin itse koodaamisen ihan kevyesti koodamalla pari funktiota, joista ensimmäinen luo satunnaisen merkkijonon ja toinen laskee merkkien frekvenssejä. Sen lisäksi aloitin testaamaan merkkien huffmann-koodaamista käyttäen hyväksi seuraavaa lähdettä aika surutta: https://www.geeksforgeeks.org/huffman-coding-greedy-algo-3/

Käytin noin 5-6h aikaa tähän.

Suurin epäselvyys tässä vaiheessa taitaa olla se, että minkälaisia tiedostoja pystyn pakkaamaan. Tällä tarkoitan siis minkä muotoisia tiedostoja. Olisi tietysti kivaa, jos ohjelmani osaisi käsitellä kaikenlaisia tiedostoja, eli suoraan binäärimuodossa olevia. En ole vielä valinnut varsinaisia pakkausalgoritmeja, muutoin kun huffman-koodauksen joka ilmeisesti toimii parhaiten jonkun toisen algoritmin “lisänä”. 
