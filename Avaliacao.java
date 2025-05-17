

public class Avaliacao {
    private double p1;
    private double p2;
    private double p3;
    private double lista;
    private double seminario;
    private int TipoMedia; // 0 para simples, 1 para ponderada

    public Avaliacao(double p1, double p2, double p3, double lista, double seminario) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.lista = lista;
        this.seminario = seminario;
        this.TipoMedia = 0;
    } 


    public Avaliacao() {
        this.p1 = 0;
        this.p2 = 0;
        this.p3 = 0;
        this.lista = 0;
        this.seminario = 0;
        this.TipoMedia = 0;
    }

    public double CalculoMedia(){
        
        if (this.TipoMedia == 0){
            return (p1 + p2 + p3 + lista + seminario) / 5;
        }
        else{
            return (p1 + 2*p2 + 3*p3 + lista + seminario) / 8;
        }
    }
    

    public boolean aprovado(double frequencia) {
        return CalculoMedia() >= 5.0 && (frequencia/100) >= (0.75);
    }





    public double getP1() {
        return p1;
    }


    public void setP1(double p1) {
        this.p1 = p1;
    }





    public double getP2() {
        return p2;
    }





    public void setP2(double p2) {
        this.p2 = p2;
    }





    public double getP3() {
        return p3;
    }





    public void setP3(double p3) {
        this.p3 = p3;
    }





    public double getLista() {
        return lista;
    }





    public void setLista(double lista) {
        this.lista = lista;
    }





    public double getSeminario() {
        return seminario;
    }





    public void setSeminario(double seminario) {
        this.seminario = seminario;
    }





    public int getTipoMedia() {
        return TipoMedia;
    }





    public void setTipoMedia(int tipoMedia) {
        TipoMedia = tipoMedia;
    }

}



