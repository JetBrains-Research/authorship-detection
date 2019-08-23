import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class BrownTheme extends DefaultMetalTheme {

	public String getName() {

		return "Brown";

	}

	private final ColorUIResource primary1 = new ColorUIResource (87, 87, 47);
	private final ColorUIResource primary2 = new ColorUIResource (159, 151, 111);
	private final ColorUIResource primary3 = new ColorUIResource (185, 160, 115);
	private final ColorUIResource secondary1 = new ColorUIResource (111, 111, 111);
	private final ColorUIResource secondary2 = new ColorUIResource (159, 159, 159);
	private final ColorUIResource secondary3 = new ColorUIResource (225, 200, 160);

	protected ColorUIResource getPrimary1() { return primary1; }
	protected ColorUIResource getPrimary2() { return primary2; }
	protected ColorUIResource getPrimary3() { return primary3; }
	protected ColorUIResource getSecondary1() { return secondary1; }
	protected ColorUIResource getSecondary2() { return secondary2; }
	protected ColorUIResource getSecondary3() { return secondary3; }

}