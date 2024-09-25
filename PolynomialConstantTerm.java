import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

class TestCase {
    int base;
    String value;

    public int getBase() {
        return base;
    }

    public String getValue() {
        return value;
    }
}

public class BaseConverter {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<TestCase>>(){}.getType();

        long secretC = 0; // Variable to store the secret value

        try (FileReader reader = new FileReader("test_cases.json")) {
            List<TestCase> testCases = gson.fromJson(reader, listType);
            
            for (TestCase testCase : testCases) {
                int base = testCase.getBase();
                String value = testCase.getValue();

                if (base < 2 || base > 36) {
                    System.out.println("Base must be between 2 and 36 for value: " + value);
                    continue;
                }

                try {
                    long decimalValue = convertToDecimal(value, base);
                    System.out.println("Base: " + base + ", Value: " + value + ", Decimal: " + decimalValue);
                    secretC += decimalValue; // Add to secret value
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for the given base: " + value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Output the secret value
        System.out.println("The secret value (C) is: " + secretC);
    }

    private static long convertToDecimal(String value, int base) {
        return Long.parseLong(value, base);
    }
}
