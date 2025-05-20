import java.util.ArrayList;
import java.util.List;

// Na turma, existe uma lista de alunos(objetos), que estão matriculados
public class Turma {
    private String professor;
    private String semestre;
    private String formaAvaliacao;
    private boolean presencial;
    private String sala; 
    private String horario;
    private int capacidadeMaxima;
    private List<Aluno> alunosMatriculados; // aluno(objetos), não nomes
    private String codigoDaTurma;
    private Disciplina disciplina;


    public String toString(Disciplina d) {
        return d.getCodigo() + ";" + professor + ";" + semestre + ";" + formaAvaliacao + ";" +
               presencial + ";" + horario + ";" + capacidadeMaxima + ";" + codigoDaTurma + ";" + sala;
    }

    
    public static Turma fromString(String linha, Disciplina disciplina) {
        String[] partes = linha.split(";");
        if (partes.length < 9) {
            throw new IllegalArgumentException("Linha de turma mal formatada: " + linha);
        }

        String professor = partes[1];
        String semestre = partes[2];
        String formaAvaliacao = partes[3];
        boolean presencial = Boolean.parseBoolean(partes[4]);
        String horario = partes[5];
        int capacidadeMaxima = Integer.parseInt(partes[6]);
        String codigoDaTurma = partes[7];
        String sala = partes[8];

        List<Aluno> alunosMatriculados = new ArrayList<>();

        if (presencial) {
            return new Turma(professor, semestre, formaAvaliacao, true, horario, capacidadeMaxima,
                    alunosMatriculados, codigoDaTurma, disciplina, sala);
        } else {
            return new Turma(professor, semestre, formaAvaliacao, false, horario, capacidadeMaxima,
                    alunosMatriculados, codigoDaTurma, disciplina);
        }
    } 
    
    public String getCodigoDaTurma() {
        return codigoDaTurma;
    }

    public void setCodigoDaTurma(String codigo) {
        this.codigoDaTurma = codigo;
    }

    // Sobrecarga para turma presencial
    public Turma(String professor, String semestre, String formaAvaliacao, boolean presencial,
            String horario, int capacidadeMaxima, List<Aluno> alunosMatriculados, String codigoDaTurma, Disciplina disciplina, String sala) {
        this.professor = professor;
        this.semestre = semestre;
        this.formaAvaliacao = formaAvaliacao;
        this.presencial = presencial;
        this.sala = "";
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculados = alunosMatriculados;
        this.codigoDaTurma = codigoDaTurma;
        this.disciplina = disciplina;
        this.sala = sala;
    }

    // Sobrecarga para turma remota
    public Turma(String professor, String semestre, String formaAvaliacao, boolean presencial,
            String horario, int capacidadeMaxima, List<Aluno> alunosMatriculados, String codigoDaTurma, Disciplina disciplina) {
        this.professor = professor;
        this.semestre = semestre;
        this.formaAvaliacao = formaAvaliacao;
        this.presencial = presencial;
        this.sala = "";
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculados = alunosMatriculados;
        this.codigoDaTurma = codigoDaTurma;
        this.disciplina = disciplina;
    }

    public String getProfessor() {
        return professor;
    }
    public void setProfessor(String professor) {
        this.professor = professor;
    }
    public String getSemestre() {
        return semestre;
    }
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
    public String getFormaAvaliacao() {
        return formaAvaliacao;
    }
    public void setFormaAvaliacao(String formaAvaliacao) {
        this.formaAvaliacao = formaAvaliacao;
    }
    public boolean isPresencial() {
        return presencial;
    }
    public void setPresencial(boolean presencial) {
        this.presencial = presencial;
    }
    public String getSala() {
        return sala;
    }
    public void setSala(String sala) {
        this.sala = sala;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
    public void setCapacidadeMaxima(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }
    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }
    public void setAlunosMatriculados(List<Aluno> alunosMatriculados) {
        this.alunosMatriculados = alunosMatriculados;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina nomeDisciplina) {
        this.disciplina = nomeDisciplina;
    }

    


    
}
