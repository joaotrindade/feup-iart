
public class Utilizacao implements Comparable<Utilizacao> {
	private int id;
	private String nome;
	
	private boolean restricaoSolo;			/** Se true -> E' necessario solo estavel */
	private boolean restricaoSolo_Forte;	/** Se é 1 é Forte se é 0 é Fraca **/
	
	private boolean restricaoLago;			/** Se true -> E' necessario ter lago */
	private boolean restricaoLago_Forte;    /** Se é 1 é Forte se é 0 é Fraca **/
					
	private boolean restricaoEstrada;				/** Se true -> E' necessario ter Estrada */
	private boolean restricaoEstrada_Forte;  	/** Se é 1 é Forte se é 0 é Fraca **/
	
	private int inclinacao;				/** 1 - Reduzida, 2 - Normal, 3 - Elevada */
	private boolean restricaoInclinacao; 
	private boolean restricaoInclinacao_Forte;
	
	private int dimensao;					/** 1 - Pequeno, 2 - Normal, 3 - Grande */
	private boolean restricaoDimensao;
	private boolean restricaoDimensao_Forte;
	
	private float custo;					/** Valor */ 
	
	
	Utilizacao()
	{
		setId(0) ;
		restricaoSolo = false;
		restricaoEstrada = false;
		restricaoLago = false;
		inclinacao = 2;
		restricaoInclinacao = false;
		dimensao = 2;
		restricaoDimensao = false;
		
		setRestricaoSolo_Forte(false);
		setRestricaoLago_Forte(false);
		setRestricaoEstrada_Forte(false);
		setRestricaoInclinacao_Forte(false);
		setRestricaoDimensao_Forte(false);
	}
	
	public void print()
	{
		System.out.println("#Utilizacao/Restricao");
		System.out.println("nome: " + this.nome);
		System.out.println("restricao solo: " + this.hasRestricaoSolo());
		System.out.println("restricao lago: " + this.hasRestricaoLago());
		System.out.println("restricao incl: " + this.hasRestricaoInclinacao());
		System.out.println("restricao dime: " + this.hasRestricaoDimensao());
		System.out.println("###########################");
	}
	
	public int getInclinacao() {
		return inclinacao;
	}
	public void setInclinacao(int inclinacao) {
		this.inclinacao = inclinacao;
	}
	public int getDimensao() {
		return dimensao;
	}
	public void setDimensao(int dimensao) {
		this.dimensao = dimensao;
	}

	public boolean hasRestricaoSolo() {
		return restricaoSolo;
	}

	public void setRestricaoSolo(boolean restricaoSolo) {
		this.restricaoSolo = restricaoSolo;
	}

	public boolean hasRestricaoInclinacao() {
		return restricaoInclinacao;
	}

	public void setRestricaoInclinacao(boolean restricaoInclinacao) {
		this.restricaoInclinacao = restricaoInclinacao;
	}

	public boolean getRestricaoEstrada() {
		return restricaoEstrada;
	}

	public void setRestricaoEstrada(boolean restricaoEstrada) {
		this.restricaoEstrada = restricaoEstrada;
	}

	public boolean hasRestricaoLago() {
		return restricaoLago;
	}

	public void setRestricaoLago(boolean restricaoLago) {
		this.restricaoLago = restricaoLago;
	}

	public boolean hasRestricaoDimensao() {
		return restricaoDimensao;
	}

	public void setRestricaoDimensao(boolean restricaoDimensao) {
		this.restricaoDimensao = restricaoDimensao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public float getCusto() {
		return custo;
	}
	public void setCusto(float custo) {
		this.custo = custo;
	}

	public int getNumeroRestricoes() {
        int count = 0 ; 
        if (this.hasRestricaoDimensao() && this.getRestricaoDimensao_Forte())  count++;
        if (this.getRestricaoEstrada() && this.getRestricaoEstrada_Forte()) count++;
        if (this.hasRestricaoInclinacao() && this.getRestricaoInclinacao_Forte()) count++;
        if (this.hasRestricaoLago() && this.getRestricaoLago_Forte()) count++;
        if (this.hasRestricaoSolo() && this.getRestricaoSolo_Forte()) count++;
        
        return count;
    }

	@Override
	public int compareTo(Utilizacao input) {
		return Integer.compare(this.getNumeroRestricoes() , input.getNumeroRestricoes());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getRestricaoSolo_Forte() {
		return restricaoSolo_Forte;
	}

	public void setRestricaoSolo_Forte(boolean restricaoSolo_Forte) {
		this.restricaoSolo_Forte = restricaoSolo_Forte;
	}

	public boolean getRestricaoLago_Forte() {
		return restricaoLago_Forte;
	}

	public void setRestricaoLago_Forte(boolean restricaoLago_Forte) {
		this.restricaoLago_Forte = restricaoLago_Forte;
	}

	public boolean getRestricaoEstrada_Forte() {
		return restricaoEstrada_Forte;
	}

	public void setRestricaoEstrada_Forte(boolean restricaoEstrada_Forte) {
		this.restricaoEstrada_Forte = restricaoEstrada_Forte;
	}

	public boolean getRestricaoInclinacao_Forte() {
		return restricaoInclinacao_Forte;
	}

	public void setRestricaoInclinacao_Forte(boolean restricaoInclinacao_Forte) {
		this.restricaoInclinacao_Forte = restricaoInclinacao_Forte;
	}

	public boolean getRestricaoDimensao_Forte() {
		return restricaoDimensao_Forte;
	}

	public void setRestricaoDimensao_Forte(boolean restricaoDimensao_Forte) {
		this.restricaoDimensao_Forte = restricaoDimensao_Forte;
	}

	
}
