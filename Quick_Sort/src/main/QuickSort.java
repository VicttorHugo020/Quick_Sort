package main;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Pedido {
    int id;
    Date dataPedido;
    String nomeCliente;
    double valorTotal;
    String status;
    int quantidadeItens;
    String metodoPagamento;
    Date dataEntregaEstimada;
    String cidade;
    String categoria;

    Pedido(String linha) throws ParseException {
        String[] partes = linha.split(" \\| ");
        this.id = Integer.parseInt(partes[0]);
        this.dataPedido = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(partes[1]);
        this.nomeCliente = partes[2];
        this.valorTotal = Double.parseDouble(partes[3]);
        this.status = partes[4];
        this.quantidadeItens = Integer.parseInt(partes[5]);
        this.metodoPagamento = partes[6];
        this.dataEntregaEstimada = new SimpleDateFormat("yyyy-MM-dd").parse(partes[7]);
        this.cidade = partes[8];
        this.categoria = partes[9];
    }

    public String toString() {
        return id + " | " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataPedido) + " | " +
               nomeCliente + " | " + valorTotal + " | " + status + " | " + 
               quantidadeItens + " | " + metodoPagamento + " | " +
               new SimpleDateFormat("yyyy-MM-dd").format(dataEntregaEstimada) + " | " +
               cidade + " | " + categoria;
    }
}

public class QuickSort {

    public static void quickSort(ArrayList<Pedido> pedidos, int inicio, int fim, String criterio) {
        if (inicio < fim) {
            int pivoIndex = particionar(pedidos, inicio, fim, criterio);
            quickSort(pedidos, inicio, pivoIndex - 1, criterio);
            quickSort(pedidos, pivoIndex + 1, fim, criterio);
        }
    }

    private static int particionar(ArrayList<Pedido> pedidos, int inicio, int fim, String criterio) {
        Pedido pivo = pedidos.get(fim);
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (comparar(pedidos.get(j), pivo, criterio) <= 0) {
                i++;
                Pedido temp = pedidos.get(i);
                pedidos.set(i, pedidos.get(j));
                pedidos.set(j, temp);
            }
        }

        Pedido temp = pedidos.get(i + 1);
        pedidos.set(i + 1, pedidos.get(fim));
        pedidos.set(fim, temp);

        return i + 1;
    }

    private static int comparar(Pedido a, Pedido b, String criterio) {
        switch (criterio) {
            case "valorTotal":
                return Double.compare(b.valorTotal, a.valorTotal); 
            case "dataPedido":
                return a.dataPedido.compareTo(b.dataPedido); 
            case "dataEntregaEstimada":
                return a.dataEntregaEstimada.compareTo(b.dataEntregaEstimada); 
            case "status":
                return a.status.compareTo(b.status);
            case "cidade":
                return a.cidade.compareTo(b.cidade);
            case "metodoPagamento":
                return a.metodoPagamento.compareTo(b.metodoPagamento); 
            case "quantidadeItens":
                return Integer.compare(b.quantidadeItens, a.quantidadeItens); 
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\\\\\\\Users\\\\\\\\victt\\\\\\\\Downloads\\\\\\\\pedidos_quicksort.txt"));
            String linha;
            while ((linha = br.readLine()) != null) {
                pedidos.add(new Pedido(linha));
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        System.out.println("Escolha o critério de ordenação:");
        System.out.println("1. Ordenação por Valor Total");
        System.out.println("2. Ordenação por Data do Pedido");
        System.out.println("3. Ordenação por Data de Entrega Estimada");
        System.out.println("4. Ordenação por Status do Pedido");
        System.out.println("5. Ordenação por Cidade do Cliente");
        System.out.println("6. Ordenação por Método de Pagamento");
        System.out.println("7. Ordenação por Quantidade de Itens");
        int escolha = scanner.nextInt();

        String criterio = switch (escolha) {
            case 1 -> "valorTotal";
            case 2 -> "dataPedido";
            case 3 -> "dataEntregaEstimada";
            case 4 -> "status";
            case 5 -> "cidade";
            case 6 -> "metodoPagamento";
            case 7 -> "quantidadeItens";
            default -> throw new IllegalArgumentException("Opção inválida!");
        };

        quickSort(pedidos, 0, pedidos.size() - 1, criterio);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("pedidos_ordenados_quicksort.txt"));
            for (Pedido pedido : pedidos) {
                bw.write(pedido.toString());
                bw.newLine();
            }
            bw.close();
            System.out.println("Arquivo ordenado!");
        } catch (IOException e) {
            System.out.println("Erro!" + e.getMessage());
        }
    }
}
