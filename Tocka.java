import java.util.HashSet;
import java.util.Set; // Vkljuèi definicijo Seta

public class Tocka {

	protected String ime; // komponente niso javne
	protected Set<Tocka> sosedi; // protected omeji dostop
	protected double x, y;
		
	public Tocka(String ime) { // konstruktor
		this.ime = ime; // this. je podobno kot v Pythonu self.
		sosedi = new HashSet<Tocka>(); // tukaj ni treba this., ker ni dileme katere sosede mislimo
		x = y = 0;
		// Zgoraj je recimo this, ker je komponenta ime in spremenljivka ime, da vemo na katerega se nanaša
	}
	
	public int stopnja() { // št. sosedov
		return sosedi.size();
	}
	
	@Override // S tem povemo, da delamo redefinicijo metode iz nadrazreda
	public String toString() { // osnovna metoda, ki jo imajo definirano vsi objekti, zato moramo paziti kako jo definiramo, da ne povozi one osnovne
		return ime;
	}
	
	public void premakni(double dx, double dy) {
		this.x = x + dx; this.y = y + dy;
	}
}
