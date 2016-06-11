import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

	public static ArrayList<Lote> listaLotes = new ArrayList<Lote>();
	public static ArrayList<Utilizacao> listaRestricoes = new ArrayList<Utilizacao>();
	static BufferedReader inputScanner = new BufferedReader(new InputStreamReader(System.in));
	public static int numUtilizacoes = 0;
	public static int restricoesId = 0;
	public static ArrayList<Integer> ordemPreenchimento = new ArrayList<Integer>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int op = 0;
		
		
		
		/*---------------------------------------------------- */
		while(op != 5)
		{
			numUtilizacoes = listaRestricoes.size();
			System.out.println("Alocacao de Terrenos (L: " + listaLotes.size() + " R: " + numUtilizacoes +")");
			System.out.println("1 - Carregar Terreno");
			System.out.println("2 - Carregar Restricoes");
			System.out.println("3 - Nova Restricoes");
			System.out.println("4 - Atribuir Terrenos -> Utilizacoes");
			System.out.println("5 - Sair");
			
			try {
				op = Integer.parseInt(inputScanner.readLine());
			} catch (NumberFormatException | IOException  e) {
				System.out.println("Input Invalido 1");
				//e1.printStackTrace();
			}
			
			if (op == 1){ loadLotes(); }
			if (op == 2){ loadRestricoes(); } 
			if (op == 3){ readRestricoes(); }
			if (op == 4){ if (numUtilizacoes > listaLotes.size() ){System.out.println("Alocação Impossivel!"); } else {getOrdemPreenchimento(); astar(); } }
			if (op > 5) System.out.println("Input Invalido");
		}
		

	}
	
	
	public static void getOrdemPreenchimento()
	{
		ArrayList<Utilizacao> tempList = new ArrayList<Utilizacao>(listaRestricoes);
		Collections.sort(tempList);
		Collections.reverse(tempList);
		for(int i = 0; i < tempList.size(); i++)
		{
			ordemPreenchimento.add(tempList.get(i).getId());
		}
		
	}
	
	public static void astar()
	{
		Estado solucao = new Estado();
		float valorSolucao = Integer.MAX_VALUE;
		ArrayList<Estado> LABERTA = new ArrayList<Estado>();
		ArrayList<Estado> LFECHADA = new ArrayList<Estado>();
		Estado raiz = new Estado();
		Boolean temSolucao = false;
		LABERTA.add(raiz);
		
		
		while(temSolucao == false)
		{
			if (LABERTA.size() == 0)
			{
				temSolucao = true;
			}
			else
			{
				// RETIRAR O NO MAIS PROMISSOR DE LABERTA E COLOCAR EM LFECHADA
				Collections.sort(LABERTA);
				LFECHADA.add(LABERTA.get(0));
				LABERTA.remove(0);
				Estado estadoAtual = LFECHADA.get(LFECHADA.size()-1);
				
				if (estadoAtual.getCustoDistancia() < valorSolucao)
				{
					
					//VERIFICAR SE ESSE ESTADO É SOLUCAO
					if (estadoAtual.eEstadoFinal() == true)
					{
						//System.out.println("Estado Final Encontrado!");
						//System.out.println("Lista Estado: " + Arrays.toString(estadoAtual.listaEstado));
						
						if (estadoAtual.getCustoDistancia() < valorSolucao)
						{
							solucao = estadoAtual;
							valorSolucao = estadoAtual.getCustoDistancia();
						}
						
					}
					else
					{
						// SE ESTADO NAO FOR SOLUCAO GERAR SUCESSORES
						ArrayList<Estado> sucessores = new ArrayList<Estado>();
						sucessores = estadoAtual.getFilhos(estadoAtual.getProximoSlot());
						for(int i = 0 ; i < sucessores.size(); i++)
						{
							// NESTE CASO DE ARVORE, NOVOS NOS NUNCA ESTAO NA LABERTA, ADICIONA_SE SEMPRE TODOS OS SUCESSORES A LABERTA
							
							LABERTA.add(sucessores.get(i));
							
						}
					}
					
				}
				
			}
			
			
			
		}
		
		
		System.out.println("Resultado da Alocação de Terrenos: ");
		
		if (solucao.eEstadoFinal())
		{	
			for(int k=0; k < solucao.listaEstado.length; k++)
			{
				System.out.println(">" + listaRestricoes.get(k).getNome() + " -> Lote nº: " + listaLotes.get(solucao.listaEstado[k]).getId() + " > Custo: "+ listaLotes.get(solucao.listaEstado[k]).getPreco());
			}
			//System.out.println("Melhor Solucao Encontrada: " + Arrays.toString(solucao.listaEstado) +  "\nCusto total: " + solucao.getPrecoFinal() + " Numero de Restricoes Fracas Cumpridas: " + solucao.numRestricoesFracas );
			System.out.println("\nCusto total: " + solucao.getPrecoFinal() + " Numero de Restricoes Fracas Cumpridas: " + solucao.numRestricoesFracas + "\n\n\n" );
		}
		else
		{
			System.out.println("Alocação Impossivel! - Impossivel Satisfazer Requesitos");
		}
		return;
	}
	
	public static void readRestricoes()
    {
            String temp = null;
            Utilizacao novaUtilizacao = new Utilizacao();
           
            try {
                    System.out.println("Insira o nome da utilizacao (Ex: Casas, Apartamentos, Recreio, Etc.): ");
                    temp = inputScanner.readLine();
                    novaUtilizacao.setNome(temp);
                   
                    System.out.println("Que dimensÃµes sao necessarias? [I]ndiferente | [P]equeno | [M]edio | [G]rande");
                            temp = inputScanner.readLine();
                            if (temp.equals("P")) { novaUtilizacao.setDimensao(1); novaUtilizacao.setRestricaoDimensao(true); }
                            else if (temp.equals("M")) { novaUtilizacao.setDimensao(2); novaUtilizacao.setRestricaoDimensao(true); }
                            else if (temp.equals("G")) { novaUtilizacao.setDimensao(3); novaUtilizacao.setRestricaoDimensao(true); }
                            else novaUtilizacao.setRestricaoDimensao(false);
                           
                            if(temp.equals("P") || temp.equals("M") || temp.equals("G"))
                            {
                                    System.out.println("A restrição de Dimensão é Forte ou Fraca?");
                                    temp = inputScanner.readLine();
                                    if (temp.equalsIgnoreCase("Forte")) { novaUtilizacao.setRestricaoDimensao_Forte(true); }
                            }
   
                           
                           
                    System.out.println("Que tipo de inclinacao necessita? [I]ndiferente | [R]eduzida | [N]ormal | [E]levada");
                    temp = inputScanner.readLine();
                            if (temp.equals("R")) { novaUtilizacao.setInclinacao(1); novaUtilizacao.setRestricaoInclinacao(true); }
                            else if (temp.equals("N")) { novaUtilizacao.setInclinacao(2); novaUtilizacao.setRestricaoInclinacao(true); }
                            else if (temp.equals("E")) { novaUtilizacao.setInclinacao(3); novaUtilizacao.setRestricaoInclinacao(true); }
                            else novaUtilizacao.setRestricaoInclinacao(false);
                           
                            if(temp.equals("R") || temp.equals("N") || temp.equals("E"))
                            {
                                    System.out.println("A restrição de Inclinação é Forte ou Fraca?");
                                    temp = inputScanner.readLine();
                                    if (temp.equalsIgnoreCase("Forte")) { novaUtilizacao.setRestricaoInclinacao_Forte(true); }
                            }
                           
                    System.out.println("E' necessario solo estavel? [S]im | [I]ndiferente");
                            temp = inputScanner.readLine();
                            if (temp.equals("S")) {novaUtilizacao.setRestricaoSolo(true); }
                            else novaUtilizacao.setRestricaoSolo(false);
                           
                            if(temp.equals("S"))
                            {
                                    System.out.println("A restrição de Solo é Forte ou Fraca?");
                                    temp = inputScanner.readLine();
                                    if (temp.equalsIgnoreCase("Forte")) { novaUtilizacao.setRestricaoSolo_Forte(true); }
                            }
                           
                    System.out.println("Precisa de ter um lago? [S]im | [I]ndiferente");
                            temp = inputScanner.readLine();
                            if (temp.equals("S")) { novaUtilizacao.setRestricaoLago(true); }
                            else novaUtilizacao.setRestricaoLago(false);
                           
                            if(temp.equals("S"))
                            {
                                    System.out.println("A restrição de Lago é Forte ou Fraca?");
                                    temp = inputScanner.readLine();
                                    if (temp.equalsIgnoreCase("Forte")) { novaUtilizacao.setRestricaoLago_Forte(true); }
                            }
                           
                    System.out.println("Acesso a autoestrada? [S]im | [I]ndiferente ");
                            temp = inputScanner.readLine();
                            if (temp.equals("S")) { novaUtilizacao.setRestricaoEstrada(true); }
                            else novaUtilizacao.setRestricaoEstrada(false);
                           
                            if(temp.equals("S"))
                            {
                                    System.out.println("A restrição de Estrada é Forte ou Fraca?");
                                    temp = inputScanner.readLine();
                                    if (temp.equalsIgnoreCase("Forte")) { novaUtilizacao.setRestricaoEstrada_Forte(true); }
                            }
                           
                            novaUtilizacao.setId(restricoesId);
                            restricoesId++;
                            listaRestricoes.add(novaUtilizacao);
            } catch (IOException e) {
                    System.out.println("Dado(s) Invalido(s)");
                    e.printStackTrace();
            }    
    }
	
	public static void loadRestricoes()
    {
			System.out.println("Insira o nome do ficheiro de Restrições: ");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            try {
            		String filename = br1.readLine();
                    //BufferedReader br = new BufferedReader(new FileReader("restricoes.txt"));
            		BufferedReader br = new BufferedReader(new FileReader(filename));
                    String line;
                    line = br.readLine();
                    while ((line = br.readLine()) != null && line.length()>1) {
                            Utilizacao novaUtilizacao = new Utilizacao();
                            String delims = " ";
                            String[] tokens = line.split(delims);
                           
                            // <Solo> <RSolo_Forte> <Estrada> <REstrada_Forte> <Lago> <RLago_Forte> <Tem_Inclinação> <Inclinação> <RInclinaçao_Forte> <Tem_Dimensão> <Dimensão> <RDimensao_Forte>
                           
                            // NOVO -> <Solo> <RSolo_Forte> <Estrada> <REstrada_Forte> <Lago> <RLago_Forte> <Inclinaçao> <RInclinaçao_Forte> <Dimensao> <RDimensao_Forte>
                           
                            novaUtilizacao.setNome(tokens[0]);
                            if (Integer.parseInt(tokens[1]) == 1) // restricaoSolo
                            {
                                    novaUtilizacao.setRestricaoSolo(true);
                                   
                                    if(Integer.parseInt(tokens[2]) == 1)
                                    {
                                            novaUtilizacao.setRestricaoSolo_Forte(true);
                                    }
                            }
                           
                            if (Integer.parseInt(tokens[3]) == 1) // restricaoEstrada
                            {
                                    novaUtilizacao.setRestricaoEstrada(true);
                                   
                                    if(Integer.parseInt(tokens[4]) == 1)
                                    {
                                            novaUtilizacao.setRestricaoEstrada_Forte(true);
                                    }
                            }
                           
                           
                            if (Integer.parseInt(tokens[5]) == 1) // restricaolago
                            {
                                    novaUtilizacao.setRestricaoLago(true);
                                    if(Integer.parseInt(tokens[6]) == 1)
                                    {
                                            novaUtilizacao.setRestricaoLago_Forte(true);
                                    }
                            }
                           
                            if (Integer.parseInt(tokens[7]) > 0) // restricaoInclinacao
                            {
                                    novaUtilizacao.setRestricaoInclinacao(true);
                                    novaUtilizacao.setInclinacao(Integer.parseInt(tokens[7]));
                                    if(Integer.parseInt(tokens[8]) == 1)
                                    {
                                            novaUtilizacao.setRestricaoInclinacao_Forte(true);
                                    }
                            }
                           
                           
                            if (Integer.parseInt(tokens[9]) > 0) // restricaoDimensao
                            {
                                    novaUtilizacao.setRestricaoDimensao(true);
                                    novaUtilizacao.setDimensao(Integer.parseInt(tokens[9]));
                                    if(Integer.parseInt(tokens[10]) == 1)
                                    {
                                            novaUtilizacao.setRestricaoDimensao_Forte(true);
                                    }
                            }
                           
                            novaUtilizacao.setId(restricoesId);
                            restricoesId++;
                            listaRestricoes.add(novaUtilizacao);
        
                           
                    }
                    br.close();
            } catch (FileNotFoundException e) {
                    System.out.println("Erro na abertura do ficheiro de restricoes");
                    e.printStackTrace();
            } catch (IOException e) {
                    System.out.println("Erro de leitura");
                    e.printStackTrace();
            }
    }
	
	public static void loadLotes()
	{
		BufferedReader br;
		System.out.println("Insira o nome do ficheiro com os lotes de terreno:");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
		int id = 0;
		// -------------------
		
		
		
		try {
			String filename = br1.readLine();
			br = new BufferedReader(new FileReader(filename));
			String line;
			line = br.readLine();
			while ((line = br.readLine()) != null && line.length()>1) {
			   Lote novoLote = new Lote();
			   String delims = " ";
			   String[] tokens = line.split(delims);
			   
			   novoLote.setId(id);
			   novoLote.setTipoSolo(Integer.parseInt(tokens[0]));
			   novoLote.setInclinacao(Integer.parseInt(tokens[1]));
			   if (tokens[2].equals("1")) novoLote.setEstrada(true); else novoLote.setEstrada(false) ;
			   if (tokens[3].equals("1")) novoLote.setLago(true); else novoLote.setLago(false) ;
			   novoLote.setDimensao(Integer.parseInt(tokens[4]));
			   novoLote.setPreco(Float.parseFloat(tokens[5]));
			   
			   listaLotes.add(novoLote);
			   
			   id++;
			   // -------------------
			}
			br.close();
		
		} catch (NumberFormatException | IOException e) {
			System.out.println("Erro na abertura/leitura do ficheiro de lotes");
			e.printStackTrace();
		}
		
	
	}
}
