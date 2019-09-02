# Ruota della Fortuna
### Preparazione di Gradle
Per la prima esecuzione di gradle digitare da riga di comando sui sistemi Unix:
```
./gradlew
```
Nel caso desse problemi di esecuzione del file dare il comando:
```
$ sudo chmod 775 gradlew
```
Per i sistemi Windows invece eseguire il comando:
```
.\gradlew.bat
```
### Preparazione del programma
Per la preparazione del programma dare i comandi Unix:
```
./gradlew buildAll
```
in Windows:
```
.\gradlew.bat buildAll
```
Questo comando eseguirà il build e aprirà una finestra per la creazione del database e delle relative tabelle. Completare l'esecuzione inserendo l'indirizzo del server di postgres con la relativa porta in uso e le credenziali dell'user di postgres.

### Esecuzione del programma
Per l'esecuzione del programma bisogna eseguire come prima cosa il server con il comando:
```
./gradlew runServer
```
in Windows
```
.\gradlew.bat runServer
```
Dopo di è possibile eseguire i client con il comando:
```
./gradlew runPlayer
```
in Windows
```
.\gradlew.bat runPlayer
```

### Gestione amministrativa del gioco
Per la gestione amministrativa del gioco eseguire il comando:
```
./gradlew runAdmin
```
in Windows
```
.\gradlew.bat runAdmin
```