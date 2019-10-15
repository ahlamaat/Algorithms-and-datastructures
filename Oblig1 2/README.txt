- Jeg har tatt i bruk testklassen som ble utgitt. Siden den opprinnelige ikke testet
alt, så la jeg til noen ekstra tester. Når man kompilerer ved hjelp av
denne testklassen, så må man ha med antall tall som argument.

Eksempel: Hvis man skal ha 12 tall, så kompilerer man slik
  javac *.java && java BSTtest 12

- "BSTtest.java" inneholder main-metoden.

- I findInRange-metoden, så antok jeg at det var fra og med low til og med high.

- I findInRange, så brukte jeg en hjelpemetode som returnerer ArrayList. Deretter
kopierte jeg alle elementene over i et array. Kan man unngå dette og kun bruke
array(og fortsatt få perfekt størrelse på arrayet)?
