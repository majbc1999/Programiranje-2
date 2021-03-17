
public class Test {

	public static void main(String[] args) {
		Graf g = Graf.poln(8);
		g.razporedi(200,200,150);
		g.izpis();
		
		Okno okno = new Okno();
		okno.pack(); // Razporedi layout manager
		okno.setVisible(true); // false ga skrije, true ga pokaže
		okno.platno.nastaviGraf(g);
		
	}

}
