package backend.data;

import tools.CarbonCalculator;

public class EatVegetarianMeal extends Activity {

    public int dailylMeatAndDairyConsumption(User user) {
        String meatAndDairyConsumption = user.getMeatAndDairyConsumption();
        int dailylMeatAndDairyConsumption;
        switch (meatAndDairyConsumption) {
            case "above average":
                dailylMeatAndDairyConsumption =
                        CarbonCalculator.meatAndDairyConsumptionEmissions(
                                CarbonCalculator.MeatAndDairyConsumption.ABOVE_AVERAGE) / 365;
                break;
            case "average" :
                dailylMeatAndDairyConsumption =
                        CarbonCalculator.meatAndDairyConsumptionEmissions(
                                CarbonCalculator.MeatAndDairyConsumption.AVERAGE) / 365;
                break;
            case "below average" :
                dailylMeatAndDairyConsumption =
                        CarbonCalculator.meatAndDairyConsumptionEmissions(
                                CarbonCalculator.MeatAndDairyConsumption.BELOW_AVERAGE) / 365;
                break;
            case "lacto-vegetarian" :
                dailylMeatAndDairyConsumption =
                        CarbonCalculator.meatAndDairyConsumptionEmissions(
                                CarbonCalculator.MeatAndDairyConsumption.LACTO_VEGETARIAN) / 365;
                break;
            case "vegan" :
                dailylMeatAndDairyConsumption =
                        CarbonCalculator.meatAndDairyConsumptionEmissions(
                                CarbonCalculator.MeatAndDairyConsumption.VEGAN) / 365;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return dailylMeatAndDairyConsumption;
    }


}
