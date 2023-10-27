package persistence;

public class JsonDatabase {

    private final AbstractJsonIO jsonIO;

    public JsonDatabase(AbstractJsonIO jsonIO) {
        this.jsonIO = jsonIO;
    }

    //Fungerer som en database ved liknende funksjoner som SELECT, DELETE, etc.  
    //Metodene leser inn en modell av det som lagres i fil (i dette tilfellet en "User"),
    //og kan deretter enten endre på eller hente ut data knyttet til en User-Json-fil.
    //Instansierer objektet ved bruk av AbstractJsonIO instansen.

    //en metode for å endre

    //en metode for å hente informasjon

}
