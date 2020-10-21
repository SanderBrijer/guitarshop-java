package nl.inholland.GuitarShop_Models;

import java.time.LocalDate;

public class AfgerondeBestelling {
    private static Integer Id;
    private Bestelling bestelling;
    private LocalDate datum;
    private Integer id;

    public AfgerondeBestelling(Bestelling bestelling)
    {
        this.bestelling = bestelling;
        this.datum = LocalDate.now();
        if (this.Id == null)
        {
            this.Id = 0;
        }
        else
        {
            this.Id = Id++;
        }
        this.id = this.Id;
    }

}
