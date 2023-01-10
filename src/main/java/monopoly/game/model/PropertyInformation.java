package monopoly.game.model;

public class PropertyInformation {
    private PropertyType type;
    private String name;
    private int id;
    private int price;
    private int housePrice;
    private int hotelPrice;
    private int mortgagePrice;
    private int rent;
    private int rent1House;
    private int rent2House;
    private int rent3House;
    private int rent4House;
    private int rentHotel;
    private PropertyColor color;
    private int payment;

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(int housePrice) {
        this.housePrice = housePrice;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(int hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public int getMortgagePrice() {
        return mortgagePrice;
    }

    public void setMortgagePrice(int mortgagePrice) {
        this.mortgagePrice = mortgagePrice;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRent1House() {
        return rent1House;
    }

    public void setRent1House(int rent1House) {
        this.rent1House = rent1House;
    }

    public int getRent2House() {
        return rent2House;
    }

    public void setRent2House(int rent2House) {
        this.rent2House = rent2House;
    }

    public int getRent3House() {
        return rent3House;
    }

    public void setRent3House(int rent3House) {
        this.rent3House = rent3House;
    }

    public int getRent4House() {
        return rent4House;
    }

    public void setRent4House(int rent4House) {
        this.rent4House = rent4House;
    }

    public int getRentHotel() {
        return rentHotel;
    }

    public void setRentHotel(int rentHotel) {
        this.rentHotel = rentHotel;
    }

    public PropertyColor getColor() {
        return color;
    }

    public void setColor(PropertyColor color) {
        this.color = color;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}
