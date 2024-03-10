package sporttagapp.documentservice.dataclasses;


import java.time.LocalDate;
import java.util.Date;

public class Student {
    private String gender;
    private String vorname;
    private String nachname;
    private int klasseZahl;
    private String klasseBuchstabe;
    private String geburtstag;
    private String sportklasse;
    private String lehrpersonKuerzel;

    public Student(String gender, String vorname, String nachname, int klasseZahl, String klasseBuchstabe,
                   String geburtstag, String sportklasse, String lehrpersonKuerzel) {
        this.gender = gender;
        this.vorname = vorname;
        this.nachname = nachname;
        this.klasseZahl = klasseZahl;
        this.klasseBuchstabe = klasseBuchstabe;
        this.geburtstag = geburtstag;
        this.sportklasse = sportklasse;
        this.lehrpersonKuerzel = lehrpersonKuerzel;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public int getKlasseZahl() {
        return klasseZahl;
    }

    public void setKlasseZahl(int klasseZahl) {
        this.klasseZahl = klasseZahl;
    }

    public String getKlasseBuchstabe() {
        return klasseBuchstabe;
    }

    public void setKlasseBuchstabe(String klasseBuchstabe) {
        this.klasseBuchstabe = klasseBuchstabe;
    }

    public String getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(String geburtstag) {
        this.geburtstag = geburtstag;
    }

    public String getSportklasse() {
        return sportklasse;
    }

    public void setSportklasse(String sportklasse) {
        this.sportklasse = sportklasse;
    }

    public String getLehrpersonKuerzel() {
        return lehrpersonKuerzel;
    }

    public void setLehrpersonKuerzel(String lehrpersonKuerzel) {
        this.lehrpersonKuerzel = lehrpersonKuerzel;
    }
}
