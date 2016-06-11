
public class Lote implements Comparable<Lote>{
	private int id;
	private int tipoSolo;			/** 1 - Estavel, 2 - Instavel */
	private int inclinacao;			/** 1 - Reduzida, 2 - Normal, 3 - Elevada */
	private boolean estrada;		/** 1 - Com estrada, 0 - Sem estrada */
	private boolean lago;			/** 1 - Com lago, 0 - Sem lago */
	private int dimensao;			/** 1 - Pequeno, 2 - Normal, 3 - Grande */
	private float preco;			/** Valor */
	private int utilizacao;		/** Guarda inteiro correspondente a utilizacao que vai ter ; DEFAULT = 0 */ 
	
	Lote()
	{
		tipoSolo = 0;
		inclinacao = 0;
		estrada = false;
		lago = false;
		dimensao = 0;
		preco = (float) 0.0 ;
		utilizacao = 0 ;
	}
	
	public int getTipoSolo() {
		return tipoSolo;
	}
	public void setTipoSolo(int tipoSolo) {
		this.tipoSolo = tipoSolo;
	}
	public int getInclinacao() {
		return inclinacao;
	}
	public void setInclinacao(int inclinacao) {
		this.inclinacao = inclinacao;
	}
	public boolean hasEstrada() {
		return estrada;
	}
	public void setEstrada(boolean estrada) {
		this.estrada = estrada;
	}
	public boolean hasLago() {
		return lago;
	}
	public void setLago(boolean lago) {
		this.lago = lago;
	}
	public int getDimensao() {
		return dimensao;
	}
	public void setDimensao(int dimensao) {
		this.dimensao = dimensao;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public int getUtilizacao() {
		return utilizacao;
	}
	public void setUtilizacao(int utilizacao) {
		this.utilizacao = utilizacao;
	}
	public boolean isAvailable()
	{
		if (this.utilizacao == 0) return true;
		else return false;
	}
	
	@Override
	public int compareTo(Lote entrada) {
		return  Float.compare(this.preco,  entrada.getPreco());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
