import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class WaterTheme extends DefaultMetalTheme {

	public String getName() {

		return "Water";
	}

	private final ColorUIResource primary1 = new ColorUIResource (60, 135, 215);
	private final ColorUIResource primary2 = new ColorUIResource (100, 165, 230);
	private final ColorUIResource primary3 = new ColorUIResource (110, 170, 235);
	private final ColorUIResource secondary1 = new ColorUIResource (111, 111, 111);
	private final ColorUIResource secondary2 = new ColorUIResource (159, 159, 159);
	private final ColorUIResource secondary3 = new ColorUIResource (175, 205, 245);

	protected ColorUIResource getPrimary1() { return primary1; }
	protected ColorUIResource getPrimary2() { return primary2; }
	protected ColorUIResource getPrimary3() { return primary3; }
	protected ColorUIResource getSecondary1() { return secondary1; }
	protected ColorUIResource getSecondary2() { return secondary2; }
	protected ColorUIResource getSecondary3() { return secondary3; }

}