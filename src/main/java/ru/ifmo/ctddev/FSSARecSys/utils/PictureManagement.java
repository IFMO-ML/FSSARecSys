package ru.ifmo.ctddev.FSSARecSys.utils;

import weka.core.Instance;
import weka.core.Instances;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Администратор on 19.10.2015.
 */
public class PictureManagement {
    private int numOfClusters;
    private Instances dataSet;
    private int[] assignments;

    private ArrayList<Color> colors = new ArrayList<>();

    private void assignColors(){
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.PINK);
        colors.add(Color.CYAN);
        colors.add(Color.orange);
        colors.add(Color.MAGENTA);
        colors.add(Color.YELLOW);
        colors.add(Color.getHSBColor(180, 240, 120));
    }

    public PictureManagement(int n, Instances dataSet, int[] assignments) {
        this.numOfClusters = n;
        this.dataSet = dataSet;
        this.assignments = assignments;
        assignColors();
    }

    public void DrawPicture(String name) throws IOException {
        BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

        Graphics2D ig = bi.createGraphics();

        ig.setColor(Color.WHITE);
        ig.fillRect(0, 0, 500, 500);

        ig.setBackground(Color.WHITE);
        for (int i = 0; i < dataSet.numInstances(); i++){
            Instance instance = dataSet.instance(i);
            int x = (int)instance.value(0);
            int y = (int)instance.value(1);

            if (assignments[i] < 0)
                ig.setPaint(Color.black);
            else
                ig.setPaint(colors.get(assignments[i]));
            ig.draw(new Line2D.Double(x-1, y-1, x+1, y+1));
            ig.draw(new Line2D.Double(x-1, y+1, x+1, y-1));

            ImageIO.write(bi, "PNG", new File(name + ".PNG"));
        }

    }
}
