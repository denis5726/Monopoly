package monopoly.game.model;

import java.util.Arrays;
import java.util.List;

public class PropertyData {
    private static final PropertyInformation[] data = new PropertyInformation[40];
    private static final int[] railwayStationPayments = new int[4];
    private static final int[] publicServicePayments = new int[2];

    static {
        for (int i = 0; i < data.length; i++) {
            data[i] = new PropertyInformation();
            data[i].setId(i);
        }

        data[1].setName("Mediter-Ranean Avenue");
        data[1].setType(PropertyType.PROPERTY);
        data[1].setPrice(60);
        data[1].setHousePrice(50);
        data[1].setHotelPrice(50);
        data[1].setMortgagePrice(30);
        data[1].setRent(2);
        data[1].setRent1House(10);
        data[1].setRent2House(30);
        data[1].setRent3House(90);
        data[1].setRent4House(160);
        data[1].setRentHotel(250);
        data[1].setColor(PropertyColor.BROWN);

        data[3].setName("Baltic Avenue");
        data[3].setType(PropertyType.PROPERTY);
        data[3].setPrice(60);
        data[3].setHousePrice(50);
        data[3].setHotelPrice(50);
        data[3].setMortgagePrice(30);
        data[3].setRent(4);
        data[3].setRent1House(20);
        data[3].setRent2House(60);
        data[3].setRent3House(180);
        data[3].setRent4House(320);
        data[3].setRentHotel(450);
        data[3].setColor(PropertyColor.BROWN);

        data[6].setName("Oriental Avenue");
        data[6].setType(PropertyType.PROPERTY);
        data[6].setPrice(100);
        data[6].setHousePrice(50);
        data[6].setHotelPrice(50);
        data[6].setMortgagePrice(50);
        data[6].setRent(6);
        data[6].setRent1House(30);
        data[6].setRent2House(90);
        data[6].setRent3House(270);
        data[6].setRent4House(400);
        data[6].setRentHotel(550);
        data[6].setColor(PropertyColor.BLUE);

        data[8].setName("Vermont Avenue");
        data[8].setType(PropertyType.PROPERTY);
        data[8].setPrice(100);
        data[8].setHousePrice(50);
        data[8].setHotelPrice(50);
        data[8].setMortgagePrice(50);
        data[8].setRent(6);
        data[8].setRent1House(30);
        data[8].setRent2House(90);
        data[8].setRent3House(270);
        data[8].setRent4House(400);
        data[8].setRentHotel(550);
        data[8].setColor(PropertyColor.BLUE);

        data[9].setName("Connecticut Avenue");
        data[9].setType(PropertyType.PROPERTY);
        data[9].setPrice(120);
        data[9].setHousePrice(50);
        data[9].setHotelPrice(50);
        data[9].setMortgagePrice(60);
        data[9].setRent(8);
        data[9].setRent1House(40);
        data[9].setRent2House(100);
        data[9].setRent3House(300);
        data[9].setRent4House(450);
        data[9].setRentHotel(600);
        data[9].setColor(PropertyColor.BLUE);

        data[11].setName("St. Charles Place");
        data[11].setType(PropertyType.PROPERTY);
        data[11].setPrice(140);
        data[11].setHousePrice(100);
        data[11].setHotelPrice(100);
        data[11].setMortgagePrice(70);
        data[11].setRent(10);
        data[11].setRent1House(50);
        data[11].setRent2House(150);
        data[11].setRent3House(450);
        data[11].setRent4House(625);
        data[11].setRentHotel(750);
        data[11].setColor(PropertyColor.PINK);

        data[13].setName("States Avenue");
        data[13].setType(PropertyType.PROPERTY);
        data[13].setPrice(140);
        data[13].setHousePrice(100);
        data[13].setHotelPrice(100);
        data[13].setMortgagePrice(70);
        data[13].setRent(10);
        data[13].setRent1House(50);
        data[13].setRent2House(150);
        data[13].setRent3House(450);
        data[13].setRent4House(625);
        data[13].setRentHotel(750);
        data[13].setColor(PropertyColor.PINK);

        data[14].setName("Virginia Avenue");
        data[14].setType(PropertyType.PROPERTY);
        data[14].setPrice(160);
        data[14].setHousePrice(100);
        data[14].setHotelPrice(100);
        data[14].setMortgagePrice(80);
        data[14].setRent(12);
        data[14].setRent1House(60);
        data[14].setRent2House(180);
        data[14].setRent3House(500);
        data[14].setRent4House(700);
        data[14].setRentHotel(900);
        data[14].setColor(PropertyColor.PINK);

        data[16].setName("St. James Place");
        data[16].setType(PropertyType.PROPERTY);
        data[16].setPrice(180);
        data[16].setHousePrice(100);
        data[16].setHotelPrice(100);
        data[16].setMortgagePrice(90);
        data[16].setRent(14);
        data[16].setRent1House(70);
        data[16].setRent2House(200);
        data[16].setRent3House(550);
        data[16].setRent4House(750);
        data[16].setRentHotel(950);
        data[16].setColor(PropertyColor.ORANGE);

        data[18].setName("Tennessee Avenue");
        data[18].setType(PropertyType.PROPERTY);
        data[18].setPrice(180);
        data[18].setHousePrice(100);
        data[18].setHotelPrice(100);
        data[18].setMortgagePrice(90);
        data[18].setRent(14);
        data[18].setRent1House(70);
        data[18].setRent2House(200);
        data[18].setRent3House(550);
        data[18].setRent4House(750);
        data[18].setRentHotel(950);
        data[18].setColor(PropertyColor.ORANGE);

        data[19].setName("New York Avenue");
        data[19].setType(PropertyType.PROPERTY);
        data[19].setPrice(200);
        data[19].setHousePrice(100);
        data[19].setHotelPrice(100);
        data[19].setMortgagePrice(100);
        data[19].setRent(16);
        data[19].setRent1House(80);
        data[19].setRent2House(220);
        data[19].setRent3House(600);
        data[19].setRent4House(800);
        data[19].setRentHotel(1000);
        data[19].setColor(PropertyColor.ORANGE);

        data[21].setName("Kentucky Avenue");
        data[21].setType(PropertyType.PROPERTY);
        data[21].setPrice(220);
        data[21].setHousePrice(150);
        data[21].setHotelPrice(150);
        data[21].setMortgagePrice(110);
        data[21].setRent(18);
        data[21].setRent1House(90);
        data[21].setRent2House(250);
        data[21].setRent3House(700);
        data[21].setRent4House(875);
        data[21].setRentHotel(1050);
        data[21].setColor(PropertyColor.RED);

        data[23].setName("Indiana Avenue");
        data[23].setType(PropertyType.PROPERTY);
        data[23].setPrice(220);
        data[23].setHousePrice(150);
        data[23].setHotelPrice(150);
        data[23].setMortgagePrice(110);
        data[23].setRent(18);
        data[23].setRent1House(90);
        data[23].setRent2House(250);
        data[23].setRent3House(700);
        data[23].setRent4House(875);
        data[23].setRentHotel(1050);
        data[23].setColor(PropertyColor.RED);

        data[24].setName("Illinois Avenue");
        data[24].setType(PropertyType.PROPERTY);
        data[24].setPrice(240);
        data[24].setHousePrice(150);
        data[24].setHotelPrice(150);
        data[24].setMortgagePrice(120);
        data[24].setRent(20);
        data[24].setRent1House(100);
        data[24].setRent2House(300);
        data[24].setRent3House(750);
        data[24].setRent4House(925);
        data[24].setRentHotel(110);
        data[24].setColor(PropertyColor.RED);

        data[26].setName("Atlantic Avenue");
        data[26].setType(PropertyType.PROPERTY);
        data[26].setPrice(260);
        data[26].setHousePrice(150);
        data[26].setHotelPrice(150);
        data[26].setMortgagePrice(130);
        data[26].setRent(22);
        data[26].setRent1House(110);
        data[26].setRent2House(330);
        data[26].setRent3House(800);
        data[26].setRent4House(975);
        data[26].setRentHotel(1150);
        data[26].setColor(PropertyColor.YELLOW);

        data[27].setName("Ventnor Avenue");
        data[27].setType(PropertyType.PROPERTY);
        data[27].setPrice(260);
        data[27].setHousePrice(150);
        data[27].setHotelPrice(150);
        data[27].setMortgagePrice(130);
        data[27].setRent(22);
        data[27].setRent1House(110);
        data[27].setRent2House(330);
        data[27].setRent3House(800);
        data[27].setRent4House(975);
        data[27].setRentHotel(1150);
        data[27].setColor(PropertyColor.YELLOW);

        data[29].setName("Marvin Gardens");
        data[29].setType(PropertyType.PROPERTY);
        data[29].setPrice(280);
        data[29].setHousePrice(150);
        data[29].setHotelPrice(150);
        data[29].setMortgagePrice(140);
        data[29].setRent(24);
        data[29].setRent1House(120);
        data[29].setRent2House(360);
        data[29].setRent3House(850);
        data[29].setRent4House(1025);
        data[29].setRentHotel(1200);
        data[29].setColor(PropertyColor.YELLOW);

        data[31].setName("Pacific Avenue");
        data[31].setType(PropertyType.PROPERTY);
        data[31].setPrice(300);
        data[31].setHousePrice(200);
        data[31].setHotelPrice(200);
        data[31].setMortgagePrice(150);
        data[31].setRent(26);
        data[31].setRent1House(130);
        data[31].setRent2House(390);
        data[31].setRent3House(900);
        data[31].setRent4House(1100);
        data[31].setRentHotel(1275);
        data[31].setColor(PropertyColor.GREEN);

        data[32].setName("North Carolina Avenue");
        data[32].setType(PropertyType.PROPERTY);
        data[32].setPrice(300);
        data[32].setHousePrice(200);
        data[32].setHotelPrice(200);
        data[32].setMortgagePrice(150);
        data[32].setRent(26);
        data[32].setRent1House(130);
        data[32].setRent2House(390);
        data[32].setRent3House(900);
        data[32].setRent4House(1100);
        data[32].setRentHotel(1275);
        data[32].setColor(PropertyColor.GREEN);

        data[34].setName("Pennsylvania Avenue");
        data[34].setType(PropertyType.PROPERTY);
        data[34].setPrice(320);
        data[34].setHousePrice(200);
        data[34].setHotelPrice(200);
        data[34].setMortgagePrice(160);
        data[34].setRent(28);
        data[34].setRent1House(150);
        data[34].setRent2House(450);
        data[34].setRent3House(1000);
        data[34].setRent4House(1200);
        data[34].setRentHotel(1400);
        data[34].setColor(PropertyColor.GREEN);

        data[37].setName("Park place");
        data[37].setType(PropertyType.PROPERTY);
        data[37].setPrice(350);
        data[37].setHousePrice(200);
        data[37].setHotelPrice(200);
        data[37].setMortgagePrice(175);
        data[37].setRent(35);
        data[37].setRent1House(175);
        data[37].setRent2House(500);
        data[37].setRent3House(1100);
        data[37].setRent4House(1300);
        data[37].setRentHotel(1500);
        data[37].setColor(PropertyColor.DARKBLUE);

        data[39].setName("Boardwalk");
        data[39].setType(PropertyType.PROPERTY);
        data[39].setPrice(400);
        data[39].setHousePrice(200);
        data[39].setHotelPrice(200);
        data[39].setMortgagePrice(200);
        data[39].setRent(50);
        data[39].setRent1House(200);
        data[39].setRent2House(600);
        data[39].setRent3House(1400);
        data[39].setRent4House(1700);
        data[39].setRentHotel(2000);
        data[39].setColor(PropertyColor.DARKBLUE);

        data[0].setType(PropertyType.START_FIELD);
        data[0].setName("Start");
        data[0].setPayment(200);

        data[4].setType(PropertyType.PAYMENT_FIELD);
        data[4].setName("Income tax");
        data[4].setPayment(-200);

        data[10].setType(PropertyType.JAIL_VISIT_FIELD);
        data[10].setName("Jail Visit");

        data[20].setType(PropertyType.FREE_PARKING_FIELD);
        data[20].setName("Free Parking");

        data[38].setType(PropertyType.PAYMENT_FIELD);
        data[38].setName("Luxury tax");
        data[38].setPayment(-100);

        data[5].setType(PropertyType.RAILWAY_STATION);
        data[5].setName("Reading railroad");
        data[5].setPrice(200);
        data[5].setMortgagePrice(100);

        data[15].setType(PropertyType.RAILWAY_STATION);
        data[15].setName("Pennsylvania railroad");
        data[15].setPrice(200);
        data[15].setMortgagePrice(100);

        data[25].setType(PropertyType.RAILWAY_STATION);
        data[25].setName("B. & O. railroad");
        data[25].setPrice(200);
        data[25].setMortgagePrice(100);

        data[35].setType(PropertyType.RAILWAY_STATION);
        data[35].setName("Short line");
        data[35].setPrice(200);
        data[35].setMortgagePrice(100);

        data[12].setType(PropertyType.ELECTRIC_COMPANY);
        data[12].setName("Electric company");
        data[12].setPrice(150);
        data[12].setMortgagePrice(75);

        data[28].setType(PropertyType.WATER_WORKS);
        data[28].setName("Water works");
        data[28].setPrice(150);
        data[28].setMortgagePrice(75);

        data[30].setType(PropertyType.JAIL);
        data[30].setName("Jail");

        data[7].setType(PropertyType.CHANCE_FIELD);
        data[7].setName("Chance 1");

        data[22].setType(PropertyType.CHANCE_FIELD);
        data[22].setName("Chance 2");

        data[36].setType(PropertyType.CHANCE_FIELD);
        data[36].setName("Chance 3");

        data[2].setType(PropertyType.COMMUNITY_CHEST_FIELD);
        data[2].setName("Community chest 1");

        data[17].setType(PropertyType.COMMUNITY_CHEST_FIELD);
        data[17].setName("Community chest 2");

        data[33].setType(PropertyType.COMMUNITY_CHEST_FIELD);
        data[33].setName("Community chest 3");

        railwayStationPayments[0] = 25;
        railwayStationPayments[1] = 50;
        railwayStationPayments[2] = 100;
        railwayStationPayments[3] = 200;

        publicServicePayments[0] = 4;
        publicServicePayments[1] = 10;
    }

    public static PropertyInformation getPropertyInformation(String name) {
        List<PropertyInformation> propertyInformationList = Arrays.stream(data)
                .filter((obj) -> obj.getName().equals(name)).toList();
        if (propertyInformationList.size() == 1) return propertyInformationList.get(0);
        else return null;
    }

    public static PropertyInformation getPropertyInformation(int index) {
        if (index >= 0 && index < data.length) return data[index];
        else return null;
    }

    public static int getRailwayPayment(int index) {
        if (index >= 0 && index < 4) return railwayStationPayments[index];
        else return 0;
    }

    public static int getPublicServicePayment(int index) {
        if (index >= 0 && index < 2) return publicServicePayments[index];
        else return 0;
    }
}
