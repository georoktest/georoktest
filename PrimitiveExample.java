public class PrimitiveExample {
    public static void main(String[] args) {
        int sum = 0; // Vermeide Integer
        for (int i = 0; i < 1000; i++) {
            sum += i;
        }
        System.out.println("Summe: " + sum);
    }
}