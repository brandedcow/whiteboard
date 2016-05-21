import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class DText extends DShape{
	private String input = "Hello";
	private String font;
    DText(){

    }
    DText(DTextModel model){
        dsm = model;
        dsm.addListener(this);
    }

    public void draw(Graphics2D g2) {
		FontMetrics fm = g2.getFontMetrics();
		g2.setColor(getColor());
		//int x = (getWidth() - fm.stringWidth(input)) / 2;
		int y = (fm.getAscent() + (getHeight() - (fm.getAscent() + fm.getDescent())) / 2);
		Font myFont = new Font(font, Font.PLAIN, getHeight()/2);
		g2.setFont(myFont);
		g2.drawString(input, getX(), y + getY());
    }
/**        if (selected){
            g2.setColor(dsm.getColor().brighter());
            selected = false;
        } else {
            g2.setColor(dsm.getColor());
        }
        g2.fillRect(dsm.getX(),dsm.getY(),dsm.getWidth(),dsm.getHeight());
    }**/
	public String getInput()
	{
		return input;
	}

	public void setInput(String i)
	{
		input = i;
	}

	public void setFont(String f)
	{
		font = f;
	}
}