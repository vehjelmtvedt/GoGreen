package tools;

/**
 * This class provides tools to calculate a person's carbon emissions per year in kg.
 * It provides results for 3 different sectors: household, food, transportation
 * The calculations are based on the information I found on "www.carbonindependent.org"
 * Warning: do not create instances of this class.
 * @author Kostas Lyrakis
 *
 */
public final class CarbonCalculator {

    private CarbonCalculator() {
        super();
    }

    public enum OrganicFoodConsumption {
        NONE(700), SOME(500), MOST(200), ALL(0);
        private final int carbonEmissions;
        OrganicFoodConsumption(int carbonEmissions) {
            this.carbonEmissions = carbonEmissions;
        }
    }

    public enum MeatAndDairyConsumption {
        ABOVE_AVERAGE(600), AVERAGE(400), BELOW_AVERAGE(250), LACTO_VEGETARIAN(100), VEGAN(0);
        private final int carbonEmissions;
        MeatAndDairyConsumption(int carbonEmissions) {
            this.carbonEmissions = carbonEmissions;
        }
    }

    public enum LocallyProducedFoodConsumption {
        VERY_LITTLE(500), AVERAGE(300), ABOVE_AVERAGE(200), ALMOST_ALL(100);
        private int carbonEmissions;
        LocallyProducedFoodConsumption(int carbonEmissions) {
            this.carbonEmissions = carbonEmissions;
        }
    }

    public enum ProcessedFoodConsumption {
        ABOVE_AVERAGE(600), AVERAGE(400), BELOW_AVERAGE(200), VERY_LITTLE(50);
        private int carbonEmissions;
        ProcessedFoodConsumption(int carbonEmissions) {
            this.carbonEmissions = carbonEmissions;
        }
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
    public static int nonOrganicFoodEmissions(OrganicFoodConsumption organicFoodConsumption) {
        return organicFoodConsumption.carbonEmissions;
    }

    /**
     * Emissions related to meat consumption.
     * @param meatAndDairy how much meat and dairy a user consumes
     * @return annual carbon emissions in kg
     */
    public static int meatAndDairyConsumptionEmissions(MeatAndDairyConsumption meatAndDairy) {
        return meatAndDairy.carbonEmissions;
    }

    /**
     * Food miles.
     * @param locallyProducedFood How much of a user's food is produced locally
     * @return annual carbon emissions in kg
     */
    public static int foodMilesEmissions(LocallyProducedFoodConsumption locallyProducedFood) {
        return locallyProducedFood.carbonEmissions;
    }

    /**
     * Processed food emissions.
     * @param processedFoodConsumption how much of a user's food is packaged/processed
     * @return annual carbon emissions in kg
     */
    public static int processedFoodEmissions(ProcessedFoodConsumption processedFoodConsumption) {
        return processedFoodConsumption.carbonEmissions;
    }



}

