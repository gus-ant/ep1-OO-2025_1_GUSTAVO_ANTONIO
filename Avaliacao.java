public class Avaliacao {
    private double p1;
    private double p2;
    private double p3;
    private double lista;
    private double seminario;
    private int media; // 0 para simples, 1 para ponderada

    public Avaliacao(double p1, double p2, double p3, double lista, double seminario) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.lista = lista;
        this.seminario = seminario;
        this.media = 0;
    }


    public double CalculoMedia(boolean ponderada){
        
        if (ponderada){
            return (p1 + p2 + p3 + lista + seminario) / 5;
        }
        else{
            return (p1 + 2*p2 + 3*p3 + lista + seminario) / 8;
        }
    }
    

    public boolean aprovado(double frequencia) {
        return CalculoMedia(true) >= 5.0 && frequencia >= 0.75;
    }

}
