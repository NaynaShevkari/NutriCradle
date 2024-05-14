package org.example;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * A custom JPanel that paints a background image spanning the entire area of the panel. This panel
 * is designed to display an image as its background, resizing the image to fit the panel
 * dimensions.
 */

public class BackgroundPanel extends JPanel {

  private final Image image;

  /**
   * Constructs a BackgroundPanel with the specified image path. The image at the given path is
   * loaded and used as the background for this panel.
   *
   * @param imagePath the relative or absolute path to the image file
   */

  public BackgroundPanel(String imagePath) {
    image = new ImageIcon(imagePath).getImage();
  }

  /**
   * Custom paint component for drawing the background image. This method is overridden to draw the
   * background image scaled to the size of the panel.
   *
   * @param g the Graphics object to protect
   */

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
  }
}
