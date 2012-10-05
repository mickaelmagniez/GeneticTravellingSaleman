package salesman;
// classe : Courbes
//********************
//********************

// Fichier ecrit par Eric Dubot


import java.awt.*;
import java.awt.Point;
import java.util.Vector;


// Definition de la classe Courbes
public class Curves extends Canvas{

//--------------------------------------------------------------------------------------
//
//  Donnees
//
//--------------------------------------------------------------------------------------

        // Constantes
        // Couleurs a utiliser pour le dessin des differentes courbes
        private static final Color[]  COULEURS_COURBES= { Color.blue,
                                                                                                          Color.red,
                                                                                                          Color.green,
                                                                                                          Color.yellow};
        // Couleur du repere
        private static final Color COULEUR_REPERE = Color.black;
        // Constantes de mise en page du dessin
        private static final int MARGE_GAUCHE = 30;
        private static final int MARGE_DROITE = 20;
        private static final int MARGE_HAUT = 30;
        private static final int MARGE_BAS = 20;
        private static final int MARGE_REP_DROITE = MARGE_DROITE / 2;
        private static final int MARGE_REP_HAUT = 15;
        private static final int MARGE_REP_BAS = MARGE_BAS / 2;
        private static final int PAS_GRAD_X = 50;
        private static final int PAS_GRAD_Y = 30;
        private static final int LEG_X = 100;
        private static final int LEG_Y = 0;

        // Donnees
        // Legendes a placer sur l'axe des abscisses et sur celui des ordonnees
        private String legendeAbs, legendeOrd;
        // Legende des courbes
        private String[] legendeCourbes;
        // Image cachee sur lequel se dessine le dessin avant d'etre affiche a l'ecran
        private Image imageCachee;
        // Environement graphique associe au precedent dessin
        private Graphics gCachee;
        // A t on initialise le dessin?
        private boolean initialise = false;
        // Valeur limites courantes de dessin des points
        private double valLimMin,valLimMinDep;
        private double valLimMax,valLimMaxDep;
        // Nombre de courbes a dessiner
        private int nombreCourbes;
        // Numero maximum courant de dessin des points
        private	int nMax;
        // Pas horizontal utilise dans le dessin des points
        private int pasN = 1;
        // Liste des points entres dans le graphique
        private Vector listePoints;
        // Position de l'axe des abscisses
        private int baseY;
        // Coefficients utilises dans le calcul des positions des points a l'ecran
        private double coefMulY, coefMulX;
        // Sauvegarde du dernier point dessine
        private Point dernierDessine[];

//------------------------------------------------------------------------------------
//
// 		Les Methodes
//
//------------------------------------------------------------------------------------

        //-------------
        // Constructeur
        //-------------

        // Courbes
        //************************************************************************************
        // parametres : les valeurs limites de l'axe des ordonnees
        //				le nombre de courbes a dessiner
        //				les legendes des deux axes
        //				les legendes des courbes
        // Action : Initialise les donnees qui peuvent etre initialisees
        // Renvoie :
        public Curves(double valMin,
                                   double valMax,
                                   int nbCourbes,
                                   String legAbs,
                                   String legOrd,
                                   String[] legCour){
                valLimMinDep = valMin;
                valLimMaxDep = valMax;
                nombreCourbes = nbCourbes;
                dernierDessine = new Point[nbCourbes];
                legendeAbs = legAbs;
                legendeOrd = legOrd;
                legendeCourbes = legCour;
        }	// Fin de Courbes()

        //-----------------------
        // Les methodes publiques
        //-----------------------

        // initialisation
        //************************************************************************************
        // parametres :
        // Action : initialise tous les parametres necessaires au dessin et dessine les axes
        //			doit etre appele des que l'objet de la classe Courbes a ete insere dans
        //			    un "conteneur" et que celui ci est visible (avant on ne peut connaitre
        //			    la taille dont disposera l'objet)
        // Renvoie :
        public void initialisation(){

                nMax = size().width - (MARGE_GAUCHE + MARGE_DROITE);
                valLimMin = valLimMinDep;
                valLimMax = valLimMaxDep;
                listePoints = new Vector(nMax);

                initialise = true;
                miseAJour();

                paint(getGraphics());

        }	// Fin de initialisation

        // nouveauPoint
        //************************************************************************************
        // parametres : les valeurs de chaque courbe pour le nouveau point
        // Action : ajoute les nouveaux points aux graphiques
        //			redessine toutes les courbes si un point sort du dessin
        // Renvoie :
        public void nouveauPoint(double[] valeurs){
                EnsembleValeurs	aAjouter = new EnsembleValeurs(nombreCourbes);
                int xClip;

                for(int i=0; i < nombreCourbes; i++)
                        aAjouter.tableau[i] = valeurs[i];
                listePoints.addElement(aAjouter);
                if(listePoints.size() > nMax){
                        nMax = nMax * 4 / 3;
                        miseAJour();
                }
                else{
                        dessinePoint(listePoints.size()-1);
                        xClip = retourneX(listePoints.size()-1);
                        // les deux setClip sont supprimes car ils ne sont pas supportes par
                        // les browsers
                        // getGraphics().setClip(xClip-1,0,xClip+1,size().height);
                        paint(getGraphics());
                        // getGraphics().setClip(0,0,size().width,size().height);
                }
        }	// Fin de nouveauPoint


        // paint
        //************************************************************************************
        // parametres : l'environement graphique
        // Action : affiche a l'ecran le dessin qui se trouve sur l'image cachee
        // Renvoie :
        public void paint(Graphics gr){
                if((initialise) && (gr != null) && (imageCachee != null)){
                        gr.drawImage(imageCachee,0,0,this);
                }
        }	// Fin de paint

        //-----------------------
        // Les methodes privees
        //-----------------------

        // miseAjour
        //************************************************************************************
        // parametres :
        // Action : met a jour tous les parametres du dessin (dans le cas ou le dessin est
        //			redimensionne par exemple) et redessine le dessin
        // Renvoie :
        private void miseAJour(){
                if(!initialise)
                        return;

                imageCachee = createImage(size().width,size().height);
                gCachee = imageCachee.getGraphics();

                pasN = nMax /(size().width - (MARGE_GAUCHE + MARGE_DROITE));
                coefMulX = ((double)(size().width - (MARGE_GAUCHE + MARGE_DROITE))) / nMax;
                coefMulY = (size().height - (MARGE_HAUT + MARGE_BAS)) / (valLimMax - valLimMin);
                baseY = (int)(MARGE_HAUT + coefMulY * valLimMax);
                for(int i=0; i < nombreCourbes; i++)
                        dernierDessine[i] = new Point(MARGE_GAUCHE,baseY);

                dessineRepere();
                redessinePoints();
                paint(getGraphics());

        }	// Fin de miseAJour

        // retourneX
        //************************************************************************************
        // parametres : le rang du point
        // Action : renvoie l'abscisse de la fenetre graphique ou le point doit etre dessine
        // Renvoie :
        private int retourneX(int n){
                return (int)(MARGE_GAUCHE + coefMulX * n);
        }	// Fin de retourneX

        // retourneY
        //************************************************************************************
        // parametres : la valeur du point considere
        // Action : renvoie l'ordonnee de la fentre graphique ou le point doit etre dessine
        // Renvoie :
        private int retourneY(double val){
                return (int)Math.floor(baseY - coefMulY * val);
        }	// Fin de retourneY

        // dessinePoint
        //************************************************************************************
        // parametres : l'index du point dans la structure de sauvegarde des points
        // Action : dessine le point
        // Renvoie :
        private void dessinePoint(int index){
                Point aDessiner;
                double lesValeurs[] = ((EnsembleValeurs)listePoints.elementAt(index)).tableau;

                for(int i=0; i < nombreCourbes; i++) {
                        if(lesValeurs[i] > valLimMax){
                                valLimMax = lesValeurs[i] + (lesValeurs[i] - valLimMin)/4;
                                miseAJour();
                                return;
                        }
                        if(lesValeurs[i] < valLimMin){
                                valLimMin = lesValeurs[i] - (valLimMax - lesValeurs[i])/4;
                                miseAJour();
                                return;
                        }
                        gCachee.setColor(COULEURS_COURBES[i]);
                        aDessiner = new Point(retourneX(index),retourneY(lesValeurs[i]));
                        gCachee.drawLine(dernierDessine[i].x,dernierDessine[i].y,
                                                         aDessiner.x,aDessiner.y);
                        dernierDessine[i] = aDessiner;
                }

        }	// Fin de dessinePoint

        // redessinePoints
        //************************************************************************************
        // parametres :
        // Action : redessine tous les points
        // Renvoie :
        private void redessinePoints(){
                for(int i=0; i < listePoints.size(); i+=pasN)
                        dessinePoint(i);
                paint(getGraphics());
        }	// Fin de redessinePoints



        // dessineRepere
        //************************************************************************************
        // parametres :
        // Action : dessine le repere et les legendes
        // Renvoie :
        private void dessineRepere(){
                FontMetrics fM = gCachee.getFontMetrics();

                gCachee.setColor(COULEUR_REPERE);
                // Axe des ordonnes
                gCachee.drawLine(MARGE_GAUCHE,size().height-MARGE_REP_BAS,
                                                 MARGE_GAUCHE,MARGE_REP_HAUT);
                gCachee.drawLine(MARGE_GAUCHE-5,MARGE_REP_HAUT+5,
                                                 MARGE_GAUCHE,MARGE_REP_HAUT);
                gCachee.drawLine(MARGE_GAUCHE+5,MARGE_REP_HAUT+5,
                                                 MARGE_GAUCHE,MARGE_REP_HAUT);
                // Graduation Axe des ordonnees
                int [] grad;
                double valGradY;
                grad = val_E_puis(PAS_GRAD_Y / coefMulY);
                valGradY =  grad[0] * Math.pow(10,grad[1]);
                for(int i=(int)(valLimMin/valGradY); valGradY * i < valLimMax;i++){
                        int y = retourneY(valGradY * i);
                        gCachee.drawLine(MARGE_GAUCHE-2,y,MARGE_GAUCHE+2,y);
                        gCachee.drawString(""+grad[0]*i,
                                                                MARGE_GAUCHE-3- fM.stringWidth(""+grad[0]*i),
                                                                y+fM.getDescent());
                }
              /*  gCachee.drawString(legendeOrd+"(10^"+grad[1]+")",
                                                        2,MARGE_REP_HAUT-2);*/
                gCachee.drawString("",
                        2,MARGE_REP_HAUT-2);
                // Axe des abcisses
                gCachee.drawLine(MARGE_GAUCHE,baseY,
                                                 size().width-MARGE_REP_DROITE,baseY);
                gCachee.drawLine(size().width-MARGE_REP_DROITE-5,baseY-5,
                                                 size().width-MARGE_REP_DROITE,baseY);
                gCachee.drawLine(size().width-MARGE_REP_DROITE-5,baseY+5,
                                                 size().width-MARGE_REP_DROITE,baseY);
                // Graduation Axe des abcisses
                int valGradX;
                grad = val_E_puis(PAS_GRAD_X / coefMulX);
                valGradX =  grad[0] * (int)Math.pow(10,grad[1]);
                for(int i=1; valGradX * i < nMax;i++){
                        int x = retourneX(valGradX * i);
                        gCachee.drawLine(x,baseY-2,x,baseY+2);
                        gCachee.drawString(""+valGradX * i,
                                                                x-fM.stringWidth(""+valGradX * i)/2,
                                                                baseY+3+fM.getHeight());
                }
                gCachee.drawString(legendeAbs,
                                                        size().width- 2 - fM.stringWidth(legendeAbs), baseY-5);
                // Legende
                for(int i=0; i < nombreCourbes; i++){
                        int y = LEG_Y + fM.getHeight() * (i+1);
                        gCachee.setColor(COULEURS_COURBES[i]);
                        gCachee.drawLine(LEG_X,y-fM.getHeight()/2,LEG_X+10,y-fM.getHeight()/2);
                        gCachee.setColor(Color.black);
                        gCachee.drawString(legendeCourbes[i],LEG_X+12,y);
                }

        }	// Fin de dessineRepere

        // val_E_puis
        //************************************************************************************
        // parametres : une valeur
        // Action :
        // Renvoie : la mantisse et l'exposant du nombre
        private int[] val_E_puis(double val){
                int[] sol = new int[2];

                sol[1] = (int) Math.floor(Math.log(val) / Math.log(10));
                sol[0] = (int) Math.floor(val / Math.pow(10,sol[1]));
                return sol;
        }	// Fin de val_E_puis


        //------------------------------------------------------------------------------
        // Les methodes publiques heritees et appelees lorsque l'objet est redimensionne
        //------------------------------------------------------------------------------

                // Ne sachant pas exactement laquelle des methodes qui suivent etait appelee
                // lors d'un redimensionnement du dessin, je les ai toutes mises
                // Cela garantie au moins que cette classe est compatible avec toutes les
                //	versions de Java


        // resize
        //************************************************************************************
        // parametres : les nouvelles dimension du dessin
        // Action :  redimensionne le dessin et redessine tout en fonction de la nouvelle
        //			 dimension
        // Renvoie :
        public void resize(Dimension dim){
                super.resize(dim);
                miseAJour();
        }	// Fin de resize(Dimension)

        // resize
        //************************************************************************************
        // parametres : les nouvelles dimension du dessin
        // Action :  redimensionne le dessin et redessine tout en fonction de la nouvelle
        //			 dimension
        // Renvoie :
        public void resize(int largeur,int hauteur){
                resize( new Dimension(largeur,hauteur));
        }	// Fin de resize (int, int)

        // setSize
        //************************************************************************************
        // parametres : les nouvelles dimension du dessin
        // Action :  redimensionne le dessin et redessine tout en fonction de la nouvelle
        //			 dimension
        // Renvoie :
        public void setSize(Dimension dim){
                resize(dim);
        }	// Fin de setSize(Dimension)

        // setSize
        //************************************************************************************
        // parametres : les nouvelles dimension du dessin
        // Action :  redimensionne le dessin et redessine tout en fonction de la nouvelle
        //			 dimension
        // Renvoie :
       /* public void setSize(int l, int h){
                resize(l,h);
        }	// Fin de setSize(in, int)
*/
        // reshape
        //************************************************************************************
        // parametres : les nouvelles dimension du dessin
        // Action :  redimensionne le dessin et redessine tout en fonction de la nouvelle
        //			 dimension
        // Renvoie :
        public void reshape(int x, int y, int l, int h){
                super.reshape(x,y,l,h);
                miseAJour();
        }	// Fin de de reshape


        // setBounds
        //************************************************************************************
        // parametres : les nouvelles dimension du dessin
        // Action :  redimensionne le dessin et redessine tout en fonction de la nouvelle
        //			 dimension
        // Renvoie :
        public void setBounds(int x, int y, int l, int h){
                reshape(x,y,l,h);
        }	// Fin de setBounds(int,int,int,int)


        // setBounds
        //************************************************************************************
        // parametres : les nouvelles dimension du dessin
        // Action :  redimensionne le dessin et redessine tout en fonction de la nouvelle
        //			 dimension
        // Renvoie :
        public void setBounds(Rectangle lim){
                reshape(lim.x, lim.y, lim.width, lim.height);
        }	// Fin de setBounds(Rectangle)


}	// Fin de la classe Courbes


// Definition de la classe EnsembleValeurs
class EnsembleValeurs {

//--------------------------------------------------------------------------------------
//
//  Donnees
//
//--------------------------------------------------------------------------------------
        public double[] tableau;

//------------------------------------------------------------------------------------
//
// 		Les Methodes
//
//------------------------------------------------------------------------------------

        //-------------
        // Constructeur
        //-------------

        // EnsembleValeur
        //************************************************************************************
        // parametres : le nombre d'element que comportera cet ensemble de valeurs
        // Action : initialise le tableau ou sont rangees les valeurs
        // Renvoie :
        public EnsembleValeurs(int nombre){
                tableau = new double[nombre];
        }	// Fin de ensembleValeurs(int)


}	// Fin de la classe ensembleValeurs
