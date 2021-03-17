import javax.swing.JFrame;


@SuppressWarnings("serial")

public class Okno extends JFrame {

	protected Platno platno;
	
	public Okno() {
		super();
		setTitle("Urejevalnik grafov");
		platno = new Platno(400,400);
		add(platno); // S to metodo ga vkljuèimo v okno
	}
}
