import java.util.ArrayList;
import java.util.Scanner;

// Deixe um tempo para pensar nos atributos das classes
public class Main {
    int escolhaPagina;
    static ArrayList<Aluno> listaAlunos = new ArrayList<>();
    static ArrayList<AlunoEspecial> listaAlunosEspeciais = new ArrayList<>();

    public static void main(String[] args) {
        
        Scanner sc1 = new Scanner(System.in);

        System.out.println();
        System.out.println("Bem vindo(a) ao sistema acadêmico da FCTE");
        System.out.println();

        paginaInicial(sc1);
    }

    public static void paginaInicial(Scanner sc){
        int escolhaPagina = 0;

        System.out.println("---------------------\n");
        System.out.println("### Escolha a página que você quer entrar: ");
        System.out.println("Opção 1 - Modo aluno");
        System.out.println("Opção 2 - Modo disciplina/turma");
        System.out.println("Opção 3 - Modo avaliação/frequência");
        System.out.println("Opção 4 - Fechar programa\n");


        System.out.print("Digite aqui sua opção: \n");
        escolhaPagina = Integer.parseInt(sc.nextLine());

        switch (escolhaPagina) {
            case 1:
                modoAluno(sc);
                break;
            case 2:
                modoDisciplina(sc, escolhaPagina);
            case 4:
                break;
        
            default:
                System.out.println("### Desculpe, não existe essa opção, tente novamente. \n");
                paginaInicial(sc);
                break;
        }

    }

    public static void modoDisciplina(Scanner sc, int escolhaPagina){

        escolhaPagina = 0;
        
        System.out.println("---------------------\n");
        System.out.println("Bem vindo(a) ao modo Disciplina e Turma\n");

        System.out.println("###Escolha que você quer fazer:");
        System.out.println("Opção 1 - Cadastrar disciplinas");
        System.out.println("Opção 2 - Criar turmas");
        System.out.println("Opção 3 - Exibir todas as turmas disponíveis\n");

        System.out.print("Digite aqui sua opção: \n");

        escolhaPagina = Integer.parseInt(sc.nextLine());

    }

    public static void cadastrarDisciplinas(Scanner sc){
        int escolhaPagina;
        
        System.out.println("---------------------\n");
        System.out.println("Bem vindo(a) ao modo Disciplina e Turma\n");

        System.out.println("###Escolha que você quer fazer:");
        System.out.println("Opção 1 - Cadastrar disciplinas");
        System.out.println("Opção 2 - Criar turmas");
        System.out.println("Opção 3 - Exibir todas as turmas disponíveis\n");

        System.out.print("Digite aqui sua opção: \n");

        escolhaPagina = Integer.parseInt(sc.nextLine());
    }

    public static void modoAluno(Scanner sc){

        int escolhaPagina;

        System.out.println("---------------------\n");
        System.out.println("Bem vindo(a) ao modo aluno\n");

        System.out.println("###Escolha que você quer fazer:");
        System.out.println("Opção 1 - Cadastrar aluno");
        System.out.println("Opção 2 - Trancar disciplinas");
        System.out.println("Opção 3 - Exibir todos os alunos\n");

        System.out.print("Digite aqui sua opção: \n");

        escolhaPagina = Integer.parseInt(sc.nextLine());

        switch (escolhaPagina) {
            case 1:
                cadastrarAluno(sc);
                break;
            case 3:
                mostrarAlunos();
                break;
            default:
                break;
        }
    }

    public static void mostrarAlunos(){
        for(Aluno aluno : listaAlunos){
            System.out.println("\n-------------\n");
            System.out.println("\nNome: " + aluno.getNome());
            System.out.println("\nEmail: " + aluno.getEmail());
            System.out.println("\nMatrícula: " + aluno.getMatricula());
            System.out.println("\nCurso: " + aluno.getCurso());
        }

        
    }

    public static void cadastrarAluno(Scanner sc){
        System.out.println("---------------------\n");
        System.out.println("Digite o nome do novo aluno: ");
        String nomeAluno = sc.nextLine();
        System.out.println("Digite a matrícula do novo aluno: ");
        String matricula = sc.nextLine();
        System.out.println("Digite o curso do novo aluno: ");
        String curso = sc.nextLine();
        System.out.println("Digite o email do novo aluno: ");
        String email = sc.nextLine();
        System.out.println("Digite 1 se o aluno for especial e 0 se for normal");
        int especial = sc.nextInt();
        String Skipline = sc.nextLine(); 


        if(especial == 1){
            
            AlunoEspecial novoAlunoEspecial = new AlunoEspecial(nomeAluno, matricula, curso, email);
            
            if (novoAlunoEspecial.verificarCadastro()) {
                listaAlunosEspeciais.add(novoAlunoEspecial);
                System.out.println("->>>>> Aluno Especial cadastrado com sucesso!");
                } 
                else {
                    
                System.out.println("-XXXXX Todos os campos devem ser preenchidos corretamente.");
                } 
        }
        else{
            Aluno novoAluno = new Aluno(nomeAluno, matricula, curso, email);

            if (novoAluno.verificarCadastro()) {
                listaAlunos.add(novoAluno);
                System.out.println("\n->>>>> Aluno cadastrado com sucesso!");
                } else {
                    
                System.out.println("\n-XXXXX Todos os campos devem ser preenchidos corretamente.\n");
                } 
        }

        paginaInicial(sc);
    }

}  

