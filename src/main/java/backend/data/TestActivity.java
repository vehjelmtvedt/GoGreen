package backend.data;

import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class TestActivity {
    private String category;
    private String name;
    private Date date;
    private double carbonSaved;

    /**.
     * Constructor with 2 params
     * @param category activity category
     * @param name activity name
     */
    public TestActivity(String category, String name) {
        this.category = category;
        this.name = name;
        this.date = new Date();
        this.carbonSaved = 12.50;
    }

    /**.
     * Constructor with 3 params
     * @param category activity category
     * @param name activity name
     * @param date activity date
     */
//    public TestActivity(String category, String name, Date date) {
//        this.category = new SimpleStringProperty(category);
//        this.name = new SimpleStringProperty(name);
//        this.date = date;
//    }

    /**.
     * Returns visual representation of this class
     * @return String type
     */
    public String toString() {
        String res = "";
        res += "Category : " + this.category + "\n";
        res += "Name : "  + this.name + "\n";
        res += "Date : " + this.date.toString();
        res += "Carbon : " + this.carbonSaved;

        return res;
    }

    public String getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date.toString();
    }

    public double getCarbonSaved() {
        return this.carbonSaved;
    }

}
