
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class imputation {
    public static void main(String[] args) {
        mean004();
        mean20();
        meancondition004();
        meancondition20();
    }

    public static void mean004() {
        String csvFile = "assignment2_dataset_missing004.csv";
        String csvFileCompleted = "assignment2_dataset_complete.csv";
        String line = "";
        String line2 = "";
        String csvSplitBy = ",";
        int counter;
        int set = 0;
        String[] column = new String[3588];
        String[] columnCompleted = new String[3588];
        int x = 0; // row
        int y = 0; //column
        Double[][] doubleArray = new Double[3588][84];
        Double[][] doubleArrayCompleted = new Double[3588][84];
        String[] classArray = new String[3588];
        double MAE = 0;
        int denominatorCounter = 0;
        int quickCounter = 1;

        end:
        while (y < 85) {
            x++;
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                BufferedReader br2 = new BufferedReader(new FileReader(csvFileCompleted));
                //while loop stores elements in column array as string
                counter = 0;
                while ((line = br.readLine()) != null) {
                    line2 = br2.readLine();
                    // use comma as separator
                    String[] data = line.split(csvSplitBy);
                    String[] dataCompleted = line2.split(csvSplitBy);
                    if (y == 84) {
                        classArray[counter] = data[set];
                        counter++;
                    } else {
                        column[counter] = data[set];
                        columnCompleted[counter] = dataCompleted[set];
                        counter++;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (y == 84) { //break loops if last column (class column)
                y++;
                break end;
            }

            double sum = 0.0;
            double mean = 0.0;
            //while loop scans through column array and stores elements as double in doubleArray
            //stores ? as 1.0 for later parsing
            //calculates mean value of array
            while (x < doubleArray.length) {
                try {
                    doubleArray[x - 1][y] = Double.parseDouble(column[x]);
                    doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                    sum += doubleArray[x - 1][y];
                    mean = sum / x;
                    x++;
                } catch (NumberFormatException e) {
                    doubleArray[x - 1][y] = 1.0;
                    doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                    x++;
                }
            }

            x = 0;
            double roundedMean = Math.round(mean * 10000.0) / 10000.0;
            //System.out.println("rounded mean " + quickCounter + ": " + roundedMean);
            quickCounter++;
            //while loop parses through doubleArray and replaces 1.0 with mean value
            //prints array
            while (x < doubleArray.length - 1) {
                if (doubleArray[x][y] == 1.0) {
                    doubleArray[x][y] = roundedMean;
                    MAE += Math.abs(doubleArray[x][y] - doubleArrayCompleted[x][y]);
                    denominatorCounter++;
                }
                //System.out.println(x + ": " + doubleArray[x][y]);
                x++;
            }
            x = 0;
            y++;
            set++;
        }
        double MAE004 = Math.round((MAE / denominatorCounter) * 10000.0) / 10000.0;
        System.out.println("MAE_004_mean = " + MAE004);

        String commaDelimiter = ",";
        String newLine = "\n";
        String fileHeader = "AAcomp_A,AAcomp_R,AAcomp_N,AAcomp_D,AAcomp_C,AAcomp_Q,AAcomp_E,AAcomp_G,AAcomp_H,AAcomp_I,AAcomp_L,AAcomp_K,AAcomp_M,AAcomp_F,AAcomp_P,AAcomp_S,AAcomp_T,AAcomp_W,AAcomp_Y,AAcomp_V,AAindex_ARGP820101_avg,AAindex_BULH740101_avg,AAindex_CHAM820102_avg,AAindex_CIDH920105_avg,AAindex_EISD840101_avg,AAindex_EISD860101_avg,AAindex_EISD860102_avg,AAindex_EISD860103_avg,AAindex_FAUJ830101_avg,AAindex_GOLD730101_avg,AAindex_GUYH850101_avg,AAindex_HOPT810101_avg,AAindex_JANJ790102_avg,AAindex_JOND750101_avg,AAindex_KYTJ820101_avg,AAindex_LAWE840101_avg,AAindex_LEVM760101_avg,AAindex_MANP780101_avg,AAindex_MIYS850101_avg,AAindex_NOZY710101_avg,AAindex_OOBM770101_avg,AAindex_OOBM770102_avg,AAindex_OOBM770103_avg,AAindex_OOBM770104_avg,AAindex_OOBM770105_avg,AAindex_OOBM850103_avg,AAindex_OOBM850104_avg,AAindex_PONP800101_avg,AAindex_PONP800102_avg,AAindex_PONP800103_avg,AAindex_PRAM900101_avg,AAindex_RADA880101_avg,AAindex_RADA880102_avg,AAindex_RADA880103_avg,AAindex_RADA880104_avg,AAindex_RADA880105_avg,AAindex_ROBB790101_avg,AAindex_ROSM880101_avg,AAindex_ROSM880102_avg,AAindex_SIMZ760101_avg,AAindex_SWER830101_avg,AAindex_VHEG790101_avg,AAindex_WERD780102_avg,AAindex_WERD780103_avg,AAindex_WERD780104_avg,AAindex_YUTK870101_avg,AAindex_YUTK870102_avg,AAindex_YUTK870103_avg,AAindex_YUTK870104_avg,AAindex_ZIMJ680101_avg,AAindex_PONP930101_avg,AAindex_WILM950101_avg,AAindex_WILM950102_avg,AAindex_WILM950103_avg,AAindex_WILM950104_avg,AAindex_KUHL950101_avg,AAindex_JURD980101_avg,AAindex_WOLR790101_avg,AAindex_KIDA850101_avg,AAindex_COWR900101_avg,AAindex_BLAS910101_avg,AAindex_CASG920101_avg,AAindex_ENGD860101_avg,AAindex_FASG890101_avg,class\n";
        try {
            FileWriter fileWriter = new FileWriter("V00662654_a2_missing004_imputed_mean.csv");
            fileWriter.append(fileHeader);
            for (int row = 0; row < 3587; row++) {
                for (int col = 0; col < 85; col++) {
                    if (col == 84) {
                        fileWriter.append(classArray[row + 1]);
                    } else {
                        fileWriter.append(String.valueOf(doubleArray[row][col]));
                        fileWriter.append(commaDelimiter);
                    }
                }
                fileWriter.append(newLine);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void meancondition004() {
        String csvFile = "assignment2_dataset_missing004.csv";
        String csvFileCompleted = "assignment2_dataset_complete.csv";
        String line = "";
        String line2 = "";
        String csvSplitBy = ",";
        int counter;
        int set = 0;
        String[] column = new String[3588];
        String[] columnCompleted = new String[3588];
        int x = 0; // row
        int y = 0; //column
        Double[][] doubleArray = new Double[3588][84];
        Double[][] doubleArrayCompleted = new Double[3588][84];
        String[] classArray = new String[3588];
        double MAE = 0;
        int denominatorCounter = 0;
        int quickCounter = 1;

        while (y < 85) {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                //while loop stores class elements in classArray
                counter = 0;
                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] data = line.split(csvSplitBy);
                    if (y == 84) {
                        classArray[counter] = data[set];
                        //System.out.println(classArray[counter]);
                        counter++;
                    } else {
                        counter++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            y++;
            set++;
        }
        //System.out.println(classArray[1]);
        y = 0;
        set = 0;
        end:
        while (y < 85) {
            x++;
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                BufferedReader br2 = new BufferedReader(new FileReader(csvFileCompleted));
                //while loop stores elements in column array as string
                counter = 0;
                while ((line = br.readLine()) != null) {
                    line2 = br2.readLine();
                    // use comma as separator
                    String[] data = line.split(csvSplitBy);
                    String[] dataCompleted = line2.split(csvSplitBy);
                    if (y == 84) {
                        //classArray[counter] = data[set];
                        //System.out.println(classArray[counter]);
                        counter++;
                    } else {
                        column[counter] = data[set];
                        columnCompleted[counter] = dataCompleted[set];
                        counter++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (y == 84) { //break loops if last column (class column)
                y++;
                break end;
            }

            double Fsum = 0.0;
            double Fmean = 0.0;
            double Csum = 0.0;
            double Cmean = 0.0;
            //while loop scans through column array and stores elements as double in doubleArray
            //stores ? as 1.0 for later parsing
            //calculates mean value of array
            while (x < doubleArray.length) {
                try {
                    doubleArray[x - 1][y] = Double.parseDouble(column[x]);
                    doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                    if (classArray[x].equals("F")) {
                        Fsum += doubleArray[x - 1][y];
                        Fmean = Fsum / x;
                        x++;
                        //System.out.println(doubleArray[x-1][y]);
                        //System.out.println("true1");
                    } else if (classArray[x].equals("C")) {
                        Csum += doubleArray[x - 1][y];
                        Cmean = Csum / x;
                        x++;
                        //System.out.println("true2");
                    }
                } catch (NumberFormatException e) {
                    if (classArray[x].equals("F")) {
                        doubleArray[x - 1][y] = 1.0;
                        doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                        x++;
                    } else if (classArray[x].equals("C")) {
                        doubleArray[x - 1][y] = 2.0;
                        doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                        x++;
                    }
                }
            }
            x = 0;
            double roundedFMean = Math.round(Fmean * 10000.0) / 10000.0;
            double roundedCMean = Math.round(Cmean * 10000.0) / 10000.0;
            //System.out.println("rounded mean " + quickCounter + ": " + roundedMean);
            quickCounter++;
            //while loop parses through doubleArray and replaces 1.0 with mean value
            //prints array
            while (x < doubleArray.length - 1) {
                if (doubleArray[x][y] == 1.0) {
                    doubleArray[x][y] = roundedFMean;
                    MAE += Math.abs(doubleArray[x][y] - doubleArrayCompleted[x][y]);
                    denominatorCounter++;
                } else if (doubleArray[x][y] == 2.0) {
                    doubleArray[x][y] = roundedCMean;
                    MAE += Math.abs(doubleArray[x][y] - doubleArrayCompleted[x][y]);
                    denominatorCounter++;
                }
                //System.out.println(x + ": " + doubleArray[x][y]);
                x++;
            }
            x = 0;
            y++;
            set++;
        }
        double MAE004 = Math.round((MAE / denominatorCounter) * 10000.0) / 10000.0;
        System.out.println("MAE_004_mean_conditional = " + MAE004);

        String commaDelimiter = ",";
        String newLine = "\n";
        String fileHeader = "AAcomp_A,AAcomp_R,AAcomp_N,AAcomp_D,AAcomp_C,AAcomp_Q,AAcomp_E,AAcomp_G,AAcomp_H,AAcomp_I,AAcomp_L,AAcomp_K,AAcomp_M,AAcomp_F,AAcomp_P,AAcomp_S,AAcomp_T,AAcomp_W,AAcomp_Y,AAcomp_V,AAindex_ARGP820101_avg,AAindex_BULH740101_avg,AAindex_CHAM820102_avg,AAindex_CIDH920105_avg,AAindex_EISD840101_avg,AAindex_EISD860101_avg,AAindex_EISD860102_avg,AAindex_EISD860103_avg,AAindex_FAUJ830101_avg,AAindex_GOLD730101_avg,AAindex_GUYH850101_avg,AAindex_HOPT810101_avg,AAindex_JANJ790102_avg,AAindex_JOND750101_avg,AAindex_KYTJ820101_avg,AAindex_LAWE840101_avg,AAindex_LEVM760101_avg,AAindex_MANP780101_avg,AAindex_MIYS850101_avg,AAindex_NOZY710101_avg,AAindex_OOBM770101_avg,AAindex_OOBM770102_avg,AAindex_OOBM770103_avg,AAindex_OOBM770104_avg,AAindex_OOBM770105_avg,AAindex_OOBM850103_avg,AAindex_OOBM850104_avg,AAindex_PONP800101_avg,AAindex_PONP800102_avg,AAindex_PONP800103_avg,AAindex_PRAM900101_avg,AAindex_RADA880101_avg,AAindex_RADA880102_avg,AAindex_RADA880103_avg,AAindex_RADA880104_avg,AAindex_RADA880105_avg,AAindex_ROBB790101_avg,AAindex_ROSM880101_avg,AAindex_ROSM880102_avg,AAindex_SIMZ760101_avg,AAindex_SWER830101_avg,AAindex_VHEG790101_avg,AAindex_WERD780102_avg,AAindex_WERD780103_avg,AAindex_WERD780104_avg,AAindex_YUTK870101_avg,AAindex_YUTK870102_avg,AAindex_YUTK870103_avg,AAindex_YUTK870104_avg,AAindex_ZIMJ680101_avg,AAindex_PONP930101_avg,AAindex_WILM950101_avg,AAindex_WILM950102_avg,AAindex_WILM950103_avg,AAindex_WILM950104_avg,AAindex_KUHL950101_avg,AAindex_JURD980101_avg,AAindex_WOLR790101_avg,AAindex_KIDA850101_avg,AAindex_COWR900101_avg,AAindex_BLAS910101_avg,AAindex_CASG920101_avg,AAindex_ENGD860101_avg,AAindex_FASG890101_avg,class\n";
        try {
            FileWriter fileWriter = new FileWriter("V00662654_a2_missing004_imputed_mean_conditional.csv");
            fileWriter.append(fileHeader);
            for (int row = 0; row < 3587; row++) {
                for (int col = 0; col < 85; col++) {
                    if (col == 84) {
                        fileWriter.append(classArray[row + 1]);
                    } else {
                        fileWriter.append(String.valueOf(doubleArray[row][col]));
                        fileWriter.append(commaDelimiter);
                    }
                }
                fileWriter.append(newLine);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void mean20() {
        String csvFile = "assignment2_dataset_missing20.csv";
        String csvFileCompleted = "assignment2_dataset_complete.csv";
        String line = "";
        String line2 = "";
        String csvSplitBy = ",";
        int counter;
        int set = 0;
        String[] column = new String[3588];
        String[] columnCompleted = new String[3588];
        int x = 0; // row
        int y = 0; //column
        Double[][] doubleArray = new Double[3588][84];
        Double[][] doubleArrayCompleted = new Double[3588][84];
        String[] classArray = new String[3588];
        double MAE = 0;
        int denominatorCounter = 0;
        int quickCounter = 1;

        end:
        while (y < 85) {
            x++;
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                BufferedReader br2 = new BufferedReader(new FileReader(csvFileCompleted));
                //while loop stores elements in column array as string
                counter = 0;
                while ((line = br.readLine()) != null) {
                    line2 = br2.readLine();
                    // use comma as separator
                    String[] data = line.split(csvSplitBy);
                    String[] dataCompleted = line2.split(csvSplitBy);
                    if (y == 84) {
                        classArray[counter] = data[set];
                        counter++;
                    } else {
                        column[counter] = data[set];
                        columnCompleted[counter] = dataCompleted[set];
                        counter++;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (y == 84) { //break loops if last column (class column)
                y++;
                break end;
            }

            double sum = 0.0;
            double mean = 0.0;
            //while loop scans through column array and stores elements as double in doubleArray
            //stores ? as 1.0 for later parsing
            //calculates mean value of array
            while (x < doubleArray.length) {
                try {
                    doubleArray[x - 1][y] = Double.parseDouble(column[x]);
                    doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                    sum += doubleArray[x - 1][y];
                    mean = sum / x;
                    x++;
                } catch (NumberFormatException e) {
                    doubleArray[x - 1][y] = 1.0;
                    doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                    x++;
                }
            }

            x = 0;
            double roundedMean = Math.round(mean * 10000.0) / 10000.0;
            //System.out.println("rounded mean " + quickCounter + ": " + roundedMean);
            quickCounter++;
            //while loop parses through doubleArray and replaces 1.0 with mean value
            //prints array
            while (x < doubleArray.length - 1) {
                if (doubleArray[x][y] == 1.0) {
                    doubleArray[x][y] = roundedMean;
                    MAE += Math.abs(doubleArray[x][y] - doubleArrayCompleted[x][y]);
                    denominatorCounter++;
                }
                //System.out.println(x + ": " + doubleArray[x][y]);
                x++;
            }
            x = 0;
            y++;
            set++;
        }
        double MAE20 = Math.round((MAE / denominatorCounter) * 10000.0) / 10000.0;
        System.out.println("MAE_20_mean = " + MAE20);

        String commaDelimiter = ",";
        String newLine = "\n";
        String fileHeader = "AAcomp_A,AAcomp_R,AAcomp_N,AAcomp_D,AAcomp_C,AAcomp_Q,AAcomp_E,AAcomp_G,AAcomp_H,AAcomp_I,AAcomp_L,AAcomp_K,AAcomp_M,AAcomp_F,AAcomp_P,AAcomp_S,AAcomp_T,AAcomp_W,AAcomp_Y,AAcomp_V,AAindex_ARGP820101_avg,AAindex_BULH740101_avg,AAindex_CHAM820102_avg,AAindex_CIDH920105_avg,AAindex_EISD840101_avg,AAindex_EISD860101_avg,AAindex_EISD860102_avg,AAindex_EISD860103_avg,AAindex_FAUJ830101_avg,AAindex_GOLD730101_avg,AAindex_GUYH850101_avg,AAindex_HOPT810101_avg,AAindex_JANJ790102_avg,AAindex_JOND750101_avg,AAindex_KYTJ820101_avg,AAindex_LAWE840101_avg,AAindex_LEVM760101_avg,AAindex_MANP780101_avg,AAindex_MIYS850101_avg,AAindex_NOZY710101_avg,AAindex_OOBM770101_avg,AAindex_OOBM770102_avg,AAindex_OOBM770103_avg,AAindex_OOBM770104_avg,AAindex_OOBM770105_avg,AAindex_OOBM850103_avg,AAindex_OOBM850104_avg,AAindex_PONP800101_avg,AAindex_PONP800102_avg,AAindex_PONP800103_avg,AAindex_PRAM900101_avg,AAindex_RADA880101_avg,AAindex_RADA880102_avg,AAindex_RADA880103_avg,AAindex_RADA880104_avg,AAindex_RADA880105_avg,AAindex_ROBB790101_avg,AAindex_ROSM880101_avg,AAindex_ROSM880102_avg,AAindex_SIMZ760101_avg,AAindex_SWER830101_avg,AAindex_VHEG790101_avg,AAindex_WERD780102_avg,AAindex_WERD780103_avg,AAindex_WERD780104_avg,AAindex_YUTK870101_avg,AAindex_YUTK870102_avg,AAindex_YUTK870103_avg,AAindex_YUTK870104_avg,AAindex_ZIMJ680101_avg,AAindex_PONP930101_avg,AAindex_WILM950101_avg,AAindex_WILM950102_avg,AAindex_WILM950103_avg,AAindex_WILM950104_avg,AAindex_KUHL950101_avg,AAindex_JURD980101_avg,AAindex_WOLR790101_avg,AAindex_KIDA850101_avg,AAindex_COWR900101_avg,AAindex_BLAS910101_avg,AAindex_CASG920101_avg,AAindex_ENGD860101_avg,AAindex_FASG890101_avg,class\n";
        try {
            FileWriter fileWriter = new FileWriter("V00662654_a2_missing20_imputed_mean.csv");
            fileWriter.append(fileHeader);
            for (int row = 0; row < 3587; row++) {
                for (int col = 0; col < 85; col++) {
                    if (col == 84) {
                        fileWriter.append(classArray[row + 1]);
                    } else {
                        fileWriter.append(String.valueOf(doubleArray[row][col]));
                        fileWriter.append(commaDelimiter);
                    }
                }
                fileWriter.append(newLine);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void meancondition20() {
        String csvFile = "assignment2_dataset_missing20.csv";
        String csvFileCompleted = "assignment2_dataset_complete.csv";
        String line = "";
        String line2 = "";
        String csvSplitBy = ",";
        int counter;
        int set = 0;
        String[] column = new String[3588];
        String[] columnCompleted = new String[3588];
        int x = 0; // row
        int y = 0; //column
        Double[][] doubleArray = new Double[3588][84];
        Double[][] doubleArrayCompleted = new Double[3588][84];
        String[] classArray = new String[3588];
        double MAE = 0;
        int denominatorCounter = 0;
        int quickCounter = 1;

        while (y < 85) {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                //while loop stores class elements in classArray
                counter = 0;
                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] data = line.split(csvSplitBy);
                    if (y == 84) {
                        classArray[counter] = data[set];
                        //System.out.println(classArray[counter]);
                        counter++;
                    } else {
                        counter++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            y++;
            set++;
        }
        //System.out.println(classArray[1]);
        y = 0;
        set = 0;
        end:
        while (y < 85) {
            x++;
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                BufferedReader br2 = new BufferedReader(new FileReader(csvFileCompleted));
                //while loop stores elements in column array as string
                counter = 0;
                while ((line = br.readLine()) != null) {
                    line2 = br2.readLine();
                    // use comma as separator
                    String[] data = line.split(csvSplitBy);
                    String[] dataCompleted = line2.split(csvSplitBy);
                    if (y == 84) {
                        //classArray[counter] = data[set];
                        //System.out.println(classArray[counter]);
                        counter++;
                    } else {
                        column[counter] = data[set];
                        columnCompleted[counter] = dataCompleted[set];
                        counter++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (y == 84) { //break loops if last column (class column)
                y++;
                break end;
            }

            double Fsum = 0.0;
            double Fmean = 0.0;
            double Csum = 0.0;
            double Cmean = 0.0;
            //while loop scans through column array and stores elements as double in doubleArray
            //stores ? as 1.0 for later parsing
            //calculates mean value of array
            while (x < doubleArray.length) {
                try {
                    doubleArray[x - 1][y] = Double.parseDouble(column[x]);
                    doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                    if (classArray[x].equals("F")) {
                        Fsum += doubleArray[x - 1][y];
                        Fmean = Fsum / x;
                        x++;
                        //System.out.println(doubleArray[x-1][y]);
                        //System.out.println("true1");
                    } else if (classArray[x].equals("C")) {
                        Csum += doubleArray[x - 1][y];
                        Cmean = Csum / x;
                        x++;
                        //System.out.println("true2");
                    }
                } catch (NumberFormatException e) {
                    if (classArray[x].equals("F")) {
                        doubleArray[x - 1][y] = 1.0;
                        doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                        x++;
                    } else if (classArray[x].equals("C")) {
                        doubleArray[x - 1][y] = 2.0;
                        doubleArrayCompleted[x - 1][y] = Double.parseDouble(columnCompleted[x]);
                        x++;
                    }
                }
            }
            x = 0;
            double roundedFMean = Math.round(Fmean * 10000.0) / 10000.0;
            double roundedCMean = Math.round(Cmean * 10000.0) / 10000.0;
            //System.out.println("rounded mean " + quickCounter + ": " + roundedMean);
            quickCounter++;
            //while loop parses through doubleArray and replaces 1.0 with mean value
            //prints array
            while (x < doubleArray.length - 1) {
                if (doubleArray[x][y] == 1.0) {
                    doubleArray[x][y] = roundedFMean;
                    MAE += Math.abs(doubleArray[x][y] - doubleArrayCompleted[x][y]);
                    denominatorCounter++;
                } else if (doubleArray[x][y] == 2.0) {
                    doubleArray[x][y] = roundedCMean;
                    MAE += Math.abs(doubleArray[x][y] - doubleArrayCompleted[x][y]);
                    denominatorCounter++;
                }
                //System.out.println(x + ": " + doubleArray[x][y]);
                x++;
            }
            x = 0;
            y++;
            set++;
        }
        double MAE20 = Math.round((MAE / denominatorCounter) * 10000.0) / 10000.0;
        System.out.println("MAE_20_mean_conditional = " + MAE20);

        String commaDelimiter = ",";
        String newLine = "\n";
        String fileHeader = "AAcomp_A,AAcomp_R,AAcomp_N,AAcomp_D,AAcomp_C,AAcomp_Q,AAcomp_E,AAcomp_G,AAcomp_H,AAcomp_I,AAcomp_L,AAcomp_K,AAcomp_M,AAcomp_F,AAcomp_P,AAcomp_S,AAcomp_T,AAcomp_W,AAcomp_Y,AAcomp_V,AAindex_ARGP820101_avg,AAindex_BULH740101_avg,AAindex_CHAM820102_avg,AAindex_CIDH920105_avg,AAindex_EISD840101_avg,AAindex_EISD860101_avg,AAindex_EISD860102_avg,AAindex_EISD860103_avg,AAindex_FAUJ830101_avg,AAindex_GOLD730101_avg,AAindex_GUYH850101_avg,AAindex_HOPT810101_avg,AAindex_JANJ790102_avg,AAindex_JOND750101_avg,AAindex_KYTJ820101_avg,AAindex_LAWE840101_avg,AAindex_LEVM760101_avg,AAindex_MANP780101_avg,AAindex_MIYS850101_avg,AAindex_NOZY710101_avg,AAindex_OOBM770101_avg,AAindex_OOBM770102_avg,AAindex_OOBM770103_avg,AAindex_OOBM770104_avg,AAindex_OOBM770105_avg,AAindex_OOBM850103_avg,AAindex_OOBM850104_avg,AAindex_PONP800101_avg,AAindex_PONP800102_avg,AAindex_PONP800103_avg,AAindex_PRAM900101_avg,AAindex_RADA880101_avg,AAindex_RADA880102_avg,AAindex_RADA880103_avg,AAindex_RADA880104_avg,AAindex_RADA880105_avg,AAindex_ROBB790101_avg,AAindex_ROSM880101_avg,AAindex_ROSM880102_avg,AAindex_SIMZ760101_avg,AAindex_SWER830101_avg,AAindex_VHEG790101_avg,AAindex_WERD780102_avg,AAindex_WERD780103_avg,AAindex_WERD780104_avg,AAindex_YUTK870101_avg,AAindex_YUTK870102_avg,AAindex_YUTK870103_avg,AAindex_YUTK870104_avg,AAindex_ZIMJ680101_avg,AAindex_PONP930101_avg,AAindex_WILM950101_avg,AAindex_WILM950102_avg,AAindex_WILM950103_avg,AAindex_WILM950104_avg,AAindex_KUHL950101_avg,AAindex_JURD980101_avg,AAindex_WOLR790101_avg,AAindex_KIDA850101_avg,AAindex_COWR900101_avg,AAindex_BLAS910101_avg,AAindex_CASG920101_avg,AAindex_ENGD860101_avg,AAindex_FASG890101_avg,class\n";
        try {
            FileWriter fileWriter = new FileWriter("V00662654_a2_missing20_imputed_mean_conditional.csv");
            fileWriter.append(fileHeader);
            for (int row = 0; row < 3587; row++) {
                for (int col = 0; col < 85; col++) {
                    if (col == 84) {
                        fileWriter.append(classArray[row + 1]);
                    } else {
                        fileWriter.append(String.valueOf(doubleArray[row][col]));
                        fileWriter.append(commaDelimiter);
                    }
                }
                fileWriter.append(newLine);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}