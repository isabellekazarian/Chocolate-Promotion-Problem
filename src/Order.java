/*
 * Author: Isabelle Kazarian
 * Date Created: 1/13/2021
 * Date Last Modified: 1/18/2021
 */

public class Order {

    private int cash;
    private int price;
    private int wrappersNeeded;
    private ChocType type;


    public Order(int cash, int price, int wrappersNeeded, ChocType type){
        this.cash = cash;
        this.price = price;
        this.wrappersNeeded = wrappersNeeded;
        this.type = type;
    };



    public int getCash() {
        return cash;
    }

    public int getPrice() {
        return price;
    }

    public int getWrappersNeeded() {
        return wrappersNeeded;
    }

    public ChocType getType() {
        return type;
    }

}
