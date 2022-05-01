package juegoDeLaVida;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase juegoDeLaVida. Este es un juego de tablero con reglas básicas de matrices con celdas que siguen un patrón.
 * @author Ignacio Martínez Casas
 *
 */
public class juegoDeLaVida {

	public static int dimensionMatriz = 50;
	public static int [][] matriz = new int [dimensionMatriz][dimensionMatriz];
	public static int rondas = 40;
	public static int delay = 2000;
	
	/**
	 * Método lectorMatriz para leer el documento de la matriz. Más tarde hace una copia de la misma matriz y la almacena en la variable 'matriz'.
	 * @param path
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void lectorMatriz(String path) throws NumberFormatException, IOException {

		//Iniciamos el FileReader con la ruta indicada próximamente en el main.
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
	 * Método que enseña el tablero con la matriz del principio con ceros (muerta) y unos (viva).
	 */
	public static void enseñarTablero() {

		for (int i = 0; i < dimensionMatriz; i++) {
			for (int j = 0; j < dimensionMatriz; j++) {
				if (matriz[i][j] == 1) {
					System.out.print(" º");
				} else {
					System.out.print(" -");
				}
			}
			System.out.println();
		}
		

	}

	/**
	 * Método que devuelve el número de células vivas adyacentes a una celda dada.
	 * @param fila
	 * @param columna
	 * @return
	 */
	public static int vivasCerca(int fila, int columna) {

		int numVivas = 0;

		int comienzoFila, finalFila, comienzoCol, finalCol;

		if (fila == 0) {
			comienzoFila = 0;
			finalFila = 1;
		} else if (fila == (dimensionMatriz - 1)) {
			comienzoFila = dimensionMatriz - 2;
			finalFila = dimensionMatriz - 1;
		} else {
			comienzoFila = fila - 1;
			finalFila = fila + 1;
		}

		if (columna == 0) {
			comienzoCol = 0;
			finalCol = 1;
		} else if (columna == (dimensionMatriz - 1)) {
			comienzoCol = dimensionMatriz - 2;
			finalCol = dimensionMatriz - 1;
		} else {
			comienzoCol = columna - 1;
			finalCol = columna + 1;
		}

		for (int i = comienzoFila; i <= finalFila; i++) {
			for (int j = comienzoCol; j <= finalCol; j++) {
				if (!(i == fila && j == columna) && matriz[i][j] == 1) {
					numVivas++;
				}
			}

		}

		return numVivas;

	}
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		String path = "D:\\Estudios\\FP\\Programacion\\juegoDeLaVida\\juegoDeLaVidaTableroFinal.csv";
		lectorMatriz(path);


		for (int i = 0; i <rondas ; i++) {
		
			System.out.println("Ronda" + " " + (i+1));
			enseñarTablero();
			Thread.sleep(delay);
			
			//generamos la siguiente generación y la guardamos en el tablero
			matriz = nextRound (matriz);

			
		}

	}
	
	/**
	 * Método que ejecuta el juego de la vida y avanza rondas automáticamente. Devuelve el último tablero.
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
				//Sentencia ºelse en caso de que la celula no este viva.
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
