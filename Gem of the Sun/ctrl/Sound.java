package ctrl;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import java.io.File;

public class Sound extends JFrame {
	private static final long serialVersionUID = -8495663305077106289L;
	private File file;
	private Memory m = new Memory();
	private Clip clip;

	public void playSound(int option) {
		if (m.readSettings().charAt(1) == 'T') {
			switch (option) {
			case 0:
				file = new File(getAbsolutePath("click"));
				break;
			case 1:
				file = new File(getAbsolutePath("crossbow"));
				break;
			case 2:
				file = new File(getAbsolutePath("enemyprojectile"));
				break;
			case 3:
				file = new File(getAbsolutePath("playerdie"));
				break;
			case 4:
				file = new File(getAbsolutePath("playerhit"));
				break;
			case 5:
				file = new File(getAbsolutePath("explosion"));
				break;
			case 6:
				file = new File(getAbsolutePath("boss_fire"));
				break;
			case 7:
				file = new File(getAbsolutePath("batdie"));
				break;
			case 8:
				file = new File(getAbsolutePath("slimedie"));
				break;
			case 9:
				file = new File(getAbsolutePath("crystaldie"));
				break;
			case 10:
				file = new File(getAbsolutePath("pierce"));
				break;
			}
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(file));
				clip.setFramePosition(0);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getAbsolutePath(String path) {
		File filePath = new File("");
		String absPath = filePath.getAbsolutePath() + File.separator + "res" + 
		File.separator +"snd" + File.separator;
		return absPath + path + ".wav";
	}
}
