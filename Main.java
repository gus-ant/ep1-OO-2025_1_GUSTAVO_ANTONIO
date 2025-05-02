import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

// Deixe um tempo para pensar nos atributos das classes
public class Main {

    public static void main(String[] args) {
        
        Scanner sc1 = new Scanner(System.in);
        List<Aluno> listaPessoas = new ArrayList<Aluno>();
        boolean continuar = true;

        System.out.println();
        System.out.println("Bem vindo(a) ao sistema acadêmico da FCTE");
        System.out.println();

        paginaInicial(sc1);
    }

    public static void paginaInicial(Scanner sc){
        
        int escolhaPagina;

        System.out.println("---------------------\n");
        System.out.println("### Escolha a página que você quer entrar: ");
        System.out.println("Opção 1 - Modo aluno");
        System.out.println("Opção 2 - Modo disciplina/turma");
        System.out.println("Opção 3 - Modo avaliação/frequência\n");


        System.out.print("Digite aqui sua opção: \n");
        escolhaPagina = Integer.parseInt(sc.nextLine());

        switch (escolhaPagina) {
            case 1:
                modoAluno(sc);
                break;
        
            default:
                System.out.println("### Desculpe, não existe essa opção, tente novamente. \n");
                paginaInicial(sc);
                break;
        }

    }

    public static void modoAluno(Scanner sc){

        int escolhaPagina;

        System.out.println("---------------------\n");
        System.out.println("Bem vindo(a) ao modo aluno\n");

        System.out.println("###Escolha que você quer fazer:");
        System.out.println("Opção 1 - Cadastrar alunos");
        System.out.println("Opção 2 - Matricular alunos");
        System.out.println("Opção 3 - Trancar disciplinas");
        System.out.println("Opção 4 - Exibir todos os alunos\n");

        System.out.print("Digite aqui sua opção: \n");

        escolhaPagina = Integer.parseInt(sc.nextLine());
    }
}

