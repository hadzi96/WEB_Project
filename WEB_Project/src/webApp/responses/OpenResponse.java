package webApp.responses;

import java.util.List;

public class OpenResponse {
	public int id;
	public String mesto;
	public String rezolucije;
	public String cene;
	public String opis;
	public String ocena;

	public OpenResponse(int id, String mesto, String rezolucije, String cene, String opis, String ocena) {
		super();
		this.id = id;
		this.mesto = mesto;
		this.rezolucije = rezolucije;
		this.cene = cene;
		this.opis = opis;
		this.ocena = ocena;
	}

}
