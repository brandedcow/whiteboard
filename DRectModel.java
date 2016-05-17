import java.awt.*;

/**
 * Created by Chris on 5/16/2016.
 */
public class DRectModel extends DShapeModel {
    DRectModel() {
        bounds = new Rectangle(0, 0, 50, 50);
        point = new Point(0, 0);
        color = Color.gray;
        type = "rect";
    }

}
