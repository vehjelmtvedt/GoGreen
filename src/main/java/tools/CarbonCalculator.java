package tools;

/**
 * This class provides tools to calculate a person's carbon emissions per year in kg.
 * It provides results for 3 different sectors: household, food, transportation
 * The calculations are based on the information I found on "www.carbonindependent.org"
 * Warning: do not create instances of this class.
 * @author Kostas Lyrakis
 *
 */
final class CarbonCalculator {

    private CarbonCalculator() {
        super();
    }

    // Category: Household energy consumption

    /** calculates carbon emissions from electricity.
     * @param kilowattHour the amount of electricity in kWh consumed
     * @return carbon emissions in kg
     */
    public static double electricityEmissions(int kilowattHour) {
        return kilowattHour * 0.309;
    }

    /** calculates carbon emissions from gas.
     * @param cubicMeters the amount of cubic meters of natural consumed.
     *                    I multiply this by 11.36 to convert it to kWh
     * @return carbon emissions in kg
     */
    public static double naturalGasEmissions(double cubicMeters) {
        return cubicMeters * 11.36 * 0.203;
    }

    /** calculates carbon emissions from heating oil.
     * @param litres , the amount of litres of heating oil consumed.
     * @return carbon emissions in kg
     */
    public static double heatingOilEmissions(double litres) {
        return litres * 2.96;
    }

    // Category: Transportation

    /**
     * small car (engine size < 1.5 litres)
     * @param kilometers multiply by 0.621371 to convert to miles
     * @return carbon emissions in kg
     */
    public static double smallCarEmissions(double kilometers) {
        return 0.621371 * kilometers * 0.390;
    }

    /**
     * medium car (1.5 < engine size < 2.0 litres)
     * @param kilometers multiply by 0.621371 to convert to miles
     * @return carbon emissions in kg
     */
    public static double mediumCarEmissions(double kilometers) {
        return 0.621371 * kilometers * 0.430;
    }


    /**
     * large car (engine size > 2.0 litres)
     * @param kilometers multiply by 0.621371 to convert to miles
     * @return carbon emissions in kg
     */
    public static double largeCarEmissions(double kilometers) {
        return 0.621371 * kilometers * 0.600;
    }

    /**
     * Emissions produced by bus travel.
     * @param kilometers traveled by bus
     * @return carbon emissions in kg
     */
    public static double busEmissions(double kilometers) {
        return kilometers * 0.089;
    }

    /**
     * Emissions produced by train travel.
     * @param kilometers traveled by train
     * @return carbon emissions in kg
     */
    public static double trainEmissions(double kilometers) {
        return kilometers * 0.049;
    }

    // Category: Food

    /**
     * non-organic food emissions.
     * Note: Non-farmed fish counts as organic.
     * @param organicFoodConsumption Amount of organic food a user consumes (none, some, most, all)
     * @return annual carbon emissions in kg
     */
    public static int nonOrganicFoodEmissions(String organicFoodConsumption) {
        if (organicFoodConsumption.toLowerCase().equals("none")) {
            return 700;
        } else if (organicFoodConsumption.toLowerCase().equals("some")) {
            return 500;
        } else if (organicFoodConsumption.toLowerCase().equals("most")) {
            return 200;
        } else if  (organicFoodConsumption.toLowerCase().equals("all")) {
            return 0;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Emissions related to meat consumption.
     * @param meatAndDairyConsumption how much meat and dairy a user consumes
     * @return annual carbon emissions in kg
     */
    public static int meatAndDairyConsumptionEmissions(String meatAndDairyConsumption) {
        if (meatAndDairyConsumption.toLowerCase().equals("above average")) {
            return 600;
        } else if (meatAndDairyConsumption.toLowerCase().equals("average")) {
            return 400;
        } else if (meatAndDairyConsumption.toLowerCase().equals("below average")) {
            return 250;
        } else if ( meatAndDairyConsumption.toLowerCase().equals("lacto-vegetarian")) {
            return 100;
        } else if (meatAndDairyConsumption.toLowerCase().equals("vegan")) {
            return 0;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Food miles.
     * @param localyProducedFood How much of a user's food is produced locally
     * @return annual carbon emissions in kg
     */
    public static int foodMilesEmissions(String localyProducedFood) {
        if (localyProducedFood.toLowerCase().equals("very little")) {
            return 500;
        } else if (localyProducedFood.toLowerCase().equals("average")) {
            return 300;
        } else if (localyProducedFood.toLowerCase().equals("above average")) {
            return 200;
        } else if (localyProducedFood.toLowerCase().equals("almost all")) {
            return 100;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Processed food emissions.
     * @param processedFood how much of a user's food is packaged/processed
     * @return annual carbon emissions in kg
     */
    public static int processedFoodEmissions(String processedFood) {
        if (processedFood.toLowerCase().equals("above average")) {
            return 600;
        } else if (processedFood.toLowerCase().equals("average")) {
            return 400;
        } else if (processedFood.toLowerCase().equals("below average")) {
            return 200;
        } else if (processedFood.toLowerCase().equals("very little")) {
            return 50;
        } else {
            throw new IllegalArgumentException();
        }
    }
}

