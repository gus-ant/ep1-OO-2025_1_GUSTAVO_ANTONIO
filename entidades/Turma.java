package entidades;
import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String professor;
    private String semestre;
    private String formaAvaliacao;
    private boolean presencial;
    private String sala; 
    private String horario;
    private int capacidadeMaxima;
    private List<Aluno> alunosMatriculados;
    private List<Aluno> alunosAprovados;
    private String codigoDaTurma;
    private Disciplina disciplina;

    

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


    public String toString(Disciplina d) {
        return d.getCodigo() + ";" + professor + ";" + semestre + ";" + formaAvaliacao + ";" +
               presencial + ";" + horario + ";" + capacidadeMaxima + ";" + codigoDaTurma + ";" + sala;
    }

    public static Turma fromString(String linha, Disciplina disciplina, List<Aluno> alunos) {
        String[] partes = linha.split(";");
    
        if (partes.length < 8) {
            throw new IllegalArgumentException("Linha de turma mal formatada: " + linha);
        }
    
        String professor = partes[1];
        String semestre = partes[2];
        String formaAvaliacao = partes[3];
        boolean presencial = Boolean.parseBoolean(partes[4]);
        String horario = partes[5];
        int capacidadeMaxima = Integer.parseInt(partes[6]);
        String codigoTurma = partes[7];
        String sala = partes.length > 8 ? partes[8] : "";
    
        List<Aluno> alunosMatriculados = new ArrayList<>();
        Turma turma;
    
        if (presencial) {
            turma = new Turma(professor, semestre, formaAvaliacao, true, horario, capacidadeMaxima,
                              alunosMatriculados, codigoTurma, disciplina, sala);
        } else {
            turma = new Turma(professor, semestre, formaAvaliacao, false, horario, capacidadeMaxima,
                              alunosMatriculados, codigoTurma, disciplina);
        }
    
        for (Aluno aluno : alunos) {
            for (Turma t : aluno.getTurmasMatriculadas()) {
                if (t.getCodigoDaTurma().equals(codigoTurma)) {
                    alunosMatriculados.add(aluno); 
                    break;
                }
            }
        }
    
        return turma;
    }  
    
    
    public String getCodigoDaTurma() {
        return codigoDaTurma;
    }

    public void setCodigoDaTurma(String codigo) {
        this.codigoDaTurma = codigo;
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

    public List<Aluno> getAlunosAprovados() {
        return alunosAprovados;
    }

    public void setAlunosAprovados(List<Aluno> alunosAprovados) {
        this.alunosAprovados = alunosAprovados;
    }

    


    
}
