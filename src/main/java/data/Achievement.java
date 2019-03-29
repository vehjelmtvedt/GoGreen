package data;

import org.springframework.data.annotation.Id;

public class Achievement {
    @Id
    private int id;

    private String name;
    private int bonus;

    public Achievement() {

    }

    /**
     * contains the data for the Achievement.
     *
     * @param id    id
     * @param name  name
     * @param bonus extra points
     */
    public Achievement(int id, String name, int bonus) {
        this.id = id;
        this.name = name;
        this.bonus = bonus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }


}
