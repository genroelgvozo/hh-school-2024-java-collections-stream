package tasks;

import common.Company;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
Из коллекции компаний необходимо получить всевозможные различные названия вакансий
 */
public class Task7 {

  public static Set<String> vacancyNames(Collection<Company> companies) {
    return companies.stream()
            .flatMap( // делаем вложенный поток по вакансиям компаний
                    company -> company.getVacancies().stream()
                            .map(vacancy -> vacancy.getTitle())) // получаем названия вакансий в компании
            .collect(Collectors.toSet()); // собираем в set
  }

}
