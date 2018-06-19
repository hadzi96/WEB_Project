package webApp.responses;

public class StatistikaResponse {
	public int brProdatihSlika;
	public String najcescaRez;
	public double prosecnaCena;

	public StatistikaResponse(int brProdatihSlika, String najcescaRez, double prosecnaCena) {
		this.brProdatihSlika = brProdatihSlika;
		this.najcescaRez = najcescaRez;
		this.prosecnaCena = prosecnaCena;
	}

}
