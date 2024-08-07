public class StringConcatenation {
    public static void main(String[] args) {
        int iterations = 1000;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < iterations; i++) {
            sb.append("Iteration: ").append(i).append("\n");
        }

        String result = sb.toString();
        System.out.println(result);
    }
}