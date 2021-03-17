import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")


public class Platno extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
		
	protected Graf graf;
	protected Tocka aktivnaTocka;
	protected Set<Tocka> izbraneTocke;
	
	protected Color barvaTocke;
	protected Color barvaAktivneTocke;
	protected Color barvaIzbraneTocke;
	
	protected double starX;
	protected double starY;
	protected double klikX;	
	protected double klikY;
	
	
	protected Color barvaPovezave;
	protected Color barvaRoba;
	protected int polmer;
	protected int debelinaRoba;
	protected int debelinaPovezave;
	
	
	public Platno(int sirina, int visina) {
		super();	// super predstavlja nadrazred
		graf = null;
		this.barvaTocke = Color.BLUE;
		this.barvaPovezave = Color.BLUE;
		this.barvaRoba = Color.BLACK;
		this.polmer = 12;
		this.debelinaRoba = 6;
		this.debelinaPovezave = 3;
		
		izbraneTocke = new HashSet<Tocka>();
		aktivnaTocka = null;
		
		barvaIzbraneTocke = Color.YELLOW;
		barvaAktivneTocke = Color.MAGENTA;
		
		setPreferredSize(new Dimension(sirina,visina));	
		
		addMouseListener(this); // Ob dogodku na objektu bo poklicana metoda 
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true); // Da lahko tipkamo (da je fokus na platnu)
	}
	
	public void nastaviGraf(Graf g) {
		graf = g;
		
		aktivnaTocka = null;
		izbraneTocke.clear();
		
		repaint(); // To vedno poklièemo ko kaj popravimo
		
	}
	
	private static int round(double x) { // pomožne metode so lahko privatne
		return (int)(x + 0.5);
	}
	
	@Override // Znova definiramo neko že-definirano metodo
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // Nariše ozadje
		Graphics2D g2 = (Graphics2D) g;
		
		if (graf == null) return;
		
		for (Tocka v: graf.tocke.values()) {
			for (Tocka u : v.sosedi) {
				if (v.ime.compareTo(u.ime) > 0) {
				g.setColor(barvaPovezave);
				g2.setStroke(new BasicStroke(debelinaPovezave));
				g.drawLine(round(u.x),round(u.y), round(v.x), round(v.y));
				}
			}
		}
		for (Tocka v: graf.tocke.values()) {
			if (v == aktivnaTocka) {
				g.setColor(barvaAktivneTocke);
				g.fillOval(round(v.x) - polmer, round(v.y) - polmer, 2 * polmer, 2 * polmer);
				g.setColor(barvaRoba);
				g2.setStroke(new BasicStroke(debelinaRoba));
				g.drawOval(round(v.x) - polmer, round(v.y) - polmer, 2 * polmer, 2 * polmer);				
			}
			else if (izbraneTocke.contains(v)) {
				g.setColor(barvaIzbraneTocke);
				g.fillOval(round(v.x) - polmer, round(v.y) - polmer, 2 * polmer, 2 * polmer);
			
				g.setColor(barvaRoba);
				g2.setStroke(new BasicStroke(debelinaRoba));
				g.drawOval(round(v.x) - polmer, round(v.y) - polmer, 2 * polmer, 2 * polmer);
			}
			else {
				g.setColor(barvaTocke);
				g.fillOval(round(v.x) - polmer, round(v.y) - polmer, 2 * polmer, 2 * polmer);
			
				g.setColor(barvaRoba);
				g2.setStroke(new BasicStroke(debelinaRoba));
				g.drawOval(round(v.x) - polmer, round(v.y) - polmer, 2 * polmer, 2 * polmer);
			}
		}
		// 
	}

	@Override
	public void mousePressed(MouseEvent e) { // Pritisnemo miško
		double x = e.getX(); double y = e.getY();
		starX = x;
		starY = y;
		klikX = x;
		klikY = y;
		Tocka najblizjaTocka = null;
		double najmanjsaRazdalja = 10;
		for (Tocka t : graf.tocke.values()) {
			double razdalja = Math.sqrt((x-t.x)*(x-t.x)+(y-t.y)*(y-t.y));
			if (razdalja < najmanjsaRazdalja) {
				najmanjsaRazdalja = razdalja;
				najblizjaTocka = t;
			}
		}
		if (najblizjaTocka != null) {
			aktivnaTocka = najblizjaTocka;
		}
		repaint();
	}	
	
	@Override
	public void mouseReleased(MouseEvent e) { // Spustimo miško
		int x = e.getX(); int y = e.getY();
		if (aktivnaTocka == null) {
			Tocka t = graf.dodajTocko();
			t.x = x; t.y = y;
			for (Tocka v : izbraneTocke) {
				graf.dodajPovezavo(v, t);
			}
			repaint();
		}
		else if (klikX == x && klikY == y) {
			if (izbraneTocke.contains(aktivnaTocka)) {
				izbraneTocke.remove(aktivnaTocka);
			}
			else izbraneTocke.add(aktivnaTocka);
		aktivnaTocka = null;
		}
		
	}	
	
	@Override
	public void mouseDragged(MouseEvent e) { // Drag
		if (aktivnaTocka != null) {
			starX = aktivnaTocka.x;
			starY = aktivnaTocka.y;
			double x = e.getX(); double y = e.getY();
			double dx = x - starX;
			double dy = y - starY;
			aktivnaTocka.premakni(dx, dy);
		}
		else {
			for (Tocka t : izbraneTocke) {
				starX = t.x;
				starY = t.y;
				double x = e.getX(); double y = e.getY();
				double dx = x - starX;
				double dy = y - starY;
				aktivnaTocka.premakni(dx, dy);
			}	
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) { // Premik miške
		
	}

	@Override
	public void keyTyped(KeyEvent e) { // Pritisk tipke
		char tipka = e.getKeyChar();
			if (tipka == 'a') {
			//
				repaint();
		}
	}

	
	// Teh od tu naprej ne rabimo, ampak moramo pustiti definirane
	@Override
	public void mouseClicked(MouseEvent e) {} // Moramo pustiti definirane

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}


}
