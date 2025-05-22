import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Colocar emojis para facilitar o usuário de ver se os cadastros foram ou não bem sucedidos

// Deixe um tempo para pensar nos atributos das classes



public class Main {
    int escolhaPagina;
    public static List<Disciplina> disciplinas = carregarDisciplinas();
    static ArrayList<Aluno> listaAlunos = new ArrayList<>();
    static ArrayList<AlunoEspecial> listaAlunosEspeciais = new ArrayList<>();
    static List<Turma> turmas = carregarTurmas(disciplinas);

    public static void main(String[] args) {
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("\nBem vindo(a) ao sistema acadêmico da FCTE\n");

        carregarAlunos(turmas, listaAlunos);

        paginaInicial(sc1);
    }

    public static void paginaInicial(Scanner sc){
        

        System.out.println("---------------------\n");
        System.out.println("### Escolha a página que você quer entrar: \n");
        System.out.println("Opção 1 - Modo aluno");
        System.out.println("Opção 2 - Modo disciplina/turma");
        System.out.println("Opção 3 - Modo avaliação/frequência");
        System.out.println("Opção 4 - Fechar programa\n");
        System.out.print("Digite aqui sua opção: ");
        String es = sc.nextLine(); 

        if (es.isEmpty()) {
            System.out.println("❌ Entrada inválida. Por favor, digite um número.");
            paginaInicial(sc); 
            return;
        }

        try {
            int escolhaPagina = Integer.parseInt(es);

            switch (escolhaPagina) {
                case 1:
                    modoAluno(sc);
                    break;
                case 2:
                    modoDisciplina(sc, escolhaPagina);
                case 3:
                    modoAvaliacaoFrequencia(sc);
                    break;
                case 4: 
                    fecharPrograma();
                    break;
            
                default:
                    System.out.println("❌ Desculpe, não existe essa opção, tente novamente. \n");
                    paginaInicial(sc);
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida. Por favor, digite apenas números.");
            paginaInicial(sc); 
        }



    }
    
    public static void fecharPrograma(){

    }

    public static Aluno buscarAlunoPorMatricula(String matricula){

        for(Aluno aluno : listaAlunos){

            if(aluno.getMatricula().equals(matricula)){
                System.out.println(aluno.getMatricula());
                return aluno;
            }
            
        }

        for(AlunoEspecial alunoe : listaAlunosEspeciais){
            if(alunoe.getMatricula().equals(matricula)){
                return alunoe;
            }
        }
        return null;
        
    }


    public static List<Turma> carregarTurmas(List<Disciplina> listaDisciplinas) {
        List<Turma> turmasCarregadas = new ArrayList<>();
    
        File file = new File("turmas.txt");
        if (!file.exists()) return turmasCarregadas;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length < 8) {
                    System.out.println("Linha mal formatada: " + linha);
                    continue;
                }
    
                String codigoDisciplina = partes[0];
    
                Disciplina disciplinaEncontrada = null;
                for (Disciplina d : listaDisciplinas) {
                    if (d.getCodigo().equals(codigoDisciplina)) {
                        disciplinaEncontrada = d;
                        break;
                    }
                }
    
                if (disciplinaEncontrada == null) {
                    System.out.println("Disciplina não encontrada para a linha: " + linha);
                    continue;
                }
    
                Turma turma = Turma.fromString(linha, disciplinaEncontrada);
                turmasCarregadas.add(turma);
                disciplinaEncontrada.getTurmas().add(turma);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar Turmas: " + e.getMessage());
        }
    
        return turmasCarregadas;
    }
    
    

    public static void salvarDisciplinas(List<Disciplina> disciplinas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("disciplinas.txt"))) {
            for (Disciplina d : disciplinas) {
                bw.write(d.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }
    
    public static void salvarTurmas(List<Disciplina> disciplinas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("turmas.txt"))) {
            for (Disciplina d : disciplinas) {
                for (Turma t : d.getTurmas()) {
                    bw.write(t.toString(d));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar turmas: " + e.getMessage());
        }
    }
    


    public static List<Disciplina> carregarDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader("disciplinas.txt"))) {
            String linha;
    
            while ((linha = br.readLine()) != null) {
                Disciplina d = Disciplina.fromString(linha);
                disciplinas.add(d);
            }
    
        } catch (IOException e) {
            System.out.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
    
        
        try (BufferedReader br = new BufferedReader(new FileReader("turmas.txt"))) {
            String linha;
    
            while ((linha = br.readLine()) != null) {
                String codigoDisciplina = linha.split(";")[0];
    
                for (Disciplina d : disciplinas) {
                    if (d.getCodigo().equals(codigoDisciplina)) {
                        Turma t = Turma.fromString(linha, d);
                        d.getTurmas().add(t);
                        break;
                    }
                }
            }
    
        } catch (IOException e) {
            System.out.println("Erro ao carregar turmas: " + e.getMessage());
        }
    
        return disciplinas;
    }

    public static void salvarAluno(Aluno aluno) {
    String pasta = "banco_de_dados";
    new File(pasta).mkdirs();

    String caminho = pasta + "/" + aluno.getMatricula() + "_aluno.txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
        // Linha única representando os dados principais
        writer.write(
            aluno.getNome() + ";" +
            aluno.getMatricula() + ";" +
            aluno.getCurso() + ";" +
            aluno.getEmail() + ";" +
            aluno.getFrequencia() + ";" +
            String.join(",", aluno.getTurmasAprovadas())
        );
        writer.newLine();

        for (Turma turma : aluno.getTurmasMatriculadas()) {
            writer.write("TURMA:" + turma.getCodigoDaTurma());
            writer.newLine();
        }

        for (Map.Entry<Turma, Avaliacao> entry : aluno.getAvaliacoes().entrySet()) {
            writer.write("AVALIACAO:" + entry.getKey().getCodigoDaTurma() + ";" + entry.getValue().toString());
            writer.newLine();
        }
        
        for (Map.Entry<Turma, Double> entry : aluno.getFrequencias().entrySet()) {
            writer.write("FREQ:" + entry.getKey().getCodigoDaTurma() + ";" + entry.getValue());
            writer.newLine();
        }

        } catch (IOException e) {
            System.out.println("Erro ao salvar aluno " + aluno.getNome() + ": " + e.getMessage());
        }
    }

    public static void carregarAlunos(List<Turma> turmas, List<Aluno> alunos) {
    File pasta = new File("banco_de_dados");

    if (pasta.exists() && pasta.isDirectory()) {
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.endsWith("_aluno.txt"));

        if (arquivos != null) {
            for (File arq : arquivos) {
                try (BufferedReader reader = new BufferedReader(new FileReader(arq))) {
                    String primeiraLinha = reader.readLine();
                    if (primeiraLinha == null) continue;

                    String[] partes = primeiraLinha.split(";");
                    String nome = partes[0];
                    String matricula = partes[1];
                    String curso = partes[2];
                    String email = partes[3];
                    double frequencia = Double.parseDouble(partes[4]);
                    List<String> turmasAprovadas = Arrays.asList(partes[5].split(","));

                    Aluno aluno = new Aluno(nome, matricula, curso, email);
                    aluno.setFrequencia(frequencia);
                    aluno.setTurmasAprovadas(new ArrayList<>(turmasAprovadas));

                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        if (linha.startsWith("TURMA:")) {
                            String codigo = linha.substring(6);
                            for (Turma t : turmas) {
                                if (t.getCodigoDaTurma().equals(codigo)) {
                                    aluno.getTurmasMatriculadas().add(t);
                                    break;
                                }
                            }
                        } else if (linha.startsWith("AVALIACAO:")) {
                            String[] av = linha.substring(10).split(";");
                            String codigo = av[0];
                            Avaliacao avaliacao = Avaliacao.fromString(av[1]); // precisa implementar esse método!
                            for (Turma t : turmas) {
                                if (t.getCodigoDaTurma().equals(codigo)) {
                                    aluno.getAvaliacoes().put(t, avaliacao);
                                    break;
                                }
                            }
                            } else if (linha.startsWith("FREQ:")) {
                                String[] fr = linha.substring(5).split(";");
                                String codigo = fr[0];
                                double freq = Double.parseDouble(fr[1]);
                                for (Turma t : turmas) {
                                    if (t.getCodigoDaTurma().equals(codigo)) {
                                        aluno.getFrequencias().put(t, freq);
                                        break;
                                    }
                                }
                            }
                        }

                        alunos.add(aluno);
                    } catch (IOException | NullPointerException e) {
                        System.out.println("Erro ao carregar aluno do arquivo " + arq.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }


    

    public static void matricularAluno(Scanner sc) {

        System.out.println("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();

        Aluno aluno = buscarAlunoPorMatricula(matricula);
    
        if (aluno == null) {
            System.out.println("❌ Aluno não encontrado.");
            return;
        }
    
        System.out.println("Digite o código da turma: ");
        String codigoTurma = sc.nextLine();
    
        Turma turmaSelecionada = null;
        for (Turma t : turmas) {
            if (t.getCodigoDaTurma().equals(codigoTurma)) {
                turmaSelecionada = t;
                break;
            }
        }
    
        if (turmaSelecionada == null) {
            System.out.println("❌ Turma não encontrada.");
            return;
        }

        if (aluno instanceof AlunoEspecial) {
            if (aluno.getTurmasMatriculadas().size() >= 2) {
                System.out.println("❌ Alunos especiais só pode se matricular em até 2 turmas.");
                return;
            }
        }
    
        // Verificar se o aluno já está matriculado
        if (aluno.getTurmasMatriculadas().contains(turmaSelecionada)) {
            System.out.println("❌ Aluno já matriculado nesta turma.");
            return;
        }
    
        
        if (turmaSelecionada.getAlunosMatriculados().size() >= turmaSelecionada.getCapacidadeMaxima()) {
            System.out.println("❌ Turma sem vagas disponíveis.");
            return;
        }
    
        // Verifica pré-requisitos
        Disciplina disciplina = turmaSelecionada.getDisciplina();

        if (disciplina == null) {
            System.out.println("❌ Disciplina da turma não encontrada.");

            modoAluno(sc);
            return;
        }
        
        List<String> preRequisitos = disciplina.getPreRequisitos();
    
        boolean temTodosOsPreRequisitos = true;
    
        for (String cod : preRequisitos) {
            boolean encontrou = false;
            for (Turma turmaCursada : aluno.getTurmasMatriculadas()) {
                if (turmaCursada.getDisciplina().getCodigo().equalsIgnoreCase(cod)) {
                    encontrou = true;
                    break;
                }
            }
            if (!encontrou) {
                temTodosOsPreRequisitos = false;
                break;
            }
        }
    
        if (!temTodosOsPreRequisitos) {
            System.out.println("❌ Aluno não possui os pré-requisitos para esta disciplina.");
            return;
            // No java, a negação é "!""
        }
    
        
        aluno.getTurmasMatriculadas().add(turmaSelecionada);
        turmaSelecionada.getAlunosMatriculados().add(aluno);

        salvarAluno(aluno);
    
        System.out.println("✅ Aluno matriculado com sucesso na turma: " + turmaSelecionada.getCodigoDaTurma() + " de " + turmaSelecionada.getDisciplina().getNome() + "!");
    }
    

    public static void modoAvaliacaoFrequencia(Scanner sc) {

        int escolhaPagina;
    
        System.out.println("---------------------\n");
        System.out.println("Bem-vindo(a) ao modo Avaliação/Frequência\n");
    
        System.out.println("### Escolha o que você quer fazer:\n");
        System.out.println("Opção 1 - Lançar notas dos alunos");
        System.out.println("Opção 2 - Exibir boletim de um aluno");
        System.out.println("Opção 3 - Gerar relatórios");
        System.out.println("Opção 4 - Voltar para a página inicial\n");
    
        System.out.print("Digite aqui sua opção: ");
        escolhaPagina = Integer.parseInt(sc.nextLine());


        // Uso de try exception
        try {
    
        switch (escolhaPagina) {
            case 1:
                lancarNotasEFrequencia(sc);
                break;
            case 2:
                exibirBoletimAluno(sc, listaAlunos);
                break;
            case 3:
                menuRelatorios(sc, turmas, disciplinas, listaAlunos);
                break;
            case 4:
                paginaInicial(sc);
                break;
            default:
                System.out.println("❌ Desculpe, não existe essa opção, tente novamente. \n");
                paginaInicial(sc);
                break;
        
            
        }

    } catch (NumberFormatException e) {
        System.out.println("❌ Entrada inválida. Por favor, digite apenas números.");
        paginaInicial(sc); 
    }
    

    }

    public static void menuRelatorios(Scanner sc, List<Turma> turmas, List<Disciplina> disciplinas, List<Aluno> alunos){
        // É preciso fazer esse menu para acessar o relatório de aluno, turma e Disciplina

        System.out.println("\n### Página de Relatórios \n");
        System.out.println("Opção 1 - Relatório por Turma");
        System.out.println("Opção 2 - Relatório por Disciplina");
        System.out.println("Opção 3 - Relatório por Professor");
        System.out.println("Opção 4 - Voltar");

        System.out.print("Digite aqui sua opção: ");
        int opcao = Integer.parseInt(sc.nextLine());

        try{
        switch (opcao) {
        case 1:
            relatorioPorTurma(sc, turmas, alunos);
            break;
        case 2:
            relatorioPorDisciplina(sc, disciplinas);
            break;
        case 3:
            relatorioPorProfessor(sc, turmas);
            break;
        case 4:
            return;
        default:
            System.out.println("Opção inválida.");
            //menuExibirRelatorios(sc, turmas, disciplinas, alunos);

        }
    

} catch (NumberFormatException e) {
    System.out.println("❌ Entrada inválida. Por favor, digite apenas números.");
    paginaInicial(sc); 
}
    
    

    }

    public static void relatorioPorTurma(Scanner sc, List<Turma> turmas, List<Aluno> alunos){
        System.out.print("Digite o código da turma: ");
        String codigo = sc.nextLine();

        Turma turmaSelecionada = null;
        for (Turma t : turmas) {
            if (t.getCodigoDaTurma().equalsIgnoreCase(codigo)) {
                turmaSelecionada = t;
                break;
            }
        }

        if (turmaSelecionada == null) {
            System.out.println("❌ Turma não encontrada.");
            return;
        }

        System.out.println("\n--- Relatório da Turma " + turmaSelecionada.getCodigoDaTurma() + " ---");

        for (Aluno aluno : turmaSelecionada.getAlunosMatriculados()) {
        Avaliacao av = aluno.getAvaliacao();
        Double freq = aluno.getFrequencia();

        if (av == null || freq == null) continue;

        double media = av.CalculoMedia();
        boolean notaOk = media >= 5.0;
        boolean freqOk = freq >= 0.75;
        String status = (!freqOk) ? "❌ Reprovado por falta" : (!notaOk ? "❌ Reprovado por nota" : "✅ Aprovado");

        System.out.printf("Aluno: %s | Média: %.2f | Frequência: %.2f%% | %s\n",
                aluno.getNome(), media, freq, status);
    }

    }

    public static void relatorioPorDisciplina(Scanner sc, List<Disciplina> disciplinas) {
        System.out.print("Digite o código da disciplina: ");
        String cod = sc.nextLine();
    
        Disciplina d = null;
        for (Disciplina disc : disciplinas) {
            if (disc.getCodigo().equalsIgnoreCase(cod)) {
                d = disc;
                break;
            }
        }
    
        if (d == null) {
            System.out.println("❌ Disciplina não encontrada.");
            return;
        }
    
        System.out.println("\n--- Relatório da Disciplina " + d.getNome() + " ---");
        for (Turma turma : d.getTurmas()) {
            System.out.println("Turma: " + turma.getCodigoDaTurma() + " | Professor: " + turma.getProfessor());
        }
    }
    
    
    public static void relatorioPorProfessor(Scanner sc, List<Turma> turmas) {
        System.out.print("Digite o nome do professor: ");
        String nome = sc.nextLine();
    
        System.out.println("\n--- Relatório do Professor " + nome + " ---");
    
        for (Turma turma : turmas) {
            if (turma.getProfessor().equalsIgnoreCase(nome)) {
                System.out.println("Turma: " + turma.getCodigoDaTurma() +
                                   " | Disciplina: " + turma.getDisciplina().getNome() +
                                   " | Semestre: " + turma.getSemestre());
            }
        }
    }
    

    public static void exibirBoletimAluno(Scanner sc, List<Aluno> alunos){
        System.out.println("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();

        Aluno alunoEncontrado = null;

        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().equalsIgnoreCase(matricula)) {
                alunoEncontrado = aluno;
                break;
            }
        }

        if (alunoEncontrado == null) {
            System.out.println("❌ Aluno não encontrado.");
            return;
        }
    
        System.out.println("\n--- Boletim de " + alunoEncontrado.getNome() + " ---");
    
        List<Turma> turmas = alunoEncontrado.getTurmasMatriculadas();
        if (turmas.isEmpty()) {
            System.out.println("❌ O aluno não está matriculado em nenhuma turma.");
            return;
        }
    
        for (Turma turma : turmas) {
            Avaliacao avaliacao = alunoEncontrado.getAvaliacao();
            Double frequencia = alunoEncontrado.getFrequencia();
    
            if (avaliacao == null || frequencia == null) {
                System.out.println("Turma " + turma.getCodigoDaTurma() + " - Dados incompletos.");
                continue;
            }
    
            double media = avaliacao.CalculoMedia();
            boolean aprovadoPorNota = media >= 5.0;
            boolean aprovadoPorFrequencia = frequencia >= 0.75;
            
            String status;

            if (!aprovadoPorFrequencia) {
                status = "❌ Reprovado por falta";
            } else if (!aprovadoPorNota) {
                status = "❌ Reprovado por nota";
            } else {
                status = "✅ Aprovado";
            }
    
            System.out.println("\nTurma: " + turma.getCodigoDaTurma() +
                               "\nDisciplina: " + turma.getDisciplina().getNome() +
                               "\nProfessor: " + turma.getProfessor() +
                               "\nSemestre: " + turma.getSemestre() +
                               "\nNota final: " + String.format("%.2f", media) +
                               "\nFrequência: " + String.format("%.2f", frequencia * 100) + "%" +
                               "\nStatus: " + status);
        }


    }


    public static Turma buscarTurmaPorCodigo(String codigo) {
        for (Turma t : turmas) {
            if (t.getCodigoDaTurma().equalsIgnoreCase(codigo)) {
                return t;
            }
        }
        return null;
    }

    
    public static void lancarNotasEFrequencia(Scanner sc){

        System.out.println("Escreva o código da turma que você quer lançar notas: ");

        String codigo = sc.nextLine();

        Turma turma = buscarTurmaPorCodigo(codigo);
        
        if(turma == null){
            System.err.println("❌ Essa turma não existe, tente cadastrar uma nova turma \n");
            modoDisciplina(sc, 0);
        }

        for (Aluno aluno : turma.getAlunosMatriculados()) {
            System.out.println("Aluno: " + aluno.getNome());
    
            Avaliacao avaliacao = new Avaliacao();
    
            System.out.print("Tipo de média (0 = Normal, 1 = ponderada): ");
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
    
            System.out.print("Frequência do aluno (0% a 100%): ");
            aluno.setFrequencia(Double.parseDouble(sc.nextLine()));
    
            System.out.println("Média final: " + avaliacao.CalculoMedia());
            if (avaliacao.aprovado(aluno.getFrequencia())) {
                System.out.println("Aluno aprovado ✅");
                List<String> turmasAprovadas = aluno.getTurmasAprovadas();
                turmasAprovadas.add(codigo);
                salvarAluno(aluno);
                aluno.setTurmasAprovadas(null);
            } else {
                System.out.println("Aluno reprovado ❌");
                List<Turma> turmasMatriculadas = aluno.getTurmasMatriculadas();
                
                for(Turma t1 : aluno.getTurmasMatriculadas()){
                    if(t1.equals(turma)){
                        turmasMatriculadas.remove(t1);
                        aluno.setTurmasMatriculadas(turmasMatriculadas);
                    }
                }
                salvarAluno(aluno);

            }
            System.out.println("--------------------------");}

            modoAvaliacaoFrequencia(sc);
        }
    
    

    public static void modoDisciplina(Scanner sc, int escolhaPagina){

        escolhaPagina = 0;
        
        System.out.println("---------------------\n");
        System.out.println("Bem vindo(a) ao modo Disciplina e Turma\n");

        System.out.println("### Escolha que você quer fazer: \n");
        System.out.println("Opção 1 - Cadastrar disciplinas");
        System.out.println("Opção 2 - Criar turmas");
        System.out.println("Opção 3 - Exibir todas as disciplinas");
        System.out.println("Opção 4 - Exibir todas as turmas cadastradas");
        System.out.println("Opção 5 - Voltar para a página inicial");

        System.out.print("\nDigite aqui sua opção: ");

        escolhaPagina = Integer.parseInt(sc.nextLine());

        switch (escolhaPagina) {
            case 1:
                cadastrarDisciplina(sc);
                break;
            case 2:
                cadastrarTurma(sc);
                break;
            case 3:
                exibirDisciplinas(sc);
                break;
            case 4:
                exibirTurmas(sc);
                break;
            default:
                paginaInicial(sc);
                break;
        }

    }

    public static void cadastrarTurma(Scanner sc) {
        System.out.println("\n### Cadastro de Turma ###");
    
        System.out.print("Código da disciplina para esta turma: ");
        String codigoDisciplina = sc.nextLine();
    
        // Procurar disciplina
        Disciplina disciplinaSelecionada = null;
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigoDisciplina)) {
                disciplinaSelecionada = d;
                break;
            }
        }
    
        if (disciplinaSelecionada == null) {
            System.out.println("❌ Disciplina não encontrada. Cadastre a disciplina antes.");
            return;
        }
    
        System.out.print("Nome do professor: ");
        String professor = sc.nextLine();
    
        System.out.print("Semestre (ex: 2025.1): ");
        String semestre = sc.nextLine();
    
        System.out.println("### Escolha a forma de avaliação:\n");
        System.out.println("1 - Média simples (P1 + P2 + P3 + L + S) / 5");
        System.out.println("2 - Média ponderada (P1 + P2*2 + P3*3 + L + S) / 8");
        String escolhaForma = sc.nextLine();
        String formaAvaliacao = escolhaForma.equals("1") ? "simples" : "ponderada";
    
        System.out.print("A turma é presencial? (s/n): ");
        boolean presencial = sc.nextLine().equalsIgnoreCase("s");
    
        System.out.print("Horário: ");
        String horario = sc.nextLine();
    
        System.out.print("Capacidade máxima de alunos: ");
        int capacidadeMaxima = Integer.parseInt(sc.nextLine());
    
        System.out.print("Código da turma (ex: T01): ");
        String codigoDaTurma = sc.nextLine();

        List<Aluno> alunosMatriculados = new ArrayList<>();

        if (presencial == true){
            System.out.print("Sala: ");
            String sala = sc.nextLine();

            Turma novaTurma = new Turma(professor, semestre, formaAvaliacao, presencial, horario,
                                        capacidadeMaxima, alunosMatriculados, codigoDaTurma, disciplinaSelecionada, sala);
            turmas.add(novaTurma);
            disciplinaSelecionada.getTurmas().add(novaTurma);
            salvarDisciplinas(disciplinas);
            System.out.println("\n✅ Turma cadastrada com sucesso na disciplina " + disciplinaSelecionada.getNome());
            modoDisciplina(sc, 0);

        } else {
            Turma novaTurma = new Turma(professor, semestre, formaAvaliacao, presencial, horario,
                                        capacidadeMaxima, alunosMatriculados, codigoDaTurma, disciplinaSelecionada);
            turmas.add(novaTurma);
            disciplinaSelecionada.getTurmas().add(novaTurma);
            salvarDisciplinas(disciplinas);
            System.out.println("\n✅ Turma cadastrada com sucesso na disciplina " + disciplinaSelecionada.getNome());
            modoDisciplina(sc, capacidadeMaxima);
        }
}



    public static void exibirDisciplinas(Scanner sc){

        List<Disciplina> disciplinas = carregarDisciplinas();
        System.out.println("\n### Disciplinas disponíveis: ");
        
        for (Disciplina d : disciplinas){
            System.out.println("\nCódigo: " + d.getCodigo() + " | Nome: " + d.getNome() + " | Carga horária: " + d.getCargaHoraria() + " | prerequisitos: " +d.getPreRequisitos() + " | Turmas: " + d.getTurmas() + " | Pré-requisitos: " + d.getPreRequisitos());
        }
        
        paginaInicial(sc);
        
    }

    
    public static void exibirTurmas(Scanner sc) {
        carregarDisciplinas(); // Carrega a lista de disciplinas
        carregarTurmas(disciplinas); // Passa a lista para vincular turmas às disciplinas
    
        System.out.println("\n### Turmas Cadastradas ");
        for (Turma t : turmas) {
            System.out.println(
                "Código: " + t.getCodigoDaTurma() +
                " | Professor: " + t.getProfessor() +
                " | Semestre: " + t.getSemestre() +
                " | Forma de Avaliação: " + t.getFormaAvaliacao() +
                " | Disciplina: " + t.getDisciplina().getNome()
            );
        }
    
        paginaInicial(sc);
    }
    
    
    public static void cadastrarDisciplina(Scanner sc) {
        System.out.println("\n### Cadastro de Disciplina ###");
    
        System.out.print("Nome da disciplina: ");
        String nome = sc.nextLine();
    
        System.out.print("Código da disciplina (ex: MAT101): ");
        String codigo = sc.nextLine();

        for(Disciplina p : disciplinas){
            if(p.getCodigo().equals(codigo)){
                System.out.println("❌ Uma disciplina com esse código já foi cadastrada ");
                
            }
        }

        System.out.print("Carga horária (em horas): ");
        String cargaHoraria = sc.nextLine();
    
        System.out.print("Quantos pré-requisitos essa disciplina possui? ");
        int qtdPre = Integer.parseInt(sc.nextLine());
    
        List<String> preRequisitos = new ArrayList<>();
    
        for (int i = 0; i < qtdPre; i++) {
            System.out.print("Código do pré-requisito " + (i + 1) + ": ");

            String pre = sc.nextLine();
            
            for(Disciplina p1 : carregarDisciplinas()){
                if(p1.getCodigo().equals(pre)){
                    preRequisitos.add(pre);
                    
                }
                else{
                    System.out.println("❌ Essa disciplina de pré-requisito não foi cadastrada ");
                    cadastrarDisciplina(sc);
                }
            }

            preRequisitos.add(pre);
        }

        List<Turma> turmasVazias = new ArrayList<>();

        Disciplina nova = new Disciplina(nome, codigo, cargaHoraria, preRequisitos, turmasVazias);
        disciplinas.add(nova);
        
        salvarDisciplinas(disciplinas);
        System.out.println("\n✅ Disciplina cadastrada com sucesso!");
        modoDisciplina(sc,0);
    
    }


    public static void modoAluno(Scanner sc){

        int escolhaPagina;

        System.out.println("---------------------\n");
        System.out.println("Bem vindo(a) ao modo aluno\n");

        System.out.println("### Escolha que você quer fazer:\n");
        System.out.println("Opção 1 - Cadastrar aluno");
        System.out.println("Opção 2 - Trancar disciplinas");
        System.out.println("Opção 3 - Exibir todos os alunos");
        System.out.println("Opção 4 - Matricular aluno em uma turma");
        System.out.println("Opção 5 - Voltar para a página Inicial\n");


        System.out.print("Digite aqui sua opção: ");

        escolhaPagina = Integer.parseInt(sc.nextLine());
        try{
        switch (escolhaPagina) {
            case 1:
                cadastrarAluno(sc);
                break;
            case 2:
                trancarDisciplina(sc);
                break;
            case 3:
                mostrarAlunos(sc);
                break;
            case 4:
                matricularAluno(sc);
            default:
                paginaInicial(sc);
                break;
        }
    

} catch (NumberFormatException e) {
    System.out.println("\n❌ Entrada inválida. Por favor, digite apenas números.");
    paginaInicial(sc); 
}
    }

    public static void trancarDisciplina(Scanner sc){
        System.out.println("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();

        Aluno aluno = buscarAlunoPorMatricula(matricula);

        if (aluno.equals(null)){
            System.out.println("❌ Aluno não encontrado ");
        }

        List<Turma> turmasMatriculadas = aluno.getTurmasMatriculadas();


        if (turmasMatriculadas.isEmpty()) {
            System.out.println("❌ Aluno não está matriculado em nenhuma turma ");
            return;
        }
        
        System.out.println("\n📚 Turmas matriculadas:");
        for (int i = 0; i < turmasMatriculadas.size(); i++) {
            Turma t = turmasMatriculadas.get(i);
            System.out.printf("%d - %s (%s)\n", i + 1, t.getDisciplina().getNome(), t.getCodigoDaTurma());
        }

        System.out.print("Digite o número da turma que deseja trancar: ");
        int escolha = Integer.parseInt(sc.nextLine());

        if (escolha < 1 || escolha > turmasMatriculadas.size()) {
            System.out.println("❌ Escolha inválida");
            return;
        }

        Turma turmaParaTrancar = turmasMatriculadas.get(escolha - 1);

        aluno.getTurmasMatriculadas().remove(turmaParaTrancar);
        turmaParaTrancar.getAlunosMatriculados().remove(aluno);
        salvarAluno(aluno);

        System.out.println("✅ Trancamento realizado com sucesso!");

        modoAluno(sc);

    }

    public static void mostrarAlunos(Scanner sc){

        carregarAlunos(turmas, listaAlunos);

        if(listaAlunos.size() == 0 && listaAlunosEspeciais.size()==0){
            System.out.println("\n❌ Não há alunos cadastrados no momento \n");
            modoAluno(sc);
            
        }

        System.out.println("\n-------------\n");
        System.out.println("Alunos Normais: ");


        for(Aluno aluno : listaAlunos){
            System.out.println("\n-------------\n");
            System.out.println("Nome: " + aluno.getNome() + " | Email: " + aluno.getEmail() + " | Matrícula: " 
            + aluno.getMatricula() + " | Curso: " + aluno.getCurso() );
        
            System.out.println("\nTurmas que o aluno está cursando:");

            for(Turma turma: aluno.getTurmasMatriculadas()){
                System.out.println("\nCódigo da turma: " + turma.getCodigoDaTurma() + " | Professor: " + turma.getProfessor() + " | Horário: " + turma.getHorario());

            }

            System.out.println("\nTurmas que o aluno foi aprovado:");


            for (String t : aluno.getTurmasAprovadas()) {
                Turma t1 = buscarTurmaPorCodigo(t);
                if (t1 != null) {
                    System.out.println("\nCódigo da turma: " + t1.getCodigoDaTurma() +
                        " | Disciplina: " + t1.getDisciplina().getNome());
                } else {
                    System.out.println("\n⚠️ Turma com código \"" + t + "\" não encontrada (pode ter sido deletada ou não carregada).");
                }
            }
            
        }

        System.out.println("\n-------------\n");
        System.out.println("Alunos Especiais:");

        for(AlunoEspecial aluno : listaAlunosEspeciais){
            System.out.println("\n-------------\n");
            System.out.println("Nome: " + aluno.getNome() + " | Email: " + aluno.getEmail() + " | Matrícula: " 
            + aluno.getMatricula() + " | Curso: " + aluno.getCurso() );
        
            System.out.println("\nTurmas que o aluno está cursando:");

            for(Turma turma: aluno.getTurmasMatriculadas()){
                System.out.println("\nCódigo da turma: " + turma.getCodigoDaTurma() + " | Professor: " + turma.getProfessor() + " | Horário: " + turma.getHorario());

            }

            System.out.println("\nTurmas que o aluno foi aprovado:");

            for (String t : aluno.getTurmasAprovadas()){
                Turma t1 = buscarTurmaPorCodigo(t);
                System.out.println("\nCódigo da turma: " + t1.getCodigoDaTurma() + " | Disciplina: "+ t1.getDisciplina().getNome());
            }
        }

        paginaInicial(sc);

        
    }


    public static void cadastrarAluno(Scanner sc){
        System.out.println("\n---------------------\n");
        System.out.println("Digite o nome do novo aluno: ");
        String nomeAluno = sc.nextLine();
        System.out.println("Digite a matrícula do novo aluno: ");
        String matricula = sc.nextLine();

        for(Aluno aluno : listaAlunos){
            if(aluno.getMatricula().equals(matricula)){
                System.out.println("❌ Já existe um aluno com essa matrícula, digite outra");
                cadastrarAluno(sc);
                break;
            }
        
        }

        for(AlunoEspecial alunoe : listaAlunosEspeciais){
            if(alunoe.getMatricula().equals(matricula)){
                System.out.println("❌ Já existe um aluno com essa matrícula, digite outra");
                cadastrarAluno(sc);
                break;
            }
        
        }

        System.out.println("Digite o curso do novo aluno: ");
        String curso = sc.nextLine();
        System.out.println("Digite o email do novo aluno: ");
        String email = sc.nextLine();
        System.out.println("Digite 1 se o aluno for especial e 0 se for normal");
        int especial = Integer.parseInt(sc.nextLine());



        if(especial == 1){
            
            AlunoEspecial novoAlunoEspecial = new AlunoEspecial(nomeAluno, matricula, curso, email);
            
            if (novoAlunoEspecial.verificarCadastro()) {
                listaAlunosEspeciais.add(novoAlunoEspecial);
                System.out.println("✅ Aluno Especial cadastrado com sucesso!");
                } 
                else {
                    
                System.out.println("❌ Todos os campos devem ser preenchidos corretamente.");
                } 
        }
        else{
            Aluno novoAluno = new Aluno(nomeAluno, matricula, curso, email);

            if (novoAluno.verificarCadastro()) {
                listaAlunos.add(novoAluno);
                salvarAluno(novoAluno);
                System.out.println("\n✅ Aluno cadastrado com sucesso!");
                } else {
                    
                System.out.println("\n❌ Todos os campos devem ser preenchidos corretamente.\n");
                } 
        }

        paginaInicial(sc);
    }

}  