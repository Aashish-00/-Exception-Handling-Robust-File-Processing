import java.io.*;


class InvalidProductDataException extends Exception {
    public InvalidProductDataException(String message) {
        super(message);
    }
}

public class ProductFilterWithExceptions {
    public static void main(String[] args) {
        String inputFile = "products.csv";
        String outputFile = "filtered_products.csv";

        BufferedReader br = null;
        FileWriter fw = null;

        try {
            br = new BufferedReader(new FileReader(inputFile));
            fw = new FileWriter(outputFile);

            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length != 2) {
                        throw new InvalidProductDataException("Invalid row format: " + line);
                    }

                    String name = parts[0].trim();
                    double price;

                    try {
                        price = Double.parseDouble(parts[1].trim());
                    } catch (NumberFormatException e) {
                        throw new InvalidProductDataException("Invalid price value in row: " + line);
                    }

                    if (price > 1000) {
                        fw.write(name + "," + price + "\n");
                    }
                } catch (InvalidProductDataException e) {
                    System.out.println("Skipping invalid row → " + e.getMessage());
                }
            }

            System.out.println("Filtering complete. Check " + outputFile);

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + inputFile);
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        } finally {
            try {
                if (br != null) br.close();
                if (fw != null) fw.close();
            } catch (IOException e) {
                System.out.println("⚠ Error closing resources: " + e.getMessage());
            }
        }
    }
}
