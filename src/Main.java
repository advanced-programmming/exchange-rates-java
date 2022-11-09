import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
* Given some exchange rates, write a function that takes in 3 arguments current currency, target currency, and value, and return the new value in the target currency. The converted value should be trimmed to 2 decimal places, and rounded up. If a conversion cannot be made return 0.

Below is a sample set of data you can load into a data structure of your choice to then run against the tests. For example:

solution("USD", "AUD", 100) => 129
solution("YEN", "CAD", 523) => 6.06
solution("YEN", "LLL", 1) => 0
  USD  => AUD: 1.29
  USD  => GBP: .72
  USD  => EUR: .83
  AUD  => USD: .77
  YEN  => USD: .0093
  CAD  => GBP: .58
  GBP  => CAD: 1.73
* */
public class Main {
    public static void main(String[] args) {
        String curr = "USD";
        String target = "AUD";
        double value = 100;
        System.out.printf(solution(curr, target, value));
    }

    public static String solution(String curr, String target, double value){
        Map<String, Map<String, Float>> currencies = generateCurrencies();
        return new DecimalFormat("##.00")
                .format((getCurrency(curr, target, currencies) * value));
    }
    public static double getCurrency(String curr, String target, Map<String, Map<String, Float>> currencies){
        double result = 0;
        Stack<String> stack = new Stack<>();
        getCurrency(curr, target, currencies, stack);

        String last = "";
        int size = stack.size();

        for(int i = 0; i < size; i++){
            String currStack = stack.pop();

            if (!currStack.equals(target)) {
                result = currencies.get(currStack).containsKey(target) ?
                        currencies.get(currStack).get(target) :
                        result * currencies.get(currStack).get(last);

            }
            last = currStack;

        }

      return result;
    }

    public static void getCurrency(String curr, String target, Map<String, Map<String, Float>> currencies, Stack<String> stack){
        Map<String, Float> associated = currencies.getOrDefault(curr, null);
        if (associated != null) {
            stack.push(curr);
            if(associated.containsKey(target)){
               stack.push(target);
            } else{
                associated.forEach((key, value) -> {
                    if(!stack.contains(key)){
                        getCurrency(key, target, currencies, stack);
                    } else {
                        stack.pop();
                    }
                });
            }
        }
    }

    public static Map<String, Map<String, Float>> generateCurrencies() {
        Map<String, Map<String, Float>> currencies = new HashMap<>();
        Map<String, Float> usd = new HashMap<>();
        usd.put("AUD", 1.29F);
        usd.put("GBP", 0.72F);
        usd.put("EUR", 0.83F);
        Map<String, Float> aud = new HashMap<>();
        aud.put("USD", 0.77F);
        Map<String, Float> yen = new HashMap<>();
        yen.put("USD", 0.0093F);
        Map<String, Float> cad = new HashMap<>();
        cad.put("GBP", 0.58F);
        Map<String, Float> gbp = new HashMap<>();
        gbp.put("CAD", 1.73F);

        currencies.put("USD", usd);
        currencies.put("AUD", aud);
        currencies.put("YEN", yen);
        currencies.put("CAD", cad);
        currencies.put("GBP", gbp);

        return currencies;
    }
}

