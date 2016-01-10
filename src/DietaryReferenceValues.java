import java.util.Objects;

public class DietaryReferenceValues {

    private static double EER;

    public DietaryReferenceValues() {

    }

    public static double getEER(String gender, int age, double weight, double height, double activity) {
        double EER = 0;
        if (Objects.equals(gender, "Woman")) {
            EER = 354 - (6.91 * age) + activity * ((9.36 * weight) + (726 * height));
        }
        if (Objects.equals(gender, "Man")) {
            EER = 662 - (9.53 * age) + activity * ((15.91 * weight) + (539.6 * height));
        }
        return EER;
    }

}//END