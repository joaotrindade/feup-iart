import java.util.ArrayList;
import java.util.Collections;


public class Estado implements Comparable<Estado> {

	public int[] listaEstado = new int[Main.numUtilizacoes];
	public int numRestricoesFracas;
	
	Estado(){
		for(int i=0 ; i < Main.numUtilizacoes ; i++ )
		{
			listaEstado[i] = -1 ; 
		}
		numRestricoesFracas = 0 ;
	}
	
	int getAtribuidos()
	{
		int sum = 0;
		for(int i = 0; i < Main.numUtilizacoes; i++)
		{
			if (listaEstado[i] != -1) sum++;
		}
		return sum;
	}
	
	boolean eEstadoFinal()
	{
		if (this.getAtribuidos() == listaEstado.length) return true;
		return false;
	}
	
	
	float getCustoDistancia()
	{
		
		float sum = (float) 0;
		for(int i = 0; i < Main.numUtilizacoes; i++)
		{
			if (listaEstado[i] != -1)
			{
				sum += Main.listaLotes.get(listaEstado[i]).getPreco();
			}
		}
		return sum/(1+numRestricoesFracas);
	}
	
	
	float getPrecoFinal()
	{
		
		float sum = (float) 0;
		for(int i = 0; i < Main.numUtilizacoes; i++)
		{
			if (listaEstado[i] != -1)
			{
				sum += Main.listaLotes.get(listaEstado[i]).getPreco();
			}
		}
		return sum;
	}
	
	
	
	ArrayList<Lote> getLotesLivres()
	{
		ArrayList<Lote> temporario = new ArrayList<Lote>();
		boolean utilizado;
		
		for(int i=0; i < Main.listaLotes.size(); i++)
		{
			utilizado = false;
			for(int j = 0; j < listaEstado.length; j++)
			{
				if (listaEstado[j] == Main.listaLotes.get(i).getId())
				{
					utilizado = true;
					j = listaEstado.length;
				}
			}
			if (utilizado == false) temporario.add(Main.listaLotes.get(i));	
		}
		
		return temporario;
	}
	
	
	
	float getCustoHeuristico()
	{
		float sum = (float) 0;
		int numEmFalta = 0 ;
		numEmFalta = Main.numUtilizacoes - this.getAtribuidos();
		ArrayList<Lote> temporario = new ArrayList<Lote>();
		
		
		// Se faltarem alocar duas utilizacoes, vai somar o valor dos dois terrenos mais baratos que ainda nao tenham sido alocados.
		if(numEmFalta == 0) return 0;
		else if (listaEstado.length > Main.listaLotes.size()) return -1 ;
		else
		{
			
			temporario = this.getLotesLivres();
			
			Collections.sort(temporario);
			
			for(int i = 0; i < numEmFalta ; i++ )
			{
				sum+= temporario.get(i).getPreco();
			}
	
			//return sum;
			//possivel novo retorno
			return sum/(numRestricoesFracas+1);
		}
	}
	
	public boolean estadoEquivalente(Estado entrada)
	{
		for(int i = 0 ; i < Main.numUtilizacoes; i++)
		{
			if (this.listaEstado[i] != entrada.listaEstado[i]) return false;
		}
		return true;
	}
	
	@Override
	public int compareTo(Estado entrada) {
		return  Float.compare(this.getCustoDistancia()+this.getCustoHeuristico(),  entrada.getCustoDistancia()+entrada.getCustoHeuristico());
	}
	
	
	public int getProximoSlot()
	{
		if (this.eEstadoFinal() == true) return -1;
		else
		{
			for (int i = 0; i< Main.ordemPreenchimento.size(); i++)
			{
				if (listaEstado[Main.ordemPreenchimento.get(i)] == -1) return Main.ordemPreenchimento.get(i);
			}
		}
		return -1;
	}

	ArrayList<Estado> getFilhos(int indexUtilizacao)
	{
		ArrayList<Lote> temporario = new ArrayList<Lote>();
		ArrayList<Estado> resultado = new ArrayList<Estado>();
		boolean fail;
		int numFracos = 0;
		if (listaEstado[indexUtilizacao] != -1) return null;
		else
		{
			Utilizacao utilizacaoFilho = new Utilizacao();
			utilizacaoFilho = Main.listaRestricoes.get(indexUtilizacao);
			//System.out.println("Utilizacao Filho");
			temporario = this.getLotesLivres();
			
			for(int i = 0 ; i < temporario.size(); i++)
			{
				fail = false;
				numFracos = 0;
				// VERIFICAR TODAS AS RESTRICOES DA UTILIZACAO !
				if ((utilizacaoFilho.hasRestricaoLago() == true ) && (utilizacaoFilho.getRestricaoLago_Forte() == true))		// NECESSARIO TER LAGO
				{
					if (temporario.get(i).hasLago() == false) fail = true;
				}
				else if ((utilizacaoFilho.hasRestricaoLago() == true ) && (utilizacaoFilho.getRestricaoLago_Forte() == false))
				{
					if (temporario.get(i).hasLago() == true) numFracos++;
					
				}
				
				
				if ((utilizacaoFilho.getRestricaoEstrada() == true ) && (utilizacaoFilho.getRestricaoEstrada_Forte() == true) ) 	//NECESSARIO NAO TER ESTRADA
				{
					if (temporario.get(i).hasEstrada() == false) fail = true;
				}
				else if ((utilizacaoFilho.getRestricaoEstrada() == true ) && (utilizacaoFilho.getRestricaoEstrada_Forte() == false) ) 
				{
					if (temporario.get(i).hasEstrada() == true) numFracos++;
					
				}
				 
				
				if ((utilizacaoFilho.hasRestricaoSolo() == true) &&  (utilizacaoFilho.getRestricaoSolo_Forte() == true))
				{
					if (temporario.get(i).getTipoSolo() == 2) fail = true;
				}
				else if ((utilizacaoFilho.hasRestricaoSolo() == true) &&  (utilizacaoFilho.getRestricaoSolo_Forte() == false))
				{
					if (temporario.get(i).getTipoSolo() == 1) numFracos++;
					
				}
				
				
				
				if ( (utilizacaoFilho.hasRestricaoInclinacao() == true) && (utilizacaoFilho.getRestricaoInclinacao_Forte() == true) )
				{
					if (utilizacaoFilho.getInclinacao() != (temporario.get(i).getInclinacao())) fail = true;
				}
				else if ( (utilizacaoFilho.hasRestricaoInclinacao() == true) && (utilizacaoFilho.getRestricaoInclinacao_Forte() == false) )
				{
					if (utilizacaoFilho.getInclinacao() == (temporario.get(i).getInclinacao())) numFracos++;
					
				}
				
				
				if ((utilizacaoFilho.hasRestricaoDimensao() == true) && (utilizacaoFilho.getRestricaoDimensao_Forte() == true))
				{
					if (utilizacaoFilho.getDimensao() != temporario.get(i).getDimensao()) fail = true;
				}
				else if ((utilizacaoFilho.hasRestricaoDimensao() == true) && (utilizacaoFilho.getRestricaoDimensao_Forte() == false))
				{
					if (utilizacaoFilho.getDimensao() == temporario.get(i).getDimensao()) numFracos++;
					
				}
				
				
				if (fail == false)
				{
					Estado nEstado = new Estado();
					System.arraycopy(this.listaEstado, 0, nEstado.listaEstado, 0, this.listaEstado.length);
					nEstado.listaEstado[indexUtilizacao] = temporario.get(i).getId();
					nEstado.numRestricoesFracas = this.numRestricoesFracas + numFracos;
					resultado.add(nEstado);
				}
			}
			
		}
		return resultado;
	}

}

