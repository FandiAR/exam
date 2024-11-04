import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan deret angka (pisahkan dengan spasi): ");
        String input = scanner.nextLine();

        // Memisahkan input berdasarkan spasi dan mengonversi ke array integer
        String[] inputArray = input.split(" ");
        int[] numbers = new int[inputArray.length];
        
        try {
            for (int i = 0; i < inputArray.length; i++) {
                numbers[i] = Integer.parseInt(inputArray[i]);
                if (numbers[i] < 1 || numbers[i] > 100) {
                    throw new IllegalArgumentException("Deret angka hanya boleh terdiri dari angka bulat positif maksimal 100.");
                }
            }

            int min = Arrays.stream(numbers).min().getAsInt();
            int max = Arrays.stream(numbers).max().getAsInt();

            System.out.println("Angka terkecil adalah: " + min);
            System.out.println("Angka terbesar adalah: " + max);
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Pastikan hanya memasukkan angka bulat positif yang dipisahkan dengan spasi.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
