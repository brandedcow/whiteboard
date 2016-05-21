import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;


public class DTextModel extends DShapeModel{
    DTextModel() {
        bounds = new Rectangle(10,10,20,20);
        color = Color.gray;
        type = "rect";
        dShape = new DText(this);
        modelListeners = new ArrayList<>();

    }

     DTextModel(boolean random){
        if (random) {
            Random r = new Random();
            //------------------------------------------------------------
            // Random Position and Size
            // dimensions of shape from 25-200
            int dimX= r.nextInt(195)+5;
            int dimY = r.nextInt(195)+5;

            // coordinates of shape from 0 to 400-dimensions
            int x = r.nextInt(600);
            while ((x+dimX) > 400){
                x = r.nextInt(600);
            }
            int y = r.nextInt(600);
            while ((y+dimY) > 400){
                y = r.nextInt(600);
            }

            bounds = new Rectangle(x,y,dimX,dimY);
            point = new Point(x, y);

            /*// Randomize Color
            float red = r.nextFloat();
            float green = r.nextFloat();
            float blue = r.nextFloat();
            color = new Color(red,green,blue);*/

            color = Color.GRAY;
            type = "rect";
            dShape = new DText(this);
            modelListeners = new ArrayList<>();

        }
    }


}