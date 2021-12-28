package Main;
import Classes.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Command line interface
// c'est l'invite de commande
// ou on fait entrer nos commandes
public class Test {

    // Le programme `main`
    public static void main(String[] args) throws IOException {

        Intro intro =new Intro();
        intro.presentation();
        // Le buffer ou on recupere les commandes de l'utilisateur ligne par ligne
        BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(System.in) );

        // l'entree dans sa forme brute
        String in;
        // L'interpreteur du language
        Interpreteur inter = new Interpreteur();
        for (;;) { // boucle infinie jusqu'a l'entrer de la commande `end` pour terminer le programme
            System.out.print(" > ");
            in = reader.readLine().trim(); // on lit la ligne de commande
                                           // et on supprime les espaces de debut et de la fin
            if (in.equalsIgnoreCase("help")) { // si on tape `help` **it's not case sensitive**
               // Le message help

            } else if (in.equalsIgnoreCase("end")) { // si on tappe `end` pour arreter le programme
                System.out.println("Fin du programme");
                break; // on sort de la boucle
            } else {
                inter.setInput(in); // donner l'entree de l'utilisateur a l'`input` de l'interpreteur
                if(inter.checkErreur() == false) { // s'il y a pas d'erreur
                    inter.executerCommande(); // on execute la commande
                } else { // apres l'affichage de l'erreur par l'interpreteur, on affiche le menu du `help`

                }
            }
        }
    }
}