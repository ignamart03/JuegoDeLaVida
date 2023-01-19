package juegoDeLaVida;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase juegoDeLaVida. Este es un juego de tablero con reglas b�sicas de matrices con celdas que siguen un patr�n.
 * @author Ignacio Mart�nez Casas
 *
 */
public class juegoDeLaVida {

	public static int dimensionMatriz = 50;
	public static int [][] matriz = new int [dimensionMatriz][dimensionMatriz];
	public static int rondas = 20;
	public static int delay = 250;
	
	/**
	 * M�todo lectorMatriz para leer el documento de la matriz. M�s tarde hace una copia de la misma matriz y la almacena en la variable 'matriz'.
	 * @param path
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void lectorMatriz(String path) throws NumberFormatException, IOException {

		//Iniciamos el FileReader con la ruta indicada pr�ximamente en el main.
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			String linea;
			String[] fila;
			int numFila = 0;

			while ((linea = br.readLine()) != null) {

				fila = linea.split(",");
				for (int i = 0; i < dimensionMatriz; i++) {
					if (i < fila.length) {
						matriz[numFila][i] = Integer.parseInt(fila[i].trim());
					} else {
						matriz[numFila][i] = 0;
					}

				} 
				numFila++;

			} 
			
			if (numFila < dimensionMatriz) {
				for (int i = numFila; i < dimensionMatriz; i++) {
					for (int j = 0; j < dimensionMatriz; j++) {
						matriz[i][j] = 0;
					}

				}
			} 
			fr.close();

	}
	/**
	 * M�todo que ense�a el tablero con la matriz del principio con ceros (muerta) y unos (viva).
	 */
	public static void enseñarTablero() {

		for (int i = 0; i < dimensionMatriz; i++) {
			for (int j = 0; j < dimensionMatriz; j++) {
				if (matriz[i][j] == 1) {
					System.out.print(" �");
				} else {
					System.out.print(" -");
				}
			}
			System.out.println();
		}
		

	}

	/**
	 * M�todo que devuelve el n�mero de c�lulas vivas adyacentes a una celda dada.
	 * @param fila
	 * @param columna
	 * @return
	 */
	public static int vivasCerca(int fila, int columna) {

		int vivas = 0;
		int startFila;
		int endFila;
		int startcolumn;
		int finalcolumn;

		if (fila == 0) {
			startFila = 0;
			endFila = 1;
		} else if (fila == (dimensionMatriz - 1)) {
			startFila = dimensionMatriz - 2;
			endFila = dimensionMatriz - 1;
		} else {
			startFila = fila - 1;
			endFila = fila + 1;
		}

		if (columna == 0) {
			startcolumn = 0;
			finalcolumn = 1;
		} else if (columna == (dimensionMatriz - 1)) {
			startcolumn = dimensionMatriz - 2;
			finalcolumn = dimensionMatriz - 1;
		} else {
			startcolumn = columna - 1;
			finalcolumn = columna + 1;
		}

		for (int i = startFila; i <= endFila; i++) {
			for (int j = startcolumn; j <= finalcolumn; j++) {
				if (!(i == fila && j == columna) && matriz[i][j] == 1) {
					vivas++;
				}
			}

		}

		return vivas;

	}
	
	static final String SEPARADOR = ",";
	
	/**
	 * M�todo escribirFichero para sacar el �ltimo tablero como fichero .csv
	 * @param matriz
	 * @param finalPath
	 * @return
	 */
	static boolean escribirFichero(int[][] matriz, String finalPath) {
        String linea;
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(finalPath))) {
            for (int i = 0; i < matriz.length; i++) {
                linea = "";
                for (int k = 0; k < matriz[i].length - 1; k++) {
                    linea = linea + Integer.toString(matriz[i][k]) + SEPARADOR;
                }
                linea = linea + Integer.toString(matriz[i][matriz[i].length - 1]);
                escritor.write(linea);
                if (i != matriz.length - 1) {
                    escritor.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Se produjo el siguiente error al acceder al fichero \n" + e.getMessage());
            return false;
        }
    }
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String path = "D:\\Estudios\\FP\\Programacion\\juegoDeLaVida\\juegoDeLaVidaTableroInicial.csv";
		String finalPath = "D:\\Estudios\\FP\\Programacion\\juegoDeLaVida\\juegoDeLaVidaTableroFinal.csv";
		lectorMatriz(path);


		for (int i = 0; i < rondas ; i++) {
		
			System.out.println("Ronda" + " " + (i+1));
			enseñarTablero();
			Thread.sleep(delay);
			
			//Corremos la siguiente ronda y la guardamos en 'matriz'.
			if (i == rondas - 1 ) {
				escribirFichero(matriz, finalPath);
			}
			matriz = nextRound (matriz);

			
		}
		
		//escribirFichero(matriz, finalPath);

	}
	
	/**
	 * M�todo que ejecuta el juego de la vida y avanza rondas autom�ticamente. Devuelve el �ltimo tablero.
	 * @param rondas
	 * @return
	 * @throws InterruptedException
	 */
	public static int[][] nextRound (int[][] rondas) throws InterruptedException {

		int [][] tableroFinal= new int[dimensionMatriz][dimensionMatriz];
		for (int i = 0; i < dimensionMatriz ; i++) {
			for (int j = 0; j < dimensionMatriz ; j++) {
				//Sentencia if para si la celula esta viva
				if (rondas[i][j] == 1) {
					if (vivasCerca(i, j) < 2) {
						tableroFinal[i][j] = 0;
					} else if (vivasCerca(i, j) < 4) {
						tableroFinal[i][j] = 1;
					} else {
						tableroFinal[i][j] = 0;
					}
				//Sentencia �else en caso de que la celula no este viva.
				} else {
					if (vivasCerca(i, j) == 3) {
						tableroFinal[i][j] = 1;
					}
				}
			}
		}
		
		return tableroFinal;
	}
	
}

