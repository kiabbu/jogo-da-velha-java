package grupog.eduardojoaoviniciosizadora;

import java.util.Scanner;
import java.util.Random;

public class teste {
    static Scanner obj = new Scanner(System.in);
    static int imprimeMenuPrincipal(){
        /*
        *menu do jogo onde o jogador escolherá o modo de jogo;
        */   
        int n;
        System.out.println(" ###Bem vindo ao jogo da velha###\n\n     O jogo terminará quando: \nUm dos jogadores alcançar 3 vitórias\n\n   Favor escolher o modo de jogo:\n");
        System.out.println("------------Menu------------");
        System.out.println("1 - Jogador x Jogador"); //modo onde dois jogadores disputam entre si em jogadas alternadas;
        System.out.println("2 - Jogador x Máquina(Fácil)"); //modo onde um jogador disputa com o computador no modo de jogadas aletório;
        System.out.println("3 - Jogador x Máquina(Dificil)"); //modo onde um jogador disputa com o computador e as jogadas são revistas para alocar na melhor posicao;
        n = obj.nextInt();
        return n; 
    }
    
    static void controlador(int n){
        /*
        *controlador que inicia o modo de jogo selecionado a partir do menu;
        */
        switch(n){
            case 1 : modoJogador(); break;
            case 2 : modoFacil(); break;
            case 3 : modoDificil(); break;
        }
                
    }
    
    static String[][] inicializarTabuleiro(){
        /*
        *inicializa o tabuleiro (matriz que será utilizada no jogo) com "vazio" em todos os endereços);
        */
        String[][] matriz = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
        return matriz;
    }
        
    static void imprimirTabuleiro(String[][] matriz){
        /*
        *imprime o tabuleiro no formato de '#', com os respectivos 'x' e 'o' quando estes preenchem o endereco da matriz;
        */        
        int i, j;
        System.out.println("");
        for(i = 0; i<3; i++){
            for(j = 0; j<3; j++){
                if(i<2){
                    if(matriz[i][j].equals(" ")){
                        System.out.print("_"+"_");
                    } else{
                        System.out.print(matriz[i][j]+"_");
                    }
                } else{
                    if(matriz[i][j].equals(" ")){
                        System.out.print(" "+" ");
                    } else{
                        System.out.print(matriz[i][j]+" ");
                    }
                } if(j<=1){
                        System.out.print("|");
                    }  
            }
            System.out.println("");
        }
    }
    
    static int leiaCoordenadaLinha(){
        /*
        *recebe a coordenada da linha a ser preenchida da matriz;
        */        
        int linha;
        System.out.print("\nEscolha a Linha: ");
        linha = obj.nextInt();
        return linha;
    }
    
    static int leiaCoordenadaColuna(){
        /*
        *recebe a coordenada da coluna a ser preenchida da matriz;
        */        
        int coluna;
        System.out.print("Escolha a Coluna: ");
        coluna = obj.nextInt();
        return coluna;
    }
    
    static int[] posicaoValida(String[][] matriz){
        /*
        *recebe as coordenadas da linha e coluna a serem preenchidas na matriz e verifica se sao validas;
        *se acaso ja houver preenchimento na coordenada selecionada o jogador deve escolher nova coordenada;
        *caso a coordenada seja uma posicao que nao exista na matriz o jogador deve escolher nova coordenada;
        */        
        int linha = 1, coluna = 1;
        do{
        do{
        if(linha<1 || linha>3 || coluna<1 || coluna>3){
            System.out.println("Posição inválida!!!\nTente novamente");
        }
        linha = leiaCoordenadaLinha();
        coluna = leiaCoordenadaColuna();
        } while(linha<1 || linha>3 || coluna<1 || coluna>3);
            if(!matriz[linha-1][coluna-1].equals(" ")){
                System.out.println("Posição já preenchida!!!\nTente novamente");
            }
        } while(!matriz[linha-1][coluna-1].equals(" "));
        int[] posicao = {linha, coluna};
        return posicao;
    }
    
    static void imprimePontuacao(int l1, int l2){
        /*
        *recebe a quantidade de pontos de cada jogador na rodada, soma ao placar agregado e imprime o novo placar agragado;
        */        
        int p1=0+l1, p2=0+l2;
        System.out.println("\n    ----Placar----");
        System.out.println("    Jogador X:   "+p1);
        System.out.println("    Jogador O:   "+p2+"\n");
        System.out.println("=======================\n");
        if(p1!=3 && p2!=3){
            System.out.println("Nova partida:");
        }
    }
    
    static String[][] jogadaUsuario(String[][] matriz, String letra){
        /*
        *recebe a matriz do tabuleiro, o jogador da vez, e após validar a jogada passa essas informacoes para aplicar a jogada;
        */        
        System.out.print("\nJogador "+letra+" é a sua vez!");
        int[] posicaoValida1 = posicaoValida(matriz);
        jogada(posicaoValida1[0]-1, posicaoValida1[1]-1, matriz, letra);
        return matriz;
    }
    
    static void jogada(int linha, int coluna, String[][] matriz, String letra){
        /*
        *recebe as coordenadas da jogada e aplica o simbolo do jogador no endereco selecionado;
        */        
        matriz[linha][coluna]=letra;
        imprimirTabuleiro(matriz);
    }
    
    static void modoJogador(){
        /*
        *funcao matriz do modo jogador x jogador, onde inicializa-se o tabuleiro e ha as jogadas de cada jogador por turno;
        *ao final de cada partida esta utiliza a funcao verifica vencedor ou verifica velha para atribuir o placar;
        */        
        int i, x=0, o=0, h=0;
        String letra;
        while(x!=3 && o!=3){
        int g=0;
        String matriz[][] = inicializarTabuleiro();
        imprimirTabuleiro(matriz);
            for(i = h; i < (h+9) && g==0; i++) {
                if(i%2==0){
                    letra = "X";
                } else {
                    letra = "O";
                }
                jogadaUsuario(matriz, letra);
                g=verificaVencedor(matriz, letra);
            }
        System.out.println("\n======Fim de jogo======\n");
        if(i==(h+9) && g==0){
            verificaVelha();
        }
        else if(i%2!=0){
            System.out.println("  Jogador X venceu!!!");
            x = x+1;
            o = o+0;
            h = 0;
        } else{
            System.out.println("  Jogador O venceu!!!");
            x = x+0;
            o = o+1;
            h = 1;
        }
        imprimePontuacao(x, o);
        }
    }
    
    static int verificaVencedor(String[][] matriz, String letra){
        /*
        *verifica a cada jogada se algum jogador preencheu as colunas, linhas ou diagonais com o seu simbolo no jogo;
        *caso este o identifique, ele informa o modo jogador para sair do laco de repeticao e informar o vencedor;
        */        
        int g=0;
                if(matriz[0][0].equals(letra) && matriz[1][0].equals(letra) && matriz[2][0].equals(letra)){
                    g=1;
                }
                else if(matriz[0][1].equals(letra) && matriz[1][1].equals(letra) && matriz[2][1].equals(letra)){
                    g=1;
                }
                else if(matriz[0][2].equals(letra) && matriz[1][2].equals(letra) && matriz[2][2].equals(letra)){
                    g=1;
                }
                else if(matriz[0][0].equals(letra) && matriz[0][1].equals(letra) && matriz[0][2].equals(letra)){
                    g=1;
                }
                else if(matriz[1][0].equals(letra) && matriz[1][1].equals(letra) && matriz[1][2].equals(letra)){
                    g=1;
                }
                else if(matriz[2][0].equals(letra) && matriz[2][1].equals(letra) && matriz[2][2].equals(letra)){
                    g=1;
                }
                else if(matriz[0][0].equals(letra) && matriz[1][1].equals(letra) && matriz[2][2].equals(letra)){
                    g=1;
                }
                else if(matriz[0][2].equals(letra) && matriz[1][1].equals(letra) && matriz[2][0].equals(letra)){
                    g=1;
                }
        return g;
    }

    static void verificaVelha(){
        /*
        *caso todos os endereços tenham sido preenchidos e nao haja vencedor, esta funcao retorna com o empate;
        */        
        System.out.println("  O jogo deu velha!!!");
    }
    
    static String[][] jogadaMaquinaFacil(String[][] matriz, String letra){
        /*
        *recebe a matriz do tabuleiro, gera uma posicao aleatoria, e após validar a jogada passa essas informacoes para aplicar a jogada da maquina;
        */        
        Random random = new Random();
        int z = 0;
        System.out.print("\nMáquina é a sua vez!\n");
        while(z==0){
            int c = random.nextInt(3);
            int d = random.nextInt(3);
            if(matriz[c][d].equals(" ")){
                jogada(c, d, matriz, letra);
                z = 1;
            } 
        }
        return matriz;
    }
    
    static void modoFacil(){
        /*
        *funcao matriz do modo jogador x maquina facil, onde inicializa-se o tabuleiro e ha as jogadas do jogador e da maquina (aleatorio) por turno;
        *ao final de cada partida esta utiliza a funcao verifica vencedor ou verifica velha para atribuir o placar;
        */        
        int i, x=0, o=0, h=0;
        String letra;
        while(x!=3 && o!=3){
        int g=0;
        String matriz[][] = inicializarTabuleiro();
        imprimirTabuleiro(matriz);
            for(i = h; i < (h+9) && g==0; i++) {
                if(i%2==0){
                    letra = "X";
                    jogadaUsuario(matriz, letra);
                } else {
                    letra = "O";
                    jogadaMaquinaFacil(matriz, letra);
                }
                g=verificaVencedor(matriz, letra);
            }
        System.out.println("\n======Fim de jogo======\n");
        if(i==(h+9) && g==0){
            verificaVelha();
        }
        else if(i%2!=0){
            System.out.println("  Jogador X venceu!!!");
            x = x+1;
            o = o+0;
            h = 0;
        } else{
            System.out.println("  Jogador O venceu!!!");
            x = x+0;
            o = o+1;
            h = 1;
        }
        imprimePontuacao(x, o);
        }        
    }
    
    static String[][] jogadaMaquinaDificil(String[][] matriz, String letra){
        System.out.print("\nMáquina é a sua vez!\n");
            /*
             *se o meio do tabuleiro estiver vazio a maquina joga ali;
             */
            if(matriz[1][1].equals(" ")){
                jogada(1, 1, matriz, letra);
            }
            /*
             *ganhar: se a maquina tem duas pecas numa linha, ponha a terceira;
             */
            else if(matriz[2][0].equals(" ") && ((matriz[0][0].equals("O") && matriz[1][0].equals("O")) || (matriz[0][2].equals("O") && matriz[1][1].equals("O")) || (matriz[2][1].equals("O") && matriz[2][2].equals("O")))){
                jogada(2, 0, matriz, letra);
            } else if(matriz[1][0].equals(" ") &&((matriz[0][0].equals("O") && matriz[2][0].equals("O")) || (matriz[1][1].equals("O") && matriz[1][2].equals("O")))){
                jogada(1, 0, matriz, letra);
            } else if(matriz[0][0].equals(" ") &&((matriz[1][0].equals("O") && matriz[2][0].equals("O")) || (matriz[1][1].equals("O") && matriz[2][2].equals("O")) || (matriz[0][1].equals("O") && matriz[0][2].equals("O")))){
                jogada(0, 0, matriz, letra);
            } else if(matriz[0][1].equals(" ") &&((matriz[1][1].equals("O") && matriz[2][1].equals("O")) || (matriz[0][0].equals("O") && matriz[0][2].equals("O")))){
                jogada(0, 1, matriz, letra);
            } else if(matriz[2][1].equals(" ") &&((matriz[1][1].equals("O") && matriz[0][1].equals("O")) || (matriz[2][0].equals("O") && matriz[2][2].equals("O")))){
                jogada(2, 1, matriz, letra);                
            } else if(matriz[0][2].equals(" ") &&((matriz[1][2].equals("O") && matriz[2][2].equals("O")) || (matriz[1][1].equals("O") && matriz[2][0].equals("O")) || (matriz[0][0].equals("O") && matriz[0][1].equals("O")))){
                jogada(0, 2, matriz, letra);                
            } else if(matriz[1][2].equals(" ") &&((matriz[0][2].equals("O") && matriz[2][2].equals("O")) || (matriz[1][0].equals("O") && matriz[1][1].equals("O")))){
                jogada(1, 2, matriz, letra);                                
            } else if(matriz[2][2].equals(" ") &&((matriz[0][2].equals("O") && matriz[1][2].equals("O")) || (matriz[0][0].equals("O") && matriz[1][1].equals("O")) || (matriz[2][0].equals("O") && matriz[2][1].equals("O")))){
                jogada(2, 2, matriz, letra);                                
            } 
            /*
             *bloquear: se o oponente tiver duas pecas em linha, ponha a terceira para bloquea-lo;
             */
            else if(matriz[2][0].equals(" ") &&((matriz[0][0].equals("X") && matriz[1][0].equals("X")) || (matriz[0][2].equals("X") && matriz[1][1].equals("X")) || (matriz[2][1].equals("X") && matriz[2][2].equals("X")))){
                jogada(2, 0, matriz, letra);
            } else if(matriz[1][0].equals(" ") &&((matriz[0][0].equals("X") && matriz[2][0].equals("X")) || (matriz[1][1].equals("X") && matriz[1][2].equals("X")))){
                jogada(1, 0, matriz, letra);
            } else if(matriz[0][0].equals(" ") &&((matriz[1][0].equals("X") && matriz[2][0].equals("X")) || (matriz[1][1].equals("X") && matriz[2][2].equals("X")) || (matriz[0][1].equals("X") && matriz[0][2].equals("X")))){
                jogada(0, 0, matriz, letra);
            } else if(matriz[0][1].equals(" ") &&((matriz[1][1].equals("X") && matriz[2][1].equals("X")) || (matriz[0][0].equals("X") && matriz[0][2].equals("X")))){
                jogada(0, 1, matriz, letra);
            } else if(matriz[2][1].equals(" ") &&((matriz[1][1].equals("X") && matriz[0][1].equals("X")) || (matriz[2][0].equals("X") && matriz[2][2].equals("X")))){
                jogada(2, 1, matriz, letra);                
            } else if(matriz[0][2].equals(" ") &&((matriz[1][2].equals("X") && matriz[2][2].equals("X")) || (matriz[1][1].equals("X") && matriz[2][0].equals("X")) || (matriz[0][0].equals("X") && matriz[0][1].equals("X")))){
                jogada(0, 2, matriz, letra);                
            } else if(matriz[1][2].equals(" ") &&((matriz[0][2].equals("X") && matriz[2][2].equals("X")) || (matriz[1][0].equals("X") && matriz[1][1].equals("X")))){
                jogada(1, 2, matriz, letra);                                
            } else if(matriz[2][2].equals(" ") &&((matriz[0][2].equals("X") && matriz[1][2].equals("X")) || (matriz[0][0].equals("X") && matriz[1][1].equals("X")) || (matriz[2][0].equals("X") && matriz[2][1].equals("X")))){
                jogada(2, 2, matriz, letra);                                                
            }
            /*
             *canto vazio: jogue num canto que esteja vazio. preferencialmente, jogue em um canto onde o seu canto inverso tambem esteja vazio;
             */
            else if((matriz[0][0].equals(" ") && matriz[2][2].equals(" "))){
                jogada(0, 0, matriz, letra);                                                                
            } else if((matriz[0][2].equals(" ") && matriz[2][0].equals(" "))){
                jogada(2, 0, matriz, letra);                                                                  
            } else if(matriz[0][0].equals(" ")){
                jogada(0, 0, matriz, letra);                                                                  
            } else if(matriz[0][2].equals(" ")){
                jogada(0, 2, matriz, letra);                                                                  
            } else if(matriz[2][0].equals(" ")){
                jogada(2, 0, matriz, letra);                                                                  
            } else if(matriz[2][2].equals(" ")){
                jogada(2, 2, matriz, letra);                                                                  
            } 
            /*
             *borda vazia: jogue em uma borda que esteja vazia;
             */
            else if(matriz[0][1].equals(" ")){
                jogada(0, 1, matriz, letra);                                                                  
            } else if(matriz[1][0].equals(" ")){
                jogada(1, 0, matriz, letra);                                                                  
            } else if(matriz[1][2].equals(" ")){
                jogada(1, 2, matriz, letra);                                                                  
            } else if(matriz[2][1].equals(" ")){
                jogada(2, 1, matriz, letra);                                                                  
            }
        return matriz;
    }
    
    static void modoDificil(){
        /*
        *funcao matriz do modo jogador x maquina dificil, onde inicializa-se o tabuleiro e ha as jogadas do jogador e da maquina (onde ela verifica a melhor jogada possivel) por turno;
        *ao final de cada partida esta utiliza a funcao verifica vencedor ou verifica velha para atribuir o placar;
        */        
        int i, x=0, o=0, h=0;
        String letra;
        while(x!=3 && o!=3){
        int g=0;
        String matriz[][] = inicializarTabuleiro();
        imprimirTabuleiro(matriz);
            for(i = h; i < (h+9) && g==0; i++) {
                if(i%2==0){
                    letra = "X";
                    jogadaUsuario(matriz, letra);
                } else {
                    letra = "O";
                    jogadaMaquinaDificil(matriz, letra);
                }
                g=verificaVencedor(matriz, letra);
            }
        System.out.println("\n======Fim de jogo======\n");
        if(i==(h+9) && g==0){
            verificaVelha();
        }
        else if(i%2!=0){
            System.out.println("  Jogador X venceu!!!");
            x = x+1;
            o = o+0;
            h = 0;
        } else{
            System.out.println("  Jogador O venceu!!!");
            x = x+0;
            o = o+1;
            h = 1;
        }
        imprimePontuacao(x, o);
        }        
    }
    
    public static void main(String[] args) {
        int n = imprimeMenuPrincipal();
        controlador(n);
    }    
}
