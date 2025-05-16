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

    
    public String getCodigoDaTurma() {
        return codigoDaTurma;
    }

    public void setCodigoDaTurma(String codigo) {
        this.codigoDaTurma = codigo;
    }

    public Turma(String professor, String semestre, String formaAvaliacao, boolean presencial, String sala,
            String horario, int capacidadeMaxima, List<Aluno> alunosMatriculados) {
        this.professor = professor;
        this.semestre = semestre;
        this.formaAvaliacao = formaAvaliacao;
        this.presencial = presencial;
        this.sala = sala;
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculados = alunosMatriculados;
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

    


    
}
