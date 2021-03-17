import java.util.HashMap;
import java.util.Map;

public class Graf {
	
	private int stevec; // Privatna, ker jo bomo uporabljali samo znotraj tega razreda
	protected Map<String, Tocka> tocke;
	
	public Graf() {
		stevec = 0;
		tocke = new HashMap<String, Tocka>();
	}
		
	public Tocka tocka(String ime) {
		return tocke.get(ime); // Get vrne vrednost kljuèa
	}
	
	public boolean povezava(Tocka v, Tocka u) {
		return v.sosedi.contains(u); // Ali je u med sosedi v-ja
	}
	
	
	
	public Tocka dodajTocko(String ime) {
		// Preverimo, ali ta toèka že obstaja
		Tocka v = tocka(ime);
		if (v == null) {
			v = new Tocka(ime);
			tocke.put(ime, v); // metoda put doda element v slovar
		}
		return v;
	}
	
	public Tocka dodajTocko() {
		while (true) { // zanka se sama konèa z returnom
			String ime = Integer.toString(++stevec); // moramo pisati ++ na zaèetku, sicer najprej pretvori v niz in nato šele poveèa za ena
			if (tocka(ime) != null) continue;
			Tocka v = new Tocka(ime);
			tocke.put(ime, v);
			return v; // izhod iz zanke
		}
	}
	
	public void dodajPovezavo(Tocka v, Tocka u) {
		if (v == u) return; // Ne vrne niè èe tako napišemo
		v.sosedi.add(u); // Ni treba testirati, èe je toèka že v množici. Namreè èe dodaš dvakrat v množico je še vedno samo ena notri
		u.sosedi.add(v);
	}
	
	public void odstraniPovezavo(Tocka v, Tocka u) {
		v.sosedi.remove(u);
		u.sosedi.remove(v);
	}
	
	public void odstraniTocko(Tocka u) {
		// for (Tocka v : u.sosedi) {odstraniPovezavo(v, u); Ne smemo tako, ker spreminjamo slovar po katerem teèe zanka
		for (Tocka v : u.sosedi) v.sosedi.remove(u);
		tocke.remove(u.ime);
	}
	
	
	
	private Tocka[] dodajTocke(int n) { // private ker je pomožna
		Tocka[] tab = new Tocka[n]; // Ustvarimo novo tabelo velikosti n z vrednostmi null
		for (int i = 0; i < n; ++i) tab[i] = dodajTocko();
		return tab;
	}
	
	public static Graf prazen(int n) {
		Graf graf = new Graf(); // Ustvari
		graf.dodajTocke(n);
		return graf; // Vrne
	}
	
	public static Graf cikel(int n) {
		Graf graf = new Graf();
		Tocka[] tocke = graf.dodajTocke(n);
		
		for (int i = 0; i < n; i++) {
			graf.dodajPovezavo(tocke[i], tocke[(i+1) % n]);
		}
		return graf;
	}
	
	public static Graf poln(int n) {
		Graf graf = new Graf();
		Tocka[] tocke = graf.dodajTocke(n);
	for (int i = 0; i < n; i++) {
		for (int j = i + 1; j < n; j++) {
			graf.dodajPovezavo(tocke[i], tocke[j]);
		}
	}
		return graf;
	}
	
	public static Graf polnDvodelen(int n, int m) {
		Graf graf = new Graf();
		Tocka[] tab1 = graf.dodajTocke(n);
		Tocka[] tab2 = graf.dodajTocke(m);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				graf.dodajPovezavo(tab1[i], tab2[j]);
			}
		}
		return graf;
	}

	
	
	
	public void izpis() { // Ni treba parametra graf ker ima itak dostop do njega
		for (Tocka v : tocke.values()) {
			System.out.print(v + ":");
			for (Tocka u : v.sosedi) {
				System.out.print(" " + u);
			}
			System.out.println();
		}
	}
	
	
	// 4. VAJE
	
	public void razporedi(double x, double y, double r) {
		int n = tocke.size(); // size je metoda na slovarju, tocke so slovar
		int i = 0;
		for (Tocka v : tocke.values()) {
			v.x = x + r * Math.cos(2 * i * Math.PI / n);
			v.y = y + r * Math.sin(2 * i * Math.PI / n);
			++i;
		}
	}
	
	
}
