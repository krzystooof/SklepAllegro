package pl.krzystooof.sklepallegro.data;

import java.util.ArrayList;

public class Offers {
    private ArrayList<Offer> offers;

    public Offers() {
        this.offers = new ArrayList<>();
    }


    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offer> offers) {
        this.offers = offers;
    }
}
