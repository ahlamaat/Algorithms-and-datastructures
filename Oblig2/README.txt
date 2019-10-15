1. Man kompilerer og kjører filen ved å skrive:
      javac *.java && java Oblig2 <prosjektfilen>.txt

2. Main-metoden finner du i Oblig2 klassen.

3. Selve prosjekt-klassen kan sammenliknes med en graf-klasse, mens task-klassen
  kan sammenliknes med en node-klasse.

4. Jeg valgte å ha task-klassen som en indre klasse i Project. I tillegg er det verdt
å bemerke at jeg "juksa" litt ved å ha en "startTak" (nesten som en rotnode).
Dette funker bra med disse tekstfilene, men jeg vet at hasCycle() ville ha hatt problemer
med å finne løkker hvis vi fikk utdelt andre tekstfiler. 

5. Når jeg kjører programmet, så fungerer alt (tror jeg).

6. hasCycle() og topSort() er "inspirert" av pseudokoden fra Graf1- foilene.
