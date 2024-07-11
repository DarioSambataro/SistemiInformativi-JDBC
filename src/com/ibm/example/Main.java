package com.ibm.example;

public class Main {

	public static void main(String[] args) {
		
		Database db = new Database("test_jdbc");
		db.databaseCreation(db.getDatabaseName());
		
		String query = "USE " + db.getDatabaseName() + ";";
		db.NoResultSetQuery(query);
		
		query = "CREATE TABLE IF NOT EXISTS utenti("
				+ "id INTEGER UNSIGNED NOT NULL PRIMARY KEY,"
				+ "nome VARCHAR(255),"
				+ "cognome VARCHAR(255)"
				+ ");";
		db.NoResultSetQuery(query);
		
		
		query = "CREATE TABLE IF NOT EXISTS libri("
				+ "id INTEGER UNSIGNED NOT NULL PRIMARY KEY,"
				+ "titolo VARCHAR(255),"
				+ "autore VARCHAR(255)"
				+ ");";
		db.NoResultSetQuery(query);
		
		query = "CREATE TABLE IF NOT EXISTS prestito("
				+ "id INTEGER UNSIGNED NOT NULL PRIMARY KEY,"
				+ "inizio DATE,"
				+ "fine DATE,"
				+ "id_utente INTEGER UNSIGNED NOT NULL REFERENCES utenti(id),"
				+ "id_libro INTEGER UNSIGNED NOT NULL REFERENCES libri(id)"
				+ ");";
		db.NoResultSetQuery(query);
		
		/*query = "INSERT INTO utenti (id, nome, cognome) VALUES (11, 'Mario', 'Rossi'),"
				+ "(12, 'Andrea', 'Verdi'),"
				+ "(13, 'Massimo', 'Bianchi'),"
				+ "(14, 'Sara', 'Vallieri'),"
				+ "(15, 'Marco', 'Graviglia'),"
				+ "(16, 'Marzia', 'Esposito');";
		
		db.NoResultSetQuery(query);*/
		query = "SELECT libri.titolo "
				+ "FROM libri INNER JOIN prestito "
				+ "ON libri.id = prestito.id_libro "
				+ "INNER JOIN utenti "
				+ "ON prestito.id_utente = utenti.id "
				+ "WHERE utenti.cognome = 'Vallieri' "
				+ "ORDER BY inizio;";
		
		db.firstQuery(query);
		System.out.println("--------------------------------------------------------");
		db.secondQuery();
		System.out.println("--------------------------------------------------------");
		db.thirdQuery();
		System.out.println("--------------------------------------------------------");
		db.fourthQuery("Sara Vallieri");
		System.out.println("--------------------------------------------------------");
		db.fifthQuery();
		System.out.println("--------------------------------------------------------");
		db.sixthQuery();
	}
}
