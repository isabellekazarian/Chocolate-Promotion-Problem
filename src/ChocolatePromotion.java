/*
 * Author: Isabelle Kazarian
 * Date Created: 1/13/2021
 * Date Last Modified: 1/18/2021
 * Reference: Adapted from https://www.hackerrank.com/challenges/chocolate-feast/problem
 */

import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class ChocolatePromotion {

    /**
     * Reads order data from a file
     * @param inputPath the path of the input file
     * @return The list of Orders read from file
     */
    public static ArrayList<Order> readOrderFile(String inputPath) {

        try {

            Scanner inputScanner = new Scanner(new File(inputPath));

            // Consume heading, return if no order data
            inputScanner.nextLine();
            if(!inputScanner.hasNextLine()) {
                return null;
            }

            // Read order data
            System.out.println("Reading input data...");
            ArrayList<Order> OrderList = new ArrayList<>();
            int numOrders = 0;

            while(inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
                if(line.isEmpty()) {
                    continue;
                }

                // Read line data
                String[] lineSplit = line.split(", ");
                int cash = Integer.parseInt(lineSplit[0]);
                int price = Integer.parseInt(lineSplit[1]);
                int wrappers = Integer.parseInt(lineSplit[2]);
                String typeInput = lineSplit[3].strip().toLowerCase().replaceAll("\'", "");

                // Validate chocolate type
                ChocType chocType;
                switch(typeInput){
                    case "milk": chocType = ChocType.MILK;
                        break;
                    case "dark": chocType = ChocType.DARK;
                        break;
                    case "sugar free": chocType = ChocType.SUGAR_FREE;
                        break;
                    case "white": chocType = ChocType.WHITE;
                        break;
                    default:
                        System.out.println("Error: Invalid chocolate type. Please check order file.");
                        return null;
                }

                // Add data to orders list
                OrderList.add(new Order(cash, price, wrappers, chocType));
                numOrders++;

            }
            inputScanner.close();

            System.out.println(numOrders + " orders read successfully.");
            return OrderList;


        } catch (FileNotFoundException e) {
            System.out.println("Error:  File \'" + inputPath + "\' not found ");
            return null;

        } catch (NumberFormatException e) {
            System.out.println("Error reading input file. Please check order format is:  \'cash, price, wrappers needed, type\' .");
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes existing output file
     * @param outputPath the path of the output file to delete
     */
    public static void clearOutputFile(String outputPath){
        File outputFile = new File (outputPath);
        outputFile.delete();
    }

    /**
     * Writes chocolate data from the EnumMap to the output file
     * @param chocolates EnumMap of chocolate types and corresponding data
     * @param outputPath path of the output file
     */
    public static void appendToOutput(EnumMap<ChocType, ChocData> chocolates, String outputPath){

        try{

            FileWriter outputFw = new FileWriter(outputPath, true);

            outputFw.write(
                    "milk " + chocolates.get(ChocType.MILK).getTotalReceived() +
                        ", dark " + chocolates.get(ChocType.DARK).getTotalReceived() +
                        ", white " + chocolates.get(ChocType.WHITE).getTotalReceived() +
                        ", sugar free " + chocolates.get(ChocType.SUGAR_FREE).getTotalReceived() + "\n"
            );
            outputFw.close();


        } catch (Exception e) {
            System.out.println("File write error.");
            e.printStackTrace();
        }

    }

    /**
     * Determines if any chocolates have enough wrappers to trade
     * @param chocolates EnumMap of chocolate types and corresponding data
     * @param wrappersNeeded number of wrappers needed to make trade
     * @return true if any chocolates have enough wrappers to trade
     */
    public static boolean canMakeTrade(EnumMap<ChocType, ChocData> chocolates, int wrappersNeeded){

        for(ChocType type : ChocType.values()){
            if (chocolates.get(type).getNumWrappers() >= wrappersNeeded){
                return true;
            }
        }
        return false;
    }

    /**
     * Trades maximum possible wrappers for the given chocolate type
     * @param chocolates EnumMap of chocolate types and corresponding data
     * @param type Type of chocolate being traded
     * @param wrappersNeeded number of wrappers needed to make a trade
     */
    public static void tradeWrappers(EnumMap<ChocType, ChocData> chocolates, ChocType type, int wrappersNeeded){

        // Check that there are enough wrappers to trade
        int numWrappersToTrade = chocolates.get(type).getNumWrappers();
        if(numWrappersToTrade < wrappersNeeded){
            return;
        }

        // Make base trade
        int numChocReceived = numWrappersToTrade / wrappersNeeded;
        int numWrappersRemaining = numWrappersToTrade % wrappersNeeded;

        chocolates.get(type).setNumWrappers(numWrappersRemaining + numChocReceived);
        chocolates.get(type).addToTotalReceived(numChocReceived);


        // "Bonus" promotional chocolates
        switch(type){
            case MILK:
            case WHITE:
                chocolates.get(ChocType.SUGAR_FREE).addToTotalReceived(numChocReceived);
                chocolates.get(ChocType.SUGAR_FREE).addToNumWrappers(numChocReceived);
                break;

            case SUGAR_FREE:
                chocolates.get(ChocType.DARK).addToTotalReceived(numChocReceived);
                chocolates.get(ChocType.DARK).addToNumWrappers(numChocReceived);
                break;

            }
        }

    /** Runs promotion & returns data for a single order
     * @param order the order to run the promotion on
     * @return EnumMap of chocolate types and corresponding data
     */
    public static EnumMap<ChocType, ChocData> runPromotion(Order order){

        // Create map of Chocolate Types
        EnumMap<ChocType, ChocData> chocolates = new EnumMap<>(ChocType.class);
        chocolates.put(ChocType.MILK, new ChocData());
        chocolates.put(ChocType.DARK, new ChocData());
        chocolates.put(ChocType.WHITE, new ChocData());
        chocolates.put(ChocType.SUGAR_FREE, new ChocData());

        // Process initial purchase
        if(order.getCash() < order.getPrice()){
            return chocolates;
        }

        final int NUM_WRAPPERS_NEEDED = order.getWrappersNeeded();
        int numPurchased = order.getCash() / order.getPrice();
        ChocType chocType = order.getType();


        // Initialize number of chocolates & wrappers to order
        chocolates.get(chocType).setTotalReceived(numPurchased);
        chocolates.get(chocType).setNumWrappers(numPurchased);

        if(numPurchased < NUM_WRAPPERS_NEEDED){
            return chocolates;
        }

        // Make all trades for this order
        do{
            for(ChocType type : ChocType.values()) {
                tradeWrappers(chocolates, type, NUM_WRAPPERS_NEEDED);
            }

        } while(canMakeTrade(chocolates, NUM_WRAPPERS_NEEDED));


        return chocolates;

    }


    /**
     * Driver
     */
    public static void main (String[] args){

        final String INPUT_PATH = "input/orders.txt";
        final String OUTPUT_PATH = "output/output.txt";


        // Read & validate input
        ArrayList<Order> orderList = readOrderFile(INPUT_PATH);

        if(orderList == null){
            System.out.println("Data read error. Program terminated.");
            System.exit(1);
        }


        // Run promotion on each order and add to output file
        clearOutputFile(OUTPUT_PATH);

        System.out.println();
        System.out.println("Processing orders...");
        int ordersProcessed = 0;

        for(Order order : orderList){
            appendToOutput(runPromotion(order), OUTPUT_PATH);
            ordersProcessed++;
            System.out.print(ordersProcessed + "... ");
        }

        System.out.println("\n");
        System.out.println(ordersProcessed + " orders processed successfully. View " + OUTPUT_PATH + " for details.");
    }
}

