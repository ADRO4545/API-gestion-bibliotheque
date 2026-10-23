import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientBibliotheque {

	private static final String BASE_URL = "http://localhost:8080/api/v1";

	private static final HttpClient client = HttpClient.newHttpClient();

	private static int nombreEtapes = 0;
	private static int nombreConformes = 0;



	private static HttpResponse<String> get(String chemin) throws Exception {
		HttpRequest requete = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + chemin))
				.header("Accept", "application/json")
				.GET()
				.build();
		return client.send(requete, HttpResponse.BodyHandlers.ofString());
	}

	private static HttpResponse<String> post(String chemin, String corpsJson) throws Exception {
		HttpRequest requete = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + chemin))
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(corpsJson))
				.build();
		return client.send(requete, HttpResponse.BodyHandlers.ofString());
	}

	private static HttpResponse<String> patch(String chemin) throws Exception {
		HttpRequest requete = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + chemin))
				.header("Accept", "application/json")
				.method("PATCH", HttpRequest.BodyPublishers.noBody())
				.build();
		return client.send(requete, HttpResponse.BodyHandlers.ofString());
	}

	private static HttpResponse<String> delete(String chemin) throws Exception {
		HttpRequest requete = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + chemin))
				.DELETE()
				.build();
		return client.send(requete, HttpResponse.BodyHandlers.ofString());
	}



	private static void afficher(String description, String methode, String chemin,
								 HttpResponse<String> reponse, int codeAttendu) {
		nombreEtapes++;
		int codeRecu = reponse.statusCode();
		String verdict;
		if (codeRecu == codeAttendu) {
			nombreConformes++;
			verdict = "CONFORME";
		} else {
			verdict = "NON CONFORME";
		}
		System.out.println("=== Étape " + nombreEtapes + " : " + description + " ===");
		System.out.println(methode + " " + chemin + " → " + codeRecu + " (attendu : " + codeAttendu + ") " + verdict);
		System.out.println("Réponse : " + reponse.body());
		System.out.println();
	}



	public static void main(String[] args) throws Exception {


		HttpResponse<String> reponse = get("/livres/disponibles?size=3");
		afficher("Lister les 3 premiers livres disponibles", "GET", "/livres/disponibles?size=3", reponse, 200);


		reponse = get("/livres/99");
		afficher("Lire un livre inexistant", "GET", "/livres/99", reponse, 404);


		String corpsAuteur = """
                {"nom": "Verne", "prenom": "Jules", "nationalite": "Française"}
                """;
		reponse = post("/auteurs", corpsAuteur);
		afficher("Créer l'auteur Jules Verne", "POST", "/auteurs", reponse, 201);


		String corpsEmpruntLivre2 = """
                {"livreId": 2, "emprunteur": "Alice Durand", "emailEmprunteur": "alice.durand@email.com"}
                """;
		reponse = post("/emprunts", corpsEmpruntLivre2);
		afficher("Emprunter le livre 2", "POST", "/emprunts", reponse, 201);


		String corpsEmpruntLivre8 = """
                {"livreId": 8, "emprunteur": "Alice Durand", "emailEmprunteur": "alice.durand@email.com"}
                """;
		reponse = post("/emprunts", corpsEmpruntLivre8);
		afficher("Emprunter le livre 8 (stock à 0)", "POST", "/emprunts", reponse, 409);


		reponse = patch("/emprunts/2/retour");
		afficher("Rendre l'emprunt 2", "PATCH", "/emprunts/2/retour", reponse, 200);


		reponse = patch("/emprunts/2/retour");
		afficher("Rendre l'emprunt 2 une deuxième fois", "PATCH", "/emprunts/2/retour", reponse, 409);


		reponse = delete("/auteurs/1");
		afficher("Supprimer l'auteur 1 (il a des livres)", "DELETE", "/auteurs/1", reponse, 409);


		String corpsLivreInvalide = """
                {"titre": "", "isbn": "978-2-0000-0000-0", "prix": -5, "stock": 1, "auteurId": 1}
                """;
		reponse = post("/livres", corpsLivreInvalide);
		afficher("Créer un livre invalide (titre vide, prix négatif)", "POST", "/livres", reponse, 400);


		reponse = delete("/auteurs/5");
		afficher("Supprimer l'auteur 5 (aucun livre)", "DELETE", "/auteurs/5", reponse, 204);


		System.out.println("Bilan : " + nombreConformes + "/" + nombreEtapes + " étapes conformes");
	}
}