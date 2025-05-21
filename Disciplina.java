
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



// Começo da classe de Disciplina. Criei uma <lista de Turmas> pois uma disciplina pode ter várias turmas.
public class Disciplina {
    private String nome;
    private String codigo;
    private String cargaHoraria;
    private List<String> preRequisitos;
    private List<Turma> turmas;

    public Disciplina(String nome, String codigo, String cargaHoraria, List<String> preRequisitos, List<Turma> turmas) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = preRequisitos;
        this.turmas = turmas != null ? turmas : new ArrayList<>();
    }


    public Disciplina(String nome, String codigo, String cargaHoraria) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
    }



    public static Disciplina fromString(String linha) {
        String[] partes = linha.split(";");
    
        if (partes.length < 4) {
            throw new IllegalArgumentException("Formato inválido: " + linha);
        }
    
        String nome = partes[0];
        String codigo = partes[1];
        String cargaHoraria = partes[2];
    
        List<String> preRequisitos;
        if (partes[3].equals("0")) {
            preRequisitos = new ArrayList<>();
        } else {
            preRequisitos = Arrays.asList(partes[3].split(","));
        }
    
        return new Disciplina(nome, codigo, cargaHoraria, preRequisitos, new ArrayList<>());
    }
    


    @Override
    public String toString() {
        String preReqs = String.join(",", preRequisitos);
        if (preRequisitos.size() == 0) {
            preReqs = "0";
        }
    return nome + ";" + codigo + ";" + cargaHoraria + ";" + preReqs;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public List<String> getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(List<String> preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        this.turmas = turmas;
    }
    

    

}
