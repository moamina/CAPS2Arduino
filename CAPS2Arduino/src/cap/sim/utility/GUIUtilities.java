package cap.sim.utility;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLegacyDefaultLookAndFeel;
import org.jvnet.substance.api.skin.SubstanceGeminiLookAndFeel;
import org.jvnet.substance.api.skin.SubstanceMagellanLookAndFeel;
import org.jvnet.substance.skin.SubstanceAutumnLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;
import org.jvnet.substance.skin.SubstanceDustLookAndFeel;
import org.jvnet.substance.skin.SubstanceMistAquaLookAndFeel;
import org.jvnet.substance.skin.SubstanceModerateLookAndFeel;
import org.jvnet.substance.skin.SubstanceNebulaBrickWallLookAndFeel;
import org.jvnet.substance.skin.SubstanceNebulaLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenLookAndFeel;
import org.jvnet.substance.skin.SubstanceTwilightLookAndFeel;

import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;


public class GUIUtilities {
	
	public static void GetDefault()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceGeminiLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceGeminiLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceCremeLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceAutumnLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceDustLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceDustLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceLegacyDefaultLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceLegacyDefaultLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceNebulaBrickWallLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceNebulaBrickWallLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceRavenGraphiteLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceRavenGraphiteLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceTwilightLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceTwilightLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceModerateLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceMagellanLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceMagellanLookAndFeel());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceMistAquaLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceMistAquaLookAndFeel());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceRavenLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceRavenLookAndFeel());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceRavenGraphiteGlassLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceRavenGraphiteGlassLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceNebulalLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceNebulaLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSubstanceBusinessLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel() );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void GetSyntheticaBlackStarLookAndFeel()
	{
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
