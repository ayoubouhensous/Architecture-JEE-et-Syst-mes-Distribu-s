## TP : Injection des Dépendances en Java

### Objectif

Ce TP a pour but d'explorer les concepts d'injection de dépendances en Java, en passant par différentes approches :

- Instanciation statique

- Instanciation dynamique

- Utilisation du framework Spring (XML et annotations)

### Structure du TP

#### 1- Création des interfaces et implémentations

a) Interface IDao

Définition d'une interface IDao avec une méthode getData().

```java
package dao;

public interface Idao {
    double getData();
}
```

b) Implémentation de IDao

Création d'une classe qui implémente l'interface IDao.

```java
package dao;
public class DaoImpl implements Idao{

    @Override
    public double getData() {
        System.out.println("Version base de donnes ");
        double temp = 10;
        return temp;
    }
}

```

c) Interface IMetier

Définition d'une interface IMetier avec une méthode calcul().

```java
package metier;

public interface IMetier {
    double calculer();
}


```

d) Implémentation de IMetier avec un couplage faible

Création d'une classe MetierImpl qui dépend de IDao sans instanciation directe (utilisation d'un setter ou d'un constructeur).

```java
package metier;

import dao.Idao;

public class ImetierImpl implements IMetier{

    private Idao dao;



    public ImetierImpl(Idao dao) {
        this.dao = dao;
    }
    @Override
    public double calculer() {

        double t =dao.getData();
        double resultado = t*23;

        return resultado;
    }

    public void setDao(Idao dao) {
        this.dao = dao;
    }
}



```

#### 2- Injection des dépendances

a) Par instanciation statique

Dans cette approche, nous instancions directement IDao dans MetierImpl en affectant l'objet à une variable.
```java
package presentation;

import dao.DaoImpl;
import ext.DaoImplV2;
import metier.ImetierImpl;

public class PresentationV1 {

    public static void main(String[] args) {

        // Injection de dépendance par instanciation statique
        DaoImpl dao = new DaoImpl();
        ImetierImpl imetier = new ImetierImpl();
        imetier.setDao(dao); // via setter
        System.out.println(imetier.calculer());

        // Injection de dépendance via constructeur
        DaoImplV2 daoV2 = new DaoImplV2();
        ImetierImpl imetierV2 = new ImetierImpl(daoV2);
        System.out.println(imetierV2.calculer());
    }
}


```

b) Par instanciation dynamique

Utilisation de la réflexion (Class.forName()) pour charger dynamiquement l'implémentation de IDao.

```java
package presentation;

import dao.Idao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class PresentationV2 {
    public static void main(String[] args) {

        try {

            Scanner sc = new Scanner(new File("config.txt"));
            String daoClasseName = sc.nextLine();

            Class cdao = Class.forName(daoClasseName);
            Idao idao = (Idao) cdao.getDeclaredConstructor().newInstance();

            String metierClassName = sc.nextLine();

            Class cmetier=Class.forName(metierClassName);
            IMetier metier=(IMetier) cmetier.getDeclaredConstructor().newInstance();

            Method setDao=cmetier.getDeclaredMethod("setDao",Idao.class);
            setDao.invoke(metier,idao);

            System.out.println(metier.calculer());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

c) En utilisant le framework Spring

- Version XML

Déclaration des beans et injection des dépendances via un fichier de configuration XML.

exemple de fichier config.xml 

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dao" class="dao.DaoImpl"></bean>
    <bean id="metier" class="metier.ImetierImpl">
        <property name="dao" ref="dao"></property>
    </bean>

</beans>


```
#### 1. Déclaration des Beans:

<bean id="dao" class="dao.DaoImpl">: Cette ligne définit un bean avec l'identifiant dao qui utilise la classe DaoImpl. C'est l'implémentation concrète de l'interface IDao.
<bean id="metier" class="metier.ImetierImpl">: Cette ligne définit un bean avec l'identifiant metier qui utilise la classe ImetierImpl. C'est l'implémentation concrète de l'interface IMetier.

#### 2. Injection des Dépendances:

<property name="dao" ref="dao"></property>: Cette ligne injecte le bean dao dans le bean metier en utilisant la méthode setter correspondante. Cela permet à metier d'utiliser l'implémentation de IDao sans avoir besoin de l'instancier directement.


##### Code de Présentation avec Spring
```java
package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresentationAvecSpringXML {

    public static void main(String[] args) {
        // Chargement du contexte Spring à partir du fichier de configuration XML
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");

        // Récupération du bean 'metier'
        IMetier metier = (IMetier) context.getBean("metier");

        // Appel de la méthode calculer et affichage du résultat
        System.out.println(metier.calculer());
    }
}


```
- Version annotations

Utilisation des annotations Spring comme @Component, @Autowired, et @Configuration pour gérer l'injection des dépendances.

```java
package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresentationAvecSpringAnnotation {

    public static void main(String[] args) {
        // Chargement du contexte Spring avec les configurations basées sur les annotations
        ApplicationContext context = new AnnotationConfigApplicationContext("ext", "metier", "dao");

        // Récupération du bean 'metier'
        IMetier metier = (IMetier) context.getBean("metier");

        // Appel de la méthode calculer et affichage du résultat
        System.out.println(metier.calculer());
    }
}
```

 ### Classes de Service et de DAO
```java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("metier")
public class ImetierImpl implements IMetier {
    private Idao dao;

    public ImetierImpl(@Qualifier("dao2") Idao dao) {
        this.dao = dao; // Injection via constructeur
    }

    @Override
    public int calculer() {
        // Logique de calcul utilisant l'objet dao
        return dao.getData(); // Exemple d'utilisation de dao
    }
}

```

### Implémentation de IDao

```java
import org.springframework.stereotype.Repository;

@Repository("dao1")
public class DaoImpl implements Idao {
    @Override
    public int getData() {
        // Retourne des données
        return 42; // Exemple de valeur
    }
}

@Component("dao2")
public class DaoImplV2 implements Idao {
    @Override
    public int getData() {
        // Retourne des données
        return 100; // Exemple de valeur différente
    }
}


```
#### 1.PresentationAvecSpringAnnotation:

- Utilise AnnotationConfigApplicationContext pour charger le contexte Spring en se basant sur les packages spécifiés (ext, metier, dao).
- Récupère le bean metier et appelle la méthode calculer.

#### 2.ImetierImpl:

- Annoté avec @Service, ce qui indique à Spring que c'est un service et qu'il doit être géré comme un bean.
- Le constructeur utilise @Autowired pour indiquer que la dépendance dao doit être injectée automatiquement. Le @Qualifier("dao2") permet de spécifier quelle implémentation de IDao doit être injectée (dans ce cas, DaoImplV2).



#### 3.DaoImpl et DaoImplV2:

- Annotés avec @Repository et @Component, respectivement, pour indiquer à Spring qu'ils sont des composants gérés.
- Ces classes implémentent IDao et fournissent des méthodes pour récupérer des données.




### Conclusion


Ce TP a permis d'explorer en profondeur les concepts d'injection des dépendances en Java, en illustrant les avantages d'une conception orientée interface. Nous avons examiné différentes méthodes d'injection, notamment l'instanciation statique et dynamique, ainsi que l'utilisation du framework Spring à travers des configurations XML et des annotations.


