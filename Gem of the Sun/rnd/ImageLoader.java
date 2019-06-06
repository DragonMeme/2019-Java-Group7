package rnd;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
	private BufferedImage image;

	public ImageLoader(String img) {

		try {
			image = ImageIO.read(new File(getAbsolutePath(img)));
		} catch (IOException e) {
			System.out.print(getAbsolutePath(img) + " ");
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public BufferedImage getPartImage(int x, int y, int width, int height) {
		BufferedImage partImage = image.getSubimage(x, y, width, height);
		return partImage;
	}

	private String getAbsolutePath(String path) {
		File filePath = new File("");
		String absPath = filePath.getAbsolutePath() + File.separator + 
				"res" + File.separator + "img" + File.separator;
		return absPath + path + ".png";
	}
}
