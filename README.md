# Naloga 1
Izvajan je bil program, ki obrne vrstni red črk v besedah, zamenja sode in lihe
besede (prvo in drugo, tretjo in četrto,...) v povedi, obrne vrstni red povedi ter obrne vrstni red lihih
odstavkov (medtem ko sodi odstavki ostanejo na mestu). Napiši program, ki bo pretvoril besedilo v
prvotno stanje.

# Naloga 2
Simulacija dogajanja v frizerskem salonu. Ko se striženje ene stranke začne, se izvede do konca. Po končanem striženju stranka zapusti salon in
se prične s striženjem naslednjega v vrsti (če le-ta obstaja). Vsako striženje dodatno utrudi frizerja, kar
povzroči podaljševanje časa naslednjih striženj.

# Naloga 3
Vhodna tekstovna datoteka vsebuje logični izraz, sestavljen iz konstant ("TRUE" in "FALSE"), spremenljivk
(simbolična imena, ki začnejo z malo črko, nadaljujejo se pa lahko s poljubnimi črkami ali ciframi), operatorjev
("AND", "OR" in "NOT") in oklepajev.

Implementirajte razred, ki vsebuje metodo main. Metoda v argumentih prejme poti do vhodne in
izhodne datoteke (args[0] in args[1]) in na podlagi prebranega vhoda sestavi binarno izrazno drevo (pri tem se
držite dogovora, da sta operatorja "AND" in "OR" levo asociativna). Ko je drevo zgrajeno, naj se v izhodno
datoteko najprej izpišejo oznake vseh vozlišč drevesa v premem (preorder) vrstnem redu (ločeno z vejicami, brez
presledkov), nato se v novi vrstici izpiše še višina drevesa.

# Naloga 4
Želimo poskrbeti za lepši izris splošnih binarnih dreves (med elementi ni nujna urejenost). V ta namen želimo
vsakemu vozlišču v drevesu določiti koordinati (x,y) v izrisu. Veljajo naslednja pravila:
- Koordinata y je enaka globini vozlišča v drevesu. Koren je na globini 0.
- Za vsako poddrevo s korenom k velja:
    - Koordinate x vozlišč v levem poddrevesu so manjše od koordinate x korena k.
    - Koordinate x vozlišč v desnem poddrevesu so večje od koordinate x korena k.
- Noben par vozlišč v drevesu nima enakih koordinat x.
- Zaloga vrednosti koordinat x je od 0 do N - 1, pri čemer je N število vozlišč v drevesu.

Drevo je podano v tekstovni vhodni datoteki. V prvi vrstici je zapisano celo število N, ki označuje število vozlišč v
drevesu. V naslednjih N vrsticah so zapisani podatki o vozliščih v poljubnem vrstnem redu. Posamezna vrstica je
oblike ID,V,ID_L,ID_R (vse vrednosti so cela števila). ID predstavlja identifikator vozlišča; V je vrednost, zapisana

v tem vozlišču; ID_L je identifikator levega sina; ID_R je identifikator desnega sina. Za identifikatorje velja, da so
enolično določeni. Identifikator -1 označuje prazno poddrevo (vozlišče nima ustreznega sina).
Izhodna datoteka naj vsebuje N vrstic. Posamezna vrstica naj bo sestavljena iz podatkov: vrednost v vozlišču, x
koordinata vozlišča, y koordinata vozlišča (ločeno z vejicama). Vozlišča izpisujte v vrstnem redu, ki ustreza obhodu
drevesa po nivojih - vozlišča izpisujemo nivo po nivo (vozlišča znotraj istega nivoja naj bodo izpisana od skrajno
levega proti skrajno desnem).

Implementirajte razred, ki vsebuje metodo main. Metoda v argumentih prejme poti do vhodne in
izhodne datoteke (args[0] in args[1]), prebere vhodne podatke in sestavi izhodno datoteko.

# Naloga 5
Podana je množica N-točk v 2d prostoru. Vsaka točka je podana s koordinatama (x, y). Točke želimo razdeliti v K
skupin. Razdalja med skupinama S1 in S2 je definirana kot evklidska razdalja med najbližjima točkama t1 iz S1 in
t2 iz S2. Naloga je poiskati dodelitev točk skupinam tako, da so razdalje med skupinami maksimalne. Ideja: na
začetku vsaka točka predstavlja svojo skupino, nato združujemo najbližje skupine dokler ne dobimo zahtevanega
števila skupin.

Implementirajte razred, ki vsebuje metodo main. Metoda v argumentih prejme poti do vhodne in
izhodne datoteke (args[0] in args[1])
