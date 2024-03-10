package sporttagapp.documentservice.dataclasses;
import java.util.List;

import sporttagapp.documentservice.dataclasses.Disziplin;
public class Riegenblatt {
    private List<Person> personen;
    private List<Disziplin> disziplinen;
    private String title;
    private String eventTitle;
    private int riegenNr;

    public Riegenblatt(List<Person> personen, List<Disziplin> disziplinen, String title, String eventTitle, int riegenNr) {
        this.personen = personen;
        this.disziplinen = disziplinen;
        this.title = title;
        this.eventTitle = eventTitle;
        this.riegenNr = riegenNr;
    }

    public List<Person> getPersonen() {
        return this.personen;
    }

    public void setPersonen(List<Person> personen) {
        this.personen = personen;
    }

    public List<Disziplin> getDisziplinen() {
        return this.disziplinen;
    }

    public void setDisziplinen(List<Disziplin> disziplinen) {
        this.disziplinen = disziplinen;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventTitle() {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public int getRiegenNr() {
        return this.riegenNr;
    }

    public void setRiegenNr(int riegenNr) {
        this.riegenNr = riegenNr;
    }

}
