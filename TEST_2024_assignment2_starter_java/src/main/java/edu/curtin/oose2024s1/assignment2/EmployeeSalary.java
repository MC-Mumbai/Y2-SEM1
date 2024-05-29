package edu.curtin.oose2024s1.assignment2;

import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;

public class EmployeeSalary {
    private static final long PAY_INTERVAL = 8000; // 7 seconds in milliseconds
    private static final int SALARY_AMOUNT = 1000;
    private long lastPayTime;
    private BicycleShop shop;

    public EmployeeSalary(BicycleShop shop) {
        this.shop = shop;
        this.lastPayTime = System.currentTimeMillis();
    }

    public void checkAndPaySalary() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPayTime >= PAY_INTERVAL) {
            shop.setCash(shop.getCash() - SALARY_AMOUNT);
            lastPayTime = currentTime;
            System.out.println("Employee paid $1000. Current cash: " + shop.getCash());
        }
    }
}
