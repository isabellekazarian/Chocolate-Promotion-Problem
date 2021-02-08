# Chocolate Promotion Problem
This program processes orders for different types of chocolate according to a special promotion. Customers can trade in chocolate wrappers to get free chocolates.

The promotion is as follows:\
There are four chocolate types: `Milk`, `White`, `Dark`, `Sugar Free`\
An order contains: cash on hand, price per chocolate, wrappers, chocolate type
* When the `wrappers` number of any chocolate is traded, the customer receives a free chocolate of the same type. Additionally...
* `Milk` and `White` wrappers traded yield an extra sugar free chocolate
* `Sugar Free` wrappers traded yield an extra dark chocolate
* `Dark` chocolate is premium and has no extra bonus

Customers can continue to trade wrappers and receive free candies until they no longer have enough wrappers to trade.

Given an order, this program calculates the total number of each chocolate the customer will have received after the maximum number of these trades take place.

## Prerequisites
* Java 8

## Getting Started
Check the paths of the following directories in the main method:
* `INPUT_PATH`: File containing the input orders. The format for this file is as follows:
```
cash, price, wrappers needed, type

12, 2, 5, 'milk'
12, 4, 4, 'dark'
6, 2, 2, 'sugar free'
6, 2, 2, 'white'
```
Please note that the heading is for reference only and will not affect the program output.

* `OUTPUT_PATH`: File containing final chocolate data will be created here.

## Using the Program
* Run the main method in `ChocolatePromotion.java`.
* Console output will show status messages as the program runs.
* The final chocolate data will be saved to `OUTPUT_PATH`

## References
* Adapted from [Chocolate Feast on HackerRank](https://www.hackerrank.com/challenges/chocolate-feast/problem)
