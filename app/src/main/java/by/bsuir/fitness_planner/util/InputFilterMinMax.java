package by.bsuir.fitness_planner.util;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {

    private final int min;
    private final int max;
    private final int fraction;

    public InputFilterMinMax(int min, int max, int fraction) {
        this.min = min;
        this.max = max;
        this.fraction = fraction;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            String[] input = (dest.toString() + source.toString()).split("\\.");
            String inputNumber = parseNumber(input);
            String inputFraction = parseFraction(input);
            if ((inputNumber == null || validateInputNumber(Integer.parseInt(inputNumber)))
                    && (inputFraction == null || validateFraction(inputFraction))) {
                return null;
            }
        } catch (NumberFormatException e) {
            return "";
        }
        return "";
    }

    private String parseNumber(String[] input) {
        return input.length >= 1 && !input[0].isEmpty() && !input[0].startsWith(".") ? input[0] : null;
    }

    private String parseFraction(String[] input) {
        return input.length == 2 && !input[1].isEmpty() ? input[1] : null;
    }

    private boolean validateInputNumber(int inputNumber) {
        return inputNumber >= min && inputNumber <= max;
    }

    private boolean validateFraction(String inputFraction) {
        return inputFraction.length() <= fraction;
    }
}