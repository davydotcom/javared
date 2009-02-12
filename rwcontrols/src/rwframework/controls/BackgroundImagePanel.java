/*
 * BackgroundImagePanel.java
 *
 * Created on October 25, 2007, 6:42 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package rwframework.controls;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.*;

/**
 *
 * @author David Estes
 */

public class BackgroundImagePanel extends JComponent {
 
	private Image backgroundImage = null;
        private boolean repeatx = false;
        private boolean bottom = false;
	/**
	 * Constructor
	 */
	public BackgroundImagePanel() {
		super();
	}
        public void loadStandardImage()
        {
            java.net.URL imgURL = BackgroundImagePanel.class.getResource("/rwframework/images/redbar.png");
            ImageIcon standardbar = new ImageIcon(imgURL);
            this.setBackground(java.awt.Color.BLACK);
            this.setBackgroundImage(standardbar.getImage(), true, true);
        }
 
	/**
	 * Returns the background image
	 * @return	Background image
	 */
	public Image getBackgroundImage() {
		return backgroundImage;
	}
 
	/**
	 * Sets the background image
	 * @param backgroundImage	Background image
	 */
	public void setBackgroundImage(Image backgroundImage,boolean repeatx,boolean bottom) {
		this.backgroundImage = backgroundImage;
                this.repeatx = true;
                this.bottom = bottom;
	}
	
	/**
	 * Overrides the painting to display a background image
	 */
	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
                int ypos = 0;
                if (bottom)
                {
                    ypos = this.getHeight() - backgroundImage.getHeight(null);
                }
		if (backgroundImage != null) {
                        if(!this.repeatx)
                        {
			g.drawImage(backgroundImage,0,ypos,this);
                        
                        }
                        else
                        {
                            
                            int x = backgroundImage.getWidth(null);
                            for(int counter = 0;counter < getWidth();counter = counter + x)
                            {
                                g.drawImage(backgroundImage,counter,ypos,this);        
                            }
                        }
		}
	}
}