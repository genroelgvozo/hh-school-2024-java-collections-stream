package tasks;

import common.Company;
import common.Vacancy;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  public static Set<String> vacancyNames(Collection<Company> companies) {
    System.out.println(companies.toString());
    Set<String> vacancyNames = new HashSet<>();
    for (Company company : companies) {
      Set<Vacancy> vacancies = company.getVacancies();
      for (Vacancy vacancy : vacancies) {
        vacancyNames.add(vacancy.getTitle());
      }
    }
    return vacancyNames;
  }

}
