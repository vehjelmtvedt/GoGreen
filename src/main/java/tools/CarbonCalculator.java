package carboncalculator;
/**
 * This class provides tools to calculate a person's carbon emissions per year in kg.
 * It provides results for 3 different sectors: household, food, transportation
 * The calculations are based on the information I found on "www.carbonindependent.org"
 * @author Kostas Lyrakis
 *
 */

final class CarbonCalculator {

    private CarbonCalculator() {
        super();
    }

    /**
     * @param kilowattHour the amount of electricity in kWh consumed
     * @return carbon emissions in kg
     */

    public static double electricityEmissions(int kilowattHour) {
        return kilowattHour * 0.309;
    }

    /**
     * @param cubicMeters the amount of cubic meters of natural consumed.
     *                    I multiply this by 11.36 to convert it to kWh
     * @return carbon emissions in kg
     */
    public static double naturalGasEmissions(double cubicMeters) {
        return cubicMeters * 11.36 * 0.203;
    }

    /**
     * @param litres , the amount of litres of heating oil consumed.
     * @return carbon emissions in kg
     */
    public static double heatingOilEmissions(double litres) {
        return litres * 2.96;
    }

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
        return 0.621371 * kilometers * 430;
    }

    /**
     * large car (engine size > 2.0 litres)
     * @param kilometers multiply by 0.621371 to convert to miles
     * @return carbon emissions in kg
     */
    public static double largeCarEmissions(double kilometers) {
        return 0.621371 * kilometers * 600;
    }


}

