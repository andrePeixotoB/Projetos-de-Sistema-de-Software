import model.Cliente;
import model.Item;
import model.Pedido;
import model.CupomDesconto;
import service.CalculadoraDeDescontoService;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner entradaUsuario = new Scanner(System.in);
        
        // Inicialização de entidades
        Cliente clienteAtual = new Cliente("Ana Claudia", "Ouro", 1, "Rua das Flores", "Centro", "Alegre");
        Item itemPrincipal = new Item("Livro UML", 1, 300.0, "Educação");
        Pedido pedidoAtual = new Pedido(LocalDate.now(), clienteAtual);
        
        // Adiciona o item ao pedido
        pedidoAtual.adicionarItem(itemPrincipal);
        
        // Exibe informações iniciais do pedido
        System.out.println("\n--- DETALHES DO PEDIDO ---");        
        System.out.println("Dados do Cliente: " + clienteAtual.toString());
        System.out.println("Custo de Entrega Base: " + pedidoAtual.getTaxaEntrega());
        System.out.println("Valor Total Inicial do Pedido: " + pedidoAtual.getValorPedido());
        
        // Pergunta sobre aplicação de desconto na taxa de entrega
        System.out.print("\nDeseja aplicar algum desconto na taxa de entrega? (s/n): ");
        String respostaDescontoEntrega = entradaUsuario.nextLine();
        
        if(respostaDescontoEntrega.equalsIgnoreCase("s")){
            CalculadoraDeDescontoService servicoDescontoEntrega = new CalculadoraDeDescontoService();
            servicoDescontoEntrega.calcularDesconto(pedidoAtual);
            System.out.println("Custo de Entrega com Descontos Aplicados: " + pedidoAtual.getTaxaEntrega()); 
        } else {
            System.out.println("Custo de Entrega Mantido: " + pedidoAtual.getTaxaEntrega());
        }
        
        // Loop para aplicação de cupom de desconto no pedido
        boolean continuarProcesso = true;
        while(continuarProcesso){
            System.out.print("\nInsira o código do cupom de desconto (ou \'sair\' para finalizar): ");
            String codigoCupom = entradaUsuario.nextLine();
            
            if (codigoCupom.equalsIgnoreCase("sair")) {
                continuarProcesso = false;
                break;
            }

            double percentual = 0.0;
            try {
                System.out.print("Insira o percentual de desconto para este cupom (ex: 10 para 10%): ");
                percentual = entradaUsuario.nextDouble();
                entradaUsuario.nextLine(); // Consumir a nova linha
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida para o percentual. Por favor, insira um número.");
                entradaUsuario.nextLine(); // Limpar o buffer do scanner
                continue;
            }

            if (percentual < 0 || percentual > 100) {
                System.out.println("Percentual de desconto inválido. Deve ser entre 0 e 100.");
                continue;
            }

            CupomDesconto novoCupom = new CupomDesconto(codigoCupom, percentual);
            pedidoAtual.aplicarCupomDesconto(novoCupom);
            
            System.out.println("\n--- INFORMAÇÕES DO PEDIDO COM DESCONTO DO CUPOM ---");
            System.out.println("Status do Pedido: " + pedidoAtual.toString());
            
            System.out.println("\n--- OPÇÕES ---");
            System.out.println("1 - Concluir Compra\n2 - Tentar Outro Cupom\n3 - Cancelar Operação");
            System.out.print("Escolha uma opção: ");
            String escolhaUsuario = entradaUsuario.nextLine();
            
            if(escolhaUsuario.equals("1")){
                System.out.println("Compra finalizada com sucesso! Agradecemos a preferência.");
                continuarProcesso = false;
            } else if (escolhaUsuario.equals("3")){
                System.out.println("Operação cancelada. Volte sempre!");
                continuarProcesso = false;
            }
        }
        
        entradaUsuario.close();
    }
}


