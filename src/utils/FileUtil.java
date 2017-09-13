package utils;

import assets.Element;
import platformer.ShapeType;

import java.io.*;
import java.util.List;

public class FileUtil {

    public static void writeElementSetToFile(List<Element> list, String fileName) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
        for (Object aSet : list) {
            out.write(aSet + "\n");
        }
        out.close();
    }

    public static void readElementSetFromFileTo(List<Element> list, String fileName) throws IOException, ClassNotFoundException {

        FileReader in = new FileReader(fileName);
        BufferedReader br = new BufferedReader(in);
        String line = br.readLine();
        while (line != null) {
            String[] lineValues = line.split(",");
            int subImageX = Integer.parseInt(lineValues[0]);
            int subImageY = Integer.parseInt(lineValues[1]);
            int positionX = Integer.parseInt(lineValues[2]);
            int positionY = Integer.parseInt(lineValues[3]);
            ShapeType shapeType = null;
            if (lineValues[4].equals("StairsRightDown")) {
                shapeType = ShapeType.StairsRightDown;
            }
            if (lineValues[4].equals("HighCelling")) {
                shapeType = ShapeType.HighCelling;
            }

            list.add(new Element(positionX, positionY, subImageX, subImageY, shapeType));
            line = br.readLine();
        }
        in.close();
    }
}