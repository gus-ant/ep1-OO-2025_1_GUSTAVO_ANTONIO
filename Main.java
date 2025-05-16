import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Deixe um tempo para pensar nos atributos das classes
public class Main {
    int escolhaPagina;
    public static List<Disciplina> disciplinas = new ArrayList<>();
    static ArrayList<Aluno> listaAlunos = new ArrayList<>();
    static ArrayList<AlunoEspecial> listaAlunosEspeciais = new ArrayList<>();
    static List<Turma> turmas = new ArrayList<>();

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
                modoAvaliacaoFrequencia(sc);
                break;
        
            default:
                System.out.println("### Desculpe, não existe essa opção, tente novamente. \n");
                paginaInicial(sc);
                break;
        }

    }

    public static void modoAvaliacaoFrequencia(Scanner sc) {

        int escolhaPagina;
    
        System.out.println("---------------------\n");
        System.out.println("Bem-vindo(a) ao modo Avaliação/Frequência\n");
    
        System.out.println("### Escolha o que você quer fazer:");
        System.out.println("Opção 1 - Lançar notas dos alunos");
        System.out.println("Opção 2 - Exibir boletim de um aluno");
        System.out.println("Opção 3 - Gerar relatórios");
        System.out.println("Opção 4 - Voltar para a página inicial\n");
    
        System.out.print("Digite aqui sua opção: ");
        escolhaPagina = Integer.parseInt(sc.nextLine());
    
        switch (escolhaPagina) {
            case 1:

                lancarNotasEFrequencia(null, sc);
                break;
            case 2:
                //lancarPresenca(sc);
                break;
            case 3:
                //exibirBoletimAluno(sc);
                break;
            case 4:
                //gerarRelatorios(sc);
                break;
            case 5:
                paginaInicial(sc);
                return;
            default:
                System.out.println("### Desculpe, não existe essa opção, tente novamente. \n");
                break;
        }
    
        modoAvaliacaoFrequencia(sc); 
    }

    public static void lancarNotasEFrequencia(Turma turma, Scanner sc){

        for (Aluno aluno : turma.getAlunosMatriculados()) {
            System.out.println("Aluno: " + aluno.getNome());
    
            Avaliacao avaliacao = new Avaliacao();
    
            System.out.print("Tipo de média (0 = Normal, 2 = ponderada): ");
            avaliacao.setTipoMedia(Integer.parseInt(sc.nextLine()));
    
            System.out.print("Nota P1: ");
            avaliacao.setP1(Double.parseDouble(sc.nextLine()));
    
            System.out.print("Nota P2: ");
            avaliacao.setP2(Double.parseDouble(sc.nextLine()));
    
            System.out.print("Nota P3: ");
            avaliacao.setP3(Double.parseDouble(sc.nextLine()));
    
            System.out.print("Nota das listas: ");
            avaliacao.setLista(Double.parseDouble(sc.nextLine()));
    
            System.out.print("Nota do seminário: ");
            avaliacao.setSeminario(Double.parseDouble(sc.nextLine()));
    
            aluno.setAvaliacao(avaliacao);
    
            System.out.print("Frequência do aluno (0.0 a 1.0): ");
            aluno.setFrequencia(Double.parseDouble(sc.nextLine()));
    
            System.out.println("Média final: " + avaliacao.CalculoMedia(avaliacao.getTipoMedia()));
            if (avaliacao.aprovado(aluno.getFrequencia())) {
                System.out.println("Aluno aprovado ✅");
            } else {
                System.out.println("Aluno reprovado ❌");
            }
            System.out.println("--------------------------");}
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

        switch (escolhaPagina) {
            case 1:
                cadastrarDisciplina(sc);
                break;
        
            default:
                break;
        }

    }

    public static void cadastrarDisciplina(Scanner sc) {
        System.out.println("\n### Cadastro de Disciplina ###");
    
        System.out.print("Nome da disciplina: ");
        String nome = sc.nextLine();
    
        System.out.print("Código da disciplina (ex: MAT101): ");
        String codigo = sc.nextLine();
    
        System.out.print("Carga horária (em horas): ");
        int cargaHoraria = Integer.parseInt(sc.nextLine());
    
        System.out.print("Quantos pré-requisitos essa disciplina possui? ");
        int qtdPre = Integer.parseInt(sc.nextLine());
    
        List<String> preRequisitos = new ArrayList<>();
    
        for (int i = 0; i < qtdPre; i++) {
            System.out.print("Código do pré-requisito " + (i + 1) + ": ");
            String pre = sc.nextLine();
            preRequisitos.add(pre);}

        List<Turma> turmasVazias = new ArrayList<>();

        Disciplina nova = new Disciplina(nome, codigo, cargaHoraria, preRequisitos, turmasVazias);
        disciplinas.add(nova);
    
        System.out.println("\n✅ Disciplina cadastrada com sucesso!");

        }


    public static void modoAluno(Scanner sc){

        int escolhaPagina;

        System.out.println("---------------------\n");
        System.out.println("Bem vindo(a) ao modo aluno\n");

        System.out.println("###Escolha que você quer fazer:");
        System.out.println("Opção 1 - Cadastrar aluno");
        System.out.println("Opção 2 - Trancar disciplinas");
        System.out.println("Opção 3 - Exibir todos os alunos");
        System.out.println("Opção 4 - Voltar para a página Inicial\n");


        System.out.print("Digite aqui sua opção: \n");

        escolhaPagina = Integer.parseInt(sc.nextLine());

        switch (escolhaPagina) {
            case 1:
                cadastrarAluno(sc);
                break;
            case 3:
                mostrarAlunos();
                break;
            case 4:
                paginaInicial(sc);
            default:
                modoAluno(sc);
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

