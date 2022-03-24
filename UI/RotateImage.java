/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *
 * @author sosso
 */
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RotateImage {

    public static BufferedImage rotateImage(BufferedImage imageToRotate) {
        int widthOfImage = imageToRotate.getWidth();
        int heightOfImage = imageToRotate.getHeight();
        int typeOfImage = imageToRotate.getType();

        BufferedImage newImageFromBuffer = new BufferedImage(widthOfImage, heightOfImage, typeOfImage);

        Graphics2D graphics2D = newImageFromBuffer.createGraphics();

        graphics2D.rotate(Math.toRadians(90), widthOfImage / 2, heightOfImage / 2);
        graphics2D.drawImage(imageToRotate, null, 0, 0);

        return newImageFromBuffer;
    }

    public static void main(String[] args) {

        try {

            BufferedImage originalImage = ImageIO.read(new File("./src/jpg/brain1_0003.jpg"));
            
            //"./src/jpg/182091.jpg"

            BufferedImage subImage = rotateImage(originalImage);

           File rotatedImageFile = new File("C:\\Users\\sosso\\Documents\\TIS 4\\SEMESTRE 2 (8)\\UE1  INGENIERIE INFORMATIQUE POUR LA SANTE 5\\Projet SIS\\ProjetSIRNew\\src\\jpg\\imagePivoté2223.jpg");
            //"C:\\Users\\sosso\\Documents\\TIS 4\\SEMESTRE 2 (8)\\UE1  INGENIERIE INFORMATIQUE POUR LA SANTE 5\\Projet SIS\\ProjetSIRNew\\src\\jpg"
           
           ImageIO.write(subImage, "jpg", rotatedImageFile);

           // System.out.println("New Rotated Image File Path: "+rotatedImageFile.getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}