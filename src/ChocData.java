/*
 * Author: Isabelle Kazarian
 * Date Created: 1/13/2021
 * Date Last Modified: 1/18/2021
 */

public class ChocData {

    private int totalReceived;
    private int numWrappers;

    ChocData(){
        this.totalReceived = 0;
        this.numWrappers = 0;
    }

    public int getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(int totalReceived) {
        this.totalReceived = totalReceived;
    }

    public int getNumWrappers() {
        return numWrappers;
    }

    public void setNumWrappers(int numWrappers) {
        this.numWrappers = numWrappers;
    }

    public void addToTotalReceived(int numToAdd) {
        this.totalReceived += numToAdd;
    }

    public void addToNumWrappers(int numToAdd) {
        this.numWrappers += numToAdd;
    }




}
