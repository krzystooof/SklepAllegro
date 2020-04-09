package pl.krzystooof.sklepallegro.data;

import java.util.List;

public class Offers {
    List<Offer> offers;

    public Offers(List<Offer> offers) {
        this.offers = offers;
    }


    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
