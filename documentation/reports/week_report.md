# Viikkoraportit

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
