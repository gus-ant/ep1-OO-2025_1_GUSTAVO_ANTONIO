import java.util.List;
import java.util.ArrayList;


// Começo da classe de Disciplina. Criei uma <lista de Turmas> pois uma disciplina pode ter várias turmas.
public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private List<String> preRequisitos;
    private List<Turma> turmas;

}
