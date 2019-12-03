# ohtu-miniprojekti ![](https://github.com/iidxTe/ohtu-miniprojekti/workflows/Java%20CI/badge.svg)

Projektin tarkoituksena on harjoitella ketterien ohjelmistotuotantoprosessien ja scrum-menetelmien käyttöä. 

Projektissa toteutetaan lukuvinkkikirjasto. Lukuvinkkikirjaston tarkoituksena on toimia käyttäjän henkilökohtaisena lukuvinkkien tallennuspaikkana. Tallennettujen lukuvinkkien selailun lisäksi käyttäjä voi merkata lukemansa kirjan luetuksi. Lukuvinkkikirjasto vaatii rekisteröitymisen palvelun käyttäjäksi. 

Sovellusta voi käyttää suoraan selaimella ositteessa: https://limitless-lowlands-09269.herokuapp.com/.

Vaihtoehtoisesti voit ladata ohjeman koneellesi kloonaamalla projektin osoitteesta https://github.com/iidxTe/ohtu-miniprojekti.git. Kloonattuasi projektin voit suorittaa komennon "./gradlew bootrun" projetin juurikansiossa ja tämän jälkeen käyttää sovellusta osoitteessa localhost:8080.

Toimiakseen ohjelma vaatii että koneellesi on asennettuna Java8.

Testit voit suorittaa komennolla "./gradlew test jacocoTestReport".


[ProductBacklog](https://docs.google.com/spreadsheets/d/1jcgyrBhQjKcOjReRpKeF86ApAhfezBlr4MvJ3JAZQGc/edit?usp=sharing)


## Definition of Done
* Story has acceptance criteria, that is also documented with Cucumber and is
automatically tested
* Line coverage of added code is at least 70%
* CI passes all tests
* Feature is usable on production (which may be local computer)
* Code quality is acceptable
  * Good naming
  * Modular architecture
  * Consistent code style
